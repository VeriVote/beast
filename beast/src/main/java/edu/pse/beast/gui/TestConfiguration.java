package edu.pse.beast.gui;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class TestConfiguration {

	public enum TestTypes {
		CBMC_PROPERTY_TEST,
	}

	private CElectionDescription descr;
	private List<CBMCPropertyTestConfiguration> cbmcPropertyTestConfigurations = new ArrayList<>();

	public TestConfiguration(CElectionDescription descr) {
		this.descr = descr;
	}

	public CElectionDescription getDescr() {
		return descr;
	}

	public String getName() {
		return descr.getName();
	}

	public List<CBMCPropertyTestConfiguration> getCbmcPropertyTestConfigurations() {
		return cbmcPropertyTestConfigurations;
	}

	public CBMCPropertyTestConfiguration getCbmcPropertyTestConfigByName(
			String name) {
		for (CBMCPropertyTestConfiguration config : cbmcPropertyTestConfigurations) {
			if (config.getName().equals(name))
				return config;
		}
		return null;
	}

	public TestTypes getTestTypeFromName(String name) {
		if (getCbmcPropertyTestConfigByName(name) != null)
			return TestTypes.CBMC_PROPERTY_TEST;
		return null;
	}
}
