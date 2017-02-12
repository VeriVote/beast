package edu.pse.beast.highlevel;

import java.util.List;

/**
 * The ResultCheckerCommunicator starts and stops checks.
 * 
 * @author Niels
 */
public interface ResultCheckerCommunicator {
    /**
     * Starts checks.
     * @param elecDescr
     *            ElectionDescriptionSource
     * @param propDescrSrc
     *            PostAndPrePropertiesDescription
     * @param params
     *            ElectionCheckParameter
     * @return array of ResultInterfaces
     */
    List<ResultInterface> checkPropertiesForDescription(ElectionDescriptionSource elecDescr,
            PostAndPrePropertiesDescriptionSource propDescrSrc, ParameterSource params);

    /**
     * Stops checks.
     * returns 0 if 
     */
    boolean abortChecking();
}
