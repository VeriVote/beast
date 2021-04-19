package edu.pse.beast.gui.workspace;

public class WorkspaceErrorEvent {
	private WorkspaceErrorEventType type;

	public static WorkspaceErrorEvent from(WorkspaceErrorEventType type) {
		WorkspaceErrorEvent e = new WorkspaceErrorEvent();
		e.type = type;
		return e;
	}

	public WorkspaceErrorEventType getType() {
		return type;
	}

}
