package edu.pse.beast.types.cbmctypes.cbmcstructs;

import java.util.Arrays;

import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.ComplexType;
import edu.pse.beast.types.InOutType;
import edu.pse.beast.types.InOutType.DataType;

public class CBMCStruct extends ComplexType {
    private static final int PRIME_THREE = 1237;
    private static final int PRIME_TWO = 1231;
    private static final int PRIME_ONE = 31;
    private final InOutType inOutType;
    private final DataType dataType;
    private final int dimensions;
    private final String[] sizeOfDimensions;
    private final boolean unsigned;
    private final String structName;

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

    /**
     *
     * @return the string which defines this struct
     */
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
