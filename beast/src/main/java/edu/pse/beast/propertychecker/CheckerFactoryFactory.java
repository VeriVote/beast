package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;
import edu.pse.beast.highlevel.PostAndPrePropertiesDescriptionSource;
import edu.pse.beast.toolbox.ErrorLogger;

public final class CheckerFactoryFactory {
	private static Map<String, CheckerFactory> factories = new HashMap<String, CheckerFactory>();
	private static boolean initialized = false;
	
	private CheckerFactoryFactory() {
		
	}
	
	private static void init() {
	    //cbmc is always included, so we add it here
	    factories.put("cbmc", new CBMCProcessFactory(null, null, null, null, null));
	    
	    
	    //search for other classes
		
	    
	    
	    
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
	
	public static CheckerFactory getCheckerFactory(String checkerID, FactoryController controller, ElectionDescriptionSource electionDescSrc,
            PostAndPrePropertiesDescriptionSource postAndPrepPropDescSrc, ParameterSource paramSrc, Result result) {
		if (!initialized) {
			init();
		}
		
		if (factories.keySet().contains(checkerID)) {
			return factories.get(checkerID).getNewInstance(controller, electionDescSrc, postAndPrepPropDescSrc, paramSrc, result);

		} else {
			ErrorLogger.log("The specified checkerID wasn't found");
			return null;
		}
		
	}

    public static String newUniqueName() {
        if (!initialized) {
            init();
        }
        // TODO Auto-generated method stub
        return null;
    }
 	
}
