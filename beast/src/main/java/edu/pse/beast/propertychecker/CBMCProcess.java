package edu.pse.beast.propertychecker;

import java.io.File;

public abstract class CBMCProcess extends Checker {

    /**
     * creates a new CBMCProcess that is a super class for the system specific processes that run cbmc
     * @param voters the amount of voters
     * @param candidates the amount of candidates
     * @param seats the amount of seats
     * @param advanced the string that represents the advanced options
     * @param toCheck the file to check with cbmc
     * @param parent the parent CheckerFactory, that has to be notified about finished checking
     */
    public CBMCProcess(int voters, int candidates, int seats, String advanced, File toCheck, CheckerFactory parent) {
        super(voters, candidates, seats, advanced, toCheck, parent);
    }
    
}
