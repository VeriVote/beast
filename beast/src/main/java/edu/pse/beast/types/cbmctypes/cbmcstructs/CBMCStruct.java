package edu.pse.beast.types.cbmctypes.cbmcstructs;

import java.util.Arrays;

import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InOutType.DataType;

/**
 * The Class CBMCStruct.
 *
 * @author Lukas Stapelbroek
 */
public class CBMCStruct extends ComplexType {

    /** The Constant PRIME_THREE. */
    private static final int PRIME_THREE = 1237;

    /** The Constant PRIME_TWO. */
    private static final int PRIME_TWO = 1231;

    /** The Constant PRIME_ONE. */
    private static final int PRIME_ONE = 31;

    /** The in out type. */
    private final InOutType inOutType;

    /** The data type. */
    private final DataType dataType;

    /** The dimensions. */
    private final int dimensions;

    /** The size of dimensions. */
    private final String[] sizeOfDimensions;

    /** The unsigned. */
    private final boolean unsigned;

    /** The struct name. */
    private final String structName;

    /**
     * The constructor.
     *
     * @param inputOutputType
     *            the input output type
     */
    public CBMCStruct(final InOutType inputOutputType) {
        this.inOutType = inputOutputType;
        this.dataType = inputOutputType.getDataType();
        this.dimensions = inputOutputType.getAmountOfDimensions();
        this.sizeOfDimensions = inputOutputType.getSizeOfDimensions();
        this.unsigned = inputOutputType.isDataTypeUnsigned();
        String generatedName = "auto_"
                + this.dataType.toString().replaceAll("\\s", "");

        for (int i = 0; i < dimensions; i++) {
            generatedName = generatedName + sizeOfDimensions[i];
        }
        this.structName = generatedName;
    }

    @Override
    public int hashCode() {
        int result = 1;
        result = PRIME_ONE * result
                + ((dataType == null) ? 0 : dataType.hashCode());
        result = PRIME_ONE * result + dimensions;
        result = PRIME_ONE * result + Arrays.hashCode(sizeOfDimensions);
        result = PRIME_ONE * result + (unsigned ? PRIME_TWO : PRIME_THREE);
        return result;
    }

    @Override
    public boolean equals(final Object toCompare) {
        if (this == toCompare) {
            return true;
        }

        if (this.getClass().equals(toCompare.getClass())) {
            CBMCStruct otherStruct = (CBMCStruct) toCompare;
            return (this.dataType == otherStruct.dataType
                    && this.dimensions == otherStruct.dimensions
                    && Arrays.equals(this.sizeOfDimensions,
                            otherStruct.sizeOfDimensions)
                    && this.unsigned == otherStruct.unsigned);
        }
        return false;
    }

    @Override
    public String getStructDefinition() {
        String sign = "";
        if (inOutType.isDataTypeUnsigned()) {
            sign = "unsigned ";
        }
        return "struct " + structName + " { " + sign + inOutType.getDataType()
                + " " + UnifiedNameContainer.getStructValueName()
                + inOutType.getDimensionDescriptor(true) + ";};";
    }

    @Override
    public String getStructAccess() {
        return "struct " + structName;
    }
}
