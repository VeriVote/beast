package edu.pse.beast.gui.testruneditor.testconfig.cbmc.runs;

import java.io.File;
import java.io.IOException;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
	private TextField createdFileTextField;
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
		Platform.runLater(() -> {
			display();
		});
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
		File cbmcFile = run.getCbmcFile();
		createdFileTextField.setText(cbmcFile.getAbsolutePath());
		openCreatedFileButton.setOnAction(e -> {
			try {
				String code = SavingLoadingInterface
						.readStringFromFile(cbmcFile);
				outputTextElement.clear();
				outputTextElement.insertText(0, code);
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		});

		stateHBox.getChildren().clear();
		WorkUnitState state = run.getState();
		stateLabel.setText("state: " + state.toString());
		stateHBox.getChildren().add(stateLabel);

		switch (state) {
			case CREATED : {
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});
				stateHBox.getChildren().add(putRunOnQueueButton);
				break;
			}
			case ON_QUEUE :
				break;
			case WORKED_ON :
				outputTextElement.clear();
				outputTextElement.insertText(0, run.getTestOutput());

				break;
			case FINISHED : {
				outputTextElement.clear();
				outputTextElement.insertText(0, run.getTestOutput());
				outputTextElement.insertText(0, "FINISHED :)\n");
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});
				stateHBox.getChildren().add(putRunOnQueueButton);
				break;
			}

			case STOPPED : {
				outputTextElement.clear();
				outputTextElement.insertText(0, run.getTestOutput());
				outputTextElement.insertText(0, "INTERRUPTED BY USER :(\n");
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});
				stateHBox.getChildren().add(putRunOnQueueButton);
				break;
			}
		}

	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	@FXML
	public void initialize() {
		createdFileTextField.setEditable(false);

		VirtualizedScrollPane<OutputTextElement> vsp = new VirtualizedScrollPane<>(
				outputTextElement);
		outputAnchorPane.getChildren().add(vsp);
		AnchorPane.setTopAnchor(vsp, 0d);
		AnchorPane.setBottomAnchor(vsp, 0d);
		AnchorPane.setLeftAnchor(vsp, 0d);
		AnchorPane.setRightAnchor(vsp, 0d);
	}

}
