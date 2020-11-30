package edu.pse.beast.api;

import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class BEASTTestRunner {
	private BEASTCallback cb;
	private ElectionDescription description;
	private List<PreAndPostConditionsDescription> propertiesToTest; 
	private ElectionCheckParameter param;

	
	public BEASTTestRunner(BEASTCallback cb, ElectionDescription description,
			List<PreAndPostConditionsDescription> propertiesToTest, ElectionCheckParameter param) {
		this.cb = cb;
		this.description = description;
		this.propertiesToTest = propertiesToTest;
		this.param = param;
		startTest();
	}

	public void stop() {
		cb.onTestStopped();
	}
	
	private void startTest() {
		cb.onTestStarted();
		//TODO pass which type of checker we want to use
		//for now only use cbmc
		
		
	}

}
