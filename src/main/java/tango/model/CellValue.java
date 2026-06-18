package tango.model;

public enum CellValue {
    VAZIO('.'),
    SOL('S'),
    LUA('L');

    private final char symbol;

    CellValue(char symbol) {
        this.symbol = symbol;
    }

    public char getSymbol() {
        return symbol;
    }

    public boolean isEmpty() {
        return this == VAZIO;
    }

    public boolean isVazio() {
        return this == VAZIO;
    }

    public static CellValue fromSymbol(char symbol) {
        char normalizedSymbol = Character.toUpperCase(symbol);

        for (CellValue value : values()) {
            if (value.symbol == normalizedSymbol) {
                return value;
            }
        }

        throw new IllegalArgumentException("simbolo de celula desconhecido: " + symbol);
    }
}
