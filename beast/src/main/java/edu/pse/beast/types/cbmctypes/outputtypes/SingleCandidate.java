package edu.pse.beast.types.cbmctypes.outputtypes;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.propertychecker.CBMCResultWrapperLong;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class SingleCandidate extends CBMCOutputType {

	@Override
	public String getOutputString() {
		return "unsigned int";
	}

	@Override
	public String getOutputIDinFile() {
		return "CAND_OR_UNDEF";
	}

	@Override
	public boolean isOutputOneCandidate() {
		return true;
	}

	@Override
	public String[] extractResult(List<String> toExtract) {
		String[] tmpArray = { "" + super.helper.readLongs("new_result", toExtract).get(0).getValue() };
		return tmpArray;
	}

	@Override
	public List<CBMCResultWrapperSingleArray> readSeatList(List<String> toExtract) {
		return null;
	}

	@Override
	public List<CBMCResultWrapperLong> readElect(List<String> toExtract) {
		return super.helper.readLongs("elect", toExtract);
	}

	@Override
	public CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code) {
		code.add("void verifyMain() {");
		// code.add("int new_votes1[V], diff[V], total_diff, pos_diff;");

		code.addTab();

		code.add("int total_diff = 0;");

		code.add("int new_result1 = voting(new_votes1);");
		code.add("assert(new_result1 == ORIG_RESULT);");

		code.deleteTab();

		code.add("}"); // end of the function

		return code;
	}

	@Override
	public CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber) {
		String electX = "unsigned int elect" + voteNumber;
		electX = electX + getCArrayType();
		code.add(electX + ";");
		code.add("elect" + voteNumber + " = voting(votes" + voteNumber + ");");

		return code;
	}

	@Override
	public String getCArrayType() {
		return "[C]";
	}

	@Override
	public CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber) {
		code.add("int main() {");
		code.addTab();

		code.add("int elect1 = voting(ORIG_VOTES);"); // we just have a
		// single int as
		// the winner

		// add an assertion that always fails, so we can extract the trace
		code.add("assert(0);");

		code.deleteTab();

		code.add("}");

		return code;
	}

	@Override
	public List<String> getCodeToRunMargin(List<String> origResult, List<String> lastResult) {
		List<CBMCResultWrapperLong> tmpResultLong = super.helper.readLongs("elect", lastResult);

		origResult.add(tmpResultLong.get(0).getValue());

		return origResult;
	}

	@Override
	public List<String> getNewResult(List<String> lastFailedRun) {
		List<CBMCResultWrapperLong> tmpResultLong = super.helper.readLongs("new_result", lastFailedRun);

		List<String> toReturn = new ArrayList<String>();
		toReturn.add(tmpResultLong.get(0).getValue());
		return toReturn;
	}
	
	@Override
	public InternalTypeContainer getInternalTypeContainer() {
		return new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
	}

	@Override
	public void addVerifyOutput(CodeArrayListBeautifier code) {
		code.add("int new_result1 = voting(new_votes1);");
		code.add("assert(new_result1 == ORIG_RESULT);");
	}

	@Override
	public void addLastResultAsCode(CodeArrayListBeautifier code, List<String> origResult) {
		// just declare the variable as the result
		String declaration = "";

		declaration = "int ORIG_RESULT = " + origResult.get(0) + ";";

		code.add(declaration);
	}
}
