package edu.pse.beast.api.testrunner.propertycheck;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCXmlOutputHandler {

	private List<CBMCResult> results = new ArrayList<>();

	public void onCompleteOutput(ElectionDescription description, PreAndPostConditionsDescription propertyDescr, int s,
			int c, int v, String uuid, List<String> cbmcOutput) {
		synchronized (results) {
			results.add(new CBMCResult(description, propertyDescr, s, c, v, uuid, cbmcOutput));
		}
	}

	CBMCResult getResult(ElectionDescription description, PreAndPostConditionsDescription propertyDescr, int s, int c,
			int v) {

		for (CBMCResult res : results)
			if (res.isResForParams(description, propertyDescr, s, c, v))
				return res;
		return null;
	}
	
	public List<CBMCResult> getResults() {
		return results;
	}
}
