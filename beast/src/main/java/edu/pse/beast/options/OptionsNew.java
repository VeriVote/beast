package edu.pse.beast.options;

public class OptionsNew {
    private int minVoters = 1;
    private int maxVoters = 1;

    private int minCandidates = 1;
    private int maxCandidates = 1;

    private int minSeats = 1;
    private int maxSeats = 1;

    public int getMinVoters() {
        return minVoters;
    }

    public void setMinVoters(final int minVoterBound) {
        this.minVoters = minVoterBound;
    }

    public int getMaxVoters() {
        return maxVoters;
    }

    public void setMaxVoters(final int maxVoterBound) {
        this.maxVoters = maxVoterBound;
    }

    public int getMinCandidates() {
        return minCandidates;
    }

    public void setMinCandidates(final int minCandidateBound) {
        this.minCandidates = minCandidateBound;
    }

    public int getMaxCandidates() {
        return maxCandidates;
    }

    public void setMaxCandidates(final int maxCandidateBound) {
        this.maxCandidates = maxCandidateBound;
    }

    public int getMinSeats() {
        return minSeats;
    }

    public void setMinSeats(final int minSeatBound) {
        this.minSeats = minSeatBound;
    }

    public int getMaxSeats() {
        return maxSeats;
    }

    public void setMaxSeats(final int maxSeatBound) {
        this.maxSeats = maxSeatBound;
    }
}
