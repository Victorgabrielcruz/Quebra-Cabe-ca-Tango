package tango.solver;

import tango.model.Board;

/**
 * Contrato comum para estrategias de resolucao do Tango.
 */
public interface Solver {
    /**
     * Procura a primeira solucao valida preservando o tabuleiro recebido.
     *
     * @param initialBoard configuracao inicial e suas restricoes
     * @return resultado com solucao opcional e numero de estados visitados
     */
    SolverResult solve(Board initialBoard);
}
