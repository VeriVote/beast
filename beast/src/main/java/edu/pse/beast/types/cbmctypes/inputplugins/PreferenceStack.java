package edu.pse.beast.types.cbmctypes.inputplugins;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

import java.util.List;

public class PreferenceStack extends CBMCInputType {
    public static final int DIMENSIONS = 1;
    public static final String[] SIZE_OF_DIMENSIONS= {UnifiedNameContainer.getCandidate()};
    public PreferenceStack() {
        super(true, DataType.INT, 1, SIZE_OF_DIMENSIONS);
    }
    @Override
    public String getInputIDinFile() {
        return "PREFERENCE_STACK";
    }

    @Override
    public boolean isVotingForOneCandidate() {
        return false;
    }

    @Override
    public String vetValue(ElectionTypeContainer container, List<NEWRowOfValues> rows, int rowNumber, int positionInRow, String newValue) {
        return "null";
    }

    @Override
    public String[] getVotePoints(String[][] votes, int amountCandidates, int amountVoters) {
        return new String[0];
    }

    @Override
    public String[] getVotePoints(String[] votes, int amountCandidates, int amountVoters) {
        return new String[0];
    }

    @Override
    public void addCodeForVoteSum(CodeArrayListBeautifier code, boolean unique) {

    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return null;
    }

    @Override
    public String otherToString() {
        return "null";
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, String valueName, List<String> loopVariables) {

    }

    @Override
    public int vetAmountCandidates(int amountCandidates) {
        return 0;
    }

    @Override
    public int vetAmountVoters(int amountVoters) {
        return 0;
    }

    @Override
    public int vetAmountSeats(int amountSeats) {
        return 0;
    }

    @Override
    public int getNumVotingPoints(ResultValueWrapper result) {
        return 0;
    }

    @Override
    public CBMCResultValue convertRowToResultValue(NEWRowOfValues row) {
        return null;
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
    public String getMinimalValue() {
        return null;
    }

    @Override
    public String getMaximalValue() {
        return null;
    }

    @Override
    public void restrictVotes(String voteName, CodeArrayListBeautifier code) {

    }
}
