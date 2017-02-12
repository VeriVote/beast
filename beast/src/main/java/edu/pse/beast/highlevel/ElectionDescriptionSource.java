
package edu.pse.beast.highlevel;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;

/**
 * The ElectionDescriptionSource is an interface to the package that handles
 * the ElectionDescription given by the user.
 * @author Holger-Desktop
 */
public interface ElectionDescriptionSource {
    /**
     * Getter for the ElectionDescription the user created
     * @return description of an election as an ElectionDescription object
     */
    ElectionDescription getElectionDescription();
    /**
     * Returns true if the ElectionDescription is correct.
     * @return correctness the correctness of the Electiondescription
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

