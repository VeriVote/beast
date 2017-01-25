
package edu.pse.beast.highlevel;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;

import java.util.List;

import edu.pse.beast.datatypes.ElectionCheckParameter;

/**
 * The ResultCheckerCommunicator starts and stops checks.
 * @author Niels
 */
public interface ResultCheckerCommunicator {
    /**
     * Starts checks.
     * @param propDescr PostAndPrePropertiesDescription
     * @param elecDescr ElectionDescription
     * @param params ElectionCheckParameter
     * @return array of ResultInterfaces
     */
    List<ResultInterface> checkPropertiesForDescription(
            List<PostAndPrePropertiesDescription> propDescr, 
            ElectionDescription elecDescr, ElectionCheckParameter params);
    /**
     * Stops checks.
     */
    void abortChecking();
}
