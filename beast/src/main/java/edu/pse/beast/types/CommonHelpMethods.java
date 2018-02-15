package edu.pse.beast.types;

import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;

public abstract class CommonHelpMethods {
	
	/**
	 * extracts a two dimensional variable for the given checker from the output
	 * list
	 * 
	 * @param toExtract
	 *            the list to extract from
	 * @param name
	 *            the name of the variable
	 * @return a two dim wrapper of the voting results
	 */
	public abstract List<CBMCResultWrapperMultiArray> readTwoDimVarLong(String name, List<String> toExtract);

	/**
	 * reads a symbolic variable with a given name from a list
	 * 
	 * @param name
	 * @param toExtract
	 * @return
	 */
	public abstract Long readSymbolicVariable(String name, List<String> toExtract);

	/**
	 * extracts a long variable out of the output from cbmc
	 * 
	 * @param name
	 *            the name of the variable
	 * @param toExtract
	 *            all the lines that should get checked, it they contain the
	 *            searched var
	 * @return a list of all occurrences
	 */
	public abstract List<CBMCResultWrapperLong> readLongs(String name, List<String> toExtract);

	/**
	 * this method is used to extract
	 * 
	 * @param name
	 *            the name of the saved variable
	 * @param toExtract
	 *            the string list to extract the variable out of
	 * @return a list of all variables with a matching name with their index and
	 *         values that occured in the give list
	 */
	public abstract List<CBMCResultWrapperSingleArray> readOneDimVarLong(String name, List<String> toExtract);
}
