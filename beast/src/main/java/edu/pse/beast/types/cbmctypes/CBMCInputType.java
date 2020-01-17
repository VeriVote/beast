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

    @Override
    public void flipVote(final String newVotesName, final String origVotesName,
                         final List<String> loopVars,
                         final CodeArrayListBeautifier code) {
        code.add("int changed = nondet_int();");
        code.add("assume(0 <= changed);");
        code.add("assume(changed <= 1);");
        code.add("if(changed) {");
        String newVotesNameAcc = getFullVoteAccess(newVotesName, loopVars);
        String origVotesNameAcc = getFullVoteAccess(origVotesName, loopVars);
        // we changed one vote, so we keep track of it
        code.add("pos_diff++;");
        code.add("assume(" + newVotesNameAcc
                + " != " + origVotesNameAcc + ");");
        code.add("} else {");
        code.addTab();
        code.add(this.setVoteValue(newVotesName,
                                   UnifiedNameContainer.getOrigVotesName(),
                                   loopVars));
        code.add("}");
    }

    @Override
    public void addCheckerSpecificHeaders(final CodeArrayListBeautifier code) {
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
    public String getVoteDescriptionString(final List<List<String>> origVotes) {
        String votesString = "";
        int voterIndex = 0;
        // iterate over the voters
        for (Iterator<List<String>> iterator = origVotes.iterator();
                iterator.hasNext();) {
            List<String> list = iterator.next();
            String oneVoter = "";
            try {
                oneVoter = GUIController.getController().getElectionSimulation()
                        .getVoterName(voterIndex);
            } catch (IndexOutOfBoundsException e) {
                oneVoter = "" + voterIndex;
            }
            oneVoter = oneVoter + ": ";
            voterIndex++;
            int partyIndex = 0;
            // iterate over the candidates
            for (Iterator<String> iterator2 = list.iterator(); iterator2
                    .hasNext();) {
                String voteAmount = iterator2.next();
                try {
                    oneVoter +=
                            GUIController.getController()
                            .getElectionSimulation().getPartyName(partyIndex);
                } catch (IndexOutOfBoundsException e) {
                    oneVoter = "" + partyIndex;
                }
                oneVoter += ": " + voteAmount + ", ";
                partyIndex++;
            }
            votesString += oneVoter + "\n";
        }
        return votesString;
    }
}
