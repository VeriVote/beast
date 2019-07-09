package edu.pse.beast.types;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.propertychecker.Result;

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
	
	/**
	 *
	 * @return returns a String containing the shape of the input object e.g "[" +
    * UnifiedNameContainer.getVoter() + "]" for single choice
	 */
	public final String getDimensionDescriptor(boolean includeSizes) {	
		String toReturn = "";
		
		for(int i = 0; i < dimensions; i++) {
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
			return ""; //zero dimensional dataTypes are not represented by structs
		} else {
			return electionContainer.getNameContainer().getResultArrName();
		}
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
	 * @param startY the y position to start the drawing at
	 * @return the bottom most y-position the presentation has
	 */
	public abstract List<String> drawResult(Result result);

	/**
	 * 
	 * @return a text describing everything the user needs to know about this type
	 *         (e.g description of structs...)
	 */
	public abstract String getInfo();
}
