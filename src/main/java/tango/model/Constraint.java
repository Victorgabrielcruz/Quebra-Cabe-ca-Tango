package tango.model;

/**
 * Restricao imutavel entre duas posicoes do tabuleiro.
 */
public class Constraint {
    /** Primeira extremidade da relacao. */
    private final Position firstPosition;
    /** Segunda extremidade da relacao. */
    private final Position secondPosition;
    /** Regra aplicada entre as extremidades. */
    private final ConstraintType type;

    /**
     * Cria uma restricao. Os limites e a adjacencia das posicoes sao validados
     * pelas camadas que conhecem o tabuleiro.
     *
     * @param firstPosition primeira celula envolvida
     * @param secondPosition segunda celula envolvida
     * @param type igualdade ou oposicao
     * @throws IllegalArgumentException se alguma posicao ou o tipo forem nulos
     */
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

    /**
     * Consulta a primeira extremidade.
     *
     * @return primeira posicao da restricao
     */
    public Position getFirstPosition() {
        return firstPosition;
    }

    /**
     * Consulta a segunda extremidade.
     *
     * @return segunda posicao da restricao
     */
    public Position getSecondPosition() {
        return secondPosition;
    }

    /**
     * Consulta a regra da relacao.
     *
     * @return tipo da relacao entre as posicoes
     */
    public ConstraintType getType() {
        return type;
    }
}
