package tango.solver;

import tango.model.Board;

/**
 * Resultado imutavel produzido por uma estrategia de busca.
 */
public class SolverResult {
    private final Board solvedBoard;
    private final long visitedStates;

    /**
     * Cria o resultado de uma execucao.
     *
     * @param solvedBoard primeira solucao encontrada, ou {@code null}
     * @param visitedStates quantidade de atribuicoes candidatas testadas
     */
    public SolverResult(Board solvedBoard, long visitedStates) {
        this.solvedBoard = solvedBoard;
        this.visitedStates = visitedStates;
    }

    /**
     * Indica a presenca de uma solucao.
     *
     * @return {@code true} quando um tabuleiro resolvido estiver presente
     */
    public boolean hasSolution() {
        return solvedBoard != null;
    }

    /**
     * Consulta a solucao encontrada.
     *
     * @return tabuleiro resolvido, ou {@code null} quando nao houver solucao
     */
    public Board getSolvedBoard() {
        return solvedBoard;
    }

    /**
     * Consulta a medida de trabalho da busca.
     *
     * @return quantidade de atribuicoes de Sol ou Lua testadas
     */
    public long getVisitedStates() {
        return visitedStates;
    }
}
