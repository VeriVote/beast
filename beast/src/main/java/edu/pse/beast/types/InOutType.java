package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CBMCResultPresentationHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.valueContainer.ResultValue;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.ResultValue.ResultType;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.cbmctypes.cbmcstructs.CBMCStruct;

public abstract class InOutType {

	public enum DataType {
		CHAR("byte"), SHORT("short"), INT("int"), LONG("long"), DOUBLE("double");

		private final String text;

		/**
		 * @param text
		 */
		DataType(final String text) {
			this.text = text;
		}

		@Override
		public String toString() {
			return text;
		}
	}

	private final boolean unsigned;
	private final DataType dataType;
	private final int dimensions;
	private final String[] sizeOfDimensions;

	private ComplexType complexType;
	
	private ElectionTypeContainer container;

	public InOutType(boolean unsigned, DataType dataType, int dimensions, String[] sizeOfDimensions) {
		this.unsigned = unsigned;
		this.dataType = dataType;
		this.dimensions = dimensions;
		this.sizeOfDimensions = sizeOfDimensions;
	}

	public void setElectionTypeContainer(ElectionTypeContainer container) {
		this.container = container;
	}

	public boolean isDataTypeUnsigned() {
		return this.unsigned;
	}

	public DataType getDataType() {
		return this.dataType;
	}

	public String getDataTypeAsString() {
		return this.dataType.toString();
	}

	/**
	 *
	 * @return the dimensions of the array which holds the votes (e.g 1 for single
	 *         choice, 2 for approval, 0 for single candidate)
	 */
	public final int getAmountOfDimensions() {
		return dimensions;
	}

	/**
	 * 
	 * @return the size of all dimensions, null if it is 0 dimensional
	 */
	public String[] getSizeOfDimensions() {
		return sizeOfDimensions;
	}

	public List<String> getSizeOfDimensionsAsList() {
		return Arrays.asList(sizeOfDimensions);
	}

	/**
	 *
	 * @return returns a String containing the shape of the input object e.g "[" +
	 *         UnifiedNameContainer.getVoter() + "]" for single choice
	 */
	public final String getDimensionDescriptor(boolean includeSizes) {
		String toReturn = "";

		for (int i = 0; i < dimensions; i++) {
			String content = "";
			if (includeSizes) {
				content = sizeOfDimensions[i];
			}
			toReturn = toReturn + createSquareBrackets(content);
		}

		return toReturn;
	}

	public String getDataTypeAndSign() {
		String sign = "";

		if (unsigned) {
			sign = "unsigned ";
		}
		return sign + this.dataType;
	}

	public ElectionTypeContainer getContainer() {
		return container;
	}

	/**
	 * 
	 * @param content the content to be put in the bracketes
	 * @return e.g "[content]"
	 */
	private String createSquareBrackets(String content) {
		return "[" + content + "]";
	}

	/**
	 * 
	 * @return a string which, when written behind a variable which given type,
	 *         allows access to its values (e.g ".arr", if it is a struct in which
	 *         the value is stored in "arr"
	 */
	public final String accessValues(ElectionTypeContainer electionContainer) {
		if (dimensions == 0) {
			return ""; // zero dimensional dataTypes are not represented by structs
		} else {
			return this.getContainer().getNameContainer().getStructValueName();
		}
	}

	public String printArray(CBMCResultValueWrapper wrapper) {

		ResultValue resultValue = wrapper.getResultValue();

		if (resultValue.getResultType() == ResultType.STRUCT) {
			CBMCResultValueStruct struct = (CBMCResultValueStruct) resultValue;

			return printArray(struct.getResultVariable(getContainer().getNameContainer().getStructValueName()));
		}
		if (resultValue.getResultType() == ResultType.SINGLE) {

			CBMCResultValueSingle single = (CBMCResultValueSingle) resultValue;
			return single.getValue();

		} else if (resultValue.getResultType() == ResultType.ARRAY) {

			CBMCResultValueArray array = (CBMCResultValueArray) resultValue;

			List<CBMCResultValueWrapper> newValues = array.getValues();

			String subArray = "";

			for (int i = 0; i < array.getArraySize(); i++) {
				subArray = subArray + printArray(newValues.get(i)) + ",";
			}

			subArray = subArray.substring(0, subArray.length() - 1); // cut off the last ","

			subArray = "{" + subArray + "}";

			return subArray;

		} else {
			throw new IllegalArgumentException("Only single numbers arrays, and a struct of an array are allowed here");
		}
	}

	public String getAccessDimensions(List<String> filling) {
		String dimAccess = "";

		for (int i = 0; i < this.getAmountOfDimensions(); i++) {
			dimAccess = dimAccess + "[" + filling.get(i) + "]";
		}

		return dimAccess;
	}
	
	public String getFullVarAccess(String varName, List<String> filling) {
		return varName + "." + this.getContainer().getNameContainer().getStructValueName() + getAccessDimensions(filling);
	}

	public abstract InternalTypeContainer getInternalTypeContainer();

	/**
	 * A human readable representation of this type.
	 * 
	 * @return
	 */
	public abstract String otherToString();

	/**
	 * 
	 * @param result the result to be presented
	 * @param varNameMatcher TODO
	 * @param startY the y position to start the drawing at
	 * @return the bottom most y-position the presentation has
	 */
	public abstract List<String> drawResult(Result result, String varNameMatcher); //TODO extract to topmost position

	//public abstract List<String> drawResult(ResultValueWrapper wrapper, String varName);
	
	public abstract List<String> drawResult(ResultValueWrapper wrapper, String varName); // extract later to here if possible
	
	
	/**
	 * 
	 * @return a text describing everything the user needs to know about this type
	 *         (e.g description of structs...)
	 */
	public abstract String getInfo();

	/**
	 * so far only used for preference voting
	 *
	 * @param code       the code
	 * @param voteNumber the amount of votes
	 */
	public abstract void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, String valueName,
			List<String> loopVariables);
	

	public void setStruct(ComplexType complexType) {
		this.complexType = complexType;
	}
	
	public ComplexType getStruct() {
		return complexType;
	}
}
