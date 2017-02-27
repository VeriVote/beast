package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.toolbox.ErrorLogger;

public final class CheckerFactoryFactory {
    private static Map<String, CheckerFactory> factories = new HashMap<String, CheckerFactory>();
    private static boolean initialized = false;

    private CheckerFactoryFactory() {

    }

    private static void init() {
        // cbmc is always included, so we add it here
        factories.put("cbmc", new CBMCProcessFactory(null, null, null, null, null));

        // TODO search for other classes

        initialized = true;
    }

    /**
     * is used to get a list of all available checkers
     * @return a list of all available checkers
     */
    public static List<String> getAvailableCheckerIDs() {
        if (!initialized) {
            init();
        }
        return new ArrayList<String>(factories.keySet());
    }

    /**
     * creates a propertychecker for the given ID
     * @param checkerID the ID for the PropertyChecker
     * @return the by the ID specified PropertyChecker, if the ID is found, or null, if it isn't found
     */
    public static PropertyChecker createPropertyChecker(String checkerID) {
        if (!initialized) {
            init();
        }

        if (factories.keySet().contains(checkerID)) {
            return new PropertyChecker(checkerID);
        } else {
            ErrorLogger.log("The specified checkerID wasn't found");
            return null;
        }
    }

    /**
     * this methode returns a new checkerfactory to produce checkers
     * @param checkerID the id
     * @param controller the controller that controls the factory
     * @param electionDescSrc the electionSource
     * @param postAndPrepPropDesc the properySource
     * @param paramSrc the parameters
     * @param result the result object where the result should be put in
     * @return a new CheckerFactory if the ID was found, else null
     */
    public static CheckerFactory getCheckerFactory(String checkerID, FactoryController controller,
            ElectionDescriptionSource electionDescSrc, PostAndPrePropertiesDescription postAndPrepPropDesc,
            ParameterSource paramSrc, Result result) {
        if (!initialized) {
            init();
        }

        if (factories.keySet().contains(checkerID)) {
            return factories.get(checkerID).getNewInstance(controller, electionDescSrc, postAndPrepPropDesc,
                    paramSrc, result);

        } else {
            ErrorLogger.log("The specified checkerID wasn't found");
            return null;
        }

    }

    /**
     * creates a specified amount of result objects that fit for the checkerID
     * @param checkerID the ID for the checker
     * @param amount the amount of result objects
     * @return a list of the specified result objects with the lenght of "amount", null else
     */
    public static List<Result> getMatchingResult(String checkerID, int amount) {
        if (!initialized) {
            init();
        }
        if (factories.keySet().contains(checkerID)) {
            return factories.get(checkerID).getMatchingResult(amount);

        } else {
            ErrorLogger.log("The specified checkerID wasn't found");
            return null;
        }
    }

}
