package tango.solver;

import tango.model.Board;

public interface Solver {
    SolverResult solve(Board initialBoard);
}
