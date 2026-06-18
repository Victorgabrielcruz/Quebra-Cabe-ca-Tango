package tango.app;

import tango.io.BoardPrinter;
import tango.io.InputGenerator;
import tango.io.InputReader;
import tango.model.Board;
import tango.solver.Solver;
import tango.solver.SolverFactory;
import tango.solver.SolverResult;
import tango.solver.SolverType;

import java.io.IOException;
import java.nio.file.Path;
import java.util.Scanner;

public class TangoApplication {
    private final InputReader inputReader;
    private final InputGenerator inputGenerator;
    private final BoardPrinter boardPrinter;
    private final SolverFactory solverFactory;

    public TangoApplication() {
        this.inputReader = new InputReader();
        this.inputGenerator = new InputGenerator();
        this.boardPrinter = new BoardPrinter();
        this.solverFactory = new SolverFactory();
    }

    public void runInteractive() {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            printMenu();
            String option = scanner.nextLine().trim();

            try {
                switch (option) {
                    case "1" -> runSolverFromMenu(scanner);
                    case "2" -> generateInputFromMenu(scanner);
                    case "3" -> tango.Main.printUsage();
                    case "0" -> running = false;
                    default -> System.out.println("Opcao invalida. Escolha uma opcao do menu.");
                }
            } catch (IllegalArgumentException exception) {
                System.out.println("Erro: " + exception.getMessage());
            }

            if (running) {
                System.out.println();
            }
        }

        System.out.println("Programa encerrado.");
    }

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

    private boolean isHelp(String value) {
        return "--help".equals(value) || "-h".equals(value);
    }

    private void printMenu() {
        System.out.println("TP2 FPAA - Tango Solver");
        System.out.println("1 - Resolver tabuleiro");
        System.out.println("2 - Gerar arquivo de entrada");
        System.out.println("3 - Ver ajuda");
        System.out.println("0 - Sair");
        System.out.print("Escolha uma opcao: ");
    }

    private void runSolverFromMenu(Scanner scanner) {
        System.out.print("Caminho do arquivo de entrada: ");
        String inputPath = scanner.nextLine().trim();

        System.out.println("Algoritmo:");
        System.out.println("1 - Backtracking");
        System.out.println("2 - Forca bruta");
        System.out.print("Escolha: ");
        String solverOption = scanner.nextLine().trim();

        String solverArgument = switch (solverOption) {
            case "1", "" -> SolverType.BACKTRACKING.getArgumentName();
            case "2" -> SolverType.BRUTE_FORCE.getArgumentName();
            default -> throw new IllegalArgumentException("opcao de algoritmo invalida.");
        };

        try {
            run(new String[] {inputPath, solverArgument});
        } catch (UnsupportedOperationException exception) {
            System.out.println("Parte ainda nao implementada: " + exception.getMessage());
        } catch (IllegalArgumentException exception) {
            System.out.println("Erro: " + exception.getMessage());
        }
    }

    private void generateInputFromMenu(Scanner scanner) {
        System.out.print("Tamanho do tabuleiro, por exemplo 6 ou 8: ");
        int size = readSize(scanner.nextLine().trim());

        System.out.print("Arquivo de destino, por exemplo examples/tabuleiro-6x6.txt: ");
        Path outputPath = Path.of(scanner.nextLine().trim());

        try {
            inputGenerator.generateEmptyBoard(outputPath, size);
            System.out.println("Arquivo gerado em: " + outputPath.toAbsolutePath());
        } catch (IOException exception) {
            System.out.println("Erro ao gerar arquivo: " + exception.getMessage());
        }
    }

    private int readSize(String value) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException("o tamanho deve ser um numero inteiro.");
        }
    }
}
