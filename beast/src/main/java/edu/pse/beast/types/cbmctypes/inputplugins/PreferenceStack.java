package edu.pse.beast.types.cbmctypes.inputplugins;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.*;
import edu.pse.beast.types.InOutType;
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
    // +1 to candidates because we need to save the votes for each stack somewhere
    public static final String[] SIZE_OF_DIMENSIONS= {UnifiedNameContainer.getStacks(),UnifiedNameContainer.getCandidate() + "+1"};
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

        final CBMCResultValueArray toReturn =new CBMCResultValueArray();
        for (String value:values) {
            final CBMCResultValueWrapper wrapper = new CBMCResultValueWrapper();
            final CBMCResultValueSingle toWrap = new CBMCResultValueSingle();
            toWrap.setValue(CCodeHelper.INT, value, INT_LENGTH);
            wrapper.setValue(toWrap);
            wrappedValues.add(wrapper);
        };
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

    @Override
    public int vetAmountStacks(int amountStacks) {
        return vetAmountInputValue(amountStacks);
    }

    @Override
    public List<Integer> getSizesInOrder(int amountVoters, int amountCandidates, int amountSeats, int amountStacks) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(amountStacks);
        list.add(amountCandidates + 1);
        list.add(amountVoters);
        return list;
    }

    @Override
    public String getDimensionDescriptor(boolean includeSizes) {
        return InOutType.createSquareBrackets(UnifiedNameContainer.getStacks())
                + createSquareBrackets(UnifiedNameContainer.getCandidate() + "+1");
    }

    @Override
    public String getDimensionDescriptor(String[] sizes) {
        return super.getDimensionDescriptor(sizes);
    }

}
