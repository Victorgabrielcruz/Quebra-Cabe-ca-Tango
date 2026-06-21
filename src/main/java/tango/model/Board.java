package tango.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Estado de um quebra-cabeca Tango, incluindo grade e restricoes.
 *
 * <p>O tamanho e as restricoes definem o problema, enquanto as celulas podem
 * ser alteradas pelos algoritmos durante a busca.</p>
 */
public class Board {
    /** Dimensao fixa da grade quadrada. */
    private final int size;
    /** Matriz mutavel com os valores atuais. */
    private final CellValue[][] cells;
    /** Restricoes associadas a esta instancia do problema. */
    private final List<Constraint> constraints;

    /**
     * Cria um tabuleiro quadrado preenchido com {@link CellValue#VAZIO}.
     *
     * @param size quantidade de linhas e colunas
     * @throws IllegalArgumentException se o tamanho nao for positivo e par
     */
    public Board(int size) {
        if (size <= 0 || size % 2 != 0) {
            throw new IllegalArgumentException("o tamanho do tabuleiro deve ser positivo e par.");
        }

        this.size = size;
        this.cells = new CellValue[size][size];
        this.constraints = new ArrayList<>();

        fillEmptyCells();
    }

    /** Cria internamente uma copia com estruturas ja preparadas. */
    private Board(int size, CellValue[][] cells, List<Constraint> constraints) {
        this.size = size;
        this.cells = cells;
        this.constraints = constraints;
    }

    /**
     * Informa a dimensao da grade.
     *
     * @return quantidade de linhas e colunas
     */
    public int getSize() {
        return size;
    }

    /**
     * Consulta o valor de uma celula.
     *
     * @param position coordenada consultada
     * @return valor armazenado na celula
     * @throws IllegalArgumentException se a posicao estiver fora do tabuleiro
     */
    public CellValue getCell(Position position) {
        validatePosition(position);
        return cells[position.getRow()][position.getColumn()];
    }

    /**
     * Altera o valor de uma celula.
     *
     * @param position coordenada alterada
     * @param value novo valor
     * @throws IllegalArgumentException se a posicao for invalida ou o valor for nulo
     */
    public void setCell(Position position, CellValue value) {
        validatePosition(position);

        if (value == null) {
            throw new IllegalArgumentException("o valor da celula nao pode ser nulo.");
        }

        cells[position.getRow()][position.getColumn()] = value;
    }

    /**
     * Verifica se uma celula ainda nao foi preenchida.
     *
     * @param position coordenada consultada
     * @return {@code true} quando a celula contiver {@link CellValue#VAZIO}
     */
    public boolean isEmpty(Position position) {
        return getCell(position).isEmpty();
    }

    /**
     * Adiciona uma restricao ao problema.
     *
     * @param constraint restricao adicionada
     * @throws IllegalArgumentException se a restricao for nula ou usar posicao
     *         fora do tabuleiro
     */
    public void addConstraint(Constraint constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("a restricao nao pode ser nula.");
        }

        validatePosition(constraint.getFirstPosition());
        validatePosition(constraint.getSecondPosition());
        constraints.add(constraint);
    }

    /**
     * Retorna uma visao nao modificavel das restricoes.
     *
     * @return restricoes cadastradas
     */
    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    /**
     * Cria uma copia independente da matriz e da lista de restricoes.
     *
     * <p>As restricoes podem ser compartilhadas porque sao imutaveis.</p>
     *
     * @return novo tabuleiro com o mesmo estado
     */
    public Board copy() {
        CellValue[][] copiedCells = new CellValue[size][size];

        for (int row = 0; row < size; row++) {
            System.arraycopy(cells[row], 0, copiedCells[row], 0, size);
        }

        return new Board(size, copiedCells, new ArrayList<>(constraints));
    }

    /**
     * Verifica se uma coordenada pertence a grade.
     *
     * @param position coordenada testada
     * @return {@code true} quando linha e coluna estiverem dentro dos limites
     */
    public boolean isInside(Position position) {
        return position != null
                && position.getRow() >= 0
                && position.getRow() < size
                && position.getColumn() >= 0
                && position.getColumn() < size;
    }

    /** Inicializa todas as posicoes com o valor vazio explicito. */
    private void fillEmptyCells() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                cells[row][column] = CellValue.VAZIO;
            }
        }
    }

    /** Rejeita coordenadas nulas ou externas a grade. */
    private void validatePosition(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("posicao fora dos limites do tabuleiro.");
        }
    }
}
