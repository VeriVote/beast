package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	
	private static PropertyChecker createPropertyChecker(String checkerID) {
		if (!initialized) {
			init();
		}
		
		return new
	}
	
}
