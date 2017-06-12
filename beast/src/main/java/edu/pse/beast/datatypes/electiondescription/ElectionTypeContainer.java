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
    private ElectionInputTypeIds inID;
    private ElectionOutputTypeIds outID;
    
//    public enum ElectionTypeIds {
//        SINGLE_CHOICE,
//        PREFERENCE,
//        APPROVAL,
//        WEIGHTED_APPROVAL,
//        CAND_OR_UNDEF,
//        CAND_PER_SEAT
//    }
    
    
    public enum ElectionInputTypeIds {
        SINGLE_CHOICE,
        PREFERENCE,
        APPROVAL,
        WEIGHTED_APPROVAL,
    }
    
    public enum ElectionOutputTypeIds {
        CAND_OR_UNDEF,
        CAND_PER_SEAT
    }

    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InternalTypeContainer type, ElectionInputTypeIds inID, ElectionOutputTypeIds outID) {
        this.type = type;
        this.inID = inID;
        this.outID = outID;
        lowerBound = 0;
        upperBound = 100;
    }
    
    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InternalTypeContainer type, ElectionInputTypeIds inID) {
        this(type, inID, null);
    }
    
    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InternalTypeContainer type, ElectionOutputTypeIds outID) {
        this(type, null, outID);
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
     * @return the id of this input typecontainer
     */
    public ElectionInputTypeIds getInputId() {
        return inID;
    }

    /**
    *
    * @return the id of this output typecontainer
    */
   public ElectionOutputTypeIds getOutputId() {
       return outID;
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
