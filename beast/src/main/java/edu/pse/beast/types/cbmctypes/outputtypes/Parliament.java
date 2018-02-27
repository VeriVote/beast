package edu.pse.beast.types.cbmctypes.outputtypes;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.electionSimulator.ElectionSimulation;
import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class Parliament extends CBMCOutputType {

	@Override
	public String getOutputString() {
		return UnifiedNameContainer.getStruct_result();
	}

	@Override
	public String getOutputIDinFile() {
		return "CAND_PER_SEAT";
	}

	@Override
	public boolean isOutputOneCandidate() {
		return false;
	}

	@Override
	public String[] extractResult(List<String> toExtract) {
		return super.helper.readOneDimVarLong("new_result", toExtract).get(0).getArray();
	}

	@Override
	public List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract) {
		return super.helper.readOneDimVarLong("elect", toExtract);
	}

	@Override
	public List<CBMCResultWrapperLong> readElect(List<String> toExtract) {
		return null;
	}

	@Override
	public CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code) {
		code.add("void verifyMain() {");
		// code.add("int new_votes1[V], diff[V], total_diff, pos_diff;");

		code.addTab();

		code.add("struct result tmp = voting(new_votes1);");

		code.add("unsigned int *tmp_result = tmp." + UnifiedNameContainer.getResult_arr_name() + ";");

		code.add("unsigned int new_result1[" + UnifiedNameContainer.getSeats() + "];"); // create the array where the
		// new seats will get saved

		code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats() + "; i++) {"); // iterate over the
		// seat array, and
		// fill it
		code.addTab();
		// we do this, so our cbmc parser can read out the value of the
		// array
		code.add("new_result1[i] = tmp_result[i];");
		code.deleteTab();
		code.add("}"); // close the for loop

		code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats() + "; i++) {"); // iterate over all
		code.addTab();
		// candidates /
		// seats
		code.add("assert(new_result1[i] == ORIG_RESULT[i]);");
		code.deleteTab();
		code.add("}"); // end of the for loop

		code.deleteTab();

		code.add("}"); // end of the function

		return code;
	}

	@Override
	public CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber) {
		String temp = "struct result tmp" + voteNumber + " = voting(votes" + voteNumber + ");";
		code.add(temp);
		String tempElect = "unsigned int *tempElect" + voteNumber + " = tmp" + voteNumber + "." + UnifiedNameContainer.getResult_arr_name() +";";
		code.add(tempElect);
		String electX = "unsigned int elect" + voteNumber + "[" + UnifiedNameContainer.getSeats() + "];";
		code.add(electX);
		String forLoop = "for (int electLoop = 0; electLoop < " + UnifiedNameContainer.getSeats() + "; electLoop++) {";
		code.add(forLoop);
		code.addTab();
		code.add("elect" + voteNumber + "[electLoop] = tempElect" + voteNumber + "[electLoop];");
		code.deleteTab();
		code.add("}");

		return code;
	}

	@Override
	public String getCArrayType() {
		return "[" + UnifiedNameContainer.getSeats() + "]";
	}

	@Override
	public CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber) {
		code.add("int main() {");
		code.addTab();

		String temp = "struct result tmp" + voteNumber + " = voting(ORIG_VOTES);";
		code.add(temp);
		String tempElect = "unsigned int *tempElect" + voteNumber + " = tmp" + voteNumber + "." + UnifiedNameContainer.getResult_arr_name() + ";";
		code.add(tempElect);
		String electX = "unsigned int elect" + voteNumber + "[" + UnifiedNameContainer.getSeats() + "];";
		code.add(electX);
		String forLoop = "for (int electLoop = 0; electLoop < " + UnifiedNameContainer.getSeats() + "; electLoop++) {";
		code.add(forLoop);
		code.addTab();
		code.add("elect" + voteNumber + "[electLoop] = tempElect" + voteNumber + "[electLoop];");
		code.deleteTab();
		code.add("}");

		// add an assertion that always fails, so we can extract the trace
		code.add("assert(0);");

		code.deleteTab();

		code.add("}");

		return code;
	}

	@Override
	public List<String> getCodeToRunMargin(List<String> origResult, List<String> lastResult) {

		List<CBMCResultWrapperSingleArray> tmpResultOneDim = super.helper.readOneDimVarLong("elect", lastResult);

		origResult = tmpResultOneDim.get(0).getList();

		return origResult;
	}

	@Override
	public List<String> getNewResult(List<String> lastFailedRun) {
		List<CBMCResultWrapperSingleArray> tmpResultOneDim = super.helper.readOneDimVarLong("new_result",
				lastFailedRun);

		return tmpResultOneDim.get(0).getList();
	}

	@Override
	public InternalTypeContainer getInternalTypeContainer() {
		return new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
	}

	@Override
	public void addVerifyOutput(CodeArrayListBeautifier code) {
		code.add("struct result tmp_result = voting(new_votes1);");

		code.add("unsigned int new_result1[" + UnifiedNameContainer.getSeats() + "];"); // create the array where the
											// new seats will get saved

		code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats() + "; i++) {"); // iterate over the
													// seat array, and
													// fill it
		code.addTab();

		// we do this, so our cbmc parser can read out the value of the
		// array
		code.add("new_result1[i] = tmp_result." + UnifiedNameContainer.getResult_arr_name() + "[i];");

		code.deleteTab();
		code.add("}"); // close the for loop

		code.add("for (int i = 0; i < " + UnifiedNameContainer.getSeats() + "; i++) {"); // iterate over all
													// candidates /
													// seats and assert
													// their equality
		code.addTab();

		code.add("assert(new_result1[i] == ORIG_RESULT[i]);");

		code.deleteTab();
		code.add("}"); // end of the for loop
	}

	@Override
	public void addLastResultAsCode(CodeArrayListBeautifier code, List<String> origResult) {
		// first create the declaration of the array:
		String declaration = "";

		declaration = "int ORIG_RESULT[" + origResult.size() + "] = {";
		code.addTab();

		code.add(declaration);

		String tmp = ""; // saves the amount of votes this seat got
		for (int i = 0; i < origResult.size(); i++) {
			if (i < origResult.size() - 1) {
				tmp = tmp + origResult.get(i) + ",";
			} else {
				tmp = tmp + origResult.get(i);
			}
		}
		code.add(tmp);

		
		code.deleteTab();
		code.add("};");

	}

	@Override
	public String getResultDescriptionString(List<String> result) {
		
		String toReturn = "[";
		
		for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
			String currentValue = (String) iterator.next();
			
			try {
				toReturn = toReturn + ElectionSimulation.getPartyName(Integer.parseInt(currentValue)) + ", ";
			} catch (Exception e) {
				toReturn = toReturn + currentValue + ", ";
			}
		}
		
		toReturn = toReturn + "]";
		
		return toReturn;
	}
}
