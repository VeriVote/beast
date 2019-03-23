package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.propertychecker.CBMCResultWrapperMultiArray;
import edu.pse.beast.propertychecker.CBMCResultWrapperSingleArray;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

public class SingleChoice extends CBMCInputType {

    String[] sizes = {UnifiedNameContainer.getVoter()};

    @Override
    public String getInputString() {
        return "[" + UnifiedNameContainer.getVoter() + "]";
    }

    @Override
    public String getInputIDinFile() {
        return "SINGLE_CHOICE";
    }

    @Override
    public List<List<String>> getVotingArray(List<String> lastFailedRun, int index) {
        return super.helper.readTwoDimVarLong("votes", lastFailedRun).get(index).getList();
    }

    @Override
    public String getMinimalValue() {
        return "0";
    }

    @Override
    public String getMaximalValue() {
        return UnifiedNameContainer.getCandidate();
    }

    @Override
    public String getMaximalSize(int listDepth) {
        return sizes[listDepth];
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
        code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" + UnifiedNameContainer.getVoter() + "];");
        code.add("for (int i = 0; i < " + UnifiedNameContainer.getVoter() + "; i++) {"); // go over all voters
        code.addTab();
        code.add("int changed = nondet_int();"); // determine, if we want to
                                                 // changed votes for
                                                 // this
                                                 // voter
        code.add("assume(0 <= changed);");
        code.add("assume(changed <= 1);");
        code.add("if(changed) {");
        code.addTab();
        code.add("total_diff++;"); // if we changed the vote, we keep track
                                   // of it
        code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i] = !ORIG_VOTES[i];"); // flip the vote (0 ->
                                                                                          // 1 |
                                                                                          // 1 -> 0)
        code.deleteTab();
        code.add("} else {");
        code.addTab();
        code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i] = ORIG_VOTES[i];");
        code.deleteTab();
        code.add("}");
        code.deleteTab();
        code.add("}");
        code.add("assume(total_diff <= MARGIN);"); // no more changes than
                                                   // margin allows

        outType.addVerifyOutput(code);

        code.deleteTab();
        code.add("}"); // end of the function
    }

    @Override
    public boolean isTwoDim() {
        return getDimension() == 2;
    }

    @Override
    public CBMCResultWrapperMultiArray extractVotesWrappedMulti(List<String> result, int numberCandidates) {
        List<CBMCResultWrapperSingleArray> singleVotesList = super.helper.readOneDimVarLong("votes", result);

        List<CBMCResultWrapperMultiArray> toReturn = new ArrayList<CBMCResultWrapperMultiArray>();

        for (Iterator<CBMCResultWrapperSingleArray> iterator = singleVotesList.iterator(); iterator.hasNext();) {
            CBMCResultWrapperSingleArray cbmcResultWrapperSingleArray = (CBMCResultWrapperSingleArray) iterator.next();
            toReturn.add(cbmcResultWrapperSingleArray.wrapInTwoDim(1, "" + UnifiedNameContainer.getNewVotesName() + "",
                    numberCandidates));
        }

        return toReturn.get(0);
    }

    @Override
    public String vetValue(String newValue, ElectionTypeContainer container, NEWRowOfValues row) {

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
        return super.helper.readOneDimVarLong("votes", toExtract);
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

//  @Override
//  public void addMarginMainCheck(CodeArrayListBeautifier code, int margin,
//      List<String> origResult) {
//    code.add("int " + UnifiedNameContainer.getNewVotesName() + "1[" + UnifiedNameContainer.getVoter() + "];");
//    code.add("for (int i = 0; i < V; i++) {"); // go over all voters
//    code.addTab();
//    code.add("int changed = nondet_int();"); // determine, if we want to
//                          // changed votes for
//                          // this
//                          // voter
//    code.add("assume(0 <= changed);");
//    code.add("assume(changed <= 1);");
//    code.add("if(changed) {");
//    code.addTab();
//    code.add("total_diff++;"); // if we changed the vote, we keep track
//                  // of it
//    code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i] = !ORIG_VOTES[i];"); // flip the vote (0 ->
//                            // 1 |
//                            // 1 -> 0)
//    code.deleteTab();
//    code.add("} else {");
//    code.addTab();
//    code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i] = ORIG_VOTES[i];");
//    code.deleteTab();
//    code.add("}");
//    code.deleteTab();
//    code.add("}");
//    code.add("assume(total_diff <= MARGIN);"); // no more changes than
//                          // margin allows
//  }

    @Override
    public List<String> getVotingResultCode(String[][] votingData) {
        List<String> toReturn = new ArrayList<String>();

        toReturn.add("int ORIG_VOTES[" + votingData.length + "] = {");

        // we have to map the two dimensional array to an one
        // dimensional one
        for (int i = 0; i < votingData.length; i++) {
            int tmp = 0; // saves what this voter voted for
            int tmpSum = 0;
            for (int j = 0; j < votingData[i].length; j++) {
                tmpSum += Long.parseLong(votingData[i][j]);
                if (votingData[i][j].equals("0")) {
                    tmp = j;
                }
            }

            if (tmpSum == 0) {
                if (i < votingData.length - 1) {
                    toReturn.add("" + UnifiedNameContainer.getCandidate() + " ,");
                } else {
                    toReturn.add(UnifiedNameContainer.getCandidate());
                }
            } else {

                if (i < votingData.length - 1) {
                    toReturn.add(tmp + ",");
                } else {
                    toReturn.add("" + tmp);
                }
            }
        }

        toReturn.add("};"); // close the array declaration

        return toReturn;
    }

    @Override
    public int getDimension() {
        return 1;
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, int voteNumber) {
    }

    @Override
    public void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique) {
        code.add("if(arr[i] == candidate) sum++;");
    }

    @Override
    public List<List<String>> getNewVotes(List<String> lastFailedRun, int index) {
        List<List<String>> toReturn = new ArrayList<List<String>>();

        toReturn.add(super.helper.readOneDimVarLong("" + UnifiedNameContainer.getNewVotesName() + "", lastFailedRun)
                .get(index).getList());

        return toReturn;
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
    public int getNumVotingPoints(String[][] votingData) {
        return GUIController.getController().getElectionSimulation().getNumVoters();
    }

    @Override
    public String otherToString() {
        return "Single choice";
    }
}
