package edu.pse.beast.electionsimulator;

import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

/**
 * The Class ElectionSimulationData.
 *
 * @author Lukas Stapelbroek
 */
public class ElectionSimulationData {

    /** The voters. */
    private final int voters;

    /** The candidates. */
    private final int candidates;

    /** The seats. */
    private final int seats;

    /** the number of stacks */
    private int stacks;
    /** The values. */
    private final ResultValueWrapper values;


    /**
     * Instantiates a new election simulation data.
     *  @param voterNum
     *            the voter num
     * @param candidateNum
     *            the candidate num
     * @param seatNum
     *            the seat num
     * @param stacks
     * @param vals
     */
    public ElectionSimulationData(final int voterNum, final int candidateNum,
                                  final int seatNum,
                                  int stacks, final ResultValueWrapper vals) {
        this.voters = voterNum;
        this.candidates = candidateNum;
        this.seats = seatNum;
        this.values = vals;
        this.stacks = stacks;
    }

    /**
     * Gets the voters.
     *
     * @return the voters
     */
    public int getVoters() {
        return voters;
    }

    /**
     * Gets the candidates.
     *
     * @return the candidates
     */
    public int getCandidates() {
        return candidates;
    }

    /**
     * Gets the seats.
     *
     * @return the seats
     */
    public int getSeats() {
        return seats;
    }

    /**
     * Gets the values.
     *
     * @return the values
     */
    public ResultValueWrapper getValues() {
        return values;
    }

    public int getStacks() {
        return stacks;
    }
}
