package edu.pse.beast.gui.workspace;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfigurationList;

public class BeastWorkspace {
	private List<CElectionDescription> loadedDescrs = new ArrayList<>();
	private List<PreAndPostConditionsDescription> loadedPropDescrs = new ArrayList<>();
	private CodeGenOptions codeGenOptions;
	private TestConfigurationList testConfigList = new TestConfigurationList();
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

	public void addTestConfiguration(TestConfiguration testConfig) {
		testConfigList.add(testConfig);
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

	private void messageUpdateListener() {
		for (WorkspaceUpdateListener l : updateListener)
			l.handleWorkspaceUpdate(WorkspaceUpdateEvent
					.fromType(WorkspaceUpdateEventType.ALL));
	}

	public void addElectionDescription(CElectionDescription descr) {
		if (getDescrByName(descr.getName()) != null)
			return;
		loadedDescrs.add(descr);
		messageUpdateListener();
	}

	public void addPropertyDescription(
			PreAndPostConditionsDescription propDescr) {
		loadedPropDescrs.add(propDescr);
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

	public Map<String, List<TestConfiguration>> getConfigsByElectionDescription() {
		return testConfigList.getConfigsByElectionDescription();
	}

	public Map<String, List<TestConfiguration>> getConfigsByPropertyDescription() {
		return testConfigList.getConfigsByPropertyDescription();
	}

}
