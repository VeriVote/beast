package edu.pse.beast.zzz.electionsimulator;

import edu.pse.beast.zzz.toolbox.valueContainer.ResultValueWrapper;

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

    /** The values. */
    private final ResultValueWrapper values;

    /**
     * Instantiates a new election simulation data.
     *
     * @param voterNum
     *            the voter num
     * @param candidateNum
     *            the candidate num
     * @param seatNum
     *            the seat num
     * @param vals
     *            the vals
     */
    public ElectionSimulationData(final int voterNum, final int candidateNum,
                                  final int seatNum,
                                  final ResultValueWrapper vals) {
        this.voters = voterNum;
        this.candidates = candidateNum;
        this.seats = seatNum;
        this.values = vals;
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
}
