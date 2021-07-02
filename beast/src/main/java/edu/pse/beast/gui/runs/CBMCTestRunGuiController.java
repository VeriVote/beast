package edu.pse.beast.gui.runs;

import java.io.IOException;
import java.util.List;

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.CBMCTestCallback;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonMessage;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCCounterExample;
import edu.pse.beast.api.testrunner.threadpool.WorkUnitState;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testconfigeditor.testconfig.cbmc.CBMCTestConfiguration;
import edu.pse.beast.gui.testconfigeditor.treeview.TestConfigTreeItemSuper;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.control.TextField;
import javafx.scene.control.TreeView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;

public class CBMCTestRunGuiController
		implements CBMCTestCallback, WorkspaceUpdateListener {

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
	@FXML
	private Slider runTextfieldFontSizeSlider;

	private TreeView<TestConfigTreeItemSuper> testConfigTreeView;

	private CodeArea fileContents = new OutputTextElement();
	private CodeArea logs = new OutputTextElement();
	private CodeArea messages = new OutputTextElement();

	private CBMCTestRun run;

	private BeastWorkspace beastWorkspace;

	private double currentDisplayFontSize = 12;

	private CounterExampleGuiController counterExampleGuiController = new CounterExampleGuiController();
	private final String counterExampleFXML = "/edu/pse/beast/cbmcCounterExampleGui.fxml";
	FXMLLoader counterExampleLoader = new FXMLLoader(
			getClass().getResource(counterExampleFXML));

	public CBMCTestRunGuiController(BeastWorkspace beastWorkspace,
			TreeView<TestConfigTreeItemSuper> testConfigTreeView)
			throws IOException {
		this.beastWorkspace = beastWorkspace;
		beastWorkspace.registerUpdateListener(this);
		this.testConfigTreeView = testConfigTreeView;
		counterExampleLoader.setController(counterExampleGuiController);
		counterExampleLoader.load();
	}

	public void display(CBMCTestRun run) {
		if (this.run != null) {
			this.run.setCb(null);
		}
		this.run = run;
		this.run.setCb(this);

		String code = "";
		try {
			code = SavingLoadingInterface
					.readStringFromFile(run.getCbmcCodeFile().getFile());

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		fileContents.clear();
		fileContents.insertText(0, code);

		messages.clear();
		for (CBMCJsonMessage m : run.getMessagesAsList()) {
			messages.appendText(m.toString());
		}

		logs.clear();
		logs.appendText(run.getTestOutput());

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
	public void onPropertyTestStopped(CElectionDescription descr,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid) {
		display();
	}

	@Override
	public void onPropertyTestRawOutput(String sessionUUID,
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, String output) {
		Platform.runLater(() -> {
			logs.appendText(output + "\n");
		});
	}

	@Override
	public void onPropertyTestRawOutputComplete(
			CElectionDescription description,
			PreAndPostConditionsDescription propertyDescr, int s, int c, int v,
			String uuid, List<String> cbmcOutput) {
	}

	@Override
	public void onNewCBMCMessage(CBMCJsonMessage msg) {
		Platform.runLater(() -> {
			messages.appendText(msg.toString() + "\n");
		});
	}

	private void display() {

		Platform.runLater(() -> {
			if (run == null)
				return;

			testConfigTreeView.refresh();

			// created file controls
			CBMCCodeFileData cbmcFile = run.getCbmcCodeFile();
			createdFileTextField.setText(cbmcFile.getFile().getAbsolutePath());
			openCreatedFileButton.setOnAction(e -> {
				displayCodeArea(fileContents);
			});

			WorkUnitState state = run.getState();

			stateHBox.getChildren().clear();

			stateLabel.setText("state: " + run.getStatusString());
			stateHBox.getChildren().add(stateLabel);

			if (run.isDescrChanged() || run.isPropDescrChanged()) {
				Label descrChangedLabel = new Label(
						"Run out of date, has changes");
				stateHBox.getChildren().add(descrChangedLabel);
			}

			Button showLogsButton = new Button("show logs");
			showLogsButton.setOnAction(e -> {
				displayCodeArea(logs);
			});

			Button showMessagesButton = new Button("CBMC Messages");
			showMessagesButton.setOnAction(e -> {
				displayCodeArea(messages);
			});

			Button deleteButton = new Button("delete");
			deleteButton.setOnAction(e -> {
				beastWorkspace.deleteCBMCRun(run);
			});

			switch (state) {
			case INITIALIZED: {
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});
				stateHBox.getChildren()
						.addAll(List.of(putRunOnQueueButton, deleteButton));
				break;
			}
			case ON_QUEUE:
				break;
			case WORKED_ON:

				Button stopCBMCButton = new Button("stop");
				stopCBMCButton.setOnAction(e -> {
					beastWorkspace.stopRun(run);
				});
				stateHBox.getChildren().add(showLogsButton);
				stateHBox.getChildren().add(showMessagesButton);
				stateHBox.getChildren().add(stopCBMCButton);
				break;
			case FINISHED: {
				if (run.getJsonOutputHandler().didCBMCFindExample()) {
					Button showExampleButton = new Button(
							"show generated Example");
					showExampleButton.setOnAction(e -> {
						AnchorPane pane = counterExampleGuiController
								.display(run.getJsonOutputHandler()
										.getGeneratedExample());
						displayNode(pane);
					});

					stateHBox.getChildren().add(showExampleButton);
				}

				stateHBox.getChildren().add(showLogsButton);
				stateHBox.getChildren().add(showMessagesButton);
				stateHBox.getChildren().add(deleteButton);
				break;
			}

			case STOPPED: {
				Button putRunOnQueueButton = new Button("put Run on Queue");
				putRunOnQueueButton.setOnAction(e -> {
					beastWorkspace.addRunToQueue(run);
				});

				stateHBox.getChildren().add(putRunOnQueueButton);
				stateHBox.getChildren().add(deleteButton);
				break;
			}
			default:
				break;
			}
		});
	}

	public AnchorPane getTopLevelAnchorPane() {
		return topLevelAnchorPane;
	}

	private void displayCodeArea(CodeArea codeArea) {
		VirtualizedScrollPane<CodeArea> vsp = new VirtualizedScrollPane<>(
				codeArea);
		displayNode(codeArea);
	}

	private void displayNode(Node n) {
		outputAnchorPane.getChildren().clear();
		outputAnchorPane.getChildren().add(n);
		AnchorPane.setTopAnchor(n, 0d);
		AnchorPane.setBottomAnchor(n, 0d);
		AnchorPane.setLeftAnchor(n, 0d);
		AnchorPane.setRightAnchor(n, 0d);
	}

	@FXML
	public void initialize() {
		createdFileTextField.setEditable(false);
		runTextfieldFontSizeSlider.setShowTickMarks(true);
		runTextfieldFontSizeSlider.setMin(4.0);
		runTextfieldFontSizeSlider.setMax(30.0);
		runTextfieldFontSizeSlider.setValue(currentDisplayFontSize);
		runTextfieldFontSizeSlider.valueProperty().addListener((ob, o, n) -> {
			double currentDisplayFontSize = Math.round((double) n * 100) / 100;
			String styleString = "-fx-font-size: " + currentDisplayFontSize
					+ "px;";
			logs.setStyle(styleString);
			messages.setStyle(styleString);
		});
	}

	@Override
	public void handleCBMConfigUpdatedFiles(
			CBMCTestConfiguration currentConfig) {
		display();
	}

}
