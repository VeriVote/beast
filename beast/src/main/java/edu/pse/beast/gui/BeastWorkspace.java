package edu.pse.beast.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private List<TestConfiguration> testConfigs = new ArrayList<>();
	private CodeGenOptions codeGenOptions;
	private File baseDir;
	
	public BeastWorkspace(CodeGenOptions codeGenOptions) {
		this.codeGenOptions = codeGenOptions;
	}

	private List<WorkspaceUpdateListener> updateListener = new ArrayList<>();

	public void registerUpdateListener(WorkspaceUpdateListener l) {
		updateListener.add(l);
	}

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

	private TestConfiguration createAndReturnTestConfigForDescr(
			CElectionDescription descr) {
		TestConfiguration config = new TestConfiguration(descr);
		testConfigs.add(config);
		return config;
	}

	private void messageUpdateListener() {
		for (WorkspaceUpdateListener l : updateListener)
			l.handleWorkspaceUpdate();
	}

	public void addElectionDescription(CElectionDescription descr) {
		if (getDescrByName(descr.getName()) != null)
			return;
		loadedDescrs.add(descr);
		messageUpdateListener();
	}

	public void changeDescrForCBMCTestConfig(
			CBMCPropertyTestConfiguration selectedConfig, String newDescrName) {
		CElectionDescription oldDescr = selectedConfig.getDescr();
		if (oldDescr.getName().equals(newDescrName))
			return;
		TestConfiguration oldParentTestConfig = getTestConfigByDescrName(
				oldDescr.getName());
		TestConfiguration newParentTestConfig = getTestConfigByDescrName(
				newDescrName);
		CElectionDescription newDescr = getDescrByName(newDescrName);
		if (newParentTestConfig == null) {
			newParentTestConfig = createAndReturnTestConfigForDescr(newDescr);
		}
		newParentTestConfig.getCbmcPropertyTestConfigurations()
				.add(selectedConfig);
		oldParentTestConfig.getCbmcPropertyTestConfigurations()
				.remove(selectedConfig);
		selectedConfig.setDescr(newDescr);
		messageUpdateListener();
	}

	public CodeGenOptions getCodeGenOptions() {
		return codeGenOptions;
	}

	public void setBaseDir(File baseDir) {
		this.baseDir = baseDir;
	}
	
	public File getBaseDir() {
		return baseDir;
	}
}
