package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCTestRun implements CBMCTestCallback {
	private CBMCPropertyCheckWorkUnit workUnit;
	private CBMCTestRunGuiController updateListener;
	private List<String> testOutput = new ArrayList<>();

	public CBMCTestRun(CBMCPropertyCheckWorkUnit workUnit) {
		this.workUnit = workUnit;
		workUnit.setCallback(this);
	}

	public void setUpdateListener(CBMCTestRunGuiController updateListener) {
		this.updateListener = updateListener;
	}

	public void removeUpdateListener() {
		this.updateListener = null;
	}

	public File getCbmcFile() {
		return workUnit.getCbmcFile();
	}

	public WorkUnitState getState() {
		return workUnit.getState();
	}

	public CBMCPropertyCheckWorkUnit getWorkUnit() {
		return workUnit;
	}

	public String getTestOutput() {
		synchronized (testOutput) {
			return String.join("\n", testOutput);
		}
	}

	private void updateGui() {
		if (updateListener != null)
			updateListener.handleRunUpdate();
	}

	@Override
	public void onPropertyTestAddedToQueue(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		synchronized (testOutput) {
			testOutput.clear();
		}
		updateGui();
	}

	@Override
	public void onPropertyTestStart(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		updateGui();
	}

	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
		synchronized (testOutput) {
			testOutput.add(output);
		}
		updateGui();
	}
	
	@Override
	public void onPropertyTestFinished(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		updateGui();
	}
}
