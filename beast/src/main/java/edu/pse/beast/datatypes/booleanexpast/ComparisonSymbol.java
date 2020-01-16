package edu.pse.beast.datatypes.booleanexpast;

/**
 * The Class ComparisonSymbol.
 *
 * @author Lukas Stapelbroek
 */
public class ComparisonSymbol {

    /** The c string representation. */
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
     * Gets the c string rep.
     *
     * @return the representation of this symbol in C
     */
    public String getCStringRep() {
        return cStringRepresentation;
    }
}
