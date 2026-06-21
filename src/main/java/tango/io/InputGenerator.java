package tango.io;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import tango.model.Board;
import tango.model.CellValue;
import tango.model.Constraint;
import tango.model.ConstraintType;
import tango.model.Position;
import tango.validation.BoardValidator;

/**
 * Gera quebra-cabecas aleatorios que possuem pelo menos uma solucao.
 *
 * <p>Primeiro constroi uma solucao completa usando escolhas embaralhadas e
 * validacao parcial. Depois revela dicas e deriva restricoes dessa solucao.</p>
 */
public class InputGenerator {
    /** Fonte usada para embaralhar candidatos, dicas e restricoes. */
    private final Random random;
    /** Validador usado durante a construcao da solucao-base. */
    private final BoardValidator validator;

    /**
     * Cria um gerador com fonte de aleatoriedade nao deterministica.
     */
    public InputGenerator() {
        this(new Random());
    }

    /** Cria um gerador com fonte injetada para testes reproduziveis. */
    InputGenerator(Random random) {
        this.random = random;
        this.validator = new BoardValidator();
    }

    /**
     * Gera um tabuleiro parcial aleatorio e solucionavel.
     *
     * @param size quantidade par de linhas e colunas
     * @param clueCount quantidade de celulas inicialmente preenchidas
     * @param constraintCount quantidade de relacoes entre celulas vizinhas
     * @return quebra-cabeca gerado
     * @throws IllegalArgumentException se algum parametro estiver fora dos
     *         limites permitidos
     * @throws IllegalStateException se nenhuma solucao puder ser gerada
     */
    public Board generateRandomBoard(int size, int clueCount, int constraintCount) {
        validateParameters(size, clueCount, constraintCount);

        Board solution = new Board(size);
        if (!fillRandomSolution(solution, 0)) {
            throw new IllegalStateException("nao foi possivel gerar uma solucao valida.");
        }

        Board puzzle = new Board(size);
        copyRandomClues(solution, puzzle, clueCount);
        addRandomConstraints(solution, puzzle, constraintCount);
        return puzzle;
    }

    /** Preenche recursivamente uma solucao com ordem aleatoria de simbolos. */
    private boolean fillRandomSolution(Board board, int cellIndex) {
        int totalCells = board.getSize() * board.getSize();
        if (cellIndex == totalCells) {
            return validator.isFinalValid(board);
        }

        int row = cellIndex / board.getSize();
        int column = cellIndex % board.getSize();
        Position position = new Position(row, column);
        List<CellValue> candidates = new ArrayList<>(List.of(CellValue.SOL, CellValue.LUA));
        Collections.shuffle(candidates, random);

        for (CellValue candidate : candidates) {
            board.setCell(position, candidate);
            if (validator.isPartialValid(board) && fillRandomSolution(board, cellIndex + 1)) {
                return true;
            }
        }

        board.setCell(position, CellValue.VAZIO);
        return false;
    }

    /** Copia uma amostra embaralhada de celulas da solucao. */
    private void copyRandomClues(Board solution, Board puzzle, int clueCount) {
        List<Position> positions = allPositions(solution.getSize());
        Collections.shuffle(positions, random);

        for (int index = 0; index < clueCount; index++) {
            Position position = positions.get(index);
            puzzle.setCell(position, solution.getCell(position));
        }
    }

    /** Deriva relacoes validas de pares vizinhos escolhidos aleatoriamente. */
    private void addRandomConstraints(Board solution, Board puzzle, int constraintCount) {
        List<PositionPair> adjacentPairs = adjacentPairs(solution.getSize());
        Collections.shuffle(adjacentPairs, random);

        for (int index = 0; index < constraintCount; index++) {
            PositionPair pair = adjacentPairs.get(index);
            CellValue first = solution.getCell(pair.first());
            CellValue second = solution.getCell(pair.second());
            ConstraintType type = first == second
                    ? ConstraintType.EQUAL
                    : ConstraintType.OPPOSITE;
            puzzle.addConstraint(new Constraint(pair.first(), pair.second(), type));
        }
    }

    /** Cria a lista de todas as coordenadas em ordem de linha. */
    private List<Position> allPositions(int size) {
        List<Position> positions = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                positions.add(new Position(row, column));
            }
        }
        return positions;
    }

    /** Enumera cada par horizontal ou vertical uma unica vez. */
    private List<PositionPair> adjacentPairs(int size) {
        List<PositionPair> pairs = new ArrayList<>();
        for (int row = 0; row < size; row++) {
            for (int column = 0; column < size; column++) {
                Position current = new Position(row, column);
                if (column + 1 < size) {
                    pairs.add(new PositionPair(current, new Position(row, column + 1)));
                }
                if (row + 1 < size) {
                    pairs.add(new PositionPair(current, new Position(row + 1, column)));
                }
            }
        }
        return pairs;
    }

    /** Verifica os limites numericos solicitados pela interface. */
    private void validateParameters(int size, int clueCount, int constraintCount) {
        if (size <= 0 || size % 2 != 0) {
            throw new IllegalArgumentException("o tamanho do tabuleiro deve ser positivo e par.");
        }

        int totalCells = size * size;
        if (clueCount < 0 || clueCount > totalCells) {
            throw new IllegalArgumentException("a quantidade de dicas deve estar entre 0 e " + totalCells + ".");
        }

        int maximumConstraints = 2 * size * (size - 1);
        if (constraintCount < 0 || constraintCount > maximumConstraints) {
            throw new IllegalArgumentException(
                    "a quantidade de restricoes deve estar entre 0 e " + maximumConstraints + ".");
        }
    }

    /** Par imutavel de celulas ortogonalmente adjacentes. */
    private record PositionPair(Position first, Position second) {
    }
}
