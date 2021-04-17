package edu.pse.beast.gui.testruneditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;

public class TestConfiguration {

	private CElectionDescription descr;
	private PreAndPostConditionsDescription propDescr;
	private String name;

	private List<CBMCPropertyTestConfiguration> cbmcTestConfigs = new ArrayList<>();

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
		cbmcTestConfigs.add(config);
	}

	public List<CBMCPropertyTestConfiguration> getCbmcTestConfigs() {
		return Collections.unmodifiableList(cbmcTestConfigs);
	}

}
