package edu.pse.beast.types.cbmctypes.outputplugins;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

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

public class ParliamentStack extends CBMCOutputType {
	
	private static final int dimensions = 1;

	private final static String[] sizeOfDimensions = { UnifiedNameContainer.getCandidate() };
	
	public ParliamentStack() {
		super(true, DataType.INT, dimensions, sizeOfDimensions);
	}
	
    @Override
    public String getOutputIDinFile() {
        return "STACK_PER_PARTY";
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

        code.add("struct stack_result tmp = " + UnifiedNameContainer.getVotingMethod() + "("
                + UnifiedNameContainer.getNewVotesName() + "1);");

        code.add("unsigned int *tmp_result = tmp.arr;");
        // create the array where the new seats will get saved
        code.add("unsigned int "
                + UnifiedNameContainer.getNewResultName() + "1["
                + UnifiedNameContainer.getSeats() + "];");
        // iterate over the seat array, and fill it
        code.add("for (int i = 0; i < "
                 + UnifiedNameContainer.getSeats() + "; i++) {");
        code.addTab();
        // we do this, so our cbmc parser can read out the value of the
        // array
        code.add("" + UnifiedNameContainer.getNewResultName() + "1[i] = tmp_result[i];");
        code.deleteTab();
        code.add("}"); // close the for loop
        // iterate over all
        code.add("for (int i = 0; i < "
                 + UnifiedNameContainer.getSeats() + "; i++) {");
        code.addTab();
        // candidates / seats
        code.add("assert(" + UnifiedNameContainer.getNewResultName() + "1[i] == "
                + UnifiedNameContainer.getOrigResultName() + " [i]);");
        code.deleteTab();
        code.add("}"); // end of the for loop
        code.deleteTab();
        code.add("}"); // end of the function
        CodeArrayListBeautifier c = new CodeArrayListBeautifier();
        c.add("IF SOMETHING GOES WRONG: SEARCH FOR DEBUG56693");
        return c;
    }

    @Override
    public CodeArrayListBeautifier
            addVotesArrayAndInit(CodeArrayListBeautifier code,
                                 int voteNumber) {
        String electX = super.getContainer().getOutputStruct().getStructAccess() + " elect" + voteNumber
                      + " = " + UnifiedNameContainer.getVotingMethod()
                      + "(votes" + voteNumber + ");";
        code.add(electX);
        return code;
    }

    @Override
    public String getCArrayType() {
        return "[" + UnifiedNameContainer.getCandidate() + "]";
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER);
    }
    
    @Override
    public String getResultDescriptionString(List<String> result) {
        String toReturn = "[";
        int index = 0;
        for (Iterator<String> iterator = result.iterator(); iterator.hasNext();) {
            String currentValue = (String) iterator.next();
            try {
                toReturn +=
                        GUIController.getController()
                        .getElectionSimulation().getPartyName(index)
                        + ": " + currentValue + ", ";
            } catch (NumberFormatException e) {
                toReturn = toReturn + index + ": " + currentValue + ", ";
            }
            index++;
        }
        toReturn = toReturn + "]";
        return toReturn;
    }

    @Override
    public String otherToString() {
        return "Parliament stack";
    }
}