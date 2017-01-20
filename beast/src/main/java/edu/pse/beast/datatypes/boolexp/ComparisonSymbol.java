package edu.pse.beast.datatypes.boolexp;

/**
 * 
 * @author Lukas
 *
 */
public class ComparisonSymbol {
    
    private final String cStringRepresentation;
    
    /**
     * Constructor
     * @param cStringRepresentation the representation of this symbol in C
     */
    public ComparisonSymbol(String cStringRepresentation) {
        this.cStringRepresentation = cStringRepresentation;
    }
    
    /**
     * 
     * @return the representation of this symbol in C
     */
    public String getCStringRep() {
        return cStringRepresentation;
    }
}
