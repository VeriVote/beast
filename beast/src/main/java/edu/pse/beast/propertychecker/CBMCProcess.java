package edu.pse.beast.propertychecker;

import java.io.File;

/**
 * The Class CBMCProcess.
 */
public abstract class CBMCProcess extends Checker {

    /**
     * Creates a new CBMCProcess that is a super class for the system specific
     * processes that run cbmc.
     *
     * @param voters     the amount of voters
     * @param candidates the amount of candidates
     * @param seats      the amount of seats
     * @param advanced   the string that represents the advanced options
     * @param toCheck    the file to check with cbmc
     * @param parent     the parent CheckerFactory, that has to be notified about
     *                   finished checking
     * @param result     the result
     */
    public CBMCProcess(final int voters,
                       final int candidates,
                       final int seats,
                       final String advanced,
                       final File toCheck,
                       final CheckerFactory parent,
                       final Result result) {
        super(voters, candidates, seats, advanced, toCheck, parent, result);
    }
}
