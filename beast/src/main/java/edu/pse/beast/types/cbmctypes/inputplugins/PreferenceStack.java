package edu.pse.beast.types.cbmctypes.inputplugins;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CCodeHelper;
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

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import static edu.pse.beast.toolbox.CCodeHelper.*;
import static edu.pse.beast.toolbox.CCodeHelper.plusPlus;

public final class PreferenceStack extends CBMCInputType {
    public static final int DIMENSIONS = 1;
    public static final String[] SIZE_OF_DIMENSIONS= {UnifiedNameContainer.getVoter(),UnifiedNameContainer.getCandidate()};
    public PreferenceStack() {
        super(true, DataType.INT, 2, SIZE_OF_DIMENSIONS);
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
    public String vetValue(ElectionTypeContainer container, List<NEWRowOfValues> row, int rowNumber, int positionInRow, String newValue) {
        final int number;
        try {
            number = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            return zero();
        }
        final String result;
        if (number < 0 || (number > row.get(rowNumber).getAmountCandidates() && positionInRow != 0)) {
            result = zero();
        } else if (row.get(rowNumber).getValues().contains(newValue)) {
            result = zero();
        } else {
            result = newValue;
        }
        return result;
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
        code.add(functionCode(CCodeHelper.IF,
                eq(arrAccess(arr(), i(), zero()),
                        CANDIDATE)
        ) + space() + plusPlus(SUM)
                + CCodeHelper.SEMICOLON);
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(
                        new InternalTypeContainer(InternalTypeRep.INTEGER),
                        InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER
        );
    }

    @Override
    public String otherToString() {
        return "Preference stack";
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(CodeArrayListBeautifier code, String valueName, List<String> loopVariables) {

    }

    @Override
    public int vetAmountCandidates(int amountCandidates) {
        return vetAmountInputValue(amountCandidates);
    }

    @Override
    public int vetAmountVoters(int amountVoters) {
        return vetAmountInputValue(amountVoters);
    }

    @Override
    public int vetAmountSeats(int amountSeats) {
        return vetAmountInputValue(amountSeats);
    }

    @Override
    public int getNumVotingPoints(ResultValueWrapper result) {
        return GUIController.getController().getElectionSimulation()
                .getNumVoters();
    }

    @Override
    public CBMCResultValue convertRowToResultValue(NEWRowOfValues row) {
        final List<String> values = row.getValues();
        final List<CBMCResultValueWrapper> wrappedValues =
                new ArrayList<CBMCResultValueWrapper>();
        for (final Iterator<String> iterator = values.iterator();
             iterator.hasNext();) {
            final String value = iterator.next();
            final CBMCResultValueWrapper wrapper = new CBMCResultValueWrapper();
            final CBMCResultValueSingle toWrap = new CBMCResultValueSingle();
            toWrap.setValue(CCodeHelper.INT, value, INT_LENGTH);
            wrapper.setValue(toWrap);
        }
        final CBMCResultValueArray toReturn = new CBMCResultValueArray();
        toReturn.setValue(wrappedValues);
        return toReturn;
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
    public String getMinimalValue() {
        return zero();
    }

    @Override
    public String getMaximalValue() {
        return UnifiedNameContainer.getCandidate();
    }

    @Override
    public void restrictVotes(String voteName, CodeArrayListBeautifier code) {

    }
}
