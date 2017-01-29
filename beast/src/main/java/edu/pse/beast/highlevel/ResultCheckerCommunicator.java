package edu.pse.beast.highlevel;

import java.util.List;



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
            PostAndPrePropertiesDescriptionSource propDescrSrc, 
            ElectionDescriptionSource elecDescrSrc, ParameterSource params);
    /**
     * Stops checks.
     */
    void abortChecking();
}
