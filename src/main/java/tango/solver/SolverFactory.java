package tango.solver;

public class SolverFactory {
    public Solver create(SolverType type) {
        return switch (type) {
            case BRUTE_FORCE -> new BruteForceSolver();
            case BACKTRACKING -> new BacktrackingSolver();
        };
    }
}
