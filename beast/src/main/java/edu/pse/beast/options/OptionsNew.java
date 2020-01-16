package edu.pse.beast.options;

/**
 * The Class OptionsNew.
 */
public class OptionsNew {

    /** The min voters. */
    private int minVoters = 1;

    /** The max voters. */
    private int maxVoters = 1;

    /** The min candidates. */
    private int minCandidates = 1;

    /** The max candidates. */
    private int maxCandidates = 1;

    /** The min seats. */
    private int minSeats = 1;

    /** The max seats. */
    private int maxSeats = 1;

    /**
     * Gets the min voters.
     *
     * @return the min voters
     */
    public int getMinVoters() {
        return minVoters;
    }

    /**
     * Sets the min voters.
     *
     * @param minVoterBound the new min voters
     */
    public void setMinVoters(final int minVoterBound) {
        this.minVoters = minVoterBound;
    }

    /**
     * Gets the max voters.
     *
     * @return the max voters
     */
    public int getMaxVoters() {
        return maxVoters;
    }

    /**
     * Sets the max voters.
     *
     * @param maxVoterBound the new max voters
     */
    public void setMaxVoters(final int maxVoterBound) {
        this.maxVoters = maxVoterBound;
    }

    /**
     * Gets the min candidates.
     *
     * @return the min candidates
     */
    public int getMinCandidates() {
        return minCandidates;
    }

    /**
     * Sets the min candidates.
     *
     * @param minCandidateBound the new min candidates
     */
    public void setMinCandidates(final int minCandidateBound) {
        this.minCandidates = minCandidateBound;
    }

    /**
     * Gets the max candidates.
     *
     * @return the max candidates
     */
    public int getMaxCandidates() {
        return maxCandidates;
    }

    /**
     * Sets the max candidates.
     *
     * @param maxCandidateBound the new max candidates
     */
    public void setMaxCandidates(final int maxCandidateBound) {
        this.maxCandidates = maxCandidateBound;
    }

    /**
     * Gets the min seats.
     *
     * @return the min seats
     */
    public int getMinSeats() {
        return minSeats;
    }

    /**
     * Sets the min seats.
     *
     * @param minSeatBound the new min seats
     */
    public void setMinSeats(final int minSeatBound) {
        this.minSeats = minSeatBound;
    }

    /**
     * Gets the max seats.
     *
     * @return the max seats
     */
    public int getMaxSeats() {
        return maxSeats;
    }

    /**
     * Sets the max seats.
     *
     * @param maxSeatBound the new max seats
     */
    public void setMaxSeats(final int maxSeatBound) {
        this.maxSeats = maxSeatBound;
    }
}
