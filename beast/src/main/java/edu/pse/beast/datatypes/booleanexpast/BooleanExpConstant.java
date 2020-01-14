package edu.pse.beast.datatypes.booleanexpast;

import edu.pse.beast.toolbox.UnifiedNameContainer;

public final class BooleanExpConstant {
    private BooleanExpConstant() { }

    public static String getConstForVoterAmt() {
        return UnifiedNameContainer.getVoter();
    }

    public static String getConstForCandidateAmt() {
        return UnifiedNameContainer.getCandidate();
    }

    public static String getConstForSeatAmt() {
        return UnifiedNameContainer.getSeats();
    }
}
