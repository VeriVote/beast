package edu.pse.beast.datatypes.electiondescription;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * Datatype for the Input and Output of an Election
 *
 * @author Lukas Stapelbroek
 *
 */
public class ElectionTypeContainer {
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

//    /**
//     * Constructor
//     * @param type the type of this election
//     */
//    public ElectionTypeContainer(InputType inType, OutputType outType,
//                                 int lowerBound, int upperBound) {
//        this.inType = inType;
//        this.outType = outType;
//        this.lowerBound = lowerBound;
//        this.upperBound = upperBound;
//    }

    /**
     * Constructor
     *
     * @param inType the inType of this election
     * @param outType the outType of this election
     */
    public ElectionTypeContainer(InputType inType, OutputType outType) {
        this.inType = inType;
        this.outType = outType;
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

    public void setInput(InputType inputType) {
        this.inType = inputType;
    }

    public void setOutput(OutputType outputType) {
        this.outType = outputType;
    }
}