package edu.pse.beast.highlevel;

import java.io.File;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.propertychecker.UnprocessedCBMCResult;

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

    /**
     * starts the check for a give file
     * @param toCheck the file to check
     * @param params the parameters to give extra
     * @return a single unprocessedResult
     */
    public UnprocessedCBMCResult checkFile(File toCheck, ElectionDescription elecDescr, ParameterSource params);
    
    /**
     * Stops checks.
     * @return false if unable to abort check
     */
    boolean abortChecking();
}
