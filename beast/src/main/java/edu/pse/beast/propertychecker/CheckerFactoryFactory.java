package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.toolbox.ErrorLogger;

/**
 * A factory for creating CheckerFactory objects.
 *
 * @author Lukas Stapelbroek
 */
public final class CheckerFactoryFactory {
    /** The Constant CBMC_STRING. */
    private static final String CBMC_STRING = "CBMC";

    /** The Constant ERROR_MSG. */
    private static final String ERROR_MSG = "The specified checkerID was not found.";

    /** The factories. */
    private static Map<String, CheckerFactory> factories =
            new HashMap<String, CheckerFactory>();

    /** The initialized. */
    private static boolean initialized;

    /**
     * The constructor.
     */
    private CheckerFactoryFactory() { }

    static {
        factories = searchForCheckers();
    }

    /**
     * Search for checkers.
     *
     * @return the map
     */
    private static Map<String, CheckerFactory> searchForCheckers() {
        Map<String, CheckerFactory> foundFactories =
                new HashMap<String, CheckerFactory>();
        // cbmc is always included, so we add it here
        foundFactories.put(CBMC_STRING,
                           new CBMCProcessFactory(null, null, null, null));
        // TODO search for other factories
        return foundFactories;
    }

    /**
     * Reload checkers.
     */
    public static void reloadCheckers() {
        factories.clear();
        factories = searchForCheckers();
        if (!initialized) {
            // cbmc is always included, so we add it here
            factories.put(CBMC_STRING,
                          new CBMCProcessFactory(null, null, null, null));
            // TODO search for other classes
            initialized = true;
        }
    }

    /**
     * Is used to get a list of all available checkers.
     *
     * @return a list of all available checkers
     */
    public static List<String> getAvailableCheckerIDs() {
        return new ArrayList<String>(factories.keySet());
    }

    /**
     * Creates a property checker for the given ID.
     *
     * @param checkerID
     *            the ID for the PropertyChecker
     * @return the by the ID specified PropertyChecker, if the ID is found, or
     *         null, if it is not found
     */
    public static PropertyChecker createPropertyChecker(final String checkerID) {
        if (factories.keySet().contains(checkerID)) {
            return new PropertyChecker(checkerID);
        } else {
            ErrorLogger.log(ERROR_MSG);
            return null;
        }
    }

    // /**
    //  * This method returns a new checker factory to produce checkers.
    //  *
    //  * @param checkerID
    //  *            the id
    //  * @param controller
    //  *            the controller that controls the factory
    //  * @param electionDescSrc
    //  *            the electionSource
    //  * @param postAndPrepPropDesc
    //  *            the properySource
    //  * @param parameter
    //  *            the parameters
    //  * @param result
    //  *            the result object where the result should be put in
    //  * @param isMargin
    //  *           whether this is a margin
    //  * @return a new CheckerFactory if the ID was found, else null
    //  */
    // public static CheckerFactory getCheckerFactory(String checkerID,
    //                                                FactoryController controller,
    //                                                ElectionDescriptionSource electionDescSrc,
    //                                                PreAndPostConditionsDescription
    //                                                    postAndPrepPropDesc,
    //                                                ElectionCheckParameter parameter,
    //                                                Result result, boolean isMargin) {
    //     init();
    //
    //     if (factories.keySet().contains(checkerID)) {
    //         return factories.get(checkerID).getNewInstance(controller,
    //                                                        electionDescSrc,
    //                                                        postAndPrepPropDesc,
    //                                                        parameter, result, isMargin);
    //     } else {
    //         ErrorLogger.log("The specified checkerID was not found");
    //         return null;
    //     }
    // }

    /**
     * Gets the checker factory.
     *
     * @param checkerID
     *            the checker ID
     * @param controller
     *            the controller
     * @param electionDesc
     *            the election desc
     * @param result
     *            the result
     * @param parameter
     *            the parameter
     * @return the checker factory
     */
    public static CheckerFactory getCheckerFactory(final String checkerID,
                                                   final FactoryController controller,
                                                   final ElectionDescription electionDesc,
                                                   final Result result,
                                                   final ElectionCheckParameter parameter) {
        if (factories.keySet().contains(checkerID)) {
            return factories.get(checkerID)
                    .getNewInstance(controller, electionDesc, result, parameter);
        } else {
            ErrorLogger.log(ERROR_MSG);
            return null;
        }
    }

    /**
     * Creates a specified amount of result objects that fit for the checkerID.
     *
     * @param checkerID
     *            the ID for the checker
     * @return a list of the specified result objects with the length of
     *         "amount", null else
     */
    public static Result getMatchingResult(final String checkerID) {
        if (factories.keySet().contains(checkerID)) {
            return factories.get(checkerID).getMatchingResult();
        } else {
            ErrorLogger.log(ERROR_MSG);
            return null;
        }
    }

    /**
     * Gets the matching unprocessed result.
     *
     * @param checkerID
     *            the checker ID
     * @param amount
     *            the amount
     * @return the matching unprocessed result
     */
    public static List<Result> getMatchingUnprocessedResult(final String checkerID,
                                                            final int amount) {
        List<Result> results = new ArrayList<Result>();
        for (int i = 0; i < amount; i++) {
            // results.add(new UnprocessedCBMCResult()); TODO find all classes
            // which extend
            // Result, then match the ID of the checker with the ID of all types
            results.add(new CBMCResult());
        }
        return results;
    }
}
