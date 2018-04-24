package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.ChildTreeItem;
import edu.pse.beast.toolbox.ErrorLogger;

public final class CheckerFactoryFactory {
	private static Map<String, CheckerFactory> factories = new HashMap<String, CheckerFactory>();
	private static boolean initialized = false;

	private CheckerFactoryFactory() {

	}
	
	static {
		factories = searchForCheckers();
	}
	
	private static Map<String, CheckerFactory> searchForCheckers() {
		Map<String, CheckerFactory> foundFactories = new HashMap<String, CheckerFactory>();
		
		// cbmc is always included, so we add it here
		foundFactories.put("CBMC", new CBMCProcessFactory(null, null, null, null));
		
		//TODO search for other factories
		 
		return foundFactories;
	}
		
	public static void reloadCheckers() {
		
		factories.clear();
		
		factories = searchForCheckers();
		
		if (!initialized) {

			// cbmc is always included, so we add it here
			factories.put("CBMC", new CBMCProcessFactory(null, null, null, null));

			// TODO search for other classes

			initialized = true;
		}
	}

	/**
	 * is used to get a list of all available checkers
	 * 
	 * @return a list of all available checkers
	 */
	public static List<String> getAvailableCheckerIDs() {
		return new ArrayList<String>(factories.keySet());
	}

	/**
	 * creates a propertychecker for the given ID
	 * 
	 * @param checkerID
	 *            the ID for the PropertyChecker
	 * @return the by the ID specified PropertyChecker, if the ID is found, or null,
	 *         if it isn't found
	 */
	public static PropertyChecker createPropertyChecker(String checkerID) {

		if (factories.keySet().contains(checkerID)) {
			return new PropertyChecker(checkerID);
		} else {
			ErrorLogger.log("The specified checkerID wasn't found");
			return null;
		}
	}

	// /**
	// * this method returns a new checkerfactory to produce checkers
	// *
	// * @param checkerID
	// * the id
	// * @param controller
	// * the controller that controls the factory
	// * @param electionDescSrc
	// * the electionSource
	// * @param postAndPrepPropDesc
	// * the properySource
	// * @param parameter
	// * the parameters
	// * @param result
	// * the result object where the result should be put in
	// * @param
	// * @return a new CheckerFactory if the ID was found, else null
	// */
	// public static CheckerFactory getCheckerFactory(String checkerID,
	// FactoryController controller,
	// ElectionDescriptionSource electionDescSrc, PreAndPostConditionsDescription
	// postAndPrepPropDesc,
	// ElectionCheckParameter parameter, Result result, boolean isMargin) {
	// init();
	//
	// if (factories.keySet().contains(checkerID)) {
	// return factories.get(checkerID).getNewInstance(controller, electionDescSrc,
	// postAndPrepPropDesc,
	// parameter, result, isMargin);
	//
	// } else {
	// ErrorLogger.log("The specified checkerID wasn't found");
	// return null;
	// }
	// }

	public static CheckerFactory getCheckerFactory(String checkerID, FactoryController controller,
			ElectionDescription electionDesc, ChildTreeItem childTreeItem, ElectionCheckParameter parameter) {

		if (factories.keySet().contains(checkerID)) {
			return factories.get(checkerID).getNewInstance(controller, electionDesc, childTreeItem, parameter);

		} else {
			ErrorLogger.log("The specified checkerID wasn't found");
			return null;
		}
	}

	/**
	 * creates a specified amount of result objects that fit for the checkerID
	 *
	 * @param checkerID
	 *            the ID for the checker
	 * @param amount
	 *            the amount of result objects
	 * @return a list of the specified result objects with the lenght of "amount",
	 *         null else
	 */
	public static Result getMatchingResult(String checkerID) {
		if (factories.keySet().contains(checkerID)) {
			return factories.get(checkerID).getMatchingResult();

		} else {
			ErrorLogger.log("The specified checkerID wasn't found");
			return null;
		}
	}

	public static List<Result> getMatchingUnprocessedResult(String checkerID, int amount) {

		List<Result> results = new ArrayList<Result>();

		for (int i = 0; i < amount; i++) {
			results.add(new UnprocessedCBMCResult());
		}

		return results;
	}
}
