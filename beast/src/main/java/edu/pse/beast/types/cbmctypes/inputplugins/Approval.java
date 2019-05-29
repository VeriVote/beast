package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

public class Approval extends CBMCInputType {
    private String[] sizes = { UnifiedNameContainer.getVoter(),
                               UnifiedNameContainer.getCandidate() };

    @Override
    public String getInputString() {
        return "[" + UnifiedNameContainer.getVoter() + "]["
                + UnifiedNameContainer.getCandidate() + "]";
    }

    @Override
    public String getInputIDinFile() {
        return "APPROVAL";
    }

    @Override
    public String getMinimalValue() {
        return "0";
    }

    @Override
    public String getMaximalValue() {
        return "1";
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
    public String getMaximalSize(int listDepth) {
        return sizes[listDepth];
    }

    @Override
    public void addVerifyMethod(CodeArrayListBeautifier code, OutputType outType) {
        code.add("void verify() {");
        code.add("int total_diff = 0;");
        code.add("int " + UnifiedNameContainer.getNewVotesName() + "1" + getInputString() + ";");
        // go over all voters
        code.add("for (int i = 0; i < V; i++) {");
        code.addTab();
        // go over all candidates
        code.add("for (int j = 0; i < C; i++) {");
        code.addTab();
        // determine, if we want to changed votes for this "voter - candidate" pair
        code.add("int changed = nondet_int();");
        code.add("assume(0 <= changed);");
        code.add("assume(changed <= 1);");
        code.add("if(changed) {");
        code.addTab();
        // if we changed the vote, we keep track of it
        code.add("total_diff++;");
        // flip the vote (0 -> 1 | 1 -> 0)
        code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i][j] = !ORIG_VOTES[i][j];");
        code.deleteTab();
        code.add("} else {");
        code.addTab();
        code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i][j] = ORIG_VOTES[i][j];");
        code.deleteTab();
        code.add("}");
        code.deleteTab();
        code.add("}");
        code.deleteTab();
        code.add("}"); // end of the double for-loop
        // no more changes than margin allows
        code.add("assume(total_diff <= MARGIN);");
        outType.addVerifyOutput(code);
        // end of the function
        code.add("}");
    }

    @Override
    public String vetValue(String newValue, ElectionTypeContainer container, NEWRowOfValues row) {
        final int number;
        try {
            number = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            return "0";
        }
        if (number != 0 && number != 1) {
            return "0";
        } else {
            return newValue;
        }
    }

    @Override
    public String[] getVotePoints(String[][] votes, int amountCandidates, int amountVoters) {
        String[] result = new String[amountCandidates];
        Arrays.fill(result, 0l);
        return result;
    }

    @Override
    public String[] getVotePoints(String[] votes, int amountCandidates, int amountVoters) {
        return super.wrongInputTypeArray(amountCandidates, amountVoters);
    }

    // @Override
    // public void addMarginMainCheck(CodeArrayListBeautifier code, int margin,
    // List<String> origResult) {
    // code.add("int " + UnifiedNameContainer.getNewVotesName() +
    // "1[UnifiedNameContainer.getVoter()][UnifiedNameContainer.getCandidate()];");
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
    // !ORIG_VOTES[i][j];"); // flip the vote
    // // (0 -> 1 |
    // // 1 -> 0)
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
    // }

    @Override
    public List<String> getVotingResultCode(ResultValueWrapper votingData) {
    	throw new IllegalArgumentException();
//    	
//    	
//        List<String> toReturn = new ArrayList<String>();
//        toReturn.add("int ORIG_VOTES[" + votingData.length + "][" + votingData[0].length + "] = {");
//        for (int i = 0; i < votingData.length; i++) {
//            String tmp = "";
//            for (int j = 0; j < votingData[i].length; j++) {
//                if (j < (votingData[i].length - 1)) {
//                    tmp = tmp + votingData[i][j] + ",";
//                } else {
//                    tmp = tmp + votingData[i][j];
//                }
//            }
//            tmp = "{" + tmp + "}";
//            if (i < votingData.length - 1) {
//                toReturn.add(tmp + ",");
//            } else {
//                // the last entry does not need a trailing comma
//                toReturn.add(tmp);
//            }
//        }
//        // close the array declaration
//        toReturn.add("};");
//        return toReturn;
    }

    @Override
    public int getDimension() {
        return 2;
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, int voteNumber) {
    }

    @Override
    public void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique) {
        code.add("unsigned int candSum = arr[i][candidate];");
        if (unique) {
            code.add("for(unsigned int j = 0; j < C; ++i) {");
            code.add("if(j != candidate && arr[i][j] >= candSum) candSum = 0;");
            code.add("}");
        }
        code.add("sum += candSum;");
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(new InternalTypeContainer(
                new InternalTypeContainer(InternalTypeRep.INTEGER), InternalTypeRep.CANDIDATE),
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
        return "Approval";
    }
}