package edu.pse.beast.gui.testconfigeditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

public class TestConfigurationList implements WorkspaceUpdateListener {
	private Map<CElectionDescription, List<TestConfiguration>> testConfigsByDescr = new HashMap<>();
	private Map<PreAndPostConditionsDescription, List<TestConfiguration>> testConfigsByPropDescr = new HashMap<>();
	private Map<String, TestConfiguration> testConfigsByName = new HashMap<>();
	
	public boolean canAdd(TestConfiguration testConfig) {
		return !testConfigsByName.containsKey(testConfig.getName());
	}

	public void add(TestConfiguration testConfig) {
		if (testConfigsByName.containsKey(testConfig.getName())) {
			// TODO Error
		} else {
			testConfigsByName.put(testConfig.getName(), testConfig);
		}

		if (!testConfigsByDescr.containsKey(testConfig.getDescr())) {
			testConfigsByDescr.put(testConfig.getDescr(), new ArrayList<>());
		}
		testConfigsByDescr.get(testConfig.getDescr()).add(testConfig);

		if (!testConfigsByPropDescr.containsKey(testConfig.getPropDescr())) {
			testConfigsByPropDescr.put(testConfig.getPropDescr(),
					new ArrayList<>());
		}
		testConfigsByPropDescr.get(testConfig.getPropDescr()).add(testConfig);
	}

	public Map<CElectionDescription, List<TestConfiguration>> getTestConfigsByDescr() {
		return testConfigsByDescr;
	}

	public Map<PreAndPostConditionsDescription, List<TestConfiguration>> getTestConfigsByPropDescr() {
		return testConfigsByPropDescr;
	}

	public List<TestConfiguration> getTestConfigsByPropDescr(
			PreAndPostConditionsDescription currentPropDescr) {
		if (testConfigsByPropDescr.containsKey(currentPropDescr)) {
			return testConfigsByPropDescr.get(currentPropDescr);
		}
		return List.of();
	}

	public void handleDescrChange(
			CElectionDescription descr) {
		if(testConfigsByDescr.containsKey(descr)) {
			for(TestConfiguration tc : testConfigsByDescr.get(descr)) {
				tc.handleDescrChange();
			}
		}
	}
	
	@Override
	public void handleAddedPropDescr(
			PreAndPostConditionsDescription propDescr) {
		
	}

}
