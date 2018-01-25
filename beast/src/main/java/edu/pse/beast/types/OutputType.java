package edu.pse.beast.types;

import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;

public abstract class OutputType {

	protected CommonHelpMethods helper;
	
	public OutputType() {
		getHelper();
	}

	protected abstract void getHelper();
	
	/**
	 * 
	 * @return A string which describes the output type of the election 
	 * in C. e.g "struct result"
	 */
	public abstract String getOutputString();

	/**
	 * 
	 * @return the ID this output type uses in the string resources
	 */
	public abstract String getOutputIDinFile();

	/**
	 * 
	 * @return true, if the output is just one candidate
	 */
	public abstract boolean isOutputOneCandidate();

	/**
	 * extracts the result from the given list
	 * @param toExtract the list from which the result will be extracted
	 * @return an array with the Result
	 */
	public abstract Long[] extractResult(List<String> toExtract);

	public abstract List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract);

	public abstract List<CBMCResultWrapperLong> readElect(List<String> toExtract);

}
