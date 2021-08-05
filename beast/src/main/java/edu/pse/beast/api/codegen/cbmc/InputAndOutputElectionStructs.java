package edu.pse.beast.api.codegen.cbmc;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class InputAndOutputElectionStructs {
    private ElectionTypeCStruct input;
    private ElectionTypeCStruct output;

    public InputAndOutputElectionStructs(final ElectionTypeCStruct inputStruct,
                                         final ElectionTypeCStruct outputStruct) {
        super();
        this.input = inputStruct;
        this.output = outputStruct;
    }

    public final ElectionTypeCStruct getInput() {
        return input;
    }

    public final ElectionTypeCStruct getOutput() {
        return output;
    }
}
