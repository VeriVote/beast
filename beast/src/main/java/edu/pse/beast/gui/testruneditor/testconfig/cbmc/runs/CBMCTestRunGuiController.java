package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;

public class CBMCTestRunGuiController {

	@FXML
	private AnchorPane topLevelAnchorPane;	
	
	private CBMCTestRun run;
	
	public void handleRunUpdate() {
		
	}

	public void display(CBMCTestRun run) {
		if(run != null) {
			run.removeUpdateListener();
		}
		this.run = run;
		run.setUpdateListener(this);
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}
	
	@FXML
	public void initialize() {
		
	}
	
}
