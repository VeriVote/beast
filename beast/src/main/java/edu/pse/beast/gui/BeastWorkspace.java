package edu.pse.beast.gui;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private List<TestConfiguration> testConfigs = new ArrayList<>();

	public List<CElectionDescription> getLoadedDescrs() {
		return loadedDescrs;
	}

	public List<PreAndPostConditionsDescription> getLoadedPropDescrs() {
		return loadedPropDescrs;
	}

	public List<TestConfiguration> getTestConfigs() {
		return testConfigs;
	}

	public CElectionDescription getDescrByName(String name) {
		for (CElectionDescription descr : loadedDescrs) {
			if (descr.getName().equals(name))
				return descr;
		}
		return null;
	}

	public PreAndPostConditionsDescription getPropDescrByName(String name) {
		for (PreAndPostConditionsDescription propDescr : loadedPropDescrs) {
			if (propDescr.getName().equals(name))
				return propDescr;
		}
		return null;
	}

	public TestConfiguration getTestConfigByName(String name) {
		for (TestConfiguration testConfiguration : testConfigs) {
			if (testConfiguration.getName().equals(name))
				return testConfiguration;
		}
		return null;
	}

	public TestConfiguration getTestConfigByDescrName(String newDescrName) {
		for (TestConfiguration testConfiguration : testConfigs) {
			if (testConfiguration.getDescr().getName().equals(newDescrName))
				return testConfiguration;
		}
		return null;
	}

	public TestConfiguration createAndReturnTestConfigForDescr(
			CElectionDescription descr) {
		TestConfiguration config = new TestConfiguration(descr);
		testConfigs.add(config);
		return config;
	}
}
