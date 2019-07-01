package edu.pse.beast.highlevel.javafx;

import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.Point;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.FilenameUtils;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.wellbehaved.event.InputMap;
import org.fxmisc.wellbehaved.event.Nodes;

import com.google.gson.JsonSyntaxException;

import edu.pse.beast.codeareajavafx.AutoCompleter;
import edu.pse.beast.codeareajavafx.BoundedVarCodeArea;
import edu.pse.beast.codeareajavafx.NewCodeArea;
import edu.pse.beast.codeareajavafx.NewPropertyCodeArea;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electioncheckparameter.TimeOut;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.NewElectionSimulation;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.javafx.resultpresenter.ResultImageRenderer;
import edu.pse.beast.highlevel.javafx.resultpresenter.ResultPresenterNEW;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultElements.PieChartElement;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultElements.TextImageElement;
import edu.pse.beast.highlevel.javafx.resultpresenter.resultTypes.ResultPresentationType;
import edu.pse.beast.options.OptionsNew;
import edu.pse.beast.saverloader.ChildTreeItemSaverLoader;
import edu.pse.beast.toolbox.RichTextInformation;
import edu.pse.beast.toolbox.SuperFolderFinder;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.toolbox.valueContainer.ResultValueWrapper;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;
import edu.pse.beast.types.OutputType;
import javafx.application.Platform;
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
import javafx.scene.control.Slider;
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
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GUIController {
	// 10 seconds to click "remove item" after one was selected
	private static final int THRESHOLD = 10000;

	private static GUIController controller;
	private static List<ParentTreeItem> properties = new ArrayList<ParentTreeItem>();
	private static List<TreeItem<CustomTreeItem>> treeItems = new ArrayList<TreeItem<CustomTreeItem>>();
	private static TreeItem<CustomTreeItem> root;

	private static final char SLASH = '/';
	private static final String FILE = "file:///";
	private static final String RELATIVE_PATH = "/core/images/";
	private static final String PATH_TO_IMAGES = FILE + SuperFolderFinder.getSuperFolder() + RELATIVE_PATH;
	private static final String BEAST_LOGO = "other/BEAST.png";
	private static final String START_BUTTON = "toolbar/start.png";
	private static final String STOP_BUTTON = "toolbar/stop.png";
	private static final String LOAD_BUTTON = "toolbar/load.png";
	private static final String SAVE_BUTTON = "toolbar/save.png";
	private static final String SAVE_AS_BUTTON = "toolbar/save_as.png";
	private static final String UNDO_BUTTON = "toolbar/undo.png";
	private static final String REDO_BUTTON = "toolbar/redo.png";
	private static final String CUT_BUTTON = "toolbar/cut.png";
	private static final String COPY_BUTTON = "toolbar/copy.png";
	private static final String PASTE_BUTTON = "toolbar/paste.png";
	private static final String X_MARK_BUTTON = "toolbar/x-mark.png";

	private MenuBarInterface focusedMainTab;
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

	@FXML // fx:id="timeUnitChoice"
	private ChoiceBox<TimeUnit> timeUnitChoice;

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
	private Button button;

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
	private Tab resultTab;

	@FXML // fx:id="inputPane"
	private Tab inputPane;

	@FXML // fx:id="errorPane"
	private Tab errorPane;

	@FXML // fx:id="consolePane"
	private Tab consolePane;

	@FXML
	private MenuButton displayFormat;

	@FXML
	private Tab boundVariablesTab;

	@FXML
	private Tab booleanExpressionTab;

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

	@FXML
	private BorderPane resultBorderPane;

	@FXML
	private Slider zoomSlider;

	@FXML

	// @FXML
	// private Text
	//
	// @FXML
	// private Button removeVarButton;

	private boolean running = false;

	private AutoCompleter autoComplete = new AutoCompleter();
	private NewCodeArea codeArea;
	private BoundedVarCodeArea boundedVarArea;
	private NewPropertyCodeArea preArea;
	private NewPropertyCodeArea postArea;
	private ResultArea resultArea = new ResultArea();
	private BooleanExpEditorNEW booleanExpEditor;
	private double scrollbarPadding = 15;

	private TreeItem<String> voterItems;
	private TreeItem<String> candidateItems;
	private TreeItem<String> seatItems;

	private long lastClicked = 0;

	private TreeItem<String> symbVarToRemove;
	private TreeItem<CustomTreeItem> propertyToRemove;
	private Stage mainStage;

	private boolean nameFieldIsChangeable = false;

	private SaverLoader propertyListSaverLoader = new SaverLoader(SaverLoader.PROP_LIST_FILE_ENDING,
			"BEAST list of properties");
	private SaverLoader childItemSaverLoader = new SaverLoader(SaverLoader.CHILD_PROP_FILE_ENDING,
			"BEAST child property item");
	private ChildTreeItemSaverLoader propertyListGSON = new ChildTreeItemSaverLoader();
	private SaverLoader projectSaverLoader = new SaverLoader(SaverLoader.PROJECT_FILE_ENDING, "BEAST project file");
	private OptionsSaverLoader optionSaverLoader = new OptionsSaverLoader(SaverLoader.OPT_FILE_ENDING,
			"BEAST option file");
	// private ProjectSaverLoader projectGSON = new ProjectSaverLoader();

	public GUIController(Stage mainStage) {
		this.mainStage = mainStage;
	}

	// initial setup
	@FXML
	public void initialize() {
		controller = this;
		setButtonImages();
		// populate boxes
		// add the time units you can choose
		timeUnitChoice.getItems().add(TimeUnit.SECONDS);
		timeUnitChoice.getItems().add(TimeUnit.MINUTES);
		timeUnitChoice.getItems().add(TimeUnit.HOURS);
		timeUnitChoice.setValue(TimeUnit.SECONDS);
		addListeners();

		treeView.setShowRoot(false);

		root = new TreeItem<>();
		root.setExpanded(true);

		treeView.setRoot(root);
		codeArea = new NewCodeArea();
		VirtualizedScrollPane<NewCodeArea> vsp = new VirtualizedScrollPane<NewCodeArea>(codeArea);
		codePane.setContent(vsp);
		preArea = new NewPropertyCodeArea();
		VirtualizedScrollPane<NewPropertyCodeArea> vspPre = new VirtualizedScrollPane<NewPropertyCodeArea>(preArea);
		prePropertyPane.setContent(vspPre);
		postArea = new NewPropertyCodeArea();
		VirtualizedScrollPane<NewPropertyCodeArea> vspPost = new VirtualizedScrollPane<NewPropertyCodeArea>(postArea);
		postPropertyPane.setContent(vspPost);
		boundedVarArea = new BoundedVarCodeArea();
		VirtualizedScrollPane<BoundedVarCodeArea> vspBound = new VirtualizedScrollPane<BoundedVarCodeArea>(
				boundedVarArea);
		boundVariablesTab.setContent(vspBound);
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
		booleanExpEditor = new BooleanExpEditorNEW(preArea, postArea, boundedVarArea,
				new PreAndPostConditionsDescription("default description"), null);
		variableTreeView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setSymbVarToRemove(newValue));
		treeView.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> setPropertyToRemove(newValue));
		codeArea.setStyle("-fx-font-family: consolas; -fx-font-size: 11pt;");

		Thread scrollUpdater = new Thread(new Runnable() {
			@Override
			public void run() {
				while (true) {
					long time = System.currentTimeMillis();
					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							voterScrollPane.vvalueProperty().set(inputScrollPane.getVvalue());
						}
					});

					Platform.runLater(new Runnable() {
						@Override
						public void run() {
							candidateScrollPane.hvalueProperty().set(inputScrollPane.getHvalue());
						}
					});
					inputScrollPane.fireEvent(new Event(ScrollEvent.ANY));
					try {
						Thread.sleep(Math.max(0, 16 - (System.currentTimeMillis() - time)));
					} catch (InterruptedException e) {
					}
				}
			}
		});
		mainTabPane.getSelectionModel().select(codePane);
		focusedMainTab = codeArea;
		mainTabPane.getSelectionModel().selectedItemProperty().addListener((ov, oldTab, newTab) -> {
			autoComplete.reset();
			if (newTab.equals(codePane)) {
				focusedMainTab = codeArea;
			} else if (newTab.equals(propertyPane)) {
				focusedMainTab = booleanExpEditor;
			} else if (newTab.equals(resultTab)) {
				focusedMainTab = resultArea;
			} else if (newTab.equals(inputPane)) {
				focusedMainTab = electionSimulation;
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

		List<ResultPresentationType> types = ResultPresentationType.getImplementations();
		
		for (ResultPresentationType type : types) {
			displayFormat.getItems().add(type.getMenuItem());
		}
		
		//set the default presentation type
		
		for (Iterator<ResultPresentationType> iterator = types.iterator(); iterator.hasNext();) {
			ResultPresentationType type = iterator.next();
			
		}
		
		resultScrollPane.setFitToHeight(true);
		resultScrollPane.setFitToWidth(true);
		
		// turn off the zoom slider in the beginning
		zoomSlider.setDisable(true);

		electionSimulation = new NewElectionSimulation(codeArea.getElectionDescription().getContainer(), inputGridPane,
				voterGridPane, candidateGridPane);
		// update all numbers for the input fields
		this.addInputNumberEnforcer(inputVoterField, "");
		addTreeItem(new PreAndPostConditionsDescription("New Property"));
		properties.get(0).wasClicked(false);
	}

	private void setButtonImages() {
		startStopButton.setGraphic(new ImageView(PATH_TO_IMAGES + START_BUTTON));
		openButton.setGraphic(new ImageView(PATH_TO_IMAGES + LOAD_BUTTON));
		saveButton.setGraphic(new ImageView(PATH_TO_IMAGES + SAVE_BUTTON));
		saveAsButton.setGraphic(new ImageView(PATH_TO_IMAGES + SAVE_AS_BUTTON));
		undoButton.setGraphic(new ImageView(PATH_TO_IMAGES + UNDO_BUTTON));
		redoButton.setGraphic(new ImageView(PATH_TO_IMAGES + REDO_BUTTON));
		cutButton.setGraphic(new ImageView(PATH_TO_IMAGES + CUT_BUTTON));
		copyButton.setGraphic(new ImageView(PATH_TO_IMAGES + COPY_BUTTON));
		pasteButton.setGraphic(new ImageView(PATH_TO_IMAGES + PASTE_BUTTON));
		deleteButton.setGraphic(new ImageView(PATH_TO_IMAGES + X_MARK_BUTTON));
	}

	private void addListeners() {
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
	}

	// Top Panels
	@FXML
	public void resultPaneClicked(Event event) {
	}
	
	@FXML
	public void errorPaneClicked(Event event) {
	}

	@FXML
	public void inputPaneClicked(Event event) {
	}

	@FXML
	public void propertyPaneClicked(Event event) {
	}

	// ------------
	// symb Var
	@FXML
	public void addSymbCand(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		toAdd = booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.CANDIDATE), toAdd, false);
	}

	@FXML
	public void addSymbSeat(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		toAdd = booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.SEAT), toAdd, false);
	}

	@FXML
	public void addSymbVoter(ActionEvent event) {
		String toAdd = GUIController.getController().getVariableNameField().getText();
		toAdd = booleanExpEditor.addSymbVar(new InternalTypeContainer(InternalTypeRep.VOTER), toAdd, false);
	}

	@FXML
	public void removeSymbVar() {
		if (symbVarToRemove != null) {
			long time = System.currentTimeMillis();
			if ((time - lastClicked) < THRESHOLD) {
				booleanExpEditor.removeVariable(symbVarToRemove.getValue());
				symbVarToRemove = null;
			}
		}
	}

	@FXML
	public void resetPropList() {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		
		Point position = MouseInfo.getPointerInfo().getLocation();
		
		confirmation.setX(position.getX() - confirmation.getWidth());
		confirmation.setY(position.getY() - confirmation.getHeight());
		
		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		// Add a custom icon.
		stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
		confirmation.setTitle("Confirmation Dialog");
		confirmation.setHeaderText("Do you really want to reset the property List?");
		confirmation.setContentText("Doing so will delete all current properties!");
		Optional<ButtonType> result = confirmation.showAndWait();
		if (result.get() == ButtonType.OK) {
			newPropertyList();
		}
	}

	public void removeProperty(TreeItem<CustomTreeItem> toRemove) {
		properties.remove(toRemove.getValue());
		root.getChildren().remove(toRemove);
		// reset the property fields
		GUIController.getController().resultNameField.setText("No property selected.");
		// reset the result field
		GUIController.getController().getResultScrollPane().setContent(null);
	}

	private void removeAllProperties() {
		for (TreeItem<CustomTreeItem> treeItem : treeItems) {
			removeProperty(treeItem);
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

	public void checkFinished() {
		Platform.runLater(new Runnable() {
			@Override
			public void run() {
				startStopButton.setGraphic(new ImageView(PATH_TO_IMAGES + START_BUTTON));
			}
		});
		running = false;
	}

	// --------
	// Icon Bar
	@FXML
	public synchronized void startStopPressed(ActionEvent event) {
		if (!running) {
			// react = false; // lock the GUI
			if (BEASTCommunicator.startCheckNEW()) {
				// if we start it successfully
				startStopButton.setGraphic(new ImageView(PATH_TO_IMAGES + STOP_BUTTON));
				running = true;
			}
		} else {
			if (BEASTCommunicator.stopCheck()) {
				checkFinished();
			}
		}
	}

	@FXML
	public void copyButton(ActionEvent event) {
		getFocusedArea().copy();
	}

	@FXML
	public void undoButton(ActionEvent event) {
		getFocusedArea().undo();
	}

	@FXML
	public void cutButton(ActionEvent event) {
		getFocusedArea().cut();
	}

	@FXML
	public void openButton(ActionEvent event) {
		getFocusedArea().open();
	}

	@FXML
	public void pasteButton(ActionEvent event) {
		getFocusedArea().paste();
	}

	@FXML
	public void redoButton(ActionEvent event) {
		getFocusedArea().redo();
	}

	@FXML
	public void saveAsButton(ActionEvent event) {
		getFocusedArea().saveAs();
	}

	@FXML
	public void saveButton(ActionEvent event) {
		getFocusedArea().save();
	}

	@FXML
	public void deleteButton(ActionEvent event) {
		getFocusedArea().delete();
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
			if (!text.isEmpty()) {
				if (isValidFileName(text)) {
					propNameField.setEditable(false);
					resultNameField.setText(text);
					booleanExpEditor.getPropertyDescription().setName(text);
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
		codeArea.resetSaveFile();
		Tuple3<String, InputType, OutputType> triplet = showPopUp("New Election Description",
				"chose the new Election description", "input Type:", InputType.getInputTypes(), "output Type:",
				OutputType.getOutputTypes());
		if (triplet != null) {
			codeArea.setNewElectionDescription(
					new ElectionDescription(triplet.first, triplet.second, triplet.third, 0, 0, 0, 0, true));
		}
	}

	@FXML
	public void newProject(ActionEvent event) {
		projectSaverLoader.resetHasSaveFile();
		newElectionDescription(event);
		newVotingInput();
		newPropertyList();
		setOptions(new OptionsNew()); //reset the voting bounds
	}

	@FXML
	public void openElectionDescription(ActionEvent event) {
		codeArea.open();
	}

	private void openElectionDescription(File elecDescFile) {
		codeArea.open(elecDescFile);
	}

	@FXML
	public void openProperty(ActionEvent event) {
		booleanExpEditor.open();
	}

	@FXML
	public void openProject(ActionEvent event) {
		File projectFile = projectSaverLoader.showFileLoadDialog("");
		if (projectFile != null) {
			removeAllProperties();
			String folderName = FilenameUtils.removeExtension(projectFile.getName());
			File parent = new File(projectFile.getParentFile() + Character.toString(SLASH) + folderName);
			if (parent != null) {
				// load the election Description
				File elecDescFile = new File(parent.getAbsolutePath() + SLASH + "description.elec");
				openElectionDescription(elecDescFile);
				// load the propertyList
				File propListFile = new File(
						parent.getAbsolutePath() + SLASH + "propertyList" + SaverLoader.PROP_LIST_FILE_ENDING);
				openPropertyList(propListFile);
				// load the electionInput
				File elecInputFile = new File(parent.getAbsolutePath() + SLASH + "input.elecIn");
				openVotingInput(elecInputFile);
				// load the options
				File optionsFile = new File(parent.getAbsolutePath() + SLASH + "options" + SaverLoader.OPT_FILE_ENDING);
				openOptions(optionsFile);
			}
		}
	}

	@FXML
	public void openPropertyList(ActionEvent event) {
		File listFile = propertyListSaverLoader.showFileLoadDialog("");
		openPropertyListFile(listFile);
	}

	private void openPropertyListFile(File listFile) {
		projectSaverLoader.resetHasSaveFile();
		if (listFile != null) {
			removeAllProperties();
			String folderName = FilenameUtils.removeExtension(listFile.getName());
			File parent = new File(listFile.getParentFile() + Character.toString(SLASH) + folderName);
			if (listFile != null) {
				File[] directories = parent.listFiles(File::isDirectory);
				if (directories != null && directories.length > 0) {
					for (int i = 0; i < directories.length; i++) {
						File currentDir = directories[i];
						String[] property = currentDir.list(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.endsWith(SaverLoader.PROP_DESCR_FILE_ENDING);
							}
						});
						if (property.length != 1) {
							errorTextArea
									.setText("invalid property list save format in folder: " + currentDir.getName());
							return;
						}
						PreAndPostConditionsDescription prop = booleanExpEditor
								.open(new File(currentDir.getPath() + SLASH + property[0]));
						String[] children = currentDir.list(new FilenameFilter() {
							public boolean accept(File dir, String name) {
								return name.endsWith(SaverLoader.CHILD_PROP_FILE_ENDING);
							}
						});
						if (children.length != 3) {
							errorTextArea
									.setText("invalid property list save format in folder: " + currentDir.getName());
							return;
						}
						TreeItem<CustomTreeItem> treeItem = new TreeItem<CustomTreeItem>();
						ParentTreeItem parentItem = new ParentTreeItem(prop, false, treeItem, false);
						treeItems.add(treeItem);
						properties.add(parentItem);
						root.getChildren().add(treeItem);
						for (int j = 0; j < children.length; j++) {
							final String json = childItemSaverLoader
									.load(new File(currentDir.getPath() + SLASH + children[j]));
							String[] splits = children[j].split("\\.");
							String stringIndex = splits[splits.length - 2];
							ChildTreeItemValues values = null;
							try {
								values = propertyListGSON.createFromSaveString(json);
							} catch (JsonSyntaxException e) {
								e.printStackTrace();
								return;
							}
							parentItem.addChild(values, Integer.parseInt(stringIndex));
						}
						if (parentItem.getCounter() != 3) {
							System.out.println("fehler, ");
						}
					}
				}
			}
			if (properties.size() > 0) {
				setCurrentPropertyDescription(properties.get(0), false);
			}
		}
	}

	private void openPropertyList(File propListFile) {
		openPropertyListFile(propListFile);
	}

	@FXML
	public void openVotingInput(ActionEvent event) {
		electionSimulation.open();
	}

	public void openVotingInput(File file) {
		if (file.exists()) {
			electionSimulation.open(file);
		}
	}

	@FXML
	public void processes(ActionEvent event) {
	}

	@FXML
	public void quitProgram(ActionEvent event) {
	}

	@FXML
	public void saveAsElectionDescription(ActionEvent event) {
		codeArea.saveAs();
	}

	@FXML
	public void saveAsPropertyList(ActionEvent event) {
		savePropertyListFromFile(null, true, true);
	}

	@FXML
	public void saveAsVotingInput(ActionEvent event) {
		electionSimulation.saveAs();
	}

	@FXML
	public void saveElectionDescription(ActionEvent event) {
		codeArea.save();
	}

	@FXML
	public void saveProperty(ActionEvent event) {
		booleanExpEditor.save();
	}
	
	@FXML
	public void saveAsProperty(ActionEvent event) {
		booleanExpEditor.saveAs();
	}

	@FXML
	public void saveProject(ActionEvent event) {
		if (projectSaverLoader.hasSaveFile()) {
			saveProjectFile(projectSaverLoader.getSaveFile());
		} else {
			saveAsProject(event);
		}
	}

	private void saveProjectFile(File projectFile) {
		if (projectFile != null) {
			File folder = createFolderWithName(projectFile, true);
			projectSaverLoader
					.saveAs(new File(folder.getParentFile() + Character.toString(SLASH) + projectFile.getName()), "");
			// save the electionDescription
			saveAsElectionDescription(new File(folder.getPath() + SLASH + "description.elec"));
			// save the property list
			savePropertyListFromFile(
					new File(folder.getPath() + SLASH + "propertyList" + SaverLoader.PROP_LIST_FILE_ENDING), false,
					true);
			// save election input
			electionSimulation.saveAs(new File(folder.getPath() + SLASH + "input.elecIn"));
			// save the options
			saveOptions(new File(folder.getPath() + SLASH + "options" + SaverLoader.OPT_FILE_ENDING));
		}
	}

	@FXML
	public void saveAsProject(ActionEvent event) {
		File projectFile = projectSaverLoader.showFileSaveDialog("");
		saveProjectFile(projectFile);
		projectSaverLoader.setSaveFile(projectFile);
	}

	private void saveAsElectionDescription(File file) {
		codeArea.saveAs(file);
	}

	@FXML
	public void savePropertyList(ActionEvent event) {
		savePropertyListFromFile(null, true, false);
	}
	

	public void newPropertyList() {
		propertyListSaverLoader.resetHasSaveFile();
		removeAllProperties();
		addProperty(new PreAndPostConditionsDescription("New Property"));
		properties.get(0).wasClicked(false);
		GUIController.getController().resultNameField.setText("no property selected");
		// reset the result field
		GUIController.getController().getResultScrollPane().setContent(null);
	}

	public void newVotingInput() {
		electionSimulation.reset();
	}

	private void savePropertyListFromFile(File listFile, boolean askUser, boolean saveAs) {
		if (properties.size() > 0) {
			final File file;
			if (!saveAs && askUser) {
				if (propertyListSaverLoader.hasSaveFile()) {
					savePropertyListFromFile(propertyListSaverLoader.getSaveFile(), false, false);
				} else {
					savePropertyListFromFile(listFile, true, true);
				}
				file = listFile;
			} else if (saveAs && askUser) {
				file = propertyListSaverLoader.showFileSaveDialog("");
				savePropertyListFromFile(file, false, false);
			} else {
				file = listFile;
			}
			if (file != null) {
				File folder = createFolderWithName(file, true);
				propertyListSaverLoader
						.save(new File(folder.getParentFile() + Character.toString(SLASH) + file.getName()), "");
				String listFileContent = "";

				int counter = 0;
				for (ParentTreeItem parentItem : properties) {
					listFileContent += (0 < counter) ? "\n" : 0;
					String name = parentItem.getPreAndPostProperties().getName();
					File saveFolder = new File(folder + Character.toString(SLASH) + name + "_" + counter++);
					// we want to save the parentTreeItem into this
					saveFolder = createFolderWithName(saveFolder, true);
					// folder
					name = saveFolder.getName();
					listFileContent += name;
					booleanExpEditor.saveAs(parentItem.getPreAndPostProperties(), new File(saveFolder
							+ Character.toString(SLASH) + parentItem.getText() + SaverLoader.PROP_DESCR_FILE_ENDING));
					List<ChildTreeItem> children = parentItem.getSubItems();
					int subCounter = 0;
					for (ChildTreeItem childItem : children) {
						String saveString = propertyListGSON.createSaveString(childItem.getValues());
						childItemSaverLoader.saveAs(new File(saveFolder.getAbsolutePath() + SLASH + subCounter++
								+ SaverLoader.CHILD_PROP_FILE_ENDING), saveString);
					}
				}
			}
		} else {
			errorTextArea.setText("no property items to save exist");
		}
	}

	private void saveOptions(File file) {
		OptionsNew toSave = new OptionsNew();
		toSave = fillOptionObject(toSave);
		String json = optionSaverLoader.createSaveString(toSave);
		optionSaverLoader.save(file, json);
	}

	private void openOptions(File file) {
		if (file.exists()) {
			String jsonToLoad = optionSaverLoader.load(file);
			OptionsNew options = null;
			try {
				options = optionSaverLoader.createFromSaveString(jsonToLoad);
			} catch (JsonSyntaxException e) {
				// do nothing
			}
			if (options != null) {
				setOptions(options);
			}
		}
	}

	private void setOptions(OptionsNew options) {
		this.minVoter.setText("" + options.minVoters);
		this.maxVoter.setText("" + options.maxVoters);

		this.minCandidates.setText("" + options.minCandidates);
		this.maxCandidates.setText("" + options.maxCandidates);

		this.minSeats.setText("" + options.minSeats);
		this.maxSeats.setText("" + options.maxSeats);
	}

	private OptionsNew fillOptionObject(OptionsNew options) {
		options.minVoters = Integer.parseInt(minVoter.getText());
		options.maxVoters = Integer.parseInt(maxVoter.getText());

		options.minCandidates = Integer.parseInt(minCandidates.getText());
		options.maxCandidates = Integer.parseInt(maxCandidates.getText());

		options.minSeats = Integer.parseInt(minSeats.getText());
		options.maxSeats = Integer.parseInt(maxSeats.getText());
		return options;
	}

	@FXML
	public void saveVotingInput(ActionEvent event) {
		electionSimulation.save();
	}

	@FXML
	public void timeOut(ActionEvent event) {

	}

	@FXML
	public void newProperty(ActionEvent event) {
		addProperty(new PreAndPostConditionsDescription("New Property"));
	}

	@FXML
	public void loadProperty(ActionEvent event) {
		openProperty(null);
	}

	@FXML
	public void loadPropertyList(ActionEvent event) {
		openPropertyList(new ActionEvent());
	}

	@FXML
	public void resetInput(ActionEvent event) {
		Alert confirmation = new Alert(AlertType.CONFIRMATION);
		
		Point position = MouseInfo.getPointerInfo().getLocation();
		
		confirmation.setX(position.getX() - confirmation.getWidth());
		confirmation.setY(position.getY() - confirmation.getHeight());


		Stage stage = (Stage) confirmation.getDialogPane().getScene().getWindow();
		// Add a custom icon.
		stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
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
	 * @param field        the field which shall be enforced
	 * @param partnerField the partner field, which is supposed to be not bigger /
	 *                     smaller than the main field
	 * @param sign         a sign to show if the field has to be bigger or smaller
	 *                     than the other one. e.g a sign of 1 means field <=
	 *                     partnerField, a sign of (-1) would mean field => partner
	 *                     field
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
				if (newValue.isEmpty()) {
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
		if (!processes.getText().isEmpty()) {
			numberProcesses = Integer.parseInt(processes.getText());
		}
		TimeOut time = new TimeOut(TimeUnit.SECONDS, 0);
		if (!timeOut.getText().isEmpty()) {
			time = new TimeOut(timeUnitChoice.getValue(), Integer.parseInt(timeOut.getText()));
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
		treeItems.add(propRoot);
		properties.add(new ParentTreeItem(description, false, propRoot, true));
		root.getChildren().add(propRoot);
		return propRoot;
	}

	public static void removeTreeItem(TreeItem<String> item) {
		root.getChildren().remove(item);
	}

	public static GUIController getController() {
		return controller;
	}

	public BorderPane getResultBorderPane() {
		return resultBorderPane;
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
		return resultTab;
	}

	public Tab getCodeTab() {
		return codePane;
	}

	public Tab getInputTab() {
		return inputPane;
	}

	public Slider getZoomSlider() {
		return zoomSlider;
	}

	public ScrollPane getResultScrollPane() {
		return resultScrollPane;
	}

	public List<ParentTreeItem> getProperties() {
		return properties;
	}

	public ElectionDescription getElectionDescription() {
		return codeArea.getElectionDescription();
	}

	public ResultValueWrapper getVotingData() {
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
		final String val = newValue.replaceAll(" ", "");
		if (val.length() != 0) {
			String sign = "";
			if (val.charAt(0) == '-' && val.length() > 1) {
				sign = "-";
			}
			if (!val.matches("\\d*")) {
				String newText = "0";
				if (!val.isEmpty()) {
					newText = val.replaceAll("[^\\d]", "");
				}
				if (newText.isEmpty()) {
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
		if (nameFieldIsChangeable && !bringToFront) {
			propNameButtonClicked(null); // try to save the text the user wrote
		}

		booleanExpEditor.setCurrentPropertyDescription(propertyItem, bringToFront);
		propNameField.setText(propertyItem.getPreAndPostProperties().getName());
		resultNameField.setText(propertyItem.getPreAndPostProperties().getName());
	}

	public void setPropNameField(String newText) {
		propNameField.setText(newText);
	}

	private Tuple3<String, InputType, OutputType> showPopUp(String titleText, String infoText, String inTypeDescription,
			List<InputType> inTypes, String outTypeDescription, List<OutputType> outTypes) {
		Point position = MouseInfo.getPointerInfo().getLocation();
		Dialog<String> dialog = new Dialog<>();

		dialog.setX(position.getX());
		dialog.setY(position.getY());
		dialog.setTitle(titleText);
		dialog.setHeaderText(infoText);

		Stage stage = (Stage) dialog.getDialogPane().getScene().getWindow();
		// Add a custom icon.
		stage.getIcons().add(new Image(PATH_TO_IMAGES + BEAST_LOGO));
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
				Tuple3<String, InputType, OutputType> toReturn = new Tuple3<String, InputType, OutputType>(newName,
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

	/**
	 * Taken from
	 * https://stackoverflow.com/questions/6730009/validate-a-file-name-on-windows
	 * The method checks if a name is allowed for the windows file system (more
	 * restrictive than Linux), so we can just name the files like that.
	 *
	 * @param text the file name to check
	 * @return true if the file name is valid, false if not
	 */
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

	private File createFolderWithName(File desiredFolderName, boolean clearFolder) {
		String origFileNameWithougExt = FilenameUtils.removeExtension(desiredFolderName.getAbsolutePath());
		File finalListFile = new File(origFileNameWithougExt);
		finalListFile.mkdirs();
		if (clearFolder) {
			try {
				FileUtils.cleanDirectory(finalListFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return finalListFile;
	}

	public void addProperty(PreAndPostConditionsDescription prop) {
		addTreeItem(prop);
	}

	public void setShortcutsToConsume(InputMap<Event> shortcutsToConsume) {
		Nodes.addInputMap(codeArea, shortcutsToConsume);
		Nodes.addInputMap(preArea, shortcutsToConsume);
		Nodes.addInputMap(postArea, shortcutsToConsume);
		// TODO maybe add more areas later
	}

	public MenuBarInterface getFocusedArea() {
		return focusedMainTab;
	}

	public AutoCompleter getAutoCompleter() {
		return autoComplete;
	}

	public ResultPresenterNEW getResultPresenter() {
		return ResultPresenterNEW.getInstance();
	}

	public void setPresentationTypeText(String name) {
		this.displayFormat.setText(name);
	}

	public void disableZoomSlider(boolean disabled) {
		zoomSlider.setDisable(disabled);
	}
}