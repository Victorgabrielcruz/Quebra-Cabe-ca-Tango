package tango.io;

import tango.model.Board;
import tango.model.Constraint;
import tango.model.Position;
import tango.validation.BoardValidator;

/**
 * Valida um tabuleiro de entrada antes que ele seja salvo ou resolvido.
 *
 * <p>A verificacao e parcial: celulas vazias sao permitidas, mas violacoes ja
 * determinadas sao rejeitadas com mensagens direcionadas ao usuario.</p>
 */
public class BoardInputValidator {
    /** Validador das regras que podem ser avaliadas em estado parcial. */
    private final BoardValidator validator = new BoardValidator();

    /** Cria um validador de entradas com as regras padrao. */
    public BoardInputValidator() {
    }

    /**
     * Verifica adjacencia das restricoes, trios, equilibrio parcial e relacoes
     * entre celulas ja preenchidas.
     *
     * @param board tabuleiro recebido da interface ou de arquivo externo
     * @throws IllegalArgumentException quando alguma regra verificavel estiver
     *         sendo violada
     */
    public void validate(Board board) {
        validateConstraintPositions(board);

        if (!validator.hasNoThreeEqualAdjacentCells(board)) {
            throw new IllegalArgumentException(
                    "o tabuleiro possui tres simbolos iguais consecutivos em uma linha ou coluna.");
        }

        if (!validator.hasValidPartialRowAndColumnBalance(board)) {
            throw new IllegalArgumentException(
                    "uma linha ou coluna possui mais Sois ou Luas que o limite permitido.");
        }

        if (!validator.hasValidConstraints(board)) {
            throw new IllegalArgumentException("uma restricao de igualdade ou oposicao ja esta sendo violada.");
        }
    }

    /** Exige distancia de Manhattan igual a um para cada restricao. */
    private void validateConstraintPositions(Board board) {
        for (Constraint constraint : board.getConstraints()) {
            Position first = constraint.getFirstPosition();
            Position second = constraint.getSecondPosition();
            int distance = Math.abs(first.getRow() - second.getRow())
                    + Math.abs(first.getColumn() - second.getColumn());

            if (distance != 1) {
                throw new IllegalArgumentException(
                        "a restricao entre " + first + " e " + second
                                + " e invalida: as celulas devem ser vizinhas na horizontal ou vertical.");
            }
        }
    }
}
