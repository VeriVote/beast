package edu.pse.beast.highlevel.javafx;

import java.awt.MouseInfo;
import java.awt.Point;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.codeareaJAVAFX.NewCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPropertyCodeArea;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionSimulator.NewElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.Triplet;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar.ButtonData;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.control.TreeItem;
import javafx.scene.control.TreeView;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class GUIController {

	private static GUIController controller;

	private static List<ParentTreeItem> properties = new ArrayList<ParentTreeItem>();

	private String pathToImages = "file:///" + SuperFolderFinder.getSuperFolder() + "/core/images/";

	private static TreeItem<CustomTreeItem> root;

	private List<TabClass> mainWindowTabs = new ArrayList<TabClass>();

	private List<TabClass> bottomWindowTabs = new ArrayList<TabClass>();

	private NewElectionSimulation electionSimulation;

	@FXML // fx:id="maxVoter"
	private TextField maxVoter;

	@FXML // fx:id="minVoter"
	private TextField minVoter;

	@FXML // fx:id="maxCandidates"
	private TextField maxCandidates;

	@FXML // fx:id="minCandidates"
	private TextField minCandidates;

	@FXML // fx:id="maxSeats"
	private TextField maxSeats;

	@FXML // fx:id="minSeats"
	private TextField minSeats;

	@FXML // fx:id="timeOut"
	private TextField timeOut;

	@FXML // fx:id="TimeUnitChoice"
	private ChoiceBox<TimeUnit> TimeUnitChoice;

	@FXML // fx:id="processes"
	private TextField processes;

	@FXML // fx:id="solverChoice"
	private ChoiceBox<?> solverChoice;

	@FXML // fx:id="advancedParameters1"
	private TextField advancedParameters;

	@FXML // fx:id="maxUnrolls"
	private TextField maxUnrolls;

	@FXML // fx:id="helpButton"
	private MenuItem helpButton;

	@FXML // fx:id="startStopButton"
	private Button startStopButton;

	@FXML // fx:id="openButton"
	private Button openButton;

	@FXML // fx:id="saveButton"
	private Button saveButton;

	@FXML // fx:id="saveAsButton"
	private Button saveAsButton;

	@FXML // fx:id="undoButton"
	private Button undoButton;

	@FXML // fx:id="redoButton"
	private Button redoButton;

	@FXML // fx:id="cutButton"
	private Button cutButton;

	@FXML // fx:id="copyButton"
	private Button copyButton;

	@FXML // fx:id="pasteButton"
	private Button pasteButton;

	@FXML // fx:id="deleteButton"
	private Button deleteButton;

	@FXML
	private Button newProp;

	@FXML
	private Button loadProp;

	@FXML
	private Button loadPropList;

	@FXML
	private CheckBox deleteItemsCheckbox;

	@FXML // fx:id="codePane"
	private Tab codePane;

	@FXML // fx:id="propertyPane"
	private Tab propertyPane;

	@FXML // fx:id="resultPane"
	private Tab resultPane;

	@FXML // fx:id="inputPane"
	private Tab inputPane;

	@FXML // fx:id="errorPane"
	private Tab errorPane;

	@FXML // fx:id="consolePane"
	private Tab consolePane;

	@FXML
	private Tab informationPane;

	@FXML
	private ScrollPane propertyScrollPane;

	@FXML
	private ScrollPane resultScrollPane;

	@FXML
	private ScrollPane inputScrollPane;

	@FXML
	private TitledPane prePropertyPane;

	@FXML
	private TitledPane postPropertyPane;

	@FXML
	private TextArea infoTextArea;

	@FXML
	private TextArea consoleTextArea;

	@FXML
	private TextArea errorTextArea;

	@FXML
	private TreeView<CustomTreeItem> treeView;

	@FXML
	private TextArea solutionField;

	@FXML
	private TabPane mainTabPane;

	@FXML
	private TabPane subTabPane;

	@FXML
	private TreeView<String> variableTreeView;

	@FXML
	private TextField symbVarField;

	@FXML
	private TextField inputVoterField;

	@FXML
	private TextField inputCandidateField;

	@FXML
	private TextField inputSeatField;

	@FXML
	private GridPane inputGridPane;

	@FXML
	private ScrollPane voterScrollPane;

	@FXML
	private GridPane voterGridPane;

	@FXML
	private ScrollPane candidateScrollPane;

	@FXML
	private GridPane candidateGridPane;

	@FXML
	private Button removeSymbVarButton;

	@FXML
	private MenuButton addSymbVarButton;

	@FXML
	private Button propNameButton;

	@FXML
	private TextField propNameField;

	@FXML
	private TextField resultNameField;

	// @FXML
	// private Text
	//
	// @FXML
	// private Button removeVarButton;

	private boolean running = false;

	private NewCodeArea codeArea;

	private NewPropertyCodeArea preArea;

	private NewPropertyCodeArea postArea;

	private BooleanExpEditorNEW booleanExpEditor;

	private double scrollbarPadding = 15;

	private TreeItem<String> voterItems;

	private TreeItem<String> candidateItems;

	private TreeItem<String> seatItems;

	private int threshold = 10000; // 10 seconds to click "remove item" after one was selected

	private long lastClicked = 0;

	private TreeItem<String> symbVarToRemove;

	private TreeItem<CustomTreeItem> propertyToRemove;

	private Stage mainStage;

	private boolean nameFieldIsChangeable = false;

	public GUIController(Stage mainStage) {
		this.mainStage = mainStage;
	}

	// initial setup
	@FXML
	public void initialize() {

		controller = this;

		// set images for the buttons
		startStopButton.setGraphic(new ImageView(pathToImages + "toolbar/start.png"));
		openButton.setGraphic(new ImageView(pathToImages + "toolbar/load.png"));
		saveButton.setGraphic(new ImageView(pathToImages + "toolbar/save.png"));
		saveAsButton.setGraphic(new ImageView(pathToImages + "toolbar/save_as.png"));
		undoButton.setGraphic(new ImageView(pathToImages + "toolbar/undo.png"));
		redoButton.setGraphic(new ImageView(pathToImages + "toolbar/redo.png"));
		cutButton.setGraphic(new ImageView(pathToImages + "toolbar/cut.png"));
		copyButton.setGraphic(new ImageView(pathToImages + "toolbar/copy.png"));
		pasteButton.setGraphic(new ImageView(pathToImages + "toolbar/paste.png"));
		deleteButton.setGraphic(new ImageView(pathToImages + "toolbar/x-mark.png"));

		// populate boxes
		// add the time units you can choose
		TimeUnitChoice.getItems().add(TimeUnit.SECONDS);
		TimeUnitChoice.getItems().add(TimeUnit.MINUTES);
		TimeUnitChoice.getItems().add(TimeUnit.HOURS);
		TimeUnitChoice.setValue(TimeUnit.SECONDS);

		// add listener
		addNumberEnforcer(minVoter, maxVoter, 1);
		addNumberEnforcer(maxVoter, minVoter, -1);

		addNumberEnforcer(minCandidates, maxCandidates, 1);
		addNumberEnforcer(maxCandidates, minCandidates, -1);

		addNumberEnforcer(minSeats, maxSeats, 1);
		addNumberEnforcer(maxSeats, minSeats, -1);

		addNumberEnforcer(maxUnrolls);

		addNumberEnforcer(processes);

		addNumberEnforcer(timeOut);

		inputVoterField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				addInputNumberEnforcer(inputVoterField, newValue);
			}
		});

		inputCandidateField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				addInputNumberEnforcer(inputCandidateField, newValue);
			}
		});

		inputSeatField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				addInputNumberEnforcer(inputSeatField, newValue);
			}
		});

		// init the propTree:

		// treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>(){
		//
		// @Override
		// public PropertyTreeCell call(TreeView<String> param) {
		// return new PropertyTreeCell();
		// }
		// });
		//

		// // Use a custom callback to determine the style of the tree item
		// treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
		// @Override
		// public TreeCell<String> call(TreeView<String> param) {
		// return new CheckBoxTreeCell<String>(){
		// @Override
		// public void updateItem(String item, boolean empty){
		// super.updateItem(item, empty);
		// // If there is no information for the Cell, make it empty
		// if(empty){
		// setGraphic(null);
		// setText(null);
		// // Otherwise if it's not representation as an item of the tree
		// // is not a CheckBoxTreeItem, remove the checkbox item
		// }else if (!(getTreeItem() instanceof CheckBoxTreeItem)){
		// setGraphic(null);
		// }
		// }
		// };
		// }
		// });

		treeView.setShowRoot(false);

		// treeView.setCellFactory(new Callback<TreeView<String>, TreeCell<String>>() {
		//
		//
		// public TreeCell<String>() {
		// @Override
		// public TreeCell<String> call(TreeView<String> p) {
		// return new TextFieldTreeCellImpl();
		// }
		// }
		//
		//// @Override
		//// public TreeCell<String> call(TreeView<String> param) {
		//// return new PropertyTreeCell();
		//// }
		// });

		root = new TreeItem<>();
		root.setExpanded(true);

		// final ParentTreeItem test1 = new ParentTreeItem("test1");

		// for testing add one "prop"

		// final TreeItemParent parentNode1 = new TreeItemParent("test 1");

		// final TreeItem<String> parentNode1 = new TreeItem<>("Property 1");
		//
		// final TreeItem<String> checkNode1 = new TreeItem<>("Check");
		//
		// final TreeItem<String> marginNode1 = new TreeItem<>("Margin");
		//

		// root.getChildren().add(parentNode1);
		//
		// parentNode1.getChildren().addAll(checkNode1, marginNode1);
		//
		// root.getChildren().add(test1);

		treeView.setRoot(root);

		codeArea = new NewCodeArea();

		VirtualizedScrollPane<NewCodeArea> VSP = new VirtualizedScrollPane<NewCodeArea>(codeArea);

		codePane.setContent(VSP);

		preArea = new NewPropertyCodeArea();

		VirtualizedScrollPane<NewPropertyCodeArea> VSPpre = new VirtualizedScrollPane<NewPropertyCodeArea>(preArea);

		prePropertyPane.setContent(VSPpre);

		postArea = new NewPropertyCodeArea();

		VirtualizedScrollPane<NewPropertyCodeArea> VSPpost = new VirtualizedScrollPane<NewPropertyCodeArea>(postArea);

		postPropertyPane.setContent(VSPpost);

		variableTreeView.setShowRoot(false);
		TreeItem<String> symbVarRoot = new TreeItem<String>();
		symbVarRoot.setExpanded(true);

		variableTreeView.setRoot(symbVarRoot);

		this.voterItems = new TreeItem<String>("Voters");

		this.candidateItems = new TreeItem<String>("Candidates");

		this.seatItems = new TreeItem<String>("Seats");

		symbVarRoot.getChildren().add(voterItems);
		symbVarRoot.getChildren().add(candidateItems);
		symbVarRoot.getChildren().add(seatItems);

		booleanExpEditor = new BooleanExpEditorNEW(preArea, postArea,
				new PreAndPostConditionsDescription("default description"), null);

		variableTreeView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSymbVarToRemove(newValue));

		treeView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setPropertyToRemove(newValue));

		codeArea.setStyle("-fx-font-family: consolas; -fx-font-size: 11pt;");

		// inputScrollPane.addEventHandler(ScrollEvent.ANY, new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) { // synchronize the scrolling
		// voterScrollPane.vvalueProperty().set(inputScrollPane.getVvalue());
		//
		// candidateScrollPane.hvalueProperty().set(inputScrollPane.getHvalue());
		// }
		// });

		Thread scrollUpdater = new Thread(new Runnable() {

			@Override
			public void run() {
				while (true) {
					long time = System.currentTimeMillis();

					voterScrollPane.vvalueProperty().set(inputScrollPane.getVvalue());

					candidateScrollPane.hvalueProperty().set(inputScrollPane.getHvalue());

					infoTextArea.setText("" + inputScrollPane.getHvalue() + "\n" + candidateScrollPane.getHvalue());

					inputScrollPane.fireEvent(new Event(ScrollEvent.ANY));

					try {
						Thread.sleep(Math.max(0, 16 - (System.currentTimeMillis() - time)));
					} catch (InterruptedException e) {

					}
				}
			}
		});

		scrollUpdater.start();

		voterScrollPane.setPadding(new Insets(0, 0, scrollbarPadding, 0));

		candidateScrollPane.setPadding(new Insets(0, scrollbarPadding, 0, 0));

		voterScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				event.consume();
			}
		});

		candidateScrollPane.addEventFilter(ScrollEvent.SCROLL, new EventHandler<ScrollEvent>() {
			@Override
			public void handle(ScrollEvent event) {
				event.consume();
			}
		});

		// voterScrollPane.addEventHandler(ScrollEvent.ANY, new EventHandler<Event>() {
		// @Override
		// public void handle(Event event) { // synchronize the scrolling
		// voterScrollPane.vvalueProperty().set(inputScrollPane.getVvalue());
		// }
		// });
		//
		// candidateScrollPane.addEventHandler(ScrollEvent.ANY, new
		// EventHandler<Event>() {
		// @Override
		// public void handle(Event event) { // synchronize the scrolling
		// candidateScrollPane.hvalueProperty().set(inputScrollPane.getHvalue());
		// }
		// });

		electionSimulation = new NewElectionSimulation(codeArea.getElectionDescription().getContainer(), inputGridPane,
				voterGridPane, candidateGridPane);

		this.addInputNumberEnforcer(inputVoterField, ""); // update all numbers for the input fields

	}

	// Top Panels
	@FXML
	public void errorPaneClicked(Event event) {

	}

	@FXML
	public void inputPaneClicked(Event event) {

	}

	@FXML
	public void propertyPaneClicked(Event event) {

	}

	@FXML
	public void resultPaneClicked(Event event) {

	}

	// ------------
	// symb Var
	@FXML
	public void addSymbCand(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.CANDIDATE), toAdd, false);
	}

	@FXML
	public void addSymbSeat(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.SEAT), toAdd, false);
	}

	@FXML
	public void addSymbVoter(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.VOTER), toAdd, false);
	}

	@FXML
	public void removeSymbVar() {
		if (symbVarToRemove != null) {
			long time = System.currentTimeMillis();

			if ((time - lastClicked) < threshold) {
				booleanExpEditor.removeVariable(symbVarToRemove.getValue());
				symbVarToRemove = null;
			}
		}
	}

	@FXML
	public void removePropVar() {

		if (propertyToRemove != null) {
			long time = System.currentTimeMillis();

			if ((time - lastClicked) < threshold) {
				root.getChildren().remove(propertyToRemove);

				propertyToRemove = null;
			}
		}
	}

	public TextField getInputVoters() {
		return inputVoterField;
	}

	public TextField getInputCandidates() {
		return inputCandidateField;
	}

	public TextField getInputSeats() {
		return inputSeatField;
	}

	// ------------
	// Bottom Panels
	@FXML
	public void codePaneClicked(Event event) {

	}

	@FXML
	public void consolePaneClicked(Event event) {

	}

	// --------
	// Icon Bar
	@FXML
	public synchronized void startStopPressed(ActionEvent event) {
		if (!running) {
			// react = false; // lock the GUI
			if (BEASTCommunicator.startCheckNEW()) { // if we start it successful
				startStopButton.setGraphic(new ImageView(pathToImages + "toolbar/stop.png"));
				running = true;
			}
		} else {
			if (BEASTCommunicator.stopCheck()) {
				startStopButton.setGraphic(new ImageView(pathToImages + "toolbar/start.png"));
				running = false;
			}
		}
	}

	@FXML
	public void copyButton(ActionEvent event) {

	}

	@FXML
	public void undoButton(ActionEvent event) {

	}

	@FXML
	public void cutButton(ActionEvent event) {

	}

	@FXML
	public void openButton(ActionEvent event) {

	}

	@FXML
	public void pasteButton(ActionEvent event) {

	}

	@FXML
	public void redoButton(ActionEvent event) {

	}

	@FXML
	public void saveAsButton(ActionEvent event) {
		codeArea.saveAs("test");
	}

	@FXML
	public void saveButton(ActionEvent event) {

	}

	@FXML
	public void deleteButton(ActionEvent event) {

	}

	// text manipulation menu buttons
	@FXML
	public void copy(ActionEvent event) {
		copyButton(event);
	}

	@FXML
	public void delete(ActionEvent event) {
		deleteButton(event);
	}

	@FXML
	public void cut(ActionEvent event) {
		cutButton(event);
	}

	@FXML
	public void paste(ActionEvent event) {
		pasteButton(event);
	}

	@FXML
	public void redo(ActionEvent event) {
		redoButton(event);
	}

	@FXML
	public void undo(ActionEvent event) {
		undoButton(event);
	}

	// other menu buttons

	@FXML // the user wants to edit the name of the current property
	public void propNameButtonClicked(Event event) {
		if (nameFieldIsChangeable) {
			String text = propNameField.getText();
			if (!text.equals("")) {
				if (isValidFileName(text)) {
					propNameField.setEditable(false);
					resultNameField.setText(text);

					booleanExpEditor.getPropertyDescription().setNewName(text);

					if (booleanExpEditor.getCurrentItem() != null) {
						booleanExpEditor.getCurrentItem().setText(text);
					}
					nameFieldIsChangeable = false;
					propNameButton.setText("change");
				} else {
					setErrorText("invalid property name");
				}
			}
		} else {
			propNameButton.setText("save");
			propNameField.setEditable(true);
			nameFieldIsChangeable = true;
		}
	}

	@FXML
	public void advancedParameters(Event event) {

	}

	@FXML
	public void helpClicked(ActionEvent event) {

	}

	@FXML
	public void maxCandidates(ActionEvent event) {

	}

	@FXML
	public void maxSeats(ActionEvent event) {

	}

	@FXML
	public void maxUnrolls(ActionEvent event) {

	}

	@FXML
	public void maxVoter(ActionEvent event) {
		System.out.println("test");
		System.out.println(maxVoter.getText());
	}

	@FXML
	public void minCandidates(ActionEvent event) {

	}

	@FXML
	public void minSeats(ActionEvent event) {

	}

	@FXML
	public void minVoter(ActionEvent event) {

	}

	@FXML
	public void newElectionDescription(ActionEvent event) {
		Triplet<String, InputType, OutputType> triplet = showPopUp("New Election Description",
				"chose the new Election description", "input Type:", InputType.getInputTypes(), "output Type:",
				OutputType.getOutputTypes());

		if (triplet != null) {
			codeArea.setNewElectionDescription(
					new ElectionDescription(triplet.first, triplet.second, triplet.third, 0));
		}
	}

	@FXML
	public void newProject(ActionEvent event) {

		Triplet<String, InputType, OutputType> triplet = showPopUp("New Election Description",
				"chose the new Election description", "input Type:", InputType.getInputTypes(), "output Type:",
				OutputType.getOutputTypes());

		if (triplet != null) {
			codeArea.setNewElectionDescription(
					new ElectionDescription(triplet.first, triplet.second, triplet.third, 0));
			newPropertyList(null);
			newVotingInput(null);
		}

	}

	@FXML
	public void newPropertyList(ActionEvent event) {

		root.getChildren().clear();
		booleanExpEditor.clear();

	}

	@FXML
	public void newVotingInput(ActionEvent event) {
		electionSimulation.reset();
	}

	@FXML
	public void openElectionDescription(ActionEvent event) {

	}

	@FXML
	public void openProject(ActionEvent event) {

	}

	@FXML
	public void openPropertyList(ActionEvent event) {

	}

	@FXML
	public void openVotingInput(ActionEvent event) {

	}

	@FXML
	public void processes(ActionEvent event) {

	}

	@FXML
	public void quitProgram(ActionEvent event) {

	}

	@FXML
	public void saveAsElectionDescription(ActionEvent event) {

	}

	@FXML
	public void saveAsProject(ActionEvent event) {

	}

	@FXML
	public void saveAsPropertyList(ActionEvent event) {

	}

	@FXML
	public void saveAsVotingInput(ActionEvent event) {

	}

	@FXML
	public void saveElectionDescription(ActionEvent event) {

	}

	@FXML
	public void saveProject(ActionEvent event) {

	}

	@FXML
	public void savePropertyList(ActionEvent event) {

	}

	@FXML
	public void saveVotingInput(ActionEvent event) {

	}

	@FXML
	public void timeOut(ActionEvent event) {

	}

	@FXML
	public void newProperty(ActionEvent event) {
		addTreeItem(new PreAndPostConditionsDescription("new Property"));
	}

	@FXML
	public void loadProperty(ActionEvent event) {
		System.out.println("TODO LOAD PROPERTY");
	}

	@FXML
	public void loadPropertyList(ActionEvent event) {
		System.out.println("TODO LOAD PROPERTY LIST");
	}

	@FXML
	public void resetInput(ActionEvent event) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);

		confirmation.setX(mainStage.getX());
		confirmation.setY(mainStage.getY());

		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(pathToImages + "other/BEAST.png"));

		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Do you really want to reset the input?");
		confirmation.setContentText("Doing so will reset all previously given values");

		Optional<ButtonType> result = confirmation.showAndWait();

		if (result.get() == ButtonType.OK) {
			electionSimulation.reset();
		}
	}

	private void addNumberEnforcer(TextField field) {
		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					field.setText(newValue.replaceAll("[^\\d]", ""));
				}
			}
		});
	}

	/**
	 * 
	 * @param field
	 *            the field which shall be enforced
	 * @param partnerField
	 *            the partner field, which is supposed to be not bigger / smaller
	 *            than the main field
	 * @param sign
	 *            a sign to show if the field has to be bigger or smaller than the
	 *            other one. e.g a sign of 1 means field <= partnerField, a sign of
	 *            (-1) would mean field => partner field
	 */
	private void addNumberEnforcer(TextField field, TextField partnerField, int sign) {
		if (sign == 0) {
			field.setText("");
			partnerField.setText("");
		}

		field.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
				if (!newValue.matches("\\d*")) {
					field.setText(newValue.replaceAll("[^\\d]", ""));
				}

				if (newValue.equals("")) {
					field.setText("" + 1);
				}

				int valueField = Integer.parseInt(field.getText());
				int valuePartner = Integer.parseInt(partnerField.getText());

				if (valueField * sign > valuePartner * sign) {
					partnerField.setText("" + valueField);
				}
			}
		});

	}

	public ElectionCheckParameter getParameter() {
		List<Integer> voter = getValues(minVoter, maxVoter);
		List<Integer> cand = getValues(minCandidates, maxCandidates);
		List<Integer> seat = getValues(minSeats, maxSeats);

		int marginVoters = electionSimulation.getNumVoters();
		int marginCandidates = electionSimulation.getNumCandidates();
		int marginSeats = electionSimulation.getNumSeats();

		Integer numberProcesses = Runtime.getRuntime().availableProcessors();

		if (!processes.getText().equals("")) {
			numberProcesses = Integer.parseInt(processes.getText());
		}

		TimeOut time = new TimeOut(TimeUnit.SECONDS, 0);

		if (!timeOut.getText().equals("")) {
			time = new TimeOut(TimeUnitChoice.getValue(), Integer.parseInt(timeOut.getText()));
			numberProcesses = Integer.parseInt(processes.getText());
		}

		String argument = advancedParameters.getText();

		ElectionCheckParameter param = new ElectionCheckParameter(voter, cand, seat, marginVoters, marginCandidates,
				marginSeats, time, numberProcesses, argument);
		return param;
	}

	private List<Integer> getValues(TextField minfield, TextField maxField) {
		List<Integer> toReturn = new ArrayList<Integer>();

		int valueMin = Integer.parseInt(minfield.getText());
		int valueMax = Integer.parseInt(maxField.getText());

		for (int i = valueMin; i <= valueMax; i++) {
			toReturn.add(i);
		}

		return toReturn;
	}

	public static String getInfoText() {
		return controller.infoTextArea.getText();
	}

	public static void setInfoText(String text) {
		controller.infoTextArea.setText(text);

		controller.getSubTabPane().getSelectionModel().select(controller.informationPane);
	}

	public static String getConsoleText() {
		return controller.consoleTextArea.getText();
	}

	public static void setConsoleText(String text) {
		controller.consoleTextArea.setText(text);

		controller.getSubTabPane().getSelectionModel().select(controller.consolePane);
	}

	public static String getErrorText() {
		return controller.errorTextArea.getText();
	}

	public static void setErrorText(String text) {
		controller.errorTextArea.setText(text);

		controller.getSubTabPane().getSelectionModel().select(controller.errorPane);
	}

	public static TreeItem<CustomTreeItem> addTreeItem(PreAndPostConditionsDescription description) {

		TreeItem<CustomTreeItem> propRoot = new TreeItem<CustomTreeItem>();

		properties.add(new ParentTreeItem(description, false, propRoot));

		root.getChildren().add(propRoot);

		return propRoot;
	}

	public static void removeTreeItem(TreeItem<String> item) {
		root.getChildren().remove(item);
	}

	public static GUIController getController() {
		return controller;
	}

	public TextArea getResultField() {
		return solutionField;
	}

	public NewCodeArea getCodeArea() {
		return codeArea;
	}

	public NewPropertyCodeArea getPreConditionsArea() {
		return preArea;
	}

	public NewPropertyCodeArea getPostConditionsArea() {
		return postArea;
	}

	public TabPane getMainTabPane() {
		return mainTabPane;
	}

	public TabPane getSubTabPane() {
		return subTabPane;
	}

	public Tab getPropertyTab() {
		return propertyPane;
	}

	public Tab getResultTab() {
		return resultPane;
	}

	public Tab getCodeTab() {
		return codePane;
	}

	public Tab getInputTab() {
		return inputPane;
	}

	public List<ParentTreeItem> getProperties() {
		return properties;
	}

	public ElectionDescription getElectionDescription() {
		return codeArea.getElectionDescription();
	}

	public String[][] getVotingData() {
		return electionSimulation.getVotingData();
	}

	public NewPropertyCodeArea getPreCodeArea() {
		return preArea;
	}

	public NewPropertyCodeArea getPostCodeArea() {
		return postArea;
	}

	public TextField getVariableNameField() {
		return symbVarField;
	}

	public boolean getDeleteTmpFiles() {
		return deleteItemsCheckbox.isSelected();
	}

	private void addInputNumberEnforcer(TextField field, String newValue) {
		newValue = newValue.replaceAll(" ", "");
		if (newValue.length() != 0) {
			String sign = "";

			if (newValue.charAt(0) == '-' && newValue.length() > 1) {
				sign = "-";
			}

			if (!newValue.matches("\\d*")) {

				String newText = "0";
				if (!newValue.equals("")) {
					newText = newValue.replaceAll("[^\\d]", "");
				}
				if (newText.equals("")) {
					newText = "0";
				}
				field.setText(sign + newText);
			}
		} else {
			field.setText("0");

		}
		String vettedVoters = electionSimulation.setAndVetVoterNumber(inputVoterField.getText());
		if (vettedVoters != inputVoterField.getText()) {
			inputVoterField.setText(vettedVoters);
		}

		String vettedCandidates = electionSimulation.setAndVetCandidateNumber(inputCandidateField.getText());
		if (vettedVoters != inputCandidateField.getText()) {
			inputCandidateField.setText(vettedCandidates);
		}

		String vettedSeats = electionSimulation.setAndVetSeatNumber(inputSeatField.getText());
		if (vettedVoters != inputSeatField.getText()) {
			inputSeatField.setText(vettedSeats);
		}

	}

	public NewElectionSimulation getElectionSimulation() {
		return electionSimulation;
	}

	public BooleanExpEditorNEW getBooleanExpEditor() {
		return booleanExpEditor;
	}

	public TreeItem<String> getVoterTreeItems() {
		return voterItems;
	}

	public TreeItem<String> getCandidateTreeItems() {
		return candidateItems;
	}

	public TreeItem<String> getSeatTreeItems() {
		return seatItems;
	}

	private void setPropertyToRemove(TreeItem<CustomTreeItem> prop) {
		this.propertyToRemove = prop;
		this.lastClicked = System.currentTimeMillis();
	}

	public void setSymbVarToRemove(TreeItem<String> item) {
		this.symbVarToRemove = item;
		this.lastClicked = System.currentTimeMillis();
	}

	public void setCurrentPropertyDescription(ParentTreeItem propertyItem, boolean bringToFront) {
		if (nameFieldIsChangeable) {
			propNameButtonClicked(null); // try to save the text the user wrote
		}

		if (booleanExpEditor.getCurrentItem() == null) {
			if (!nameFieldIsChangeable) {
				propertyItem.setText(propNameField.getText());
				propertyItem.getPreAndPostPropertie().setNewName(propNameField.getText());
			}
		}
		booleanExpEditor.setCurrentPropertyDescription(propertyItem, bringToFront);
		propNameField.setText(propertyItem.getPreAndPostPropertie().getName());
		resultNameField.setText(propertyItem.getPreAndPostPropertie().getName());
	}

	private Triplet<String, InputType, OutputType> showPopUp(String titleText, String infoText,
			String inTypeDescription, List<InputType> inTypes, String outTypeDescription, List<OutputType> outTypes) {

		Point position = MouseInfo.getPointerInfo().getLocation();

		Dialog<String> dialog = new Dialog<>();

		// TextInputDialog dialog = new TextInputDialog("");

		dialog.setX(position.getX());
		dialog.setY(position.getY());

		dialog.setTitle(titleText);
		dialog.setHeaderText(infoText);
		// dialog.setContentText(inputText);

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(pathToImages + "other/BEAST.png"));

		ButtonType buttonType = new ButtonType("OK", ButtonData.OK_DONE);
		dialog.getDialogPane().getButtonTypes().addAll(buttonType, ButtonType.CANCEL);

		GridPane grid = new GridPane();

		grid.setHgap(10);
		grid.setVgap(10);
		grid.setPadding(new Insets(20, 150, 10, 10));

		// populate the grid with the choices

		grid.add(new Label("name:"), 0, 0);

		TextField nameField = new TextField();

		grid.add(nameField, 1, 0);

		grid.add(new Label(inTypeDescription), 0, 1);

		ChoiceBox<InputType> inputType = new ChoiceBox<InputType>(FXCollections.observableList(inTypes));
		
		inputType.getSelectionModel().selectFirst();

		grid.add(inputType, 1, 1);

		grid.add(new Label(outTypeDescription), 0, 2);

		ChoiceBox<OutputType> outputType = new ChoiceBox<OutputType>(FXCollections.observableList(outTypes));

		outputType.getSelectionModel().selectFirst();
		
		grid.add(outputType, 1, 2);

		dialog.getDialogPane().setContent(grid);

		// wait for the user to select
		Optional<String> result = dialog.showAndWait();

		if (result.isPresent()) {

			String newName = nameField.getText();

			if (isValidFileName(newName)) {

				Triplet<String, InputType, OutputType> toReturn = new Triplet<String, InputType, OutputType>(newName,
						inputType.getValue(), outputType.getValue());

				return toReturn;

			} else {

				setErrorText("file name not valid, try again");
				return null;
			}
		} else {
			return null;
		}
	}

	// take from
	// https://stackoverflow.com/questions/6730009/validate-a-file-name-on-windows?utm_medium=organic&utm_source=google_rich_qa&utm_campaign=google_rich_qa
	//
	// checks if a name is allowed for the windows file system (more restrictive
	// than linux), so we can just name the files like that
	public static boolean isValidFileName(String text) {
		Pattern pattern = Pattern.compile(
				"# Match a valid Windows filename (unspecified file system).          \n"
						+ "^                                # Anchor to start of string.        \n"
						+ "(?!                              # Assert filename is not: CON, PRN, \n"
						+ "  (?:                            # AUX, NUL, COM1, COM2, COM3, COM4, \n"
						+ "    CON|PRN|AUX|NUL|             # COM5, COM6, COM7, COM8, COM9,     \n"
						+ "    COM[1-9]|LPT[1-9]            # LPT1, LPT2, LPT3, LPT4, LPT5,     \n"
						+ "  )                              # LPT6, LPT7, LPT8, and LPT9...     \n"
						+ "  (?:\\.[^.]*)?                  # followed by optional extension    \n"
						+ "  $                              # and end of string                 \n"
						+ ")                                # End negative lookahead assertion. \n"
						+ "[^<>:\"/\\\\|?*\\x00-\\x1F]*     # Zero or more valid filename chars.\n"
						+ "[^<>:\"/\\\\|?*\\x00-\\x1F\\ .]  # Last char is not a space or dot.  \n"
						+ "$                                # Anchor to end of string.            ",
				Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE | Pattern.COMMENTS);
		Matcher matcher = pattern.matcher(text);
		boolean isMatch = matcher.matches();
		return isMatch;
	}

}

// class PairValueFactory implements
// Callback<TableColumn.CellDataFeatures<Pair<String, Object>, Object>,
// ObservableValue<Object>> {
// @SuppressWarnings("unchecked")
// @Override
// public ObservableValue<Object> call(TableColumn.CellDataFeatures<Pair<String,
// Object>, Object> data) {
// Object value = data.getValue().getValue();
// return (value instanceof ObservableValue)
// ? (ObservableValue) value
// : new ReadOnlyObjectWrapper<>(value);
// }
// }
