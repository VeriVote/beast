package edu.pse.beast.api.codegen.cbmc;

public class InputAndOutputElectionStructs {
    private ElectionTypeCStruct input;
    private ElectionTypeCStruct output;

    public InputAndOutputElectionStructs(final ElectionTypeCStruct inputStruct,
                                         final ElectionTypeCStruct outputStruct) {
        super();
        this.input = inputStruct;
        this.output = outputStruct;
    }

    public ElectionTypeCStruct getInput() {
        return input;
    }

    public ElectionTypeCStruct getOutput() {
        return output;
    }
}
