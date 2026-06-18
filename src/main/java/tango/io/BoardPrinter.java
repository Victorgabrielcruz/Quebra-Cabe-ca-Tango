package tango.io;

import tango.model.Board;
import tango.model.Constraint;
import tango.model.Position;

public class BoardPrinter {
    public void print(Board board) {
        printGrid(board);
        printConstraints(board);
    }

    private void printGrid(Board board) {
        System.out.print("   ");

        for (int column = 0; column < board.getSize(); column++) {
            System.out.print(column + " ");
        }

        System.out.println();

        for (int row = 0; row < board.getSize(); row++) {
            System.out.print(row + "  ");

            for (int column = 0; column < board.getSize(); column++) {
                System.out.print(board.getCell(new Position(row, column)).getSymbol() + " ");
            }

            System.out.println();
        }
    }

    private void printConstraints(Board board) {
        if (board.getConstraints().isEmpty()) {
            System.out.println("Restricoes: nenhuma");
            return;
        }

        System.out.println("Restricoes:");

        for (Constraint constraint : board.getConstraints()) {
            System.out.println("  "
                    + formatPosition(constraint.getFirstPosition())
                    + " "
                    + constraint.getType().getSymbol()
                    + " "
                    + formatPosition(constraint.getSecondPosition()));
        }
    }

    private String formatPosition(Position position) {
        return "(" + position.getRow() + ", " + position.getColumn() + ")";
    }
}
