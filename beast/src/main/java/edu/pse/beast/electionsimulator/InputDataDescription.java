package edu.pse.beast.electionsimulator;

import java.util.List;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

public class InputDataDescription {
    private final List<List<String>> data;
    private final InputType inType;
    private final OutputType outType;

    public InputDataDescription(final List<List<String>> dataList,
                                final InputType inputType,
                                final OutputType outputType) {
        this.data = dataList;
        this.inType = inputType;
        this.outType = outputType;
    }

    public List<List<String>> getData() {
        return data;
    }

    public InputType getInputType() {
        return inType;
    }

    public OutputType getOutputType() {
        return outType;
    }
}
