package edu.pse.beast.propertychecker;

import java.io.File;

public abstract class CBMCProcess extends Checker {

    // this is the last line in the cbmc output, if the verification was
    // successful
    private final String SUCCESSLINE = "VERIFICATION SUCCESSFUL";

    // this is the last line in the cbmc output, if the assertion
    // failed
    private final String FAILURELINE = "VERIFICATION FAILED";
	
    /**
     * creates a new CBMCProcess that is a super class for the system specific processes that run cbmc
     * @param voters the amount of voters
     * @param candidates the amount of candidates
     * @param seats the amount of seats
     * @param advanced the string that represents the advanced options
     * @param toCheck the file to check with cbmc
     * @param parent the parent CheckerFactory, that has to be notified about finished checking
     */
    public CBMCProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent, Result result) {
        super(voters, candidates, seats, advanced, toCheck, parent, result);
    }
    
    @Override
    public boolean checkAssertionSuccess() {
        if (super.getResultList() != null && super.getResultList().size() > 0) {
            return super.getResultList().get(super.getResultList().size() - 1).contains(SUCCESSLINE);
        } else {
            return false;
        }
    }

    @Override
    public boolean checkAssertionFailure() {
        if (super.getResultList() != null && super.getResultList().size() > 0) {
            return super.getResultList().get(super.getResultList().size() - 1).contains(FAILURELINE);
        } else {
            return false;
        }
    }
}
