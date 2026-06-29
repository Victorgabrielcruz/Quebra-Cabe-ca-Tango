package tango.validation;

import java.util.Objects;

import tango.model.Board;
import tango.model.CellValue;
import tango.model.Constraint;
import tango.model.ConstraintType;
import tango.model.Position;

/**
 * Implementa as regras de validade final e parcial de um tabuleiro Tango.
 *
 * A validade parcial e usada como poda: aceita celulas vazias e rejeita
 * somente estados que ja nao podem conduzir a uma solucao.
 */
public class BoardValidator {
    /**
     * Cria um validador das regras do Tango.
     */
    public BoardValidator() {
    }

    /**
     * Verifica todas as regras aplicaveis a uma solucao completa.
     *
     * @param board tabuleiro avaliado
     * @return {@code true} se estiver completo, equilibrado e sem violacoes
     * @throws NullPointerException se o tabuleiro for nulo
     */
    public boolean isFinalValid(Board board) {
        requireBoard(board);
        return hasNoEmptyCells(board)
                && hasNoThreeEqualAdjacentCells(board)
                && hasValidRowAndColumnBalance(board)
                && hasValidConstraints(board);
    }

    /**
     * Verifica se um estado parcial ainda pode produzir uma solucao.
     *
     * @param board tabuleiro possivelmente incompleto
     * @return {@code true} quando nenhuma violacao definitiva foi encontrada
     * @throws NullPointerException se o tabuleiro for nulo
     */
    public boolean isPartialValid(Board board) {
        requireBoard(board);
        return hasNoThreeEqualAdjacentCells(board)
                && hasValidPartialRowAndColumnBalance(board)
                && hasValidConstraints(board);
    }

    /**
     * Verifica a regra de preenchimento completo.
     *
     * @param board tabuleiro avaliado
     * @return {@code true} quando nenhuma celula estiver vazia
     */
    public boolean hasNoEmptyCells(Board board) {
        requireBoard(board);
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                if (board.getCell(new Position(row, column)).isEmpty()) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Procura trios de Sol ou Lua na horizontal e na vertical.
     *
     * <p>Celulas vazias nao sao consideradas simbolos iguais.</p>
     *
     * @param board tabuleiro avaliado
     * @return {@code true} quando nenhum trio proibido existir
     */
    public boolean hasNoThreeEqualAdjacentCells(Board board) {
        requireBoard(board);
        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                CellValue current = board.getCell(new Position(row, column));

                if (current.isEmpty()) {
                    continue;
                }

                if (column >= 2
                        && current == board.getCell(new Position(row, column - 1))
                        && current == board.getCell(new Position(row, column - 2))) {
                    return false;
                }

                if (row >= 2
                        && current == board.getCell(new Position(row - 1, column))
                        && current == board.getCell(new Position(row - 2, column))) {
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Exige exatamente metade Sol e metade Lua em cada linha e coluna.
     *
     * @param board tabuleiro final avaliado
     * @return {@code true} quando todas as contagens forem exatas
     */
    public boolean hasValidRowAndColumnBalance(Board board) {
        requireBoard(board);
        int expectedAmount = board.getSize() / 2;

        for (int index = 0; index < board.getSize(); index++) {
            if (countInRow(board, index, CellValue.SOL) != expectedAmount
                    || countInRow(board, index, CellValue.LUA) != expectedAmount
                    || countInColumn(board, index, CellValue.SOL) != expectedAmount
                    || countInColumn(board, index, CellValue.LUA) != expectedAmount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica se nenhuma linha ou coluna ultrapassou metade de um simbolo.
     *
     * @param board tabuleiro parcial avaliado
     * @return {@code true} quando as celulas restantes ainda podem equilibrar
     *         todas as linhas e colunas
     */
    public boolean hasValidPartialRowAndColumnBalance(Board board) {
        requireBoard(board);
        int maximumAmount = board.getSize() / 2;

        for (int index = 0; index < board.getSize(); index++) {
            if (countInRow(board, index, CellValue.SOL) > maximumAmount
                    || countInRow(board, index, CellValue.LUA) > maximumAmount
                    || countInColumn(board, index, CellValue.SOL) > maximumAmount
                    || countInColumn(board, index, CellValue.LUA) > maximumAmount) {
                return false;
            }
        }
        return true;
    }

    /**
     * Verifica igualdade e oposicao quando as duas celulas estao preenchidas.
     *
     * @param board tabuleiro avaliado
     * @return {@code true} quando nenhuma restricao decidida for violada
     */
    public boolean hasValidConstraints(Board board) {
        requireBoard(board);
        for (Constraint constraint : board.getConstraints()) {
            CellValue first = board.getCell(constraint.getFirstPosition());
            CellValue second = board.getCell(constraint.getSecondPosition());

            if (first.isEmpty() || second.isEmpty()) {
                continue;
            }

            if (constraint.getType() == ConstraintType.EQUAL && first != second) {
                return false;
            }

            if (constraint.getType() == ConstraintType.OPPOSITE && first == second) {
                return false;
            }
        }
        return true;
    }

    /** Conta as ocorrencias de um valor em uma linha. */
    private int countInRow(Board board, int row, CellValue value) {
        int count = 0;
        for (int column = 0; column < board.getSize(); column++) {
            if (board.getCell(new Position(row, column)) == value) {
                count++;
            }
        }
        return count;
    }

    /** Conta as ocorrencias de um valor em uma coluna. */
    private int countInColumn(Board board, int column, CellValue value) {
        int count = 0;
        for (int row = 0; row < board.getSize(); row++) {
            if (board.getCell(new Position(row, column)) == value) {
                count++;
            }
        }
        return count;
    }

    /** Garante uma falha imediata e clara para argumento nulo. */
    private void requireBoard(Board board) {
        Objects.requireNonNull(board, "o tabuleiro nao pode ser nulo.");
    }
}
