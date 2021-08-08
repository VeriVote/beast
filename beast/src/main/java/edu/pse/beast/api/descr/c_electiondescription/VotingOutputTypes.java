package edu.pse.beast.api.descr.c_electiondescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public enum VotingOutputTypes {
    CANDIDATE_LIST, PARLIAMENT, PARLIAMENT_STACK, SINGLE_CANDIDATE;

    private static final String BLANK = " ";
    private static final String UNDERSCORE = "_";

    @Override
    public String toString() {
        return this.name()
                .toLowerCase()
                .replaceAll(UNDERSCORE, BLANK);
    }
}
