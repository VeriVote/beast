package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import java.io.File;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.testrunner.propertycheck.CBMCPropertyCheckWorkUnit;

public class CBMCTestRun implements CBMCTestCallback {
	private CBMCPropertyCheckWorkUnit workUnit;
	private CBMCTestRunGuiController updateListener;
	private File cbmcFile;

	public CBMCTestRun(CBMCPropertyCheckWorkUnit workUnit, File cbmcFile) {
		this.workUnit = workUnit;
		this.cbmcFile = cbmcFile;
		workUnit.setCallback(this);
	}	
	
	public void setUpdateListener(CBMCTestRunGuiController updateListener) {
		this.updateListener = updateListener;
	}
	
	public void removeUpdateListener() {
		this.updateListener = null;
	}
	
}
