package edu.pse.beast.types.cbmctypes.inputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.conjunct;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.neq;
import static edu.pse.beast.toolbox.CCodeHelper.plusEquals;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.varEqualsCode;
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
 * The Class Approval.
 *
 * @author Lukas Stapelbroek
 */
public final class Approval extends CBMCInputType {
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
    public Approval() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getInputIDinFile() {
        return "APPROVAL";
    }

    @Override
    public String getMinimalValue() {
        return CCodeHelper.zero();
    }

    @Override
    public String getMaximalValue() {
        return CCodeHelper.one();
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
    public String vetValue(final ElectionTypeContainer container,
                           final List<NEWRowOfValues> rows,
                           final int rowNumber,
                           final int positionInRow,
                           final String newValue) {
        final int number;
        try {
            number = Integer.parseInt(newValue);
        } catch (NumberFormatException e) {
            return CCodeHelper.zero();
        }
        if (number != 0 && number != 1) {
            return CCodeHelper.zero();
        } else {
            return newValue;
        }
    }

    @Override
    public String[] getVotePoints(final String[][] votes,
                                  final int amountCandidates,
                                  final int amountVoters) {
        final String[] result = new String[amountCandidates];
        Arrays.fill(result, 0L);
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
    //     code.add("int " + UnifiedNameContainer.getNewVotesName()
    //             + "1[UnifiedNameContainer.getVoter()]"
    //             + "[UnifiedNameContainer.getCandidate()];");
    //
    //     code.add("for (int i = 0; i < V; i++) {"); // go over all voters
    //     code.addTab();
    //     code.add("for (int j = 0; i < C; i++) {"); // go over all candidates
    //     code.addTab();
    //     code.add("int changed = nondet_int();"); // determine, if we want to
    //                                              // changed votes for this
    //                                              // voter - candidate pair
    //     code.add("assume(0 <= changed);");
    //     code.add("assume(changed <= 1);");
    //     code.add("if(changed) {");
    //     code.addTab();
    //     code.add("total_diff++;"); // if we changed the vote, we keep track
    //     // of it
    //     code.add("" + UnifiedNameContainer.getNewVotesName()
    //             + "1[i][j] = !ORIG_VOTES[i][j];"); // flip the vote
    //     // (0 -> 1 | 1 -> 0)
    //     code.deleteTab();
    //     code.add("} else {");
    //     code.addTab();
    //     code.add("" + UnifiedNameContainer.getNewVotesName() + "1[i][j] =
    //     ORIG_VOTES[i][j];");
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
    public void addCodeForVoteSum(final CodeArrayListBeautifier code,
                                  final boolean unique) {
        code.add(varEqualsCode(CAND_SUM) + arrAccess(ARR, I, CANDIDATE));
        if (unique) {
            code.add(forLoopHeaderCode(J, CCodeHelper.LT_SIGN, C));
            code.add(functionCode(CCodeHelper.IF,
                                  conjunct(neq(J, CANDIDATE),
                                           leq(CAND_SUM,
                                               arrAccess(ARR, I, J))))
                    + CCodeHelper.BLANK + varAssignCode(CAND_SUM, zero())
                    + CCodeHelper.SEMICOLON);
            code.add(CCodeHelper.CLOSING_BRACES);
        }
        code.add(plusEquals(SUM, CAND_SUM) + CCodeHelper.SEMICOLON);
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
        return "Approval";
    }

    @Override
    public CBMCResultValue convertRowToResultValue(final NEWRowOfValues row) {
        final List<String> values = row.getValues();
        final List<CBMCResultValueWrapper> wrappedValues =
                new ArrayList<CBMCResultValueWrapper>();

        for (Iterator<String> iterator = values.iterator();
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
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
        // TODO Auto-generated method stub

    }

    @Override
    public void restrictVotes(final String voteName,
                              final CodeArrayListBeautifier code) {
        // No extra needed
    }
}
