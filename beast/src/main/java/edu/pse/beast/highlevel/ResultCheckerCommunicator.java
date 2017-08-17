package edu.pse.beast.highlevel;

import java.io.File;
import java.util.List;

import edu.pse.beast.propertychecker.UnprocessedResult;

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
    public List<ResultInterface> checkPropertiesForDescription(ElectionDescriptionSource elecDescr,
            PostAndPrePropertiesDescriptionSource propDescrSrc, ParameterSource params);

    public List<UnprocessedResult> checkFile(File toCheck, ParameterSource params);
    
    /**
     * Stops checks.
     * @return false if unable to abort check
     */
    boolean abortChecking();
}
