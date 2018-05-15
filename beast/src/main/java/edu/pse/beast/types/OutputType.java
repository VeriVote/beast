package edu.pse.beast.types;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.types.cbmctypes.inputplugins.Approval;
import edu.pse.beast.types.cbmctypes.inputplugins.Preference;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoiceStack;
import edu.pse.beast.types.cbmctypes.inputplugins.WeightedApproval;
import edu.pse.beast.types.cbmctypes.outputtypes.Parliament;
import edu.pse.beast.types.cbmctypes.outputtypes.ParliamentStack;
import edu.pse.beast.types.cbmctypes.outputtypes.SingleCandidate;

public abstract class OutputType implements InOutType {

	private static List<InOutType> outputTypes = new ArrayList<InOutType>();
	
	static { //TODO let it load dynamically WORKAROUND so far
		outputTypes.add(new SingleCandidate());
		outputTypes.add(new Parliament());
		outputTypes.add(new ParliamentStack());
	}
	
	public static List<InOutType> getOutputTypes() {
		return outputTypes;
	}
	
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
	public abstract String[] extractResult(List<String> toExtract);

	public abstract List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract);

	public abstract List<CBMCResultWrapperLong> readElect(List<String> toExtract);

	public abstract CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code);

	public abstract CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber);
	
	public abstract String getCArrayType();

	/**
	 * returns the code with the added line of the marginmaintest method. 
	 * The method has to end with an assertion that lets cbmc fail, so we can extract the result
	 * @param code
	 * @param voteNumber
	 * @return
	 */
	public abstract CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber);

	public abstract List<String> getCodeToRunMargin(List<String> origResult, List<String> lastResult);

	public abstract List<String> getNewResult(List<String> lastFailedRun);

	public abstract InternalTypeContainer getInternalTypeContainer();

	public abstract void addVerifyOutput(CodeArrayListBeautifier code);

	public abstract void addLastResultAsCode(CodeArrayListBeautifier code, List<String> origResult);

	public abstract String getResultDescriptionString(List<String> result);
}