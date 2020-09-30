package edu.pse.beast.types.cbmctypes.inputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.arr;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.i;
import static edu.pse.beast.toolbox.CCodeHelper.plusPlus;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValue;
import edu.pse.beast.toolbox.valueContainer.cbmcValueContainers.CBMCResultValueSingle;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.cbmctypes.CBMCInputType;

/**
 * The Class SingleChoice.
 *
 * @author Lukas Stapelbroek
 */
public final class SingleChoice extends CBMCInputType {
    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 1;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {
            UnifiedNameContainer.getVoter()
    };

    /**
     * The constructor.
     */
    public SingleChoice() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getInputIDinFile() {
        return "SINGLE_CHOICE";
    }

    /**
     * Gets the minimal value.
     *
     * @return the minimal value
     */
    @Override
    public String getMinimalValue() {
        return zero();
    }

    @Override
    public String getMaximalValue() {
        return UnifiedNameContainer.getCandidate();
    }

    @Override
    public boolean isStackType() {
        return false;
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
    public String vetValue(final ElectionTypeContainer container,
                           final List<NEWRowOfValues> rows,
                           final int rowNumber,
                           final int positionInRow,
                           final String newValue) {
        final int number = parseIntegerValue(newValue);
        final String result;
        if (rowNumber < rows.size()
                && (number < 0
                        || rows.get(rowNumber).getAmountCandidates() < number)) {
            result = getMinimalValue();
        } else {
            result = newValue;
        }
        return result;
    }

    @Override
    public void restrictVotes(final String voteName,
                              final CodeArrayListBeautifier code) {
        // No need to restrict.
    }

    @Override
    public String[] getVotePoints(final String[][] votes,
                                  final int amountCandidates,
                                  final int amountVoters) {
        return super.wrongInputTypeArray(amountCandidates, amountVoters);
    }

    @Override
    public String[] getVotePoints(final String[] votes,
                                  final int amountCandidates,
                                  final int amountVoters) {
        final Long[] result = new Long[amountCandidates];
        Arrays.fill(result, 0L);
        for (int i = 0; i < amountVoters; i++) {
            final int vote = Integer.parseInt(votes[i]);
            result[vote]++;
        }
        final String[] toReturn = new String[amountCandidates];
        for (int i = 0; i < result.length; i++) {
            toReturn[i] = "" + result[i];
        }
        return toReturn;
    }

    // @Override
    // public void addMarginMainCheck(CodeArrayListBeautifier code, int margin,
    //                                List<String> origResult) {
    //     code.add("int " + UnifiedNameContainer.getNewVotesName()
    //             + "1[" + UnifiedNameContainer.getVoter() + "];");
    //     code.add("for (int i = 0; i < V; i++) {"); // go over all voters
    //     code.addTab();
    //     code.add("int changed = nondet_int();"); // determine, if we want to
    //     // changed votes for this voter
    //     code.add("assume(0 <= changed);");
    //     code.add("assume(changed <= 1);");
    //     code.add("if(changed) {");
    //     code.addTab();
    //     // if we changed the vote, we keep track of it
    //     code.add("total_diff++;");
    //     // flip the vote (0 -> 1 | 1 -> 0)
    //     code.add("" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i] = !ORIG_VOTES[i];");
    //     code.deleteTab();
    //     code.add("} else {");
    //     code.addTab();
    //     code.add("" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i] = ORIG_VOTES[i];");
    //     code.deleteTab();
    //     code.add("}");
    //     code.deleteTab();
    //     code.add("}");
    //     code.add("assume(total_diff <= MARGIN);"); // no more changes than
    //     // margin allows
    // }

    @Override
    public void addCodeForVoteSum(final CodeArrayListBeautifier code,
                                  final boolean unique) {
        code.add(functionCode(CCodeHelper.IF,
                              eq(arrAccess(arr(), i()), CANDIDATE))
                + space() + plusPlus(SUM)
                + CCodeHelper.SEMICOLON);
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER
        );
    }

    @Override
    public int vetAmountCandidates(final int amountCandidates) {
        return vetAmountInputValue(amountCandidates);
    }

    @Override
    public int vetAmountVoters(final int amountVoters) {
        return vetAmountInputValue(amountVoters);
    }

    @Override
    public int vetAmountSeats(final int amountSeats) {
        return vetAmountInputValue(amountSeats);
    }

    @Override
    public int getNumVotingPoints(final ResultValueWrapper result) {
        return GUIController.getController().getElectionSimulation()
                    .getNumVoters();
    }

    @Override
    public String otherToString() {
        return "Single choice";
    }

    @Override
    public CBMCResultValue convertRowToResultValue(final NEWRowOfValues row) {
        final List<String> values = row.getValues();
        final String value = values.get(0);
        final CBMCResultValueSingle toReturn = new CBMCResultValueSingle();
        toReturn.setValue(CCodeHelper.INT, value, INT_LENGTH);
        return toReturn;
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
    }
}
