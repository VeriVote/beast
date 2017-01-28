/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.descofvoting.ElectionDescription;

/**
 *
 * @author Holger-Desktop
 */
public interface ElectionDescriptionSource {
    public ElectionDescription getElectionDescription();
    /**
     * Returns true if the ElectionDescription is correct.
     * @return correctness
     */
    boolean isCorrect();
    /**
     * Stops reacting to user input to not interfere with running check.
     */
    void stopReacting();
    /**
     * Resumes reacting to user input after the check is over.
     */
    void resumeReacting();
}

