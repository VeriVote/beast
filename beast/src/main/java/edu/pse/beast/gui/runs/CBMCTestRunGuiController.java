package edu.pse.beast.gui.runs;

import java.io.IOException;
import java.util.List;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CBMCTestRunGuiController implements CBMCTestCallback {

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
		this.beastWorkspace = beastWorkspace;
	}

	public void display(CBMCTestRun run) {
		if(this.run != null) {
			this.run.setCb(null);
		}
		this.run = run;
		this.run.setCb(this);
		display();
	}
		
	@Override
	public void onPropertyTestStart(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		display(); 
	}
	
	@Override
	public void onPropertyTestFinished(CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		display(); 
	}
	
	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
	}
	
	@Override
	public void onPropertyTestRawOutputComplete(
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, List<String> cbmcOutput) {
		display(); 
	}
	
	private void display() {
		
		Platform.runLater(() -> {
			outputTextElement.clear();

			CBMCCodeFileData cbmcFile = run.getCbmcCodeFile();
			createdFileTextField.setText(cbmcFile.getFile().getAbsolutePath());
			openCreatedFileButton.setOnAction(e -> {
				try {
					String code = SavingLoadingInterface
							.readStringFromFile(cbmcFile.getFile());
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

			if (run.isDescrChanged() || run.isPropDescrChanged()) {
				Label descrChangedLabel = new Label("Run out of date, has changes");
				stateHBox.getChildren().add(descrChangedLabel);
			}

			switch (state) {
				case INITIALIZED : {
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
					Button button = new Button("show logs");
					button.setOnAction(e -> {
						outputTextElement.clear();
						outputTextElement.insertText(0, run.getTestOutput());
					});					
					stateHBox.getChildren().add(button);
					break;
				case FINISHED : {
					Button showLogsButton = new Button("show logs");
					showLogsButton.setOnAction(e -> {
						outputTextElement.clear();
						outputTextElement.insertText(0, run.getTestOutput());
					});					
					
					Button showExampleButton = new Button("show generated Example");
					showExampleButton.setOnAction(e -> {
						outputTextElement.clear();
						outputTextElement.insertText(0,run.getExampleText());
					});					
					Button showAllAssignments = new Button("show All");
					showAllAssignments.setOnAction(e -> {
						outputTextElement.clear();
						outputTextElement.insertText(0,run.getAllAssignmentsText());
					});					
					stateHBox.getChildren().add(showLogsButton);
					stateHBox.getChildren().add(showAllAssignments);
					stateHBox.getChildren().add(showExampleButton);
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
				default :
					break;
			}
		});
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
