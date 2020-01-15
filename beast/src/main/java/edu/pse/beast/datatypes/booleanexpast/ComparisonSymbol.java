package edu.pse.beast.datatypes.booleanexpast;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public class ComparisonSymbol {
    private final String cStringRepresentation;

    /**
     * Constructor.
     *
     * @param cStringRepr the representation of this symbol in C
     */
    public ComparisonSymbol(final String cStringRepr) {
        this.cStringRepresentation = cStringRepr;
    }

    /**
     *
     * @return the representation of this symbol in C
     */
    public String getCStringRep() {
        return cStringRepresentation;
    }
}
