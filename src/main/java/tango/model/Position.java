package tango.model;

public class Position {
    private final int row;
    private final int column;

    public Position(int row, int column) {
        this.row = row;
        this.column = column;
    }

    public int getRow() {
        return row;
    }

    public int getColumn() {
        return column;
    }

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

    @Override
    public int hashCode() {
        int result = Integer.hashCode(row);
        result = 31 * result + Integer.hashCode(column);
        return result;
    }

    @Override
    public String toString() {
        return "(" + row + ", " + column + ")";
    }
}
