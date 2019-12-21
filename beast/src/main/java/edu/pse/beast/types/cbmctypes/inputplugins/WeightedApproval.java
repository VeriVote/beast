package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueArray;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueWrapper;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

public class WeightedApproval extends CBMCInputType {
    private static final int dimensions = 2;
    private final int maxValue = 100;

    private final static String[] sizeOfDimensions = {
            UnifiedNameContainer.getVoter(),
            UnifiedNameContainer.getCandidate()
            };

    public WeightedApproval() {
        super(true, DataType.INT, dimensions, sizeOfDimensions);
    }

    @Override
    public String getInputIDinFile() {
        return "WEIGHTED_APPROVAL";
    }

    @Override
    public String getMinimalValue() {
        return "" + 0;
    }

    @Override
    public String getMaximalValue() {
        return "" + maxValue;
    }

    @Override
    public boolean hasVariableAsMinValue() {
        return false;
    }

    @Override
    public boolean hasVariableAsMaxValue() {
        return false;
    }

    @Override
    public boolean isVotingForOneCandidate() {
        return false;
    }

    @Override
    public String vetValue(ElectionTypeContainer container, List<NEWRowOfValues> rowOfValues,
                           int rowNumber, int positionInRow, String newValue) {
        final int number;
        try {
            number = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            return "0";
        }
        if (number < 0 || number > maxValue) {
            return "0";
        } else {
            return newValue;
        }
    }

    @Override
    public void restrictVotes(String voteName, CodeArrayListBeautifier code) {
        // we do not need to restrict anything
    }

    @Override
    public String[] getVotePoints(String[][] votes, int amountCandidates, int amountVoters) {
        Long[] result = new Long[amountCandidates];
        Arrays.fill(result, 0l);
        for (int i = 0; i < amountVoters; i++) {
            String[] vote = votes[i];
            for (int j = 0; j < amountCandidates; j++) {
                result[j] += Long.parseLong(vote[j]);
            }
        }
        String[] toReturn = new String[amountCandidates];
        for (int i = 0; i < result.length; i++) {
            toReturn[i] = "" + result[i];
        }
        return toReturn;
    }

    @Override
    public String[] getVotePoints(String[] votes, int amountCandidates, int amountVoters) {
        return super.wrongInputTypeArray(amountCandidates, amountVoters);
    }

    // @Override
    // public void addMarginMainCheck(CodeArrayListBeautifier code, int margin,
    // List<String> origResult) {
    // code.add("int " + UnifiedNameContainer.getNewVotesName() + "1["
    // + UnifiedNameContainer.getVoter() + "]["
    // + UnifiedNameContainer.getCandidate() + "];");
    //
    // code.add("for (int i = 0; i < V; i++) {"); // go over all voters
    // code.addTab();
    // code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
    // code.addTab();
    // code.add("int changed = nondet_int();"); // determine, if we want to
    // // changed votes for
    // // this
    // // voter - candidate
    // // pair
    // code.add("assume(0 <= changed);");
    // code.add("assume(changed <= 1);");
    // code.add("if(changed) {");
    // code.addTab();
    // code.add("total_diff++;"); // if we changed the vote, we keep track
    // // of it
    // code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i][j] =
    // nondet_int();");
    // // set the vote to (0-100), but different from original
    // code.add("assume(" + UnifiedNameContainer.getNewVotesName()
    // + "1[i][j] != ORIG_VOTES[i][j]);");
    // code.add("assume(0 <= " + UnifiedNameContainer.getNewVotesName() +
    // "1[i][j]);");
    // code.add("assume(" + UnifiedNameContainer.getNewVotesName() + "1[i][j] <=
    // 100);");
    // code.deleteTab();
    // code.add("} else {");
    // code.addTab();
    // code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i][j] =
    // ORIG_VOTES[i][j];");
    // code.deleteTab();
    // code.add("}");
    // code.deleteTab();
    // code.add("}");
    // code.deleteTab();
    // code.add("}"); // end of the double for loop
    // code.add("assume(total_diff <= MARGIN);"); // no more changes than
    // // margin allows
    // } TODO remove unused code

    @Override
    public void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique) {
        code.add("unsigned int candSum = arr[i][candidate];");
        if (unique) {
            code.add("for(unsigned int j = 0; j < "
                    + UnifiedNameContainer.getCandidate() + "; ++i) {");
            code.add("if(j != candidate && arr[i][j] >= candSum) candSum = 0;");
            code.add("}");
        }
        code.add("sum += candSum;");
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(
                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                        InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER);
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

    @Override
    public String otherToString() {
        return "Weighted Approval";
    }

    @Override
    public CBMCResultValue convertRowToResultValue(NEWRowOfValues row) {
        List<String> values = row.getValues();
        List<CBMCResultValueWrapper> wrappedValues = new ArrayList<CBMCResultValueWrapper>();
        for (Iterator<String> iterator = values.iterator(); iterator.hasNext();) {
            String value = (String) iterator.next();
            CBMCResultValueWrapper wrapper = new CBMCResultValueWrapper();
            CBMCResultValueSingle toWrap = new CBMCResultValueSingle();
            toWrap.setValue("int", value, 32);
            wrapper.setValue(toWrap);
        }
        CBMCResultValueArray toReturn = new CBMCResultValueArray();
        toReturn.setValue(wrappedValues);
        return toReturn;
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code,
            String valueName, List<String> loopVariables) {
        // TODO Auto-generated method stub
    }
}