package tango.model;

/**
 * Tipos de relacao permitidos entre duas celulas vizinhas.
 */
public enum ConstraintType {
    /** Exige que as duas celulas possuam o mesmo valor. */
    EQUAL('='),
    /** Exige que as duas celulas possuam valores opostos. */
    OPPOSITE('x');

    /** Caractere persistido para o tipo. */
    private final char symbol;

    /** Associa o tipo ao simbolo usado nos arquivos. */
    ConstraintType(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Retorna o simbolo usado no arquivo de entrada.
     *
     * @return {@code =} ou {@code x}
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Converte um simbolo textual em tipo de restricao.
     *
     * @param symbol simbolo {@code =}, {@code x} ou {@code X}
     * @return tipo correspondente
     * @throws IllegalArgumentException se o simbolo for desconhecido
     */
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
