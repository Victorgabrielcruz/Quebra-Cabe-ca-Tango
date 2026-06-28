package tango.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Stream;

import tango.model.Board;
import tango.model.Constraint;
import tango.model.Position;

/**
 * Persiste e lista os tabuleiros mantidos no catalogo do projeto.
 *
 */
public class BoardCatalog {
    private static final Path DEFAULT_CATALOG_DIRECTORY = Path.of("examples", "tabuleiros");
    private static final DateTimeFormatter TIMESTAMP_FORMAT = DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss");
    private final Path directory;

    /**
     * Cria um catalogo apontando para {@code examples/tabuleiros}.
     */
    public BoardCatalog() {
        this(DEFAULT_CATALOG_DIRECTORY);
    }

    /** Cria um catalogo em uma pasta especifica. */
    BoardCatalog(Path directory) {
        this.directory = directory;
    }

    /**
     * Lista os arquivos de texto do catalogo em ordem de nome.
     *
     * @return caminhos dos tabuleiros disponiveis
     * @throws IOException se a pasta nao puder ser criada ou lida
     */
    public List<Path> listFiles() throws IOException {
        ensureDirectoryExists();

        try (Stream<Path> paths = Files.list(directory)) {
            return paths
                    .filter(Files::isRegularFile)
                    .filter(path -> path.getFileName().toString().toLowerCase().endsWith(".txt"))
                    .sorted(Comparator.comparing(path -> path.getFileName().toString()))
                    .toList();
        }
    }

    /**
     * Salva um tabuleiro produzido pelo gerador aleatorio.
     *
     * @param board tabuleiro salvo
     * @return caminho criado
     * @throws IOException se o arquivo nao puder ser escrito
     */
    public Path saveAutomatic(Board board) throws IOException {
        return save(board, "automatico");
    }

    /**
     * Salva um tabuleiro digitado pela interface.
     *
     * @param board tabuleiro salvo
     * @return caminho criado
     * @throws IOException se o arquivo nao puder ser escrito
     */
    public Path saveManual(Board board) throws IOException {
        return save(board, "manual");
    }

    /**
     * Salva a versao normalizada de um arquivo externo validado.
     *
     * @param board tabuleiro importado
     * @return caminho criado
     * @throws IOException se o arquivo nao puder ser escrito
     */
    public Path saveImported(Board board) throws IOException {
        return save(board, "importado");
    }

    /**
     * Consulta a pasta efetiva.
     *
     * @return pasta usada pelo catalogo
     */
    public Path getDirectory() {
        return directory;
    }

    /** Serializa o tabuleiro usando o proximo nome da origem. */
    private Path save(Board board, String origin) throws IOException {
        ensureDirectoryExists();
        int sequence = nextSequence(origin);
        String timestamp = LocalDateTime.now().format(TIMESTAMP_FORMAT);
        String fileName = String.format("%s-%03d-%s.txt", origin, sequence, timestamp);
        Path destination = directory.resolve(fileName);

        Files.write(destination, serialize(board, origin));
        return destination;
    }

    /** Converte grade e restricoes para o formato textual do projeto. */
    private List<String> serialize(Board board, String origin) {
        java.util.ArrayList<String> lines = new java.util.ArrayList<>();
        lines.add("# Tabuleiro Tango criado pela interface do projeto");
        lines.add("# Origem: " + origin);
        lines.add("# Simbolos: . vazio, S Sol, L Lua");
        lines.add("size=" + board.getSize());
        lines.add("board:");

        for (int row = 0; row < board.getSize(); row++) {
            StringBuilder line = new StringBuilder();
            for (int column = 0; column < board.getSize(); column++) {
                line.append(board.getCell(new Position(row, column)).getSymbol());
            }
            lines.add(line.toString());
        }

        lines.add("constraints:");
        for (Constraint constraint : board.getConstraints()) {
            Position first = constraint.getFirstPosition();
            Position second = constraint.getSecondPosition();
            lines.add(first.getRow() + " "
                    + first.getColumn() + " "
                    + second.getRow() + " "
                    + second.getColumn() + " "
                    + constraint.getType().getSymbol());
        }
        return lines;
    }

    /** Localiza o maior numero da origem e devolve o sucessor. */
    private int nextSequence(String origin) throws IOException {
        Pattern pattern = Pattern.compile("^" + Pattern.quote(origin) + "-(\\d+)-.*\\.txt$");
        int maximum = 0;

        for (Path path : listFiles()) {
            Matcher matcher = pattern.matcher(path.getFileName().toString());
            if (matcher.matches()) {
                maximum = Math.max(maximum, Integer.parseInt(matcher.group(1)));
            }
        }
        return maximum + 1;
    }

    /** Cria a arvore de pastas do catalogo quando necessario. */
    private void ensureDirectoryExists() throws IOException {
        Files.createDirectories(directory);
    }
}
