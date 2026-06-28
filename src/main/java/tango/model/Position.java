package tango.model;

/**
 * Coordenada imutavel de uma celula do tabuleiro.
 */
public class Position {
    private final int row;
    private final int column;

    /**
     * Cria uma coordenada. A verificacao de limites pertence ao {@link Board}.
     *
     * @param row indice da linha, iniciado em zero
     * @param column indice da coluna, iniciado em zero
     */
    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    /**
     * Consulta a linha.
     *
     * @return indice da linha
     */
    public int getRow() {
        return row;
    }

    /**
     * Consulta a coluna.
     *
     * @return indice da coluna
     */
    public int getColumn() {
        return column;
    }

    /**
     * Compara linha e coluna de duas posicoes.
     *
     * @param object objeto comparado
     * @return {@code true} quando as coordenadas forem iguais
     */
    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }

        if (!(object instanceof Position other)) {
            return false;
        }

        return row == other.row && column == other.column;
    }

    /**
     * @return codigo hash calculado a partir da linha e da coluna
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(column);
        return result;
    }

    /**
     * @return coordenada no formato {@code (linha, coluna)}
     */
    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
