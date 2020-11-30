package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electiontest.cbmb.CBMCCodeFileGenerator;

public class BEASTTestRunner implements Runnable {
	private BEASTCallback cb;
	private ElectionDescription description;
	private List<PreAndPostConditionsDescription> propertiesToTest;
	private ElectionCheckParameter param;

	// TODO pass user wishes about which lvl of concurrency somewhere
	public BEASTTestRunner(BEASTCallback cb, ElectionDescription description,
			List<PreAndPostConditionsDescription> propertiesToTest, ElectionCheckParameter param) {
		this.cb = cb;
		this.description = description;
		this.propertiesToTest = propertiesToTest;
		this.param = param;
	}

	public void stop() {
		cb.onTestStopped();
	}


	@Override
	public void run() {
		cb.onTestStarted();
		// TODO pass which type of checker we want to use
		// for now only use cbmc

		// first generate the c file for cbmc. This one will be shard between the test
		// processes
		// with different numbers of candidates etc.

		// TODO pass user wishes about which lvl of concurrency somewhere.
		// For now we go property by property and just start processes for each 
		// combination of values
		for (PreAndPostConditionsDescription propertyDescr : propertiesToTest) {
			File cbmcFile = CBMCCodeFileGenerator.createCodeFileTest(description, propertyDescr);
		}
	}

}
