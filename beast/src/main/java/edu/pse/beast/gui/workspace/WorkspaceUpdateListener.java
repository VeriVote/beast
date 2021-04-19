package edu.pse.beast.gui.workspace;

import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.gui.workspace.events.WorkspaceErrorEvent;
import edu.pse.beast.gui.workspace.events.WorkspaceUpdateEvent;

public interface WorkspaceUpdateListener {
	public void handleWorkspaceUpdateGeneric();
	public default void handleWorkspaceErrorNoCBMCProcessStarter() {}
}
