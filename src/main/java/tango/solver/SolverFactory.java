package tango.solver;

/**
 * Centraliza a criacao da implementacao associada a cada {@link SolverType}.
 */
public class SolverFactory {
    public SolverFactory() {
    }

    /**
     * Cria um novo solver do tipo solicitado.
     *
     * @param type estrategia escolhida
     * @return implementacao pronta para uma execucao
     */
    public Solver create(SolverType type) {
        return switch (type) {
            case BRUTE_FORCE -> new BruteForceSolver();
            case BACKTRACKING -> new BacktrackingSolver();
        };
    }
}
