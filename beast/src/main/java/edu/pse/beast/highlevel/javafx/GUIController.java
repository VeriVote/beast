package edu.pse.beast.highlevel.javafx;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;

import org.fxmisc.flowless.VirtualizedScrollPane;

import edu.pse.beast.codeareaJAVAFX.NewCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPostPropertyCodeArea;
import edu.pse.beast.codeareaJAVAFX.NewPrePropertyCodeArea;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.electionSimulator.NewElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ChoiceBox;
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
import javafx.stage.Stage;

public class GUIController {

	private static GUIController controller;

	private static ArrayList<ParentTreeItem> properties = new ArrayList<ParentTreeItem>();

	private String pathToImages = "file:///" + SuperFolderFinder.getSuperFolder() + "/core/images/";

	private static TreeItem<CustomTreeItem> root;

	private boolean react = true;

	private List<TabClass> mainWindowTabs = new ArrayList<TabClass>();

	private List<TabClass> bottomWindowTabs = new ArrayList<TabClass>();

	private List<PreAndPostConditionsDescription> conditions = new ArrayList<PreAndPostConditionsDescription>();

	private NewElectionSimulation electionSimulation = new NewElectionSimulation();

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
	private TreeView<SymbolicVariable> variableTreeView;

	@FXML
	private TextField symbVarField;

	@FXML
	private TextField inputVoterField;

	@FXML
	private TextField inputCandidateField;

	@FXML
	private TextField inputSeatField;

	// @FXML
	// private Text
	//
	// @FXML
	// private Button removeVarButton;

	private boolean running = false;

	private NewCodeArea codeArea;

	private NewPrePropertyCodeArea preArea;

	private NewPostPropertyCodeArea postArea;

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

				addVoterNumberEnforcer(inputVoterField, newValue);
			}
		});

		inputCandidateField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				addVoterNumberEnforcer(inputCandidateField, newValue);
			}
		});
		
		inputSeatField.textProperty().addListener(new ChangeListener<String>() {
			@Override
			public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {

				addVoterNumberEnforcer(inputSeatField, newValue);
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

		preArea = new NewPrePropertyCodeArea();

		VirtualizedScrollPane<NewPrePropertyCodeArea> VSPpre = new VirtualizedScrollPane<NewPrePropertyCodeArea>(
				preArea);

		prePropertyPane.setContent(VSPpre);

		postArea = new NewPostPropertyCodeArea();

		VirtualizedScrollPane<NewPostPropertyCodeArea> VSPpost = new VirtualizedScrollPane<NewPostPropertyCodeArea>(
				postArea);

		postPropertyPane.setContent(VSPpost);

		codeArea.setStyle("-fx-font-family: consolas; -fx-font-size: 11pt;");

		// create the tabs which will be used
		// mainWindowTabs.add(new RichTextFXCodeArea());
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
		BooleanExpEditorNEW.addSymbVar(new InternalTypeContainer(InternalTypeRep.CANDIDATE));
	}

	@FXML
	public void addSymbSeat(ActionEvent event) {
		BooleanExpEditorNEW.addSymbVar(new InternalTypeContainer(InternalTypeRep.SEAT));
	}

	@FXML
	public void addSymbVoter(ActionEvent event) {
		BooleanExpEditorNEW.addSymbVar(new InternalTypeContainer(InternalTypeRep.VOTER));
	}

	@FXML
	public TextField getInputVoters() {
		return inputVoterField;
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
	public void startStopPressed(ActionEvent event) {
		if (!running) {
			// react = false; // lock the GUI
			if (BEASTCommunicator.startCheckNEW()) { // if we start it successful
				startStopButton.setGraphic(new ImageView(pathToImages + "toolbar/stop.png"));
			} else {
				// react = true;
			}
		} else {
			if (BEASTCommunicator.stopCheck()) {
				startStopButton.setGraphic(new ImageView(pathToImages + "toolbar/start.png"));
				// react = true;
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

	}

	@FXML
	public void newProject(ActionEvent event) {

	}

	@FXML
	public void newPropertyList(ActionEvent event) {

	}

	@FXML
	public void newVotingInpup(ActionEvent event) {

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
		
		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();

		// Add a custom icon.
		stage.getIcons().add(new Image(pathToImages + "other/BEAST.png"));
		
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Do you really want to reset the input?");
		confirmation.setContentText("Doing so will reset all previously given values");
		
		Optional<ButtonType> result = confirmation.showAndWait();
		
		if(result.get() == ButtonType.OK) {
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

	public void setReact(boolean react) {
		this.react = react;
	}

	public ElectionCheckParameter getParameter() {
		List<Integer> voter = getValues(minVoter, maxVoter);
		List<Integer> cand = getValues(minCandidates, maxCandidates);
		List<Integer> seat = getValues(minSeats, maxSeats);

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

		ElectionCheckParameter param = new ElectionCheckParameter(voter, cand, seat, time, numberProcesses, argument);
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

	public NewPrePropertyCodeArea getPreConditionsArea() {
		return preArea;
	}

	public NewPostPropertyCodeArea getPostConditionsArea() {
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
		return NewElectionSimulation.getVotingData();
	}

	public NewPrePropertyCodeArea getPreCodeArea() {
		return preArea;
	}

	public NewPostPropertyCodeArea getPostCodeArea() {
		return postArea;
	}

	public TextField getVariableNameField() {
		return symbVarField;
	}

	public boolean getDeleteTmpFiles() {
		return deleteItemsCheckbox.isSelected();
	}

	private void addVoterNumberEnforcer(TextField field, String newValue) {
		newValue = newValue.replaceAll(" ", "");
		if(newValue.length() != 0) {
		int sign = 1;

		if (newValue.charAt(0) == '-' && newValue.length() > 1) {
			sign = -1;
		}

		if (!newValue.matches("\\d*")) {

			String newText = newValue.replaceAll("[^\\d]", "");

			newText = "" + (Long.parseLong(newText)) * sign;

			field.setText(newText);
		}
		}
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
