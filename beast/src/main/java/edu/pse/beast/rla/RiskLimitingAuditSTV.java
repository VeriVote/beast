package edu.pse.beast.rla;

import java.util.List;
import java.util.Random;

public class RiskLimitingAuditSTV {

    private final int votes;
    private Random random;
    private int margin;
    private double alpha;
    private double lambda;
    private List<Integer> selectedVotes;

    public RiskLimitingAuditSTV(int margin, double alpha, double lambda, long seed, int votes) {
        random = new Random(seed);
        this.margin = margin;
        this.alpha = alpha;
        this.lambda = lambda;
        this.votes = votes;
        for (int i = 0; i < getNeededVotes(); i++) {
            selectedVotes.add(random.nextInt());
        }
    }

    /**
     * returns the list of all votes that need to get selected
     */
    public List<Integer> getSelectedVotes() {
        return selectedVotes;
    }




    private int getNeededVotes() {
        return 5;
    }
}
