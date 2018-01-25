package edu.pse.beast.types.cbmctypes.inputplugins;

import java.nio.ReadOnlyBufferException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.electionSimulator.Model.RowOfValues;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

public class SingleChoice extends CBMCInputType {

	@Override
	public String getInputString() {
		return "[V]";
	}

	@Override
	public String getInputIDinFile() {
		return "SINGLE_CHOICE";
	}

	@Override
	public String getMinimalValue(ElectionTypeContainer container) {
		return "0";
	}

	@Override
	public String getMaximalValue(ElectionTypeContainer container) {
		return "C";
	}

	@Override
	public boolean isVotingForOneCandidate() {
		return true;
	}

	@Override
	public List<String> addVerifyMethod(List<String> code, boolean multiOut) {
		code.add("void verify() {");
		code.add("int total_diff = 0;");
		code.add("int new_votes1[V];");
		code.add("for (int i = 0; i < V; i++) {"); // go over all voters
		code.add("int changed = nondet_int();"); // determine, if we want to
													// changed votes for
													// this
													// voter
		code.add("assume(0 <= changed);");
		code.add("assume(changed <= 1);");
		code.add("if(changed) {");
		code.add("total_diff++;"); // if we changed the vote, we keep track
									// of it
		code.add("new_votes1[i] = !ORIG_VOTES[i];"); // flip the vote (0 ->
														// 1 |
														// 1 -> 0)
		code.add("} else {");
		code.add("new_votes1[i] = ORIG_VOTES[i];");
		code.add("}");
		code.add("}");
		code.add("assume(total_diff <= MARGIN);"); // no more changes than
													// margin allows
		if (multiOut) {
			code.add("int *tmp_result = voting(new_votes1);");

			code.add("int new_result1[S];"); // create the array where the
												// new seats will get saved

			code.add("for (int i = 0; i < S; i++) {"); // iterate over the
														// seat array, and
														// fill it
			// we do this, so our cbmc parser can read out the value of the
			// array
			code.add("new_result1[i] = tmp_result[i];");
			code.add("}"); // close the for loop

			code.add("for (int i = 0; i < S; i++) {"); // iterate over all
														// candidates /
														// seats and assert
														// their equality
			code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
			code.add("}"); // end of the for loop
		} else {
			code.add("int new_result1 = voting(new_votes1);");
			code.add("assert(new_result1 == ORIG_RESULT);");
		}
		code.add("}"); // end of the function
		return code;
	}

	@Override
	public boolean isTwoDim() {
		return false;
	}

	@Override
	public CBMCResultWrapperMultiArray extractVotesWrappedMulti(List<String> result, int numberCandidates) {
		List<CBMCResultWrapperSingleArray> singleVotesList = super.helper.readOneDimVar("votes", result);
		
		List<CBMCResultWrapperMultiArray> toReturn = new ArrayList<CBMCResultWrapperMultiArray>();

		for (Iterator<CBMCResultWrapperSingleArray> iterator = singleVotesList.iterator(); iterator
				.hasNext();) {
			CBMCResultWrapperSingleArray cbmcResultWrapperSingleArray = (CBMCResultWrapperSingleArray) iterator
					.next();
			toReturn.add(cbmcResultWrapperSingleArray.wrapInTwoDim(1, "new_votes", numberCandidates));
		}

		return toReturn.get(0);
	}

	@Override
	public String vetValue(String newValue, ElectionTypeContainer container, RowOfValues row) {
		
		int number;
		
		try {
			number = Integer.parseInt(newValue);
		} catch (NumberFormatException e) {
			return "0";
		}
		
		if (number == 1) {
			for (int i = 0; i < row.getValues().size(); i++) {
				row.getValues().set(i, "0");
			}
			newValue = "1";
		} else {
			newValue = "0";
		}
		return newValue;
	}

	@Override
	public List<CBMCResultWrapperMultiArray> readVoteList(List<String> toExtract) {
		return null;
	}

	@Override
	public List<CBMCResultWrapperSingleArray> readSingleVoteList(List<String> toExtract) {
		return super.helper.readOneDimVar("votes", toExtract);
	}

}
