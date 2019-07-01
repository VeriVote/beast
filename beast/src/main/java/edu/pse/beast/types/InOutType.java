package edu.pse.beast.types;

import java.util.List;

import edu.pse.beast.propertychecker.Result;

public abstract class InOutType {

	private final String dataType;
	private final int dimensions;
	private final String[] sizeOfDimensions;

	public InOutType(String dataType, int dimensions, String[] sizeOfDimensions) {
		this.dataType = dataType;
		this.dimensions = dimensions;
		this.sizeOfDimensions = sizeOfDimensions;
	}

	/**
	 * 
	 * @param dimension one indexed, retrieves the size of the given dimension
	 * @return the size of the given dimensions, "ERROR", when dimensions < 1,
	 *         dimension > max amount dimensions
	 */
	public final String getSizeOfDimension(int dimension) {
		if (dimension > dimension || dimension < 1) {
			return "ERROR";
		}

		return sizeOfDimensions[dimension - 1];
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
	 * @return a string which, when written behind a variable which given type,
	 *         allows access to its values (e.g ".arr", if it is a struct in which
	 *         the value is stored in "arr"
	 */
	public abstract String accessValues();

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
   
	/**
	 *
	 * @return returns a String containing the shape of the input object e.g "[" +
     * UnifiedNameContainer.getVoter() + "]" for single choice
	 */
	public abstract String getSimpleType();
	
	/**
	 *
	 * @return returns a String containing the shape of the input object e.g "struct vote_single" for single choice
	 */
	public abstract String getComplexType();
}
