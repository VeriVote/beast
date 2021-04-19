package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CBMCTestRunGuiController {

	@FXML
	private AnchorPane topLevelAnchorPane;	
	@FXML
	private Label stateLabel;
	@FXML
	private HBox stateHBox;
	@FXML
	private Label createdFileLabel;
	@FXML
	private Button openCreatedFileButton;
	
	@FXML
	private AnchorPane outputAnchorPane;
	
	
	
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
