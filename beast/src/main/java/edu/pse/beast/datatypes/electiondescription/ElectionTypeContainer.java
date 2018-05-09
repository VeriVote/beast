package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * Datatype for the Input and Output of an Election
 * @author Lukas
 *
 */
public class ElectionTypeContainer {
    //private final InternalTypeContainer type;
    private int lowerBound;
    private int upperBound;
    private InputType inType;
    private OutputType outType;
//    private ElectionInputTypeIds inID;
//    private ElectionOutputTypeIds outID;
    
//    public static enum ElectionInputTypeIds {
//        SINGLE_CHOICE,
//        PREFERENCE,
//        APPROVAL,
//        WEIGHTED_APPROVAL
//    }
    
//    public static enum ElectionOutputTypeIds {
//        CAND_OR_UNDEF,
//        CAND_PER_SEAT
//    }

    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InputType inType, OutputType outType, int lowerBound, int upperBound) {
        this.inType = inType;
        this.outType = outType;
        this.lowerBound = lowerBound;
        this.upperBound = upperBound;
    }
    
    /**
     * Constructor
     * @param type the type of this election
     */
    public ElectionTypeContainer(InputType inType, OutputType outType) {
    	this(inType, outType, 0, 100);
    }

    
//    /**
//     * 
//     * @return the type of this election
//     */
//    public InternalTypeContainer getType() {
//        return type;
//    }
    
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
    public InputType getInputType() {
        return inType;
    }

    /**
    *
    * @return the id of this output typecontainer
    */
   public OutputType getOutputType() {
       return outType;
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

	public void setInput(InputType inputType) {
		this.inType = inputType;
	}

	public void setOutput(OutputType outputType) {
		this.outType = outputType;
	}
}
