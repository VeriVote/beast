package edu.pse.beast.electionsimulator;

import java.util.List;

import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;

/**
 * The Class InputDataDescription.
 */
public class InputDataDescription {

    /** The data. */
    private final List<List<String>> data;

    /** The in type. */
    private final InputType inType;

    /** The out type. */
    private final OutputType outType;

    /**
     * Instantiates a new input data description.
     *
     * @param dataList
     *            the data list
     * @param inputType
     *            the input type
     * @param outputType
     *            the output type
     */
    public InputDataDescription(final List<List<String>> dataList,
                                final InputType inputType,
                                final OutputType outputType) {
        this.data = dataList;
        this.inType = inputType;
        this.outType = outputType;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public List<List<String>> getData() {
        return data;
    }

    /**
     * Gets the input type.
     *
     * @return the input type
     */
    public InputType getInputType() {
        return inType;
    }

    /**
     * Gets the output type.
     *
     * @return the output type
     */
    public OutputType getOutputType() {
        return outType;
    }
}
