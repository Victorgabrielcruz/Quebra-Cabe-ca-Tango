package tango.solver;

/**
 * Estrategias de busca disponiveis na interface.
 */
public enum SolverType {
    BRUTE_FORCE("forca-bruta"),
    BACKTRACKING("backtracking");

    private final String displayName;

    SolverType(String displayName) {
        this.displayName = displayName;
    }

    /**
     * Consulta o nome exibido para o algoritmo.
     *
     * @return nome exibido no terminal
     */
    public String getDisplayName() {
        return displayName;
    }
}
