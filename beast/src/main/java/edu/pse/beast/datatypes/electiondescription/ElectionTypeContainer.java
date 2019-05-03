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