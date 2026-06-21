package tango.io;

import tango.model.Board;
import tango.model.CellValue;
import tango.model.Constraint;
import tango.model.ConstraintType;
import tango.model.Position;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

/**
 * Converte um arquivo textual no formato do projeto em um {@link Board}.
 *
 * <p>O leitor valida a estrutura e os simbolos. Regras semanticamente
 * verificaveis ficam sob responsabilidade de {@link BoardInputValidator}.</p>
 */
public class InputReader {
    /** Cria um leitor de arquivos sem estado. */
    public InputReader() {
    }

    /**
     * Le e interpreta um arquivo de entrada.
     *
     * @param inputPath caminho do arquivo
     * @return tabuleiro representado pelo conteudo
     * @throws IllegalArgumentException se o arquivo nao puder ser lido ou seu
     *         formato for invalido
     */
    public Board read(Path inputPath) {
        List<String> lines = readLines(inputPath);
        int size = readSize(lines);
        Board board = new Board(size);

        readBoard(lines, board);
        readConstraints(lines, board);

        return board;
    }

    /** Le todas as linhas e uniformiza falhas de acesso como erro de entrada. */
    private List<String> readLines(Path inputPath) {
        try {
            return Files.readAllLines(inputPath);
        } catch (IOException exception) {
            throw new IllegalArgumentException("nao foi possivel ler o arquivo: " + inputPath);
        }
    }

    /** Procura e converte a declaracao obrigatoria {@code size=}. */
    private int readSize(List<String> lines) {
        for (String line : lines) {
            String normalizedLine = normalize(line);

            if (normalizedLine.startsWith("size=")) {
                String value = normalizedLine.substring("size=".length()).trim();
                return parseInteger(value, "tamanho do tabuleiro invalido.");
            }
        }

        throw new IllegalArgumentException("o arquivo precisa conter uma linha size=<numero>.");
    }

    /** Le exatamente a quantidade de linhas definida pelo tamanho. */
    private void readBoard(List<String> lines, Board board) {
        int boardStart = findSection(lines, "board:");
        int row = 0;

        for (int index = boardStart + 1; index < lines.size(); index++) {
            String line = normalize(lines.get(index));

            if (shouldIgnore(line)) {
                continue;
            }

            if (isSection(line)) {
                break;
            }

            if (row >= board.getSize()) {
                throw new IllegalArgumentException("o arquivo possui linhas demais no tabuleiro.");
            }

            readBoardRow(line, board, row);
            row++;
        }

        if (row != board.getSize()) {
            throw new IllegalArgumentException("o tabuleiro precisa conter exatamente " + board.getSize() + " linhas.");
        }
    }

    /** Converte os caracteres de uma linha em valores de celula. */
    private void readBoardRow(String line, Board board, int row) {
        if (line.length() != board.getSize()) {
            throw new IllegalArgumentException("linha " + row + " do tabuleiro possui tamanho invalido.");
        }

        for (int column = 0; column < line.length(); column++) {
            CellValue value = CellValue.fromSymbol(line.charAt(column));
            board.setCell(new Position(row, column), value);
        }
    }

    /** Le a secao opcional de restricoes ate a proxima secao ou fim. */
    private void readConstraints(List<String> lines, Board board) {
        int constraintsStart = findOptionalSection(lines, "constraints:");

        if (constraintsStart == -1) {
            return;
        }

        for (int index = constraintsStart + 1; index < lines.size(); index++) {
            String line = normalize(lines.get(index));

            if (shouldIgnore(line)) {
                continue;
            }

            if (isSection(line)) {
                break;
            }

            board.addConstraint(parseConstraint(line));
        }
    }

    /** Converte cinco campos textuais em uma restricao tipada. */
    private Constraint parseConstraint(String line) {
        String[] parts = line.split("\\s+");

        if (parts.length != 5 || parts[4].length() != 1) {
            throw new IllegalArgumentException("restricao invalida: " + line);
        }

        Position firstPosition = new Position(
                parseInteger(parts[0], "linha da primeira posicao invalida."),
                parseInteger(parts[1], "coluna da primeira posicao invalida."));
        Position secondPosition = new Position(
                parseInteger(parts[2], "linha da segunda posicao invalida."),
                parseInteger(parts[3], "coluna da segunda posicao invalida."));
        ConstraintType type = ConstraintType.fromSymbol(parts[4].charAt(0));

        return new Constraint(firstPosition, secondPosition, type);
    }

    /** Localiza uma secao obrigatoria ou informa sua ausencia. */
    private int findSection(List<String> lines, String section) {
        int index = findOptionalSection(lines, section);

        if (index == -1) {
            throw new IllegalArgumentException("secao obrigatoria nao encontrada: " + section);
        }

        return index;
    }

    /** Localiza uma secao sem diferenciar maiusculas e minusculas. */
    private int findOptionalSection(List<String> lines, String section) {
        for (int index = 0; index < lines.size(); index++) {
            if (normalize(lines.get(index)).equalsIgnoreCase(section)) {
                return index;
            }
        }

        return -1;
    }

    /** Remove comentarios iniciados por {@code #} e espacos externos. */
    private String normalize(String line) {
        int commentStart = line.indexOf('#');
        String lineWithoutComment = commentStart >= 0 ? line.substring(0, commentStart) : line;
        return lineWithoutComment.trim();
    }

    /** Indica se uma linha normalizada nao possui conteudo. */
    private boolean shouldIgnore(String line) {
        return line.isEmpty();
    }

    /** Reconhece cabecalhos terminados por dois-pontos. */
    private boolean isSection(String line) {
        return line.endsWith(":");
    }

    /** Converte um inteiro usando uma mensagem adequada ao campo. */
    private int parseInteger(String value, String errorMessage) {
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException exception) {
            throw new IllegalArgumentException(errorMessage);
        }
    }
}
