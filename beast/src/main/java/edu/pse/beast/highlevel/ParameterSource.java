
package edu.pse.beast.highlevel;
import edu.pse.beast.datatypes.ElectionCheckParameter;

/**
 * The ParameterSource provides access to the parameters for checking the election.
 * @author Jonas
 */
public interface ParameterSource {
    /**
     * Provides access to the parameters for the election check.
     * @return ElectionCheckParameter
     */
    ElectionCheckParameter getParameter();
    /**
     * Returns true if the parameters are correct.
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
