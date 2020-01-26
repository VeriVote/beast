package edu.pse.beast.types.cbmctypes;

import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.include;
import static edu.pse.beast.toolbox.CCodeHelper.leq;
import static edu.pse.beast.toolbox.CCodeHelper.neq;
import static edu.pse.beast.toolbox.CCodeHelper.one;
import static edu.pse.beast.toolbox.CCodeHelper.plusPlus;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;
import static edu.pse.beast.toolbox.CCodeHelper.zero;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.CodeArrayListBeautifier;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.types.InputType;

/**
 * The Class CBMCInputType.
 *
 * @author Lukas Stapelbroek
 */
public abstract class CBMCInputType extends InputType {
    /** The Constant INT_LENGTH. */
    protected static final int INT_LENGTH = 32;

    /** The Constant ASSUME. */
    protected static final String ASSUME = "assume";
    /** The Constant ARR. */
    protected static final String ARR = "arr";

    /** The Constant NONDET_INT. */
    protected static final String NONDET_INT = "nondet_int";
    /** The Constant NONDET_UINT. */
    protected static final String NONDET_UINT = "nondet_uint";

    /** The Constant "i". */
    protected static final String I = "i";
    /** The Constant "j". */
    protected static final String J = "j";
    /** The Constant LOOP_R_0. */
    protected static final String LOOP_R_0 = "loop_r_0";
    /** The Constant POS_DIFF. */
    protected static final String POS_DIFF = "pos_diff";

    /** The Constant C. */
    protected static final String C = "C";
    /** The Constant V. */
    protected static final String V = "V";

    /** The Constant SUM. */
    protected static final String SUM = "sum";
    /** The Constant CANDIDATE. */
    protected static final String CANDIDATE = "candidate";
    /** The Constant CAND_SUM. */
    protected static final String CAND_SUM = "candSum";

    /** The Constant CPROVER_ASSUME. */
    private static final String CPROVER_ASSUME = "__CPROVER_assume";
    /** The Constant CPROVER_ASSERT. */
    private static final String CPROVER_ASSERT = "__CPROVER_assert";

    /** The Constant ASSERT2. */
    private static final String ASSERT2 = "assert2";

    /** The Constant CHANGED. */
    private static final String CHANGED = "changed";

    /** The Constant X. */
    private static final String X = "x";

    /** The Constant Y. */
    private static final String Y = "y";

    /**
     * The constructor.
     *
     * @param unsigned
     *            the unsigned
     * @param dataType
     *            the data type
     * @param dimensions
     *            the dimensions
     * @param sizeOfDimensions
     *            the size of dimensions
     */
    public CBMCInputType(final boolean unsigned, final DataType dataType,
                         final int dimensions,
                         final String[] sizeOfDimensions) {
        super(unsigned, dataType, dimensions, sizeOfDimensions);
    }

    /**
     * Vet amount of input value.
     *
     * @param value
     *            the input value
     * @return the int
     */
    protected final int vetAmountInputValue(final int value) {
        return (value < 1) ? 1 : value;
    }

    /**
     * {@inheritDoc}
     *
     * <p>FIXME: Investigate why {@link SingleChoiceStack#flipVote(..)}
     * makes a re-implementation and whether this is really necessary.
     */
    @Override
    public void flipVote(final String newVotesName, final String origVotesName,
                         final List<String> loopVars,
                         final CodeArrayListBeautifier code) {
        // TODO: Check whether this makes sense and why flipVote in
        //       SingleChoiceStack does not reuse anything of this!
        final String newVotesNameAcc = getFullVoteAccess(newVotesName, loopVars);
        final String origVotesNameAcc = getFullVoteAccess(origVotesName, loopVars);
        code.add();
        code.add(CCodeHelper.INT + space()
                + varAssignCode(CHANGED, functionCode(NONDET_INT))
                + CCodeHelper.SEMICOLON);
        code.add();
        code.add(functionCode(ASSUME, leq(zero(), CHANGED)) + CCodeHelper.SEMICOLON);
        code.add(functionCode(ASSUME, leq(CHANGED, one())) + CCodeHelper.SEMICOLON);
        code.add(functionCode(CCodeHelper.IF, CHANGED)
                + space() + CCodeHelper.OPENING_BRACES);
        // we changed one vote, so we keep track of it
        code.add(plusPlus(POS_DIFF) + CCodeHelper.SEMICOLON);
        code.add(functionCode(ASSUME, neq(newVotesNameAcc, origVotesNameAcc))
                + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.CLOSING_BRACES + space()
                + CCodeHelper.ELSE + space() + CCodeHelper.OPENING_BRACES);
        code.addTab();
        code.add(this.setVoteValue(newVotesName,
                                   UnifiedNameContainer.getOrigVotesName(),
                                   loopVars));
        code.add(CCodeHelper.CLOSING_BRACES);
    }

    @Override
    public final void addCheckerSpecificHeaders(final CodeArrayListBeautifier code) {
        // add the headers CBMC needs;
        code.add(include("stdlib"));
        code.add(include("stdint"));
        code.add(include("assert"));
        code.add();
        code.add(CCodeHelper.UNSIGNED + space()
                + CCodeHelper.INT + space()
                + functionCode(NONDET_UINT) + CCodeHelper.SEMICOLON);
        code.add(CCodeHelper.UNSIGNED + space()
                + CCodeHelper.INT + space()
                + functionCode(NONDET_INT) + CCodeHelper.SEMICOLON);
        code.add();

        code.add(CCodeHelper.DEFINE + space()
                + functionCode(ASSERT2, X, Y) + space()
                + functionCode(CPROVER_ASSERT, X, Y));
        code.add(CCodeHelper.DEFINE + space()
                + functionCode(ASSUME, X) + space()
                + functionCode(CPROVER_ASSUME, X));
        code.add();
    }

    @Override
    public final String getVoteDescriptionString(final List<List<String>> origVotes) {
        String votesString = "";
        int voterIndex = 0;
        // Iterate over the voters
        for (Iterator<List<String>> iterator = origVotes.iterator();
                iterator.hasNext();) {
            String oneVoter = "";
            try {
                oneVoter = GUIController.getController().getElectionSimulation()
                        .getVoterName(voterIndex);
            } catch (IndexOutOfBoundsException e) {
                oneVoter = "" + voterIndex;
            }
            oneVoter = oneVoter + CCodeHelper.COLON + space();
            voterIndex++;
            int partyIndex = 0;
            final List<String> list = iterator.next();
            // Iterate over the candidates
            for (Iterator<String> iterator2 = list.iterator();
                    iterator2.hasNext();) {
                String voteAmount = iterator2.next();
                try {
                    oneVoter +=
                            GUIController.getController()
                            .getElectionSimulation().getPartyName(partyIndex);
                } catch (IndexOutOfBoundsException e) {
                    oneVoter = "" + partyIndex;
                }
                oneVoter += CCodeHelper.COLON
                        + space() + voteAmount + CCodeHelper.COMMA
                        + space();
                partyIndex++;
            }
            votesString += oneVoter + CCodeHelper.LINE_BREAK;
        }
        return votesString;
    }
}
