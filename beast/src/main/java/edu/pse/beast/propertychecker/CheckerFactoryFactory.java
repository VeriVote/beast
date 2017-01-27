package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.toolbox.ErrorLogger;

public final class CheckerFactoryFactory {
	private static Map<String, CheckerFactory> factories = new HashMap<String, CheckerFactory>();
	private static boolean initialized = false;
	
	private CheckerFactoryFactory() {
		
	}
	
	private static void init() {
		//TODO initialize the class and find all available factories
		initialized = true;
	}
	
	public static List<String> getAvailableChekerIDs() {
		if (!initialized) {
			init();
		}
		return new ArrayList<String>(factories.keySet());
	}
	
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
	
	public static CheckerFactory getCheckerFactory(String checkerID) {
		if (!initialized) {
			init();
		}
		
		if (factories.keySet().contains(checkerID)) {
			return null;
			//TODO

		} else {
			ErrorLogger.log("The specified checkerID wasn't found");
			return null;
		}
		
	}

    public static String newUniqueName() {
        // TODO Auto-generated method stub
        return null;
    }
 	
}
