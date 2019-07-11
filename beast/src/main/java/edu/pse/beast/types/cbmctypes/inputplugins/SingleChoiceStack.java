package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.codearea.errorhandling.ErrorDisplayer;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CBMCResultPresentationHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.ErrorForUserDisplayer;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

public class SingleChoiceStack extends CBMCInputType {

	private static final int dimensions = 1;

	private final static String[] sizeOfDimensions = { UnifiedNameContainer.getCandidate() };

	public SingleChoiceStack() {
		super(true, DataType.INT, dimensions, sizeOfDimensions);
	}

	@Override
	public String getInputIDinFile() {
		return "SINGLE_CHOICE_STACK";
	}

	@Override
	public String getMinimalValue() {
		return "0";
	}

	@Override
	public String getMaximalValue() {
		return UnifiedNameContainer.getVoter();
	}

	@Override
	public boolean hasVariableAsMinValue() {
		return false;
	}

	@Override
	public boolean hasVariableAsMaxValue() {
		return true;
	}

	@Override
	public boolean isVotingForOneCandidate() {
		return true;
	}

	@Override
	public void addVerifyMethod(CodeArrayListBeautifier code, OutputType outType) {
		code.add("void verify() {");
		code.addTab();
		code.add("int total_diff = 0;");
		code.add("int pos_diff = 0;");
		code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" + UnifiedNameContainer.getCandidate() + "];");
		code.add("int diff[" + UnifiedNameContainer.getCandidate() + "];");
		// go over all voters
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		code.addTab();
		code.add("diff[i] = nondet_int();");
		code.add("assume(-1 * MARGIN <= diff[i]);");
		code.add("assume(diff[i] <= MARGIN);");
		code.add("assume(0 <= ORIG_VOTES[i] + diff[i]);");
		code.deleteTab();
		code.add("}");
		// go over all voters
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		code.addTab();

		code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i] = ORIG_VOTES[i] + diff[i];");
		code.add("if (0 < diff[i]) pos_diff += diff[i];");
		code.add("total_diff += diff[i];");

		code.deleteTab();
		code.add("}");

		code.add("assume(pos_diff <= MARGIN);");
		code.add("assume(total_diff == 0);");

		outType.addVerifyOutput(code);

		code.deleteTab();
		code.add("}"); // end of the function
	}

	@Override
	public String vetValue(String newValue, int position, ElectionTypeContainer container, NEWRowOfValues row) {
		final int number;
		try {
			number = Integer.parseInt(newValue);
		} catch (NumberFormatException e) {
			return "0";
		}
		if (number < 0 || number > row.getAmountVoters()) {
			return "0";
		} else {
			int newValueInt = Integer.parseInt(newValue);

			List<String> values = row.getValues();

			int sum = 0;
			for (int i = 0; i < values.size(); i++) {
				if (i != position) {
					sum = sum + Integer.parseInt(values.get(i));
				}
			}

			if ((sum + newValueInt) > row.getAmountCandidates()) {
				return "0";
			} else {
				return newValue;
			}
		}
	}

	@Override
	public String[] getVotePoints(String[][] votes, int amountCandidates, int amountVoters) {
		return super.wrongInputTypeArray(amountCandidates, amountVoters);
	}

	@Override
	public String[] getVotePoints(String[] votes, int amountCandidates, int amountVoters) {
		Long[] result = new Long[amountCandidates];
		Arrays.fill(result, 0L);
		for (int i = 0; i < amountVoters; i++) {
			int vote = Integer.parseInt(votes[i]);
			result[vote]++;

		}
		String[] toReturn = new String[amountCandidates];
		for (int i = 0; i < result.length; i++) {
			toReturn[i] = "" + result[i];
		}
		return toReturn;
	}

	@Override
	public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, int voteNumber) {
	}

	@Override
	public void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique) {
		code.add("if(arr[i] == candidate) sum++;");
	}

	@Override
	public InternalTypeContainer getInternalTypeContainer() {
		return new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
	}

	@Override
	public int vetAmountCandidates(int amountCandidates) {
		if (amountCandidates < 1) {
			return 1;
		} else {
			return amountCandidates;
		}
	}

	@Override
	public int vetAmountVoters(int amountVoters) {
		return 1;
	}

	@Override
	public int vetAmountSeats(int amountSeats) {
		if (amountSeats < 1) {
			return 1;
		} else {
			return amountSeats;
		}
	}

	@Override
	public int getNumVotingPoints(ResultValueWrapper result) {
		int sum = 0;
//        for (int i = 0; i < votingData.length; i++) {
//            for (int j = 0; j < votingData[0].length; j++) {
//                sum = sum + Integer.parseInt(votingData[i][j]);
//            }
//        }

		System.out.println("fix: SingleChoiceStack:");

		return sum;
	}

	public int test(int i) {

		return 5;
	}

	@Override
	public String otherToString() {
		return "Single choice stack";
	}

	@Override
	public List<String> drawResult(Result result) {
		List<String> toReturn = new ArrayList<String>();

		List<ResultValueWrapper> votes = result.readVariableValue("votes\\d"); // TODO name container

		for (ResultValueWrapper currentVote : votes) {

			String name = currentVote.getName();

			toReturn.add(name);

			CBMCResultValueStruct struct = (CBMCResultValueStruct) currentVote.getResultValue();
			CBMCResultValueArray arr = (CBMCResultValueArray) struct.getResultVariable("arr").getResultValue();

			toReturn.add(CBMCResultPresentationHelper.printOneDimResult(arr, name.length()));
		}
		return toReturn;
	}

	@Override
	public CBMCResultValue convertRowToResultValue(NEWRowOfValues row) {
		
		ErrorForUserDisplayer.displayError("Single choice stack is not yet functioning with Margin comp");
		
		throw new IllegalArgumentException(); //TODO
//		
//		List<String> values = row.getValues();
//
//		String foundValue = "0";
//
//		for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
//			String value = (String) iterator.next();
//
//			if (!value.equals("0")) {
//				foundValue = "0";
//			}
//		}
//
//		CBMCResultValueSingle toReturn = new CBMCResultValueSingle();
//
//		toReturn.setValue("int", foundValue, 32);
//
//		return toReturn;
	}
}