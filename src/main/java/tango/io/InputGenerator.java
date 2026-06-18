package tango.io;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

public class InputGenerator {
    public void generateEmptyBoard(Path outputPath, int size) throws IOException {
        validateSize(size);

        Path parent = outputPath.getParent();
        if (parent != null) {
            Files.createDirectories(parent);
        }

        Files.write(outputPath, buildEmptyBoardLines(size));
    }

    private List<String> buildEmptyBoardLines(int size) {
        List<String> lines = new ArrayList<>();

        lines.add("# Arquivo de entrada do TP2 FPAA - Tango");
        lines.add("# Simbolos das celulas: . vazio, S Sol, L Lua");
        lines.add("# Restricoes: linha1 coluna1 linha2 coluna2 =|x");
        lines.add("size=" + size);
        lines.add("board:");

        for (int row = 0; row < size; row++) {
            lines.add(".".repeat(size));
        }

        lines.add("constraints:");
        lines.add("# Exemplo: 0 0 0 1 =");
        lines.add("# Exemplo: 1 2 2 2 x");

        return lines;
    }

    private void validateSize(int size) {
        if (size <= 0 || size % 2 != 0) {
            throw new IllegalArgumentException("o tamanho do tabuleiro deve ser positivo e par.");
        }
    }
}
