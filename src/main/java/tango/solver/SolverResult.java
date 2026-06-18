package tango.solver;

import tango.model.Board;

public class SolverResult {
    private final Board solvedBoard;
    private final long visitedStates;

    public SolverResult(Board solvedBoard, long visitedStates) {
        this.solvedBoard = solvedBoard;
        this.visitedStates = visitedStates;
    }

    public boolean hasSolution() {
        return solvedBoard != null;
    }

    public Board getSolvedBoard() {
        return solvedBoard;
    }

    public long getVisitedStates() {
        return visitedStates;
    }
}
