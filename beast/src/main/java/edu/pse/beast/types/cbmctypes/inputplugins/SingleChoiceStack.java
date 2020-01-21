package edu.pse.beast.types.cbmctypes.inputplugins;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.NEWRowOfValues;
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
    /** The Constant ZERO. */
    private static final String ZERO = "0";
    /** The Constant FIVE. */
    private static final int FIVE = 5;

    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    private static final String CLOSING_BRACES = "}";

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
        return ZERO;
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
        for (int i = 0; i < rows.size(); i++) { // Add up all values so far
            List<String> currentValues = rows.get(i).getValues();
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
                return ZERO;
            }
            if (number < 0
                    || number > rows.get(rowNumber).getAmountVoters()) {
                value = ZERO;
            } else {
                final int totalSum =
                        computeTotalSum(rows, rowNumber, positionInRow, newValue);
                if (totalSum > rows.get(rowNumber).getAmountVoters()) {
                    // we would exceed the limit with this addition, so
                    // we reset to 0
                    value = ZERO;
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
        code.add("");
        code.add("long tmp_diff = nondet_long();");
        code.add("");
        code.add("assume(abs(tmp_diff) <= MARGIN);");
        code.add("assume(0 <= (" + origVotesNameAcc + " + tmp_diff));");
        code.add("if(tmp_diff < 0) " + OPENING_BRACES);
        code.add("");
        code.add("assume(abs(tmp_diff) <= " + origVotesNameAcc + ");");
        code.add("");
        code.add(CLOSING_BRACES);
        code.add(newVotesNameAcc + " = " + origVotesNameAcc + " + tmp_diff;");
        code.add("");
        code.add("pos_diff = total_diff  + abs(tmp_diff);");
        code.add("total_diff += tmp_diff;");
    }

    @Override
    public void restrictVotes(final String voteName,
                              final CodeArrayListBeautifier code) {
        code.add("unsigned int tmp_restr_sum = 0;");
        code.add("for(int loop_r_0 = 0; loop_r_0 < C; loop_r_0++) " + OPENING_BRACES);
        code.add("tmp_restr_sum = tmp_restr_sum + " + voteName
                + ".arr[loop_r_0];");
        code.add(CLOSING_BRACES);
        code.add("assume(tmp_restr_sum <= V);");
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

    @Override
    public void addCodeForVoteSum(final CodeArrayListBeautifier code,
                                  final boolean unique) {
        code.add("if(arr[i] == candidate) sum++;");
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
        List<String> values = row.getValues();
        String value = values.get(0);
        CBMCResultValueSingle toReturn = new CBMCResultValueSingle();
        toReturn.setValue("int", value, INT_LENGTH);
        return toReturn;
    }

    @Override
    public void addExtraCodeAtEndOfCodeInit(final CodeArrayListBeautifier code,
                                            final String valueName,
                                            final List<String> loopVariables) {
        // TODO Auto-generated method stub
    }
}
