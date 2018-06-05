package edu.pse.beast.highlevel.javafx;

import edu.pse.beast.propertychecker.Result;

public class ChildTreeItemValues {

	public final String propertyName;
	public final boolean checkBoxStatus;
	public final boolean disabled;
	public final AnalysisStatus status;
	
	public final Result result;
	
	public ChildTreeItemValues(String propertyName, boolean checkBoxStatus, boolean disabled, AnalysisStatus status, Result result) {
		this.propertyName = propertyName;
		this.checkBoxStatus = checkBoxStatus;
		this.disabled = disabled;
		this.status = status;
		
		this.result = result;
	}
}
