package tango.model;

public enum ConstraintType {
    EQUAL('='),
    OPPOSITE('x');

    private final char symbol;

    ConstraintType(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public static ConstraintType fromSymbol(char symbol) {
        char normalizedSymbol = Character.toLowerCase(symbol);

        for (ConstraintType type : values()) {
            if (type.symbol == normalizedSymbol) {
                return type;
            }
        }

        throw new IllegalArgumentException("simbolo de restricao desconhecido: " + symbol);
    }
}
