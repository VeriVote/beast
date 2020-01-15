package edu.pse.beast.electionsimulator;

import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;

public class ElectionSimulationData {
    private final int voters;
    private final int candidates;
    private final int seats;
    private final ResultValueWrapper values;

    public ElectionSimulationData(final int voterNum,
                                  final int candidateNum,
                                  final int seatNum,
                                  final ResultValueWrapper vals) {
        this.voters = voterNum;
        this.candidates = candidateNum;
        this.seats = seatNum;
        this.values = vals;
    }

    public int getVoters() {
        return voters;
    }

    public int getCandidates() {
        return candidates;
    }

    public int getSeats() {
        return seats;
    }

    public ResultValueWrapper getValues() {
        return values;
    }
}
