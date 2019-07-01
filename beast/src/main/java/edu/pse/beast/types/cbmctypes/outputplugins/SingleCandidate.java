package edu.pse.beast.types.cbmctypes.outputplugins;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.propertychecker.Result;
import edu.pse.beast.toolbox.CBMCResultPresentationHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueStruct;
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
    public int getDimension() {
        return 0;
    }

    @Override
    public boolean isOutputOneCandidate() {
        return true;
    }

    @Override
    public CodeArrayListBeautifier addMarginVerifyCheck(CodeArrayListBeautifier code) {
        code.add("void verifyMain() {");
        // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" +
        // UnifiedNameContainer.getVoter() + "], diff[" +
        // UnifiedNameContainer.getVoter() + "], total_diff, pos_diff;");
        code.addTab();
        code.add("int total_diff = 0;");
        code.add("int " + UnifiedNameContainer.getNewResultName()
                 + "1 = " + UnifiedNameContainer.getVotingMethod()
                 + "(" + UnifiedNameContainer.getNewVotesName() + "1);");
        code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1 == "
                + UnifiedNameContainer.getOrigResultName() + ");");
        code.deleteTab();
        // end of the function
        code.add("}");
        return code;
    }

    @Override
    public CodeArrayListBeautifier addVotesArrayAndInit(CodeArrayListBeautifier code,
                                                        int voteNumber) {
        String electX = "unsigned int elect" + voteNumber;
        electX = electX + getCArrayType();
        code.add(electX + ";");
        code.add("elect" + voteNumber + " = "
                 + UnifiedNameContainer.getVotingMethod()
                 + "(votes" + voteNumber + ");");
        return code;
    }

    @Override
    public String getCArrayType() {
        return ""; // we have a single candidate, so no array
    }

    @Override
    public CodeArrayListBeautifier addMarginMainTest(CodeArrayListBeautifier code, int voteNumber) {
        code.add("int main() {");
        code.addTab();
        code.add("int elect1 = " + UnifiedNameContainer.getVotingMethod()
                 + "(ORIG_VOTES);"); // we just have a single int as the winner
        // add an assertion that always fails, so we can extract the trace
        code.add("assert(0);");
        code.deleteTab();
        code.add("}");
        return code;
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(InternalTypeRep.INTEGER);
    }

    @Override
    public void addVerifyOutput(CodeArrayListBeautifier code) {
        code.add("int " + UnifiedNameContainer.getNewResultName()
                 + "1 = " + UnifiedNameContainer.getVotingMethod()
                 + "(" + UnifiedNameContainer.getNewVotesName() + "1);");
        code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1 == "
                 + UnifiedNameContainer.getOrigResultName() + ");");
    }

    @Override
    public void addLastResultAsCode(CodeArrayListBeautifier code, ResultValueWrapper origResult) {
    	//TODO
    			throw new IllegalArgumentException();
//    	
//        // just declare the variable as the result
//        String declaration = "";
//        declaration = "int " + UnifiedNameContainer.getOrigResultName()
//                      + " = " + origResult.get(0) + ";";
//        code.add(declaration);
    }

    @Override
    public String getResultDescriptionString(List<String> result) {
        String toReturn = "winner: ";
        try {
            toReturn = GUIController.getController().getElectionSimulation()
                    .getPartyName(Integer.parseInt(result.get(0)));
        } catch (NumberFormatException e) {
            toReturn = result.get(0);
        }
        return toReturn;
    }

    @Override
    public String otherToString() {
        return "Single candidate";
    }
	
	@Override
	public List<String> drawResult(Result result) {	
		List<String> toReturn = new ArrayList<String>();
		
		List<ResultValueWrapper> winners = result.readVariableValue("elect\\d"); //TODO name container
		
		for (ResultValueWrapper currentWinner: winners) {
			
			String name = currentWinner.getName();
			
			toReturn.add(name);
			
	    	CBMCResultValueSingle value = (CBMCResultValueSingle) currentWinner.getResultValue();
			
			toReturn.add(CBMCResultPresentationHelper.printSingleElement(value, name.length()));
		}		
		return toReturn;
	}
}