package tango.solver;

public enum SolverType {
    BRUTE_FORCE("forca-bruta"),
    BACKTRACKING("backtracking");

    private final String argumentName;

    SolverType(String argumentName) {
        this.argumentName = argumentName;
    }

    public String getArgumentName() {
        return argumentName;
    }

    public static SolverType fromArgument(String value) {
        for (SolverType type : values()) {
            if (type.argumentName.equalsIgnoreCase(value)) {
                return type;
            }
        }

        throw new IllegalArgumentException("algoritmo desconhecido: " + value);
    }
}
