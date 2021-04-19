package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;

public class CBMCTestRun implements CBMCTestCallback {
	private CBMCPropertyCheckWorkUnit workUnit;
	private CBMCTestRunGuiController updateListener;

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
	
}
