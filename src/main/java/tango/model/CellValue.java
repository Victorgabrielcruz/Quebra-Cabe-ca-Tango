package tango.model;

/**
 * Valores que uma celula do tabuleiro pode assumir.
 *
 * {@link #VAZIO} representa uma decisao ainda nao tomada e nao um terceiro
 * simbolo do quebra-cabeca.
 */
public enum CellValue {
    VAZIO('.'),
    SOL('S'),
    LUA('L');

    private final char symbol;

    CellValue(char symbol) {
        this.symbol = symbol;
    }

    /**
     * Retorna o caractere usado nos arquivos e na impressao do tabuleiro.
     *
     * @return simbolo textual deste valor
     */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Indica se a celula ainda nao foi preenchida.
     *
     * @return {@code true} somente para {@link #VAZIO}
     */
    public boolean isEmpty() {
        return this == VAZIO;
    }

    /**
     * Alias em portugues para {@link #isEmpty()}.
     *
     * @return {@code true} somente para {@link #VAZIO}
     */
    public boolean isVazio() {
        return this == VAZIO;
    }

    /**
     * Converte um simbolo textual em valor de celula.
     *
     * @param symbol caractere {@code .}, {@code S} ou {@code L}; letras podem
     *        estar em maiusculo ou minusculo
     * @return valor correspondente ao simbolo
     * @throws IllegalArgumentException se o simbolo nao for reconhecido
     */
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
