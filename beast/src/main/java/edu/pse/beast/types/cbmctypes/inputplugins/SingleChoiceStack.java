package edu.pse.beast.types.cbmctypes.inputplugins;

import static edu.pse.beast.toolbox.CCodeHelper.arr;
import static edu.pse.beast.toolbox.CCodeHelper.arrAccess;
import static edu.pse.beast.toolbox.CCodeHelper.dotArrStructAccess;
import static edu.pse.beast.toolbox.CCodeHelper.eq;
import static edu.pse.beast.toolbox.CCodeHelper.forLoopHeaderCode;
import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.i;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.longVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.lt;
import static edu.pse.beast.toolbox.CCodeHelper.plus;
import static edu.pse.beast.toolbox.CCodeHelper.plusEquals;
import static edu.pse.beast.toolbox.CCodeHelper.plusPlus;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;
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
 * The Class SingleChoiceStack.
 *
 * @author Lukas Stapelbroek
 */
public final class SingleChoiceStack extends CBMCInputType {
    /** The Constant ABS_FUNC. */
    private static final String ABS_FUNC = "abs";
    /** The Constant TOTAL_DIFF. */
    private static final String TOTAL_DIFF = "total_diff";
    /** The Constant TMP_DIFF. */
    private static final String TMP_DIFF = "tmp_diff";
    /** The Constant TMP_RESTR_SUM. */
    private static final String TMP_RESTR_SUM = "tmp_restr_sum";

    /** The Constant MARGIN. */
    private static final String MARGIN = "MARGIN";

    /** The Constant FIVE. */
    private static final int FIVE = 5;

    /** The Constant DIMENSIONS. */
    private static final int DIMENSIONS = 1;

    /** The Constant SIZE_OF_DIMENSIONS. */
    private static final String[] SIZE_OF_DIMENSIONS = {
            UnifiedNameContainer.getCandidate()
    };

    /**
     * The constructor.
     */
    public SingleChoiceStack() {
        super(true, DataType.INT, DIMENSIONS, SIZE_OF_DIMENSIONS);
    }

    @Override
    public String getInputIDinFile() {
        return "SINGLE_CHOICE_STACK";
    }

    @Override
    public String getMinimalValue() {
        return zero();
    }

    @Override
    public String getMaximalValue() {
        return UnifiedNameContainer.getVoter();
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

    /**
     * Compute total sum.
     *
     * @param rows
     *            the rows
     * @param rowNumber
     *            the row number
     * @param positionInRow
     *            the position in row
     * @param newValue
     *            the new value
     * @return the total sum
     */
    private static int computeTotalSum(final List<NEWRowOfValues> rows,
                                       final int rowNumber, final int positionInRow,
                                       final String newValue) {
        int totalSum = 0;
        for (int i = 0; i < rows.size(); i++) { // Add up all values so far.
            final List<String> currentValues = rows.get(i).getValues();
            for (int j = 0; j < currentValues.size(); j++) {
                totalSum += Integer.parseInt(currentValues.get(j));
            }
        }
        totalSum -= Integer.parseInt(
                rows.get(rowNumber).getValues().get(positionInRow)
                );
        totalSum += Integer.parseInt(newValue);
        return totalSum;
    }

    @Override
    public String vetValue(final ElectionTypeContainer container,
                           final List<NEWRowOfValues> rows,
                           final int rowNumber,
                           final int positionInRow,
                           final String newValue) {
        final String value;
        if (rows.size() <= rowNumber) {
            value = newValue;
        } else {
            final int number;
            try {
                number = Integer.parseInt(newValue);
            } catch (NumberFormatException e) {
                return zero();
            }
            if (number < 0
                    || number > rows.get(rowNumber).getAmountVoters()) {
                value = zero();
            } else {
                final int totalSum =
                        computeTotalSum(rows, rowNumber, positionInRow, newValue);
                if (totalSum > rows.get(rowNumber).getAmountVoters()) {
                    // we would exceed the limit with this addition, so
                    // we reset to 0
                    value = zero();
                } else {
                    value = newValue;
                }
            }
        }
        return value;
    }

    @Override
    public void flipVote(final String newVotesName,
                         final String origVotesName,
                         final List<String> loopVars,
                         final CodeArrayListBeautifier code) {
        final String newVotesNameAcc = getFullVoteAccess(newVotesName, loopVars);
        final String origVotesNameAcc = getFullVoteAccess(origVotesName, loopVars);
        code.add();
        code.add(longVarEqualsCode(TMP_DIFF)
                + functionCode(CBMCCodeGenerator.NONDET_LONG)
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(functionCode(CBMCCodeGenerator.ASSUME,
                              leq(functionCode(ABS_FUNC, TMP_DIFF),
                                  MARGIN))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode(CBMCCodeGenerator.ASSUME,
                              leq(zero(), plus(origVotesNameAcc, TMP_DIFF)))
                + CCodeHelper.SEMICOLON);
        code.add(functionCode(CCodeHelper.IF, lt(TMP_DIFF, zero()))
                + CCodeHelper.OPENING_BRACES);
        code.add();
        code.add(functionCode(CBMCCodeGenerator.ASSUME,
                              leq(functionCode(ABS_FUNC, TMP_DIFF),
                                  origVotesNameAcc))
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(varAssignCode(newVotesNameAcc, plus(origVotesNameAcc, TMP_DIFF))
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(varAssignCode(POS_DIFF,
                               plus(TOTAL_DIFF, functionCode(ABS_FUNC, TMP_DIFF)))
                + CCodeHelper.SEMICOLON);
        code.add(plusEquals(TOTAL_DIFF, TMP_DIFF) + CCodeHelper.SEMICOLON);
    }

    @Override
    public void restrictVotes(final String voteName,
                              final CodeArrayListBeautifier code) {
        code.add(varAssignCode(uintVarEqualsCode(TMP_RESTR_SUM), zero())
                + CCodeHelper.SEMICOLON);
        code.add(forLoopHeaderCode(LOOP_R_0, lt(), CBMCCodeGenerator.C));
        code.add(plusEquals(TMP_RESTR_SUM,
                            dotArrStructAccess(voteName, LOOP_R_0))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES);
        code.add(functionCode(CBMCCodeGenerator.ASSUME,
                              leq(TMP_RESTR_SUM, CBMCCodeGenerator.V))
                + CCodeHelper.SEMICOLON);
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

    @Override
    public void addCodeForVoteSum(final CodeArrayListBeautifier code,
                                  final boolean unique) {
        code.add(functionCode(CCodeHelper.IF, eq(arrAccess(arr(), i()), CANDIDATE))
                + space() + plusPlus(SUM) + CCodeHelper.SEMICOLON);
    }

    @Override
    public InternalTypeContainer getInternalTypeContainer() {
        return new InternalTypeContainer(
                new InternalTypeContainer(InternalTypeRep.CANDIDATE),
                InternalTypeRep.VOTER);
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
        return GUIController.getController().getElectionSimulation().getNumVoters();
    }

    /**
     * Test method.
     *
     * @param i
     *            the i
     * @return the int
     */
    public int test(final int i) {
        return FIVE;
    }

    @Override
    public String otherToString() {
        return "Single choice stack";
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
        // TODO Auto-generated method stub
    }
}
