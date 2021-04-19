package edu.pse.beast.gui.workspace.events;

public class WorkspaceUpdateEvent {
	
	
	private WorkspaceUpdateEventType type;

	public WorkspaceUpdateEvent(WorkspaceUpdateEventType type) {
		super();
		this.type = type;
	}
	
	public static WorkspaceUpdateEvent fromType(WorkspaceUpdateEventType t) {
		return new WorkspaceUpdateEvent(t);
	}
	
	public WorkspaceUpdateEventType getType() {
		return type;
	}
}
