package edu.pse.beast.types.cbmctypes.outputplugins;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CBMCResultPresentationHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCOutputType;

public class CandidateList extends CBMCOutputType {
	
	private static final String dataType = "int";
	
	private static final int dimensions = 1;

	private final static String[] sizeOfDimensions = { UnifiedNameContainer.getCandidate() };
	
	public CandidateList() {
		super(dataType, dimensions, sizeOfDimensions);
	}

	@Override
	public String getSimpleType() {
		return "unsigned int[" + UnifiedNameContainer.getCandidate() + "]";
	}
	
	@Override
	public String getComplexType() {
		return UnifiedNameContainer.getStructCandidateList();
	}

	@Override
	public String getOutputIDinFile() {
		return "CANDIDATE_LIST";
	}

	@Override
	public boolean isOutputOneCandidate() {
		return false;
	}

	@Override
	public CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code) {
		code.add("void verifyMain() {");
		// code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
		// UnifiedNameContainer.getVoter() + "], diff[" +
		// UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");

		code.addTab();

		code.add(UnifiedNameContainer.getStructCandidateList() + " tmp = " + UnifiedNameContainer.getVotingMethod()
				+ "(" + UnifiedNameContainer.getNewVotesName() + "1);");

		code.add("unsigned int *tmp_result = tmp." + UnifiedNameContainer.getResultArrName() + ";");

		code.add("unsigned int " + UnifiedNameContainer.getNewResultName() + "1[" + UnifiedNameContainer.getCandidate()
				+ "];"); // create the
							// array where
							// the
		// new seats will get saved
		// iterate over the
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		// seat array, and
		// fill it
		code.addTab();
		// we do this, so our cbmc parser can read out the value of the
		// array
		code.add("" + UnifiedNameContainer.getNewResultName() + "1[i] = tmp_result[i];");
		code.deleteTab();
		code.add("}"); // close the for loop
		// iterate over all
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		code.addTab();
		// candidates /
		// seats
		code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1[i] == "
				+ UnifiedNameContainer.getOrigResultName() + "[i]);");
		code.deleteTab();
		code.add("}"); // end of the for loop

		code.deleteTab();

		code.add("}"); // end of the function

		return code;
	}

	@Override
	public CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code, int voteNumber) {
		String electX = UnifiedNameContainer.getStructCandidateList() + " elect" + voteNumber + " = "
				+ UnifiedNameContainer.getVotingMethod() + "(votes" + voteNumber + ");";
		code.add(electX);

		return code;
	}

	@Override
	public String getCArrayType() {
		return "[" + UnifiedNameContainer.getCandidate() + "]";
	}

	@Override
	public CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber) {
		code.add("int main() {");
		code.addTab();

		String temp = "struct " + UnifiedNameContainer.getStructCandidateList() + " tmp" + voteNumber + " = "
				+ UnifiedNameContainer.getVotingMethod() + "(ORIG_VOTES);";
		code.add(temp);
		String tempElect = "unsigned int *tempElect" + voteNumber + " = tmp" + voteNumber + "."
				+ UnifiedNameContainer.getStructCandidateList() + ";";
		code.add(tempElect);
		String electX = "unsigned int elect" + voteNumber + "[" + UnifiedNameContainer.getCandidate() + "];";
		code.add(electX);
		String forLoop = "for (int electLoop = 0; electLoop < " + UnifiedNameContainer.getCandidate()
				+ "; electLoop++) {";
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

//	@Override TODO remove
//	public List<ResultValueWrapper> getCodeToRunMargin(List<String> origResult, List<String> lastResult) {
//		List<ResultValueWrapper> previousVotingResult = super.helper.extractVariable(UnifiedNameContainer.getElect(),
//				lastResult);
//
//		return previousVotingResult;
//	}
//
//	@Override
//	public List<ResultValueWrapper> getNewResult(String variableMatcher, List<String> lastFailedRun) {
//		List<ResultValueWrapper> tmpResult = super.helper.extractVariable(variableMatcher, lastFailedRun);
//
//		return tmpResult;
//	}

	@Override
	public InternalTypeContainer getInternalTypeContainer() {
		return new InternalTypeContainer(new InternalTypeContainer(InternalTypeRep.CANDIDATE), InternalTypeRep.VOTER);
	}

	@Override
	public void addVerifyOutput(CodeArrayListBeautifier code) {
		code.add("struct " + UnifiedNameContainer.getStructCandidateList() + " tmp_result = "
				+ UnifiedNameContainer.getVotingMethod() + "(" + UnifiedNameContainer.getNewVotesName() + "1);");
		// create the array where the
		code.add("unsigned int " + UnifiedNameContainer.getNewResultName() + "1[" + UnifiedNameContainer.getCandidate()
				+ "];");
		// new seats will get saved

		// iterate over the seat array, and fill it
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		code.addTab();

		// we do this, so our cbmc parser can read out the value of the
		// array
		code.add("" + UnifiedNameContainer.getNewResultName() + "1[i] = tmp_result."
				+ UnifiedNameContainer.getResultArrName() + "[i];");

		code.deleteTab();
		code.add("}"); // close the for loop
		// iterate over all candidates / seats and assert their equality
		code.add("for (int i = 0; i < " + UnifiedNameContainer.getCandidate() + "; i++) {");
		code.addTab();

		code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1[i] == "
				+ UnifiedNameContainer.getOrigResultName() + "[i]);");

		code.deleteTab();
		code.add("}"); // end of the for loop
	}

	@Override
	public void addLastResultAsCode(CodeArrayListBeautifier code, ResultValueWrapper origResult) {
		
		//TODO
		throw new IllegalArgumentException();
//		
//		// first create the declaration of the array:
//		String declaration = "";
//		declaration = "int " + UnifiedNameContainer.getOrigResultName() + "[" + origResult.size() + "] = {";
//		code.addTab();
//		code.add(declaration);
//		String tmp = ""; // saves the amount of votes this seat got
//		for (int i = 0; i < origResult.size(); i++) {
//			if (i < origResult.size() - 1) {
//				tmp = tmp + origResult.get(i) + ",";
//			} else {
//				tmp = tmp + origResult.get(i);
//			}
//		}
//		code.add(tmp);
//		code.deleteTab();
//		code.add("};");
	}

	@Override
	public String getResultDescriptionString(List<String> result) {
		String toReturn = "[";
		for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
			String currentValue = (String) iterator.next();
			try {
				toReturn = toReturn + GUIController.getController().getElectionSimulation()
						.getPartyName(Integer.parseInt(currentValue)) + ", ";
			} catch (NumberFormatException e) {
				toReturn = toReturn + currentValue + ", ";
			}
		}
		toReturn = toReturn + "]";
		return toReturn;
	}

	@Override
	public String otherToString() {
		return "Candidate List";
	}
	
	@Override
	public List<String> drawResult(Result result) {	
		List<String> toReturn = new ArrayList<String>();
		
		List<ResultValueWrapper> winners = result.readVariableValue("elect\\d"); //TODO name container
		
		for (ResultValueWrapper currentWinner: winners) {
			
			String name = currentWinner.getName();
			
			toReturn.add(name);
			
	    	CBMCResultValueStruct struct = (CBMCResultValueStruct) currentWinner.getResultValue();
	    	CBMCResultValueArray arr = (CBMCResultValueArray) struct.getResultVariable("arr").getResultValue();
			
			toReturn.add(CBMCResultPresentationHelper.printOneDimResult(arr, name.length()));
		}		
		return toReturn;
	}
	
	@Override
	public String accessValues() {
		// TODO Auto-generated method stub
		return ".arr";
	}
}