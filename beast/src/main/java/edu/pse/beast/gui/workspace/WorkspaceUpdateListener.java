package edu.pse.beast.gui.workspace;

import edu.pse.beast.api.electiondescription.CElectionDescription;

public interface WorkspaceUpdateListener {
	public void handleWorkspaceUpdate(WorkspaceUpdateEvent evt);
	public void handleWorkspaceError(WorkspaceErrorEvent evt);
}
