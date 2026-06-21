package tango.solver;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import tango.model.Board;
import tango.model.CellValue;
import tango.model.Position;
import tango.validation.BoardValidator;

/**
 * Busca exaustiva que gera todas as combinacoes das celulas vazias.
 *
 * <p>Nenhuma regra parcial e usada para poda. O tabuleiro e validado somente
 * quando uma combinacao estiver completa.</p>
 */
public class BruteForceSolver implements Solver {
    /** Validador aplicado somente nas folhas completas da busca. */
    private final BoardValidator validator;

    /**
     * Cria o solver com um novo validador de regras.
     */
    public BruteForceSolver() {
        this.validator = new BoardValidator();
    }

    /**
     * Executa a busca exaustiva sobre uma copia do estado inicial.
     *
     * @param initialBoard configuracao que fornece dicas e restricoes
     * @return primeira solucao e quantidade de atribuicoes testadas
     * @throws NullPointerException se o tabuleiro for nulo
     */
    @Override
    public SolverResult solve(Board initialBoard) {
        Objects.requireNonNull(initialBoard, "o tabuleiro inicial nao pode ser nulo.");

        Board candidateBoard = initialBoard.copy();
        List<Position> emptyPositions = findEmptyPositions(candidateBoard);
        long[] visitedStates = {0};

        boolean solved = fill(candidateBoard, emptyPositions, 0, visitedStates);
        return new SolverResult(solved ? candidateBoard : null, visitedStates[0]);
    }

    /**
     * Preenche recursivamente uma posicao vazia por nivel.
     *
     * @return {@code true} quando a ramificacao encontrar uma solucao
     */
    private boolean fill(
            Board board,
            List<Position> emptyPositions,
            int positionIndex,
            long[] visitedStates) {
        if (positionIndex == emptyPositions.size()) {
            return validator.isFinalValid(board);
        }

        Position position = emptyPositions.get(positionIndex);
        for (CellValue value : new CellValue[] {CellValue.SOL, CellValue.LUA}) {
            board.setCell(position, value);
            visitedStates[0]++;

            if (fill(board, emptyPositions, positionIndex + 1, visitedStates)) {
                return true;
            }
        }

        board.setCell(position, CellValue.VAZIO);
        return false;
    }

    /** Coleta as posicoes alteraveis, preservando todas as dicas iniciais. */
    private List<Position> findEmptyPositions(Board board) {
        List<Position> positions = new ArrayList<>();

        for (int row = 0; row < board.getSize(); row++) {
            for (int column = 0; column < board.getSize(); column++) {
                Position position = new Position(row, column);
                if (board.isEmpty(position)) {
                    positions.add(position);
                }
            }
        }
        return positions;
    }
}
