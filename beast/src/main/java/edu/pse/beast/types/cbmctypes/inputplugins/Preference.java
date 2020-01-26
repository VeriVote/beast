package edu.pse.beast.types.cbmctypes.inputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.dotArrStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.dotStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.neq;
import static edu.pse.beast.toolbox.CCodeHelper.plusPlus;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

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

/**
 * The Class Preference.
 *
 * @author Lukas Stapelbroek
 */
public final class Preference extends CBMCInputType {
    /** The Constant LOOP_R_1. */
    private static final String LOOP_R_1 = "loop_r_1";
    /** The Constant LOOP_R_2. */
    private static final String LOOP_R_2 = "loop_r_2";

    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 2;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {
            UnifiedNameContainer.getVoter(),
            UnifiedNameContainer.getCandidate()
    };

    /**
     * The constructor.
     */
    public Preference() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getInputIDinFile() {
        return "PREFERENCE";
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
    public boolean hasVariableAsMinValue() {
        return false;
    }

    @Override
    public boolean hasVariableAsMaxValue() {
        return true;
    }

    @Override
    public boolean isVotingForOneCandidate() {
        return false;
    }

    @Override
    public String vetValue(final ElectionTypeContainer container,
                           final List<NEWRowOfValues> row,
                           final int rowNumber,
                           final int positionInRow,
                           final String newValue) {
        final int number;
        try {
            number = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            return zero();
        }
        final String result;
        if (number < 0 || number > row.get(rowNumber).getAmountCandidates()) {
            result = zero();
        } else if (row.get(rowNumber).getValues().contains(newValue)) {
            result = zero();
        } else {
            result = newValue;
        }
        return result;
    }

    @Override
    public void restrictVotes(final String voteName,
                              final CodeArrayListBeautifier code) {
        code.add(forLoopHeaderCode(LOOP_R_0, CCodeHelper.LT_SIGN, V));
        code.add(forLoopHeaderCode(LOOP_R_1, CCodeHelper.LT_SIGN, C));
        code.add(forLoopHeaderCode(LOOP_R_2, CCodeHelper.LT_SIGN, C));

        code.add(functionCode(CCodeHelper.IF, neq(LOOP_R_1, LOOP_R_2))
                + space() + CCodeHelper.OPENING_BRACES);
        code.add(functionCode(ASSUME,
                              neq(dotArrStructAccess(voteName, LOOP_R_0, LOOP_R_1),
                                  dotArrStructAccess(voteName, LOOP_R_0, LOOP_R_2))
                ) + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    @Override
    public String[] getVotePoints(final String[][] votes,
                                  final int amountCandidates,
                                  final int amountVoters) {
        final String[] result = new String[amountCandidates];
        Arrays.fill(result, 0L);
        for (int i = 0; i < amountVoters; i++) {
            final String[] vote = votes[i];
            for (int j = 0; j < amountCandidates; j++) {
                final String chosenCandidate = vote[j];
                final int iChosenCandidate = Integer.parseInt(chosenCandidate);
                result[iChosenCandidate] += amountCandidates - 1 - j;
            }
        }
        return result;
    }

    @Override
    public String[] getVotePoints(final String[] votes,
                                  final int amountCandidates,
                                  final int amountVoters) {
        return super.wrongInputTypeArray(amountCandidates, amountVoters);
    }

    // @Override
    // public void addMarginMainCheck(CodeArrayListBeautifier code, int margin,
    //                                List<String> origResult) {
    //     code.add("int "
    //         + UnifiedNameContainer.getNewVotesName() + "1["
    //         + UnifiedNameContainer.getVoter() + "]["
    //         + UnifiedNameContainer.getCandidate() + "];");
    //
    //     code.add("for (int i = 0; i < V; i++) {"); // go over all voters
    //     code.addTab();
    //     code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
    //     code.addTab();
    //     code.add("int changed = nondet_int();"); // determine, if we want to
    //     // changed votes for voter - candidate pair
    //     code.add("assume(0 <= changed);");
    //     code.add("assume(changed <= 1);");
    //     code.add("if(changed) {");
    //     code.addTab();
    //     code.add("total_diff++;"); // if we changed the vote, we keep track
    //                                // of it
    //     code.add("" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j] = nondet_int();");
    //     // set the vote to (0-100), but different from original
    //     code.add("assume(" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j] != ORIG_VOTES[i][j]);");
    //     code.add("assume(0 <= " + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j]);");
    //     code.add("assume(" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j] <= 100);");
    //     code.deleteTab();
    //     code.add("} else {");
    //     code.addTab();
    //     code.add("" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j] = ORIG_VOTES[i][j];");
    //     code.deleteTab();
    //     code.add("}");
    //     code.deleteTab();
    //     code.add("}");
    //     code.deleteTab();
    //     code.add("}"); // end of the double for loop
    //     code.add("assume(total_diff <= MARGIN);"); // no more changes than
    //     // margin allows
    // }

    @Override
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
        final String ownLoopVar = code.getNotUsedVarName("j_prime");
        final String loopHead = forLoopHeaderCode(ownLoopVar, CCodeHelper.LT_SIGN,
                                                  loopVariables.get(1));
        code.add(loopHead);
        code.add(functionCode(
                    ASSUME,
                    neq(dotStructAccess(valueName,
                                        UnifiedNameContainer.getStructValueName(),
                                        loopVariables.get(0), loopVariables.get(1)),
                        dotStructAccess(valueName,
                                        UnifiedNameContainer.getStructValueName(),
                                        loopVariables.get(0), ownLoopVar)))
                + CCodeHelper.SEMICOLON);
        code.deleteTab();
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    @Override
    public void addCodeForVoteSum(final CodeArrayListBeautifier code,
                                  final boolean unique) {
        code.add(functionCode(CCodeHelper.IF,
                              eq(arrAccess("arr", I, zero()),
                                 "candidate")
                ) + space() + plusPlus("sum")
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
    public int vetAmountCandidates(final int amountCandidates) {
        return vetAmountInputValue(amountCandidates);
    }

    @Override
    public int vetAmountVoters(final int amountVoters) {
        return vetAmountInputValue(amountVoters);
    }

    @Override
    public int getNumVotingPoints(final ResultValueWrapper result) {
        return GUIController.getController().getElectionSimulation()
                .getNumVoters();
    }

    @Override
    public int vetAmountSeats(final int amountSeats) {
        return vetAmountInputValue(amountSeats);
    }

    @Override
    public String otherToString() {
        return "Preference";
    }

    @Override
    public CBMCResultValue convertRowToResultValue(final NEWRowOfValues row) {
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
}
