package tango.app;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Scanner;

import tango.io.BoardCatalog;
import tango.io.BoardInputValidator;
import tango.io.BoardPrinter;
import tango.io.InputGenerator;
import tango.io.InputReader;
import tango.model.Board;
import tango.model.CellValue;
import tango.model.Constraint;
import tango.model.ConstraintType;
import tango.model.Position;
import tango.solver.Solver;
import tango.solver.SolverFactory;
import tango.solver.SolverResult;
import tango.solver.SolverType;

/**
 * Fachada da aplicacao que integra interface, arquivos, validacao e solvers.
 */
public class TangoApplication {
    /** Le arquivos no formato textual do projeto. */
    private final InputReader inputReader;
    /** Produz quebra-cabecas aleatorios solucionaveis. */
    private final InputGenerator inputGenerator;
    /** Bloqueia entradas com violacoes ja determinadas. */
    private final BoardInputValidator inputValidator;
    /** Organiza os arquivos oficiais da interface. */
    private final BoardCatalog boardCatalog;
    /** Exibe grade e restricoes no terminal. */
    private final BoardPrinter boardPrinter;
    /** Cria a estrategia selecionada pelo usuario. */
    private final SolverFactory solverFactory;

    /**
     * Cria a aplicacao com as implementacoes padrao de todos os servicos.
     */
    public TangoApplication() {
        this.inputReader = new InputReader();
        this.inputGenerator = new InputGenerator();
        this.inputValidator = new BoardInputValidator();
        this.boardCatalog = new BoardCatalog();
        this.boardPrinter = new BoardPrinter();
        this.solverFactory = new SolverFactory();
    }

