package edu.pse.beast.gui.testruneditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class TestConfigurationList {
	private Map<String, List<TestConfiguration>> testConfigsByDescr = new HashMap<>();
	private Map<String, List<TestConfiguration>> testConfigsByPropDescr = new HashMap<>();
	private Map<String, TestConfiguration> testConfigsByName = new HashMap<>();

	public void add(TestConfiguration testConfig) {
		if(testConfigsByName.containsKey(testConfig.getName())) {
			//TODO Error
		} else {
			testConfigsByName.put(testConfig.getName(), testConfig);
		}
		
		if (!testConfigsByDescr.containsKey(testConfig.getDescr().getUuid())) {
			testConfigsByDescr.put(testConfig.getDescr().getUuid(),
					new ArrayList<>());
		}
		testConfigsByDescr.get(testConfig.getDescr().getUuid()).add(testConfig);

		if (!testConfigsByPropDescr
				.containsKey(testConfig.getPropDescr().getUuid())) {
			testConfigsByPropDescr.put(testConfig.getPropDescr().getUuid(),
					new ArrayList<>());
		}
		testConfigsByPropDescr.get(testConfig.getPropDescr().getUuid())
				.add(testConfig);		
	}

	public Map<String, List<TestConfiguration>> getConfigsByElectionDescription() {
		return Collections.unmodifiableMap(testConfigsByDescr);
	}

	public Map<String, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return Collections.unmodifiableMap(testConfigsByPropDescr);
	}

	public void handleDescrChange(CElectionDescription descr) {
		for(TestConfiguration tc : testConfigsByDescr.get(descr.getUuid())) {
			tc.handleDescrChange();
		}
	}




}
