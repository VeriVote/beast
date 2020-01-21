package edu.pse.beast.types.cbmctypes;

import java.util.Iterator;
import java.util.List;

import edu.pse.beast.highlevel.javafx.GUIController;
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

    /** The Constant COLON. */
    private static final String COLON = ":";
    /** The Constant BLANK. */
    private static final String BLANK = " ";

    /** The Constant OPENING_BRACES. */
    private static final String OPENING_BRACES = "{";
    /** The Constant CLOSING_BRACES. */
    private static final String CLOSING_BRACES = "}";

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
        code.add("");
        code.add("int changed = nondet_int();");
        code.add("");
        code.add("assume(0 <= changed);");
        code.add("assume(changed <= 1);");
        code.add("if(changed)" + BLANK + OPENING_BRACES);
        // we changed one vote, so we keep track of it
        code.add("pos_diff++;");
        code.add("assume(" + newVotesNameAcc
                + " != " + origVotesNameAcc + ");");
        code.add(CLOSING_BRACES + BLANK + "else" + BLANK + OPENING_BRACES);
        code.addTab();
        code.add(this.setVoteValue(newVotesName,
                                   UnifiedNameContainer.getOrigVotesName(),
                                   loopVars));
        code.add(CLOSING_BRACES);
    }

    @Override
    public final void addCheckerSpecificHeaders(final CodeArrayListBeautifier code) {
        // add the headers CBMC needs;
        code.add("#include <stdlib.h>");
        code.add("#include <stdint.h>");
        code.add("#include <assert.h>");
        code.add("");
        code.add("unsigned int nondet_uint();");
        code.add("int nondet_int();");
        code.add("");
        code.add("#define assert2(x, y) __CPROVER_assert(x, y)");
        code.add("#define assume(x) __CPROVER_assume(x)");
        code.add("");
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
            oneVoter = oneVoter + COLON + BLANK;
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
                oneVoter += COLON + BLANK + voteAmount + "," + BLANK;
                partyIndex++;
            }
            votesString += oneVoter + "\n";
        }
        return votesString;
    }
}
