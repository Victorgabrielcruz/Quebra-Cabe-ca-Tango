package tango.validation;

import tango.model.Position;
import tango.model.Board;
import tango.model.CellValue;
import tango.model.Constraint;
public class BoardValidator {
    public boolean isFinalValid(Board board) {
        throw new UnsupportedOperationException(
                "validacao final deve ser implementada pelo responsavel pelas regras e podas.");
    }

    public boolean isPartialValid(Board board) {
        throw new UnsupportedOperationException(
                "validacao parcial deve ser implementada pelo responsavel pelas regras e podas.");
    }

    public boolean hasNoEmptyCells(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                Position position = new Position(i, j);
                if(board.getCell(position).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    public boolean hasNoThreeEqualAdjacentCells(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                Position position = new Position(i, j);
                CellValue currentValue = board.getCell(position);

                if(j >= 2) {
                    
                    if(currentValue.equals(board.getCell(new Position(i, j - 1))) && currentValue.equals(board.getCell(new Position(i, j - 2)))) {
                        return false;
                    }
                }

                // Verificar verticalmente
                if(i >= 2) {

                    if(currentValue.equals(board.getCell(new Position(i - 1, j))) && currentValue.equals(board.getCell(new Position(i - 2, j)))) {
                        return false;
                    }
                }
            }
        }
        return true;
    }

    public boolean hasValidRowAndColumnBalance(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            int countZero = 0;
            int countOne = 0;

            for(int j = 0; j < board.getSize(); j++) {
                CellValue cellValueRow = board.getCell(new Position(i, j));
                CellValue cellValueColumn = board.getCell(new Position(j, i));

                if(cellValueRow.equals(CellValue.SOL)) {
                    countZero++;
                } else if(cellValueRow.equals(CellValue.LUA)) {
                    countOne++;
                }

                if(cellValueColumn.equals(CellValue.SOL)) {
                    countZero++;
                } else if(cellValueColumn.equals(CellValue.LUA)) {
                    countOne++;
                }
            }

            if(countZero > board.getSize() / 2 || countOne > board.getSize() / 2) {
                return false;
            }
        }
        return true;
    }

    public boolean hasValidPartialRowAndColumnBalance(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            int countZero = 0;
            int countOne = 0;

            for(int j = 0; j < board.getSize(); j++) {
                CellValue cellValueRow = board.getCell(new Position(i, j));
                CellValue cellValueColumn = board.getCell(new Position(j, i));

                if(cellValueRow.equals(CellValue.SOL)) {
                    countZero++;
                } else if(cellValueRow.equals(CellValue.LUA)) {
                    countOne++;
                }

                if(cellValueColumn.equals(CellValue.SOL)) {
                    countZero++;
                } else if(cellValueColumn.equals(CellValue.LUA)) {
                    countOne++;
                }
            }

            if(countZero > board.getSize() / 2 || countOne > board.getSize() / 2) {
                return false;
            }
        }
        return true;
    }

    public boolean hasValidConstraints(Board board) {
        for(int i = 0; i < board.getSize(); i++) {
            for(int j = 0; j < board.getSize(); j++) {
                Position position = new Position(i, j);
                CellValue cellValue = board.getCell(position);

                if(cellValue.equals(CellValue.SOL)) {
                    for(Constraint constraint : board.getConstraints()) {
                        if(constraint.getFirstPosition().equals(position) && !board.getCell(constraint.getSecondPosition()).equals(CellValue.LUA)) {
                            return false;
                        }
                        if(constraint.getSecondPosition().equals(position) && !board.getCell(constraint.getFirstPosition()).equals(CellValue.LUA)) {
                            return false;
                        }
                    }
                } else if(cellValue.equals(CellValue.LUA)) {
                    for(Constraint constraint : board.getConstraints()) {
                        if(constraint.getFirstPosition().equals(position) && !board.getCell(constraint.getSecondPosition()).equals(CellValue.SOL)) {
                            return false;
                        }
                        if(constraint.getSecondPosition().equals(position) && !board.getCell(constraint.getFirstPosition()).equals(CellValue.SOL)) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
}
