package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;

/**
 * Datatype for the Input and Output of an Election
 * @author Lukas
 *
 */
public class ElectionTypeContainer {
    private final InternalTypeContainer type;
    private int lowerBound;
    private int upperBound;
    private ElectionTypeIds id;

    public enum ElectionTypeIds {
        SINGLE_CHOICE,
        PREFERENCE,
        APPROVAL,
        WEIGHTED_APPROVAL,
        CAND_OR_UNDEF,
        CAND_PER_SEAT
    }

    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InternalTypeContainer type, ElectionTypeIds id) {
        this.type = type;
        this.id = id;
        lowerBound = 0;
        upperBound = 5;
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
     * @return the id of this typecontainer
     */
    public ElectionTypeIds getId() {
        return id;
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
