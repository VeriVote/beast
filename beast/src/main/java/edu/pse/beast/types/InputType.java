package edu.pse.beast.types;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;

public abstract class InputType {
	
	protected CommonHelpMethods helper;
	
	public InputType() {
		getHelper();
	}

	protected abstract void getHelper();

	/**
	 * returns a String containing the shape of the input object
	 * e.g "[V]" for single choice 
	 * @return
	 */
	public abstract String getInputString();

	/**
	 * 
	 * @return the ID used by this type in the String resources
	 */
	public abstract String getInputIDinFile();

	/**
	 * 
	 * @param container 
	 * @return the minimal value a voter can assign
	 */
	public abstract String getMinimalValue(ElectionTypeContainer container);

	/**
	 * 
	 * @param container 
	 * @return the maximal value a voter can assign
	 */
	public abstract String getMaximalValue(ElectionTypeContainer container);

	/**
	 * 
	 * @return true, if one voter can only vote for one candidate
	 */
	public abstract boolean isVotingForOneCandidate();

	/**
	 * adds the headers needed for the specified checker
	 * @param code the list to which the headers should be added to
	 * @return the list containing the additions
	 */
	public abstract List<String> addCheckerSpecificHeaders(List<String> code);

	/**
	 * adds the verify method to the code list
	 * @param code the list with the previous code
	 * @param multiOut boolean, whether we have a single output candidate or a struct
	 * @return the code with the added verify method
	 */
	public abstract List<String> addVerifyMethod(List<String> code, boolean multiOut);

	/**
	 * 
	 * @return true, if the input is two dimensional, else false
	 */
	public abstract boolean isTwoDim();

	/**
	 * extracts the voting data out of the given list of strings into a wrapper
	 * @param result the result of the computation from which the values will be extracted
	 * @return a wrapper which contains the values
	 */
	public abstract CBMCResultWrapperMultiArray extractVotesWrappedMulti(List<String> result, int numberCandidates);

	/**
	 * vets a value to determine if it is legal for the input type, or not
	 * @param newValue
	 * @param container
	 * @param rowOfValues 
	 * @return
	 */
	public abstract String vetValue(String newValue, ElectionTypeContainer container, RowOfValues rowOfValues);

	public abstract List<CBMCResultWrapperMultiArray> readVoteList(List<String> toExtract);

	public abstract List<CBMCResultWrapperSingleArray> readSingleVoteList(List<String> toExtract);
	
	public String[] wrongInputTypeArray(int amountCandidates, int amountVoters) {
		String [] toReturn = new String[amountCandidates];
		Arrays.fill(toReturn, "wrong input type");
		return toReturn;
	}

	public abstract String[] getVotePoints(String[][] votes, int amountCandidates, int amountVoters);

	public abstract String[] getVotePoints(String[] votes, int amountCandidates, int amountVoters);

	public abstract CodeArrayListBeautifier addMarginMainCheck(CodeArrayListBeautifier code, int margin, List<String> origResult);
	
	public abstract List<String> getVotingResultCode(String[][] votingData);

	/**
	 * 
	 * @return the type of the array that this input type want, e.g [] for single_choice, or [][] for Approval
	 */
	public abstract String getArrayType();
	
	/**
	 * 
	 * @return the dimension of the array which holds the votes (e.g 1 for single choice, 2 for approval)
	 */
	public abstract int getDimension();

	/**
	 * so far only used for preference voting
	 * @param code
	 * @param voteNumber
	 * @return
	 */
	public abstract CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber);

	public abstract CodeArrayListBeautifier getCodeForVoteSum(CodeArrayListBeautifier code, boolean unique);

	public abstract List<List<String>> getNewVotes(List<String> lastFailedRun);

}
