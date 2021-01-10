package edu.pse.beast.api.testrunner.propertycheck;

import java.io.File;

import edu.pse.beast.api.BEASTCallback;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public interface CBMCProcessStarter {
	public ProcessBuilder startTestForParam(ElectionDescription description, PreAndPostConditionsDescription propertyDescr,
			int V, int C, int S, String uuid, BEASTCallback cb, File cbmcFile);
}
