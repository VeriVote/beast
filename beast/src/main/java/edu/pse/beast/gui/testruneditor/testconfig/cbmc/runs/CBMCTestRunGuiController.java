package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.gui.workspace.BeastWorkspace;
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

	private OutputTextElement outputTextElement = new OutputTextElement();

	private CBMCTestRun run;

	private BeastWorkspace beastWorkspace;

	public CBMCTestRunGuiController(BeastWorkspace beastWorkspace) {
		super();
		this.beastWorkspace = beastWorkspace;
	}

	public void handleRunUpdate() {
		display();
	}

	public void display(CBMCTestRun run) {
		if (run != null) {
			run.removeUpdateListener();
		}
		this.run = run;
		display();
		run.setUpdateListener(this);
	}

	private void display() {
		stateHBox.getChildren().clear();
		WorkUnitState state = run.getState();
		stateLabel.setText("state: " + state.toString());
		stateHBox.getChildren().add(stateLabel);
		switch (state) {
			case CREATED :
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});
				stateHBox.getChildren().add(putRunOnQueueButton);
				break;
			case ON_QUEUE :
				break;
			case WORKED_ON :
				break;
			case FINISHED :
				break;
			case STOPPED :
				break;
		}
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	@FXML
	public void initialize() {
		outputAnchorPane.getChildren().add(outputTextElement);
		AnchorPane.setTopAnchor(outputTextElement, 0d);
		AnchorPane.setBottomAnchor(outputTextElement, 0d);
		AnchorPane.setLeftAnchor(outputTextElement, 0d);
		AnchorPane.setRightAnchor(outputTextElement, 0d);
	}

}
