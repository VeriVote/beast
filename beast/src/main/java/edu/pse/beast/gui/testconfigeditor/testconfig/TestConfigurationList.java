package edu.pse.beast.gui.testconfigeditor.testconfig;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
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

	public void handleDescrChange(CElectionDescription descr) {
		if (testConfigsByDescr.containsKey(descr)) {
			for (TestConfiguration tc : testConfigsByDescr.get(descr)) {
				tc.handleDescrChange();
			}
		}
	}

	public List<CBMCTestRun> getCBMCTestRuns() {
		List<CBMCTestRun> list = new ArrayList<>();

		for (CElectionDescription descr : testConfigsByDescr.keySet()) {
			List<TestConfiguration> configs = testConfigsByDescr.get(descr);
			for (TestConfiguration config : configs) {
				for (CBMCTestConfiguration cbmcTestConfig : config
						.getCBMCTestConfigs()) {
					list.addAll(cbmcTestConfig.getRuns());
				}
			}
		}

		return list;
	}

	@Override
	public void handleExtractedFunctionLoops(CElectionDescription descr,
			CElectionDescriptionFunction func) {
		handleDescrChange(descr);
	}

	@Override
	public void handleDescrChangeUpdatedFunctionCode(CElectionDescription descr,
			CElectionDescriptionFunction function, String code) {
		handleDescrChange(descr);
	}

	@Override
	public void handleDescrChangeAddedVotingSigFunction(
			CElectionDescription descr, VotingSigFunction func) {
		handleDescrChange(descr);
	}

	@Override
	public void handleDescrChangeRemovedFunction(CElectionDescription descr,
			CElectionDescriptionFunction func) {
		handleDescrChange(descr);
	}

	private void handlePropDescrChanged(
			PreAndPostConditionsDescription propDescr) {
		for (TestConfiguration tc : testConfigsByPropDescr.get(propDescr)) {
			tc.handlePropDescrChanged();
		}
	}

	@Override
	public void handleWorkspaceUpdateAddedVarToPropDescr(
			PreAndPostConditionsDescription currentPropDescr,
			SymbolicCBMCVar var) {
		handlePropDescrChanged(currentPropDescr);
	}

	@Override
	public void handlePropDescrChangedCode(
			PreAndPostConditionsDescription propDescr) {
		handlePropDescrChanged(propDescr);
	}

	@Override
	public void handlePropDescrRemovedVar(
			PreAndPostConditionsDescription propDescr,
			SymbolicCBMCVar selectedVar) {
		handlePropDescrChanged(propDescr);
	}

	public void deleteCBMCRun(CBMCTestRun run) {
		run.getTc().deleteRun(run);
	}

	public void deleteTestConfiguration(TestConfiguration tc) {
		testConfigsByName.remove(tc.getName());
		testConfigsByDescr.get(tc.getDescr()).remove(tc);
		testConfigsByPropDescr.get(tc.getPropDescr()).remove(tc);
	}

}
