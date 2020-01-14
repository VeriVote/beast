package edu.pse.beast.highlevel;

import javax.swing.JFrame;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;

/**
 * The ParameterSource provides access to the parameters for checking the
 * election.
 *
 * @author Jonas Wohnig
 */
public interface ParameterSource {
    /**
     * Provides access to the parameters for the election check.
     *
     * @return ElectionCheckParameter
     */
    ElectionCheckParameter getParameter();

    /**
     * Returns true if the parameters are correct.
     *
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

    /**
     * Returns the View of the ParameterSrc, since this is the MainWindow its used
     * for displaying the status of the Analysis by the BEASTCommunicator.
     *
     * @return the view of the parameter source
     */
    JFrame getView();
}
