package edu.pse.beast.highlevel;

import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.ParentTreeItem;
import edu.pse.beast.propertychecker.Result;

/**
 * The ResultCheckerCommunicator starts and stops checks.
 *
 * @author Niels Hanselmann
 */
public interface ResultCheckerCommunicator {
//    /**
//     * Starts checks.
//     * @param elecDescr
//     *            ElectionDescriptionSource
//     * @param propDescrSrc
//     *            PreAndPostConditionsDescription
//     * @param params
//     *            ElectionCheckParameter
//     * @return array of ResultInterfaces
//     */
//    public List<ResultInterface>
//                checkPropertiesForDescription(ElectionDescriptionSource elecDescr,
//                                              PreAndPostConditionsDescriptionSource propDescrSrc,
//                                              ElectionCheckParameter params);

    /**
     * Starts checks.
     *
     * @param electDescr             election description
     * @param parentProperties       properties
     * @param electionCheckParameter election check parameter
     * @return array of ResultInterfaces
     */
    List<Result>
            checkPropertiesForDescription(ElectionDescription electDescr,
                                          List<ParentTreeItem> parentProperties,
                                          ElectionCheckParameter electionCheckParameter);

//
//    /**
//     * starts the check for a give file
//     * @param toCheck the file to check
//     * @param params the parameters to give extra
//     * @return a single unprocessedResult
//     */
//    public UnprocessedCBMCResult checkFile(File toCheck,
//                                           ElectionDescription elecDescr,
//                                           ParameterSource params);
//
    /**
     * Stops checks.
     *
     * @return false if unable to abort check
     */
    boolean abortChecking();
}
