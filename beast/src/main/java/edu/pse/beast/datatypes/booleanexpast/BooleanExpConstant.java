package edu.pse.beast.datatypes.booleanexpast;

import edu.pse.beast.zzz.toolbox.UnifiedNameContainer;

/**
 * The Class BooleanExpConstant.
 *
 * @author Lukas Stapelbroek
 */
public final class BooleanExpConstant {

    /**
     * Instantiates a new boolean exp constant.
     */
    private BooleanExpConstant() { }

    /**
     * Gets the const for voter amt.
     *
     * @return the const for voter amt
     */
    public static String getConstForVoterAmt() {
        return UnifiedNameContainer.getVoter();
    }

    /**
     * Gets the const for candidate amt.
     *
     * @return the const for candidate amt
     */
    public static String getConstForCandidateAmt() {
        return UnifiedNameContainer.getCandidate();
    }

    /**
     * Gets the const for seat amt.
     *
     * @return the const for seat amt
     */
    public static String getConstForSeatAmt() {
        return UnifiedNameContainer.getSeats();
    }
}
