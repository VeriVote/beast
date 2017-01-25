package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.ElectionCheckParameter;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.highlevel.ParameterSource;

public abstract class CheckerFactory {

	private Checker currentlyRunning;
	private FactoryController controller;
	private Thread workingThread;
	private ElectionDescription electionDesc;
	private PostAndPrePropertiesDescription postAndPrepPropDesc;
	private ElectionCheckParameter params;
	private Result result;
	
	private CheckerFactory() {
		
	}
	
	public abstract void stopChecking();
	
	
	public static CheckerFactory clone() {
		this();
		return this();
	}
    
}
