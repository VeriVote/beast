package edu.pse.beast.gui.testruneditor.testconfig;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class TestConfiguration {

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	private String name;

	private Map<String, CBMCPropertyTestConfiguration> cbmcTestConfigsByName = new HashMap<>();

	public TestConfiguration(CElectionDescription descr,
			PreAndPostConditionsDescription propDescr, String name) {
		this.descr = descr;
		this.propDescr = propDescr;
		this.name = name;
	}

	public CElectionDescription getDescr() {
		return descr;
	}

	public PreAndPostConditionsDescription getPropDescr() {
		return propDescr;
	}

	public String getName() {
		return name;
	}

	public void addCBMCTestConfiguration(CBMCPropertyTestConfiguration config) {
		cbmcTestConfigsByName.put(config.getName(), config);
	}

	public Map<String, CBMCPropertyTestConfiguration> getCbmcTestConfigsByName() {
		return Collections.unmodifiableMap(cbmcTestConfigsByName);
	}

	public boolean contains(CBMCPropertyTestConfiguration config) {
		return cbmcTestConfigsByName.containsKey(config.getName());
	}

	public void addCBMCTestConfigurations(
			List<CBMCPropertyTestConfiguration> cbmcTestConfigs) {
		for (CBMCPropertyTestConfiguration config : cbmcTestConfigs) {
			addCBMCTestConfiguration(config);
		}

	}

}
