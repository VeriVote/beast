package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CBMCResultPresentationHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValue;
import edu.pse.beast.toolbox.valueContainer.ResultValue.ResultType;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;

/**
 * The Class InOutType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class InOutType {
    /** The Constant LINE_BREAK. */
    private static final String LINE_BREAK = "\n";
    /** The Constant ARR. */
    private static final String ARR = "arr";

    /**
     * The Enum DataType.
     */
    public enum DataType {

        /** The char. */
        CHAR("byte"),

        /** The short. */
        SHORT("short"),

        /** The int. */
        INT("int"),

        /** The long. */
        LONG("long"),

        /** The double. */
        DOUBLE("double");

        /** The text. */
        private final String text;

        /**
         * Instantiates a new data type.
         *
         * @param textString
         *            the text string
         */
        DataType(final String textString) {
            this.text = textString;
        }

        /**
         * To string.
         *
         * @return the string
         */
        @Override
        public String toString() {
            return text;
        }
    }

    /** The unsigned. */
    private final boolean unsigned;

    /** The data type. */
    private final DataType dataType;

    /** The dimensions. */
    private final int dimensions;

    /** The size of dimensions. */
    private final String[] sizeOfDimensions;

    /** The complex type. */
    private transient ComplexType complexType;

    /** The container. */
    private transient ElectionTypeContainer container;

    /**
     * The constructor.
     *
     * @param usigned
     *            the usigned
     * @param datType
     *            the dat type
     * @param dims
     *            the dims
     * @param sizeOfDims
     *            the size of dims
     */
    public InOutType(final boolean usigned, final DataType datType,
                     final int dims, final String[] sizeOfDims) {
        this.unsigned = usigned;
        this.dataType = datType;
        this.dimensions = dims;
        this.sizeOfDimensions = sizeOfDims;
    }

    /**
     * Sets the election type container.
     *
     * @param elecTypeContainer
     *            the new election type container
     */
    public void setElectionTypeContainer(final ElectionTypeContainer elecTypeContainer) {
        this.container = elecTypeContainer;
    }

    /**
     * Checks if is data type unsigned.
     *
     * @return true, if is data type unsigned
     */
    public boolean isDataTypeUnsigned() {
        return this.unsigned;
    }

    /**
     * Gets the data type.
     *
     * @return the data type
     */
    public DataType getDataType() {
        return this.dataType;
    }

    /**
     * Gets the data type as string.
     *
     * @return the data type as string
     */
    public String getDataTypeAsString() {
        return this.dataType.toString();
    }

    /**
     * Gets the amount of dimensions.
     *
     * @return the dimensions of the array which holds the votes (e.g 1 for
     *         single choice, 2 for approval, 0 for single candidate)
     */
    public final int getAmountOfDimensions() {
        return dimensions;
    }

    /**
     * Gets the size of dimensions.
     *
     * @return the size of all dimensions, null if it is 0 dimensional
     */
    public String[] getSizeOfDimensions() {
        // it is important to clone the array,
        // as it should not be changed
        return sizeOfDimensions.clone();
    }

    /**
     * Gets the size of dimensions as list.
     *
     * @return the size of dimensions as list
     */
    public List<String> getSizeOfDimensionsAsList() {
        return Arrays.asList(sizeOfDimensions);
    }

    /**
     * Gets the dimension descriptor.
     *
     * @param includeSizes
     *            the include sizes
     * @return returns a String containing the shape of the input object e.g "["
     *         + UnifiedNameContainer.getVoter() + "]" for single choice
     */
    public String getDimensionDescriptor(final boolean includeSizes) {
        return getDimensionDescriptor(sizeOfDimensions);
    }

    /**
     * Gets the dimension descriptor.
     *
     * @param sizes
     *            the sizes
     * @return the dimension descriptor
     */
    public String getDimensionDescriptor(final String[] sizes) {
        String toReturn = "";
        for (int i = 0; i < dimensions; i++) {
            String content = sizes[i];
            toReturn += createSquareBrackets(content);
        }
        return toReturn;
    }

    /**
     * Gets the data type and sign.
     *
     * @return the data type and sign
     */
    public String getDataTypeAndSign() {
        String sign = "";
        if (unsigned) {
            sign = "unsigned ";
        }
        return sign + this.dataType;
    }

    /**
     * Gets the container.
     *
     * @return the container
     */
    public ElectionTypeContainer getContainer() {
        return container;
    }

    /**
     * Creates the square brackets.
     *
     * @param content
     *            the content to be put in the brackets
     * @return e.g "[content]"
     */
    private String createSquareBrackets(final String content) {
        return "[" + content + "]";
    }

    /**
     * Access values.
     *
     * @param electionContainer
     *            the election container
     * @return a string which, when written behind a variable which given type,
     *         allows access to its values (e.g ".arr", if it is a struct in
     *         which the value is stored in "arr"
     */
    public final String accessValues(final ElectionTypeContainer electionContainer) {
        if (dimensions == 0) {
            // zero-dimensional dataTypes are not represented by structs
            return "";
        } else {
            return UnifiedNameContainer.getStructValueName();
        }
    }

    /**
     * Prints the array.
     *
     * @param wrapper
     *            the wrapper
     * @return the string
     */
    public String printArray(final CBMCResultValueWrapper wrapper) {
        ResultValue resultValue = wrapper.getResultValue();

        if (resultValue.getResultType() == ResultType.STRUCT) {
            CBMCResultValueStruct struct = (CBMCResultValueStruct) resultValue;
            return printArray(
                    struct.getResultVariable(
                            UnifiedNameContainer.getStructValueName()
                    )
            );
        } else if (resultValue.getResultType() == ResultType.SINGLE) {
            CBMCResultValueSingle single = (CBMCResultValueSingle) resultValue;
            return single.getValue();
        } else if (resultValue.getResultType() == ResultType.ARRAY) {
            CBMCResultValueArray array = (CBMCResultValueArray) resultValue;
            List<CBMCResultValueWrapper> newValues = array.getValues();
            String subArray = "";
            for (int i = 0; i < array.getArraySize(); i++) {
                subArray += printArray(newValues.get(i)) + ",";
            }
            // cut off the last ","
            subArray = subArray.substring(0, subArray.length() - 1);
            subArray = "{" + subArray + "}";
            return subArray;
        } else {
            throw new IllegalArgumentException("Only single numbers arrays,"
                                                + " and a struct of an array"
                                                + " are allowed here");
        }
    }

    /**
     * Gets the access dimensions.
     *
     * @param filling
     *            the filling
     * @return the access dimensions
     */
    public String getAccessDimensions(final List<String> filling) {
        String dimAccess = "";
        for (int i = 0; i < this.getAmountOfDimensions(); i++) {
            dimAccess = dimAccess + "[" + filling.get(i) + "]";
        }
        return dimAccess;
    }

    /**
     * Gets the full var access.
     *
     * @param varName
     *            the var name
     * @param filling
     *            the filling
     * @return the full var access
     */
    public String getFullVarAccess(final String varName,
                                   final List<String> filling) {
        return varName + "." + UnifiedNameContainer.getStructValueName()
                + getAccessDimensions(filling);
    }

    /**
     * Gets the internal type container.
     *
     * @return the internal type container
     */
    public abstract InternalTypeContainer getInternalTypeContainer();

    /**
     * A human readable representation of this type.
     *
     * @return the string
     */
    public abstract String otherToString();

    // public abstract List<String> drawResult(ResultValueWrapper wrapper,
    // String
    // varName);

    /**
     * Draw result.
     *
     * @param result
     *            the result
     * @param varNameMatcher
     *            the var name matcher
     * @param sizes
     *            the sizes
     * @return the list
     */
    public List<String> drawResult(final Result result,
                                   final String varNameMatcher,
                                   final Map<Integer, Long> sizes) {
        List<String> toReturn = new ArrayList<String>();
        List<ResultValueWrapper> votes = // TODO name container
                result.readVariableValue(varNameMatcher);

        for (final ResultValueWrapper currentVar : votes) {
            long size = sizes.get(currentVar.getMainIndex());
            String name = currentVar.getName();
            toReturn.add(name + LINE_BREAK);
            CBMCResultValueStruct struct =
                    (CBMCResultValueStruct) currentVar.getResultValue();
            if (getAmountOfDimensions() == 2) {
                CBMCResultValueArray arr =
                        (CBMCResultValueArray) struct
                            .getResultVariable(ARR).getResultValue();
                toReturn.addAll(CBMCResultPresentationHelper
                        .printTwoDimResult(arr, size, name.length()));
            } else if (getAmountOfDimensions() == 1) {
                CBMCResultValueArray arr = (CBMCResultValueArray) struct
                        .getResultVariable(ARR).getResultValue();
                toReturn.add(
                        CBMCResultPresentationHelper.printOneDimResult(
                                arr, size, name.length()
                        )
                );
            } else if (getAmountOfDimensions() == 0) {
                toReturn.add(
                        CBMCResultPresentationHelper.printSingleElement(
                                (CBMCResultValueSingle)
                                    struct.getResultVariable(ARR).getResultValue(),
                                name.length()
                        )
                );
            }
        }
        return toReturn;
    }

    /**
     * Draw result.
     *
     * @param wrapper
     *            the wrapper
     * @param varName
     *            the var name
     * @param size
     *            the size
     * @return the list
     */
    public List<String> drawResult(final ResultValueWrapper wrapper,
                                   final String varName,
                                   final Long size) {
        List<String> toReturn = new ArrayList<String>();
        toReturn.add(varName);
        CBMCResultValueStruct struct =
                (CBMCResultValueStruct) wrapper.getResultValue();

        if (getAmountOfDimensions() == 2) {
            CBMCResultValueArray arr =
                    (CBMCResultValueArray) struct.getResultVariable(ARR).getResultValue();
            toReturn.addAll(
                    CBMCResultPresentationHelper.printTwoDimResult(
                            arr, size, varName.length()
                    )
            );
        } else if (getAmountOfDimensions() == 1) {
            CBMCResultValueArray arr =
                    (CBMCResultValueArray) struct.getResultVariable(ARR).getResultValue();
            toReturn.add(
                    CBMCResultPresentationHelper.printOneDimResult(
                            arr, size, varName.length()
                    )
            );
        } else if (getAmountOfDimensions() == 0) {
            toReturn.add(
                    CBMCResultPresentationHelper.printSingleElement(
                            (CBMCResultValueSingle) struct
                                    .getResultVariable(ARR).getResultValue(),
                            varName.length()));
        }
        return toReturn;
    }

    /**
     * Gets the info.
     *
     * @return a text describing everything the user needs to know about this
     *         type (e.g description of structs...)
     */
    public abstract String getInfo();

    /**
     * So far only used for preference voting.
     *
     * @param code
     *            the code
     * @param valueName
     *            the value name
     * @param loopVariables
     *            the loop variables
     */
    public abstract void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code,
                                                     String valueName,
                                                     List<String> loopVariables);

    /**
     * Sets the struct.
     *
     * @param complType
     *            the new struct
     */
    public void setStruct(final ComplexType complType) {
        this.complexType = complType;
    }

    /**
     * Gets the struct.
     *
     * @return the struct
     */
    public ComplexType getStruct() {
        return complexType;
    }
}
