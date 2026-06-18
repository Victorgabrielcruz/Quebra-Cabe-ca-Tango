package tango.model;

public class Constraint {
    private final Position firstPosition;
    private final Position secondPosition;
    private final ConstraintType type;

    public Constraint(Position firstPosition, Position secondPosition, ConstraintType type) {
        if (firstPosition == null || secondPosition == null) {
            throw new IllegalArgumentException("as posicoes da restricao nao podem ser nulas.");
        }

        if (type == null) {
            throw new IllegalArgumentException("o tipo da restricao nao pode ser nulo.");
        }

        this.firstPosition = firstPosition;
        this.secondPosition = secondPosition;
        this.type = type;
    }

    public Position getFirstPosition() {
        return firstPosition;
    }

    public Position getSecondPosition() {
        return secondPosition;
    }

    public ConstraintType getType() {
        return type;
    }
}
