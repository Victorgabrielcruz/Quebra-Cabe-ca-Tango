package tango.solver;

/**
 * Estrategias de busca disponiveis na interface.
 */
public enum SolverType {
    /** Busca exaustiva que valida apenas combinacoes completas. */
    BRUTE_FORCE("forca-bruta"),
    /** Busca incremental que poda estados parciais invalidos. */
    BACKTRACKING("backtracking");

    /** Nome aceito pela interface de linha de comando. */
    private final String argumentName;

    /** Associa o tipo ao argumento textual. */
    SolverType(String argumentName) {
        this.argumentName = argumentName;
    }

    /**
     * Consulta o nome externo do algoritmo.
     *
     * @return nome aceito na linha de comando
     */
    public String getArgumentName() {
        return argumentName;
    }

    /**
     * Converte o nome informado pelo usuario em tipo de solver.
     *
     * @param value nome do algoritmo, sem diferenciar maiusculas e minusculas
     * @return tipo correspondente
     * @throws IllegalArgumentException se o nome for desconhecido
     */
    public static SolverType fromArgument(String value) {
        for (SolverType type : values()) {
            if (type.argumentName.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("algoritmo desconhecido: " + value);
    }
}
