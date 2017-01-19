package edu.pse.beast.datatypes;

/**
 * 
 * @author Lukas
 *
 */
public class ElectionTypeContainer {
    private final InternalTypeContainer type;
    private int lowerBound;
    private int upperBound;
    
    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InternalTypeContainer type) {
        this.type = type;
    }
    
    /**
     * 
     * @return the type of this election
     */
    public InternalTypeContainer getType() {
        return type;
    }
    
    /**
     * 
     * @return the lowerBound of this election
     */
    public int getLowerBound() {
        return lowerBound;
    }
    
    /**
     * 
     * @return the upperBound of this election
     */
    public int getUpperBound() {
        return upperBound;
    }
    
    /**
     * 
     * @param lowerBound sets the lowerBound of this election
     */
    public void setLowerBound(int lowerBound) {
        this.lowerBound = lowerBound;
    }
    
    /**
     * 
     * @param upperBound sets the upperBound of this election
     */
    public void setUpperBound(int upperBound) {
        this.upperBound = upperBound;
    }
    
}
