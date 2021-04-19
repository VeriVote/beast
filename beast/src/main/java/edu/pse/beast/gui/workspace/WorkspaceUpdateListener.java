package edu.pse.beast.gui.workspace;

import java.util.List;

import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs.CBMCTestRun;

public interface WorkspaceUpdateListener {
	public void handleWorkspaceUpdateGeneric();
	public default void handleWorkspaceUpdateAddedCBMCRuns(
			CBMCPropertyTestConfiguration config,
			List<CBMCTestRun> createdTestRuns) {
	}
	public default void handleWorkspaceErrorNoCBMCProcessStarter() {
	}
}
