package tango.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Board {
    private final int size;
    private final CellValue[][] cells;
    private final List<Constraint> constraints;

    public Board(int size) {
        if (size <= 0 || size % 2 != 0) {
            throw new IllegalArgumentException("o tamanho do tabuleiro deve ser positivo e par.");
        }

        this.size = size;
        this.cells = new CellValue[size][size];
        this.constraints = new ArrayList<>();

        fillEmptyCells();
    }

    private Board(int size, CellValue[][] cells, List<Constraint> constraints) {
        this.size = size;
        this.cells = cells;
        this.constraints = constraints;
    }

    public int getSize() {
        return size;
    }

    public CellValue getCell(Position position) {
        validatePosition(position);
        return cells[position.getRow()][position.getColumn()];
    }

    public void setCell(Position position, CellValue value) {
        validatePosition(position);

        if (value == null) {
            throw new IllegalArgumentException("o valor da celula nao pode ser nulo.");
        }

        cells[position.getRow()][position.getColumn()] = value;
    }

    public boolean isEmpty(Position position) {
        return getCell(position).isEmpty();
    }

    public void addConstraint(Constraint constraint) {
        if (constraint == null) {
            throw new IllegalArgumentException("a restricao nao pode ser nula.");
        }

        validatePosition(constraint.getFirstPosition());
        validatePosition(constraint.getSecondPosition());
        constraints.add(constraint);
    }

    public List<Constraint> getConstraints() {
        return Collections.unmodifiableList(constraints);
    }

    public Board copy() {
        CellValue[][] copiedCells = new CellValue[size][size];

        for (int row = 0; row < size; row++) {
            System.arraycopy(cells[row], 0, copiedCells[row], 0, size);
        }

        return new Board(size, copiedCells, new ArrayList<>(constraints));
    }

    public boolean isInside(Position position) {
        return position != null
                && position.getRow() >= 0
                && position.getRow() < size
                && position.getColumn() >= 0
                && position.getColumn() < size;
    }

    private void fillEmptyCells() {
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                cells[row][column] = CellValue.VAZIO;
            }
        }
    }

    private void validatePosition(Position position) {
        if (!isInside(position)) {
            throw new IllegalArgumentException("posicao fora dos limites do tabuleiro.");
        }
    }
}