    /**
     * Mantem o menu ativo ate que o usuario escolha sair.
     *
     * <p>Erros de entrada sao exibidos sem encerrar a sessao.</p>
     */
    public void runInteractive() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> runSolverFromCatalog(scanner);
                    case "2" -> generateAutomaticBoard(scanner);
                    case "3" -> createManualBoard(scanner);
                    case "4" -> importExternalBoard(scanner);
                    case "5" -> tango.Main.printUsage();
                    case "0" -> running = false;
                    default -> System.out.println("Opcao invalida. Escolha uma opcao do menu.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Erro: " + exception.getMessage());
            } catch (IOException exception) {
                System.out.println("Erro ao acessar o catalogo: " + exception.getMessage());
            }

            if (running) {
                System.out.println();
            }
        }

        System.out.println("Programa encerrado.");
    }

    /**
     * Executa a aplicacao no modo de linha de comando.
     *
     * @param args caminho do arquivo e algoritmo opcional
     * @throws IllegalArgumentException se os argumentos ou a entrada forem invalidos
     */
    public void run(String[] args) {
        if (args.length == 0 || isHelp(args[0])) {
            tango.Main.printUsage();
            return;
        }

        if (args.length > 2) {
            throw new IllegalArgumentException("foram informados argumentos demais.");
        }

        Path inputPath = Path.of(args[0]);
        SolverType solverType = args.length == 2
                ? SolverType.fromArgument(args[1])
                : SolverType.BACKTRACKING;

        Board initialBoard = inputReader.read(inputPath);
        inputValidator.validate(initialBoard);
        solveAndPrint(initialBoard, solverType);
    }

    /** Executa uma estrategia e apresenta tabuleiro, resultado e estatistica. */
    private void solveAndPrint(Board initialBoard, SolverType solverType) {
        Solver solver = solverFactory.create(solverType);

        System.out.println("Tabuleiro inicial:");
        boardPrinter.print(initialBoard);
        System.out.println();
        System.out.println("Algoritmo selecionado: " + solverType.getArgumentName());
        System.out.println();

        SolverResult result = solver.solve(initialBoard);

        if (result.hasSolution()) {
            System.out.println("Tabuleiro resolvido:");
            boardPrinter.print(result.getSolvedBoard());
        } else {
            System.out.println("Nao foi encontrada solucao para o tabuleiro informado.");
        }

        System.out.println();
        System.out.println("Estados visitados: " + result.getVisitedStates());
    }

    /** Reconhece as duas formas aceitas para solicitar ajuda. */
    private boolean isHelp(String value) {
        return "--help".equals(value) || "-h".equals(value);
    }

    /** Imprime as operacoes disponiveis no modo interativo. */
    private void printMenu() {
        System.out.println("TP2 FPAA - Tango Solver");
        System.out.println("1 - Resolver tabuleiro do catalogo");
        System.out.println("2 - Gerar tabuleiro automatico");
        System.out.println("3 - Criar tabuleiro manualmente");
        System.out.println("4 - Importar arquivo externo");
        System.out.println("5 - Ver ajuda");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    /** Lista o catalogo e resolve o arquivo selecionado por numero. */
    private void runSolverFromCatalog(Scanner scanner) throws IOException {
        List<Path> files = boardCatalog.listFiles();
        if (files.isEmpty()) {
            System.out.println("O catalogo esta vazio. Gere, crie ou importe um tabuleiro primeiro.");
            return;
        }

        System.out.println("Tabuleiros disponiveis em " + boardCatalog.getDirectory() + ":");
        for (int index = 0; index < files.size(); index++) {
            System.out.println((index + 1) + " - " + files.get(index).getFileName());
        }
        System.out.println("0 - Voltar");
        System.out.print("Escolha o arquivo: ");

        int selectedIndex = readInteger(scanner.nextLine().trim(), "a escolha do arquivo deve ser um numero.");
        if (selectedIndex == 0) {
            return;
        }
        if (selectedIndex < 1 || selectedIndex > files.size()) {
            throw new IllegalArgumentException("arquivo inexistente na lista.");
        }

        SolverType solverType = readSolverType(scanner);
        Board board = inputReader.read(files.get(selectedIndex - 1));
        inputValidator.validate(board);
        solveAndPrint(board, solverType);
    }

    /** Solicita parametros, gera, valida e salva um tabuleiro aleatorio. */
    private void generateAutomaticBoard(Scanner scanner) throws IOException {
        System.out.print("Tamanho par do tabuleiro, por exemplo 4, 6 ou 8: ");
        int size = readInteger(scanner.nextLine().trim(), "o tamanho deve ser um numero inteiro.");

        int totalCells = size > 0 ? size * size : 0;
        System.out.print("Quantidade de dicas preenchidas, entre 0 e " + totalCells + ": ");
        int clueCount = readInteger(scanner.nextLine().trim(), "a quantidade de dicas deve ser um numero inteiro.");

        int maximumConstraints = size > 0 ? 2 * size * (size - 1) : 0;
        System.out.print("Quantidade de restricoes, entre 0 e " + maximumConstraints + ": ");
        int constraintCount = readInteger(
                scanner.nextLine().trim(),
                "a quantidade de restricoes deve ser um numero inteiro.");

        Board board = inputGenerator.generateRandomBoard(size, clueCount, constraintCount);
        inputValidator.validate(board);
        Path savedPath = boardCatalog.saveAutomatic(board);

        System.out.println("Tabuleiro automatico gerado e validado:");
        boardPrinter.print(board);
        System.out.println("Arquivo salvo em: " + savedPath.toAbsolutePath());
    }

    /** Monta um tabuleiro a partir de linhas e restricoes digitadas. */
    private void createManualBoard(Scanner scanner) throws IOException {
        System.out.print("Tamanho par do tabuleiro: ");
        int size = readInteger(scanner.nextLine().trim(), "o tamanho deve ser um numero inteiro.");
        Board board = new Board(size);

        System.out.println("Informe cada linha usando apenas '.', 'S' e 'L'.");
        for (int row = 0; row < size; row++) {
            System.out.print("Linha " + row + ": ");
            readManualRow(scanner.nextLine().trim(), board, row);
        }

        System.out.print("Quantidade de restricoes: ");
        int constraintCount = readInteger(
                scanner.nextLine().trim(),
                "a quantidade de restricoes deve ser um numero inteiro.");
        if (constraintCount < 0) {
            throw new IllegalArgumentException("a quantidade de restricoes nao pode ser negativa.");
        }

        System.out.println("Formato: linha1 coluna1 linha2 coluna2 =|x");
        for (int index = 0; index < constraintCount; index++) {
            System.out.print("Restricao " + (index + 1) + ": ");
            board.addConstraint(parseConstraint(scanner.nextLine().trim()));
        }

        inputValidator.validate(board);
        Path savedPath = boardCatalog.saveManual(board);
        System.out.println("Tabuleiro manual validado e salvo em: " + savedPath.toAbsolutePath());
    }

    /** Le um caminho externo e salva sua versao normalizada apos validacao. */
    private void importExternalBoard(Scanner scanner) throws IOException {
        System.out.print("Caminho do arquivo externo: ");
        Path sourcePath = Path.of(scanner.nextLine().trim());
        Board board = inputReader.read(sourcePath);
        inputValidator.validate(board);

        Path savedPath = boardCatalog.saveImported(board);
        System.out.println("Arquivo validado e importado para: " + savedPath.toAbsolutePath());
    }

    /** Converte a escolha numerica do menu em estrategia de busca. */
    private SolverType readSolverType(Scanner scanner) {
        System.out.println("Algoritmo:");
        System.out.println("1 - Backtracking");
        System.out.println("2 - Forca bruta");
        System.out.print("Escolha: ");
        String option = scanner.nextLine().trim();

        return switch (option) {
            case "1", "" -> SolverType.BACKTRACKING;
            case "2" -> SolverType.BRUTE_FORCE;
            default -> throw new IllegalArgumentException("opcao de algoritmo invalida.");
        };
    }

    /** Valida e grava uma linha informada na criacao manual. */
    private void readManualRow(String line, Board board, int row) {
        if (line.length() != board.getSize()) {
            throw new IllegalArgumentException(
                    "a linha " + row + " deve possuir exatamente " + board.getSize() + " simbolos.");
        }

        for (int column = 0; column < line.length(); column++) {
            board.setCell(new Position(row, column), CellValue.fromSymbol(line.charAt(column)));
        }
    }

    /** Converte a restricao digitada pela interface em objeto do modelo. */
    private Constraint parseConstraint(String line) {
        String[] parts = line.split("\\s+");
        if (parts.length != 5 || parts[4].length() != 1) {
            throw new IllegalArgumentException("restricao invalida: " + line);
        }

        Position first = new Position(
                readInteger(parts[0], "linha da primeira posicao invalida."),
                readInteger(parts[1], "coluna da primeira posicao invalida."));
        Position second = new Position(
                readInteger(parts[2], "linha da segunda posicao invalida."),
                readInteger(parts[3], "coluna da segunda posicao invalida."));
        return new Constraint(first, second, ConstraintType.fromSymbol(parts[4].charAt(0)));
    }

    /** Converte uma resposta numerica usando mensagem especifica do contexto. */
    private int readInteger(String value, String errorMessage) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
