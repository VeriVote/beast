package edu.pse.beast.gui.testruneditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TestConfigurationList {
	private Map<String, List<TestConfiguration>> testConfigsByDescr = new HashMap<>();
	private Map<String, List<TestConfiguration>> testConfigsByPropDescr = new HashMap<>();

	public void add(TestConfiguration testConfig) {
		if (!testConfigsByDescr.containsKey(testConfig.getDescr().getName())) {
			testConfigsByDescr.put(testConfig.getDescr().getName(),
					new ArrayList<>());
		}
		testConfigsByDescr.get(testConfig.getDescr().getName()).add(testConfig);

		if (!testConfigsByPropDescr
				.containsKey(testConfig.getPropDescr().getName())) {
			testConfigsByPropDescr.put(testConfig.getPropDescr().getName(),
					new ArrayList<>());
		}
		testConfigsByPropDescr.get(testConfig.getPropDescr().getName())
				.add(testConfig);
	}

	public Map<String, List<TestConfiguration>> getConfigsByElectionDescription() {
		return Collections.unmodifiableMap(testConfigsByDescr);
	}

	public Map<String, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return Collections.unmodifiableMap(testConfigsByPropDescr);
	}
}
