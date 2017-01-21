
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
}
