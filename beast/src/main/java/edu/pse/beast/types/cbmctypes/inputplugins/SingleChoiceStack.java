package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
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
	public String vetValue(ElectionTypeContainer container, List<NEWRowOfValues> rows, int rowNumber, int positionInRow,
			String newValue) {
		
		if (rows.size() <= rowNumber) {
			return newValue;
		}
		
		final int number;
		try {
			number = Integer.parseInt(newValue);
		} catch (NumberFormatException e) {
			return "0";
		}
		if (number < 0 || number > rows.get(rowNumber).getAmountVoters()) {
			return "0";
		} else {
			int totalSum = 0;

			for (int i = 0; i < rows.size(); i++) { //add up all values so far
				List<String> currentValues = rows.get(i).getValues();

				for (int j = 0; j < currentValues.size(); j++) {
					totalSum = totalSum + Integer.parseInt(currentValues.get(j));
				}
			}
			
			totalSum = totalSum - Integer.parseInt(rows.get(rowNumber).getValues().get(positionInRow));
			
			totalSum = totalSum + Integer.parseInt(newValue);
			
			if (totalSum > rows.get(rowNumber).getAmountVoters()) {
				return "0"; //we would exceed the limit with this addition, so we reset to 0
			} else {
				return newValue;
			}
		}
	}
	
	@Override
	public String flipVote(String newVotesName, String origVotesName, List<String> loopVars, CodeArrayListBeautifier code) {
		String newVotesNameAcc = getFullVoteAccess(newVotesName, loopVars);

		String origVotesNameAcc = getFullVoteAccess(origVotesName, loopVars);
		
		code.add("int tmp_diff_stack = abs(" + newVotesNameAcc + " - " + origVotesNameAcc + ");");
		code.add("assume(tmp_diff_stack == 1);");
		code.add("unsigned int other_pos = nondet_uint());");
		code.add("assume(other_pos >= 0);");
		code.add("assume(other_pos < C);");
		code.add("assume(other_pos != " + loopVars.get(0) + ");");
		code.add(newVotesNameAcc + " = " + newVotesNameAcc + "(" + newVotesNameAcc + " - " + origVotesNameAcc + ");");
	}
	
	@Override
	public void restrictVotes(String voteName, CodeArrayListBeautifier code) {
		code.add("unsigned int tmp_restr_sum = 0;");
		
		code.add("for(int loop_r_0 = 0; loop_r_0 < C; loop_r_0++) {"); 
		code.add("tmp_restr_sum = tmp_restr_sum + " + voteName + ".arr[loop_r_0];");
		code.add("}");

		code.add("assume(tmp_restr_sum <= V);");
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
		if (amountVoters < 1) {
			return 1;
		} else {
			return amountVoters;
		}
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
		
		return GUIController.getController().getElectionSimulation().getNumVoters();
	}

	public int test(int i) {

		return 5;
	}

	@Override
	public String otherToString() {
		return "Single choice stack";
	}

	@Override
	public CBMCResultValue convertRowToResultValue(NEWRowOfValues row) {
		
		List<String> values = row.getValues();
		String value = values.get(0);
		
		
		CBMCResultValueSingle toReturn = new CBMCResultValueSingle();
		
		toReturn.setValue("int", value, 32);
		
		return toReturn;
	}

	@Override
	public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, String valueName,
			List<String> loopVariables) {
		// TODO Auto-generated method stub

	}
}