package edu.pse.beast.gui.ceditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.NotImplementedException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.CelectionDescriptionFunctionType;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.FileDialogHelper;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class CElectionEditor
		implements WorkspaceUpdateListener {
	private final String cssResource = "/edu/pse/beast/ceditor.css";
	private final String cssLockedClassName = "locked";
	private final String cssUnlockedClassName = "unlocked";

	private ListView<CElectionDescriptionFunction> functionList;
	private ListView<ExtractedCLoop> loopBoundList;

	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;
	private CEditorCodeElement electionCodeArea;
	private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;

	private CElectionDescription currentDescr;
	private CElectionDescriptionFunction currentDisplayedFunction;

	private BeastWorkspace beastWorkspace;

	private Stage primaryStage;

	private Button addElectionDescriptionButton;
	private Button loadElectionDescriptionButton;
	private Button saveElectionDescriptionButton;

	private MenuButton addFunctionMenuButton;
	private Button removeFunctionButton;

	private Button testLoopBoundButton;

	private VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp;

	private String codeStyleSheet;
	
	private double currentTextSize;
	
	public CElectionEditor(Stage primaryStage,
			VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp,
			Button addElectionDescriptionButton,
			Button loadElectionDescriptionButton,
			Button saveElectionDescriptionButton,
			MenuButton addFunctionMenuButton,
			Button removeFunctionButton,
			Button testLoopBoundButton,
			CEditorCodeElement electionCodeArea,
			CodeArea funcDeclArea,
			CodeArea closingBracketArea,
			ListView<CElectionDescriptionFunction> functionList,
			ListView<ExtractedCLoop> loopBoundList,
			ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox,
			BeastWorkspace beastWorkspace) {
		codeStyleSheet = this.getClass()
				.getResource(cssResource).toExternalForm();

		this.primaryStage = primaryStage;

		this.addElectionDescriptionButton = addElectionDescriptionButton;
		this.loadElectionDescriptionButton = loadElectionDescriptionButton;
		this.saveElectionDescriptionButton = saveElectionDescriptionButton;
		
		setupNewElectionButtons();

		this.testLoopBoundButton = testLoopBoundButton;
		testLoopBoundButton.setOnAction(e -> {
			beastWorkspace.findLoopBounds(currentDescr,
					currentDisplayedFunction);
		});

		this.functionList = functionList;
		this.loopBoundList = loopBoundList;
		loopBoundList.getSelectionModel()
				.selectedItemProperty()
				.addListener((e, oldVal, newVal) -> {
					if(newVal == null) return;
					int line = newVal.getLine();
					int position = electionCodeArea
							.position(line - 1,
									newVal.getPosInLine())
							.toOffset();
					electionCodeArea.moveTo(position);
					electionCodeArea.selectLine();
					electionCodeArea.requestFollowCaret();
				});

		this.electionCodeArea = electionCodeArea;
		this.funcDeclArea = funcDeclArea;
		this.closingBracketArea = closingBracketArea;
		this.cEditorGUIElementVsp = cEditorGUIElementVsp;

		this.funcDeclArea.setEditable(false);
		this.closingBracketArea.setEditable(false);

		electionCodeArea.getStylesheets()
				.add(codeStyleSheet);
		funcDeclArea.getStylesheets().add(codeStyleSheet);
		closingBracketArea.getStylesheets()
				.add(codeStyleSheet);

		this.beastWorkspace = beastWorkspace;
		this.openedElectionDescriptionChoiceBox = openedElectionDescriptionChoiceBox;

		this.addFunctionMenuButton = addFunctionMenuButton;
		this.removeFunctionButton = removeFunctionButton;
		setupFunctionButtons();

		initListViews();
		initOpenedDescrChoiceBox();
		handleWorkspaceUpdateGeneric();
		beastWorkspace.registerUpdateListener(this);

		electionCodeArea.setChangeListener((text) -> {
			beastWorkspace.updateCodeForDescrFunction(
					currentDescr, currentDisplayedFunction,
					text);
		});

	}

	private void setupNewElectionButtons() {
		addElectionDescriptionButton.setOnAction(e -> {
			createNewDescr();
		});
		loadElectionDescriptionButton.setOnAction(e -> {
			letUserLoadDescr();
		});
	}

	private void setupFunctionButtons() {
		addFunctionMenuButton.setText("Add Function");
		addFunctionMenuButton.getItems().clear();

		MenuItem addSimpleFuncMenuItem = new MenuItem(
				CelectionDescriptionFunctionType.SIMPLE
						.toString());
		MenuItem addVotingFuncMenuItem = new MenuItem(
				CelectionDescriptionFunctionType.VOTING
						.toString());

		addSimpleFuncMenuItem
				.setOnAction(e -> addSimpleFunction());
		addVotingFuncMenuItem
				.setOnAction(e -> addVotingFunction());

		addFunctionMenuButton.getItems()
				.addAll(List.of(addSimpleFuncMenuItem,
						addVotingFuncMenuItem));

		removeFunctionButton.setOnAction(e -> {
			removeSelectedFunction();
		});
	}

	private void removeSelectedFunction() {
		throw new NotImplementedException();
	}

	private void addVotingFunction() {
		TextField nameField = new TextField();
		Optional<ButtonType> res = DialogHelper
				.generateDialog(List.of("name"),
						List.of(nameField))
				.showAndWait();
		if (res.isPresent() && !res.get().getButtonData()
				.isCancelButton()) {
			String name = nameField.getText();
			beastWorkspace.addVotingSigFunctionToDescr(
					currentDescr, name);
		}
	}

	// TODO(Holger) make this nicer, add error checking for wrong var names etc
	private void addSimpleFunction() {
		TextField nameField = new TextField();

		ChoiceBox<CElectionSimpleTypes> returnTypeChoiceBox = new ChoiceBox<>();
		returnTypeChoiceBox.getItems()
				.addAll(CElectionSimpleTypes.values());
		returnTypeChoiceBox.getSelectionModel()
				.selectFirst();
		Label argumentsLabel = new Label();

		List<CElectionSimpleTypes> argTypes = new ArrayList<>();
		List<String> argNames = new ArrayList<>();

		TextField argsNameTextField = new TextField();
		ChoiceBox<CElectionSimpleTypes> argsTypeChoiceBox = new ChoiceBox();
		argsTypeChoiceBox.getItems()
				.addAll(CElectionSimpleTypes.values());
		argsTypeChoiceBox.getSelectionModel().selectFirst();

		Button addArgButton = new Button("add argument");
		Button removeArgButton = new Button(
				"remove Last Argument");

		Consumer<Label> updateArgLabel = l -> {
			String text = "";
			for (int i = 0; i < argNames.size(); ++i) {
				text += argTypes.get(i) + " "
						+ argNames.get(i) + ", ";
			}
			l.setText(text);
		};

		addArgButton.setOnAction(e -> {
			if (!nameField.getText().isEmpty()) {
				argTypes.add(argsTypeChoiceBox
						.getSelectionModel()
						.getSelectedItem());
				argNames.add(argsNameTextField.getText());
				updateArgLabel.accept(argumentsLabel);
			}
		});

		removeArgButton.setOnAction(e -> {
			if (!argTypes.isEmpty()) {
				argTypes.remove(argTypes.size() - 1);
				argNames.remove(argTypes.size() - 1);
				updateArgLabel.accept(argumentsLabel);
			}
		});

		DialogHelper.generateDialog(
				List.of("name", "return type"),
				List.of(nameField, returnTypeChoiceBox,
						argsTypeChoiceBox,
						argsNameTextField, addArgButton,
						removeArgButton))
				.showAndWait();
	}

	private void selectedDescrChanged(
			CElectionDescription descr) {
		loadElectionDescr(descr);
	}

	private void initOpenedDescrChoiceBox() {
		openedElectionDescriptionChoiceBox
				.getSelectionModel().selectedItemProperty()
				.addListener((observable, oldValue,
						newValue) -> {
					selectedDescrChanged(newValue);
				});
	}

	/* ========== handle workspace updates =========== */

	// TODO(Holger) This is where we would add undo/redo for higher level
	// changes such as adding/removing functions etc
	public void handleWorkspaceUpdateGeneric() {
		openedElectionDescriptionChoiceBox.getItems()
				.clear();
		for (CElectionDescription descr : beastWorkspace
				.getLoadedDescrs()) {
			openedElectionDescriptionChoiceBox.getItems()
					.add(descr);
		}
		// I think we need to do this here, because the selectLast doesnt trigger
		// our selectionchanged handler if the items are empty?
		if (openedElectionDescriptionChoiceBox.getItems()
				.size() == 0) {
			loadElectionDescr(null);
		} else {
			openedElectionDescriptionChoiceBox
					.getSelectionModel().selectLast();
		}
	}

	@Override
	public void handleDescrChangeAddedVotingSigFunction(
			CElectionDescription descr,
			VotingSigFunction func) {
		if (descr.equals(currentDescr)) {
			functionList.getItems().add(func);
		}
	}

	@Override
	public void handleDescrChangeRemovedFunction(
			CElectionDescription descr,
			CElectionDescriptionFunction func) {
		if (descr == currentDescr) {
			functionList.getItems().remove(func);
		}
	}

	@Override
	public void handleDescrChangeUpdatedFunctionCode(
			CElectionDescription descr,
			CElectionDescriptionFunction function,
			String code) {
		if (descr.equals(currentDescr) && function
				.equals(currentDisplayedFunction)) {
			displayLoopBounds(function.getExtractedLoops());
		}
	}

	@Override
	public void handleExtractedFunctionLoops(
			CElectionDescription descr,
			CElectionDescriptionFunction func) {
		if (descr.equals(currentDescr)
				&& func.equals(currentDisplayedFunction)) {
			displayLoopBounds(func.getExtractedLoops());
		}
	}

	/* ===== other stuff ====== */
	private void initListViews() {
		functionList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);
		functionList.getSelectionModel()
				.selectedItemProperty()
				.addListener((o, oldVal, newVal) -> {
					selectedFunctionChanged(newVal);
				});

		loopBoundList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);

	}

	private void selectedFunctionChanged(
			CElectionDescriptionFunction func) {
		currentDisplayedFunction = func;
		displayFunction(func);
	}

	private void populateFunctionList(
			CElectionDescription descr) {
		if (descr == null) {
			functionList.getItems().clear();
			addFunctionMenuButton.setDisable(true);
			removeFunctionButton.setDisable(true);
			selectedFunctionChanged(null);
		} else {
			addFunctionMenuButton.setDisable(false);
			removeFunctionButton.setDisable(false);

			ObservableList<CElectionDescriptionFunction> observableList = FXCollections
					.observableArrayList();

			for (CElectionDescriptionFunction f : descr
					.getFunctions()) {
				observableList.add(f);
			}
			functionList.setItems(observableList);
			functionList.getSelectionModel()
					.clearAndSelect(0);
		}
	}

	private void setLockedColor(CodeArea codeArea) {
		codeArea.setStyleClass(0, codeArea.getLength(),
				cssLockedClassName);
	}

	private void setUnlockedColor(CodeArea codeArea) {
		codeArea.setStyleClass(0, codeArea.getLength(),
				cssUnlockedClassName);
	}

	private void displayFunction(
			CElectionDescriptionFunction func) {
		electionCodeArea.clear();
		funcDeclArea.clear();
		closingBracketArea.clear();

		if (func == null) {
			electionCodeArea.setDisable(true);
			setLockedColor(funcDeclArea);
			setLockedColor(electionCodeArea);
			setLockedColor(closingBracketArea);
			displayLoopBounds(null);
		} else {
			String declText = func.getDeclCString(
					beastWorkspace.getCodeGenOptions());
			funcDeclArea.insertText(0, declText);
			setLockedColor(funcDeclArea);

			int amtLinesInDecl = declText
					.split("\n").length;

			AnchorPane.setTopAnchor(cEditorGUIElementVsp,
					currentTextSize * 1.3 * amtLinesInDecl);

			electionCodeArea.setDisable(false);
			electionCodeArea.insertText(0, func.getCode());

			String returnText = func.getReturnText(
					beastWorkspace.getCodeGenOptions());

			closingBracketArea.insertText(0, returnText);
			setLockedColor(closingBracketArea);

			displayLoopBounds(func.getExtractedLoops());
		}
	}

	private void displayLoopBounds(
			List<ExtractedCLoop> loops) {
		loopBoundList.getItems().clear();
		if (loops == null) {
			testLoopBoundButton.setDisable(true);
		} else {
			testLoopBoundButton.setDisable(false);
			loopBoundList.getItems().addAll(loops);
		}
	}

	private void disableControls() {
		electionCodeArea.setDisable(true);

	}

	public void loadElectionDescr(
			CElectionDescription descr) {
		this.currentDescr = descr;
		populateFunctionList(descr);
	}

	public void createNewDescr() {
		List<String> inputNames = List.of("name",
				"inputType", "outputType");
		TextField nameField = new TextField();

		ChoiceBox<String> inputTypeChoiceBox = new ChoiceBox<>();
		for (VotingInputTypes it : VotingInputTypes
				.values()) {
			inputTypeChoiceBox.getItems()
					.add(it.toString());
		}
		inputTypeChoiceBox.getSelectionModel()
				.selectFirst();

		ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<>();
		for (VotingOutputTypes ot : VotingOutputTypes
				.values()) {
			outputTypeChoiceBox.getItems()
					.add(ot.toString());
		}
		outputTypeChoiceBox.getSelectionModel()
				.selectFirst();

		List<Node> nodes = List.of(nameField,
				inputTypeChoiceBox, outputTypeChoiceBox);

		Optional<ButtonType> res = DialogHelper
				.generateDialog(inputNames, nodes)
				.showAndWait();
		if (res.isPresent()) {
			if (res.get().getButtonData().isCancelButton())
				return;
			String name = nameField.getText();
			VotingInputTypes inputType = VotingInputTypes
					.valueOf(inputTypeChoiceBox
							.getSelectionModel()
							.getSelectedItem());
			VotingOutputTypes outputType = VotingOutputTypes
					.valueOf(outputTypeChoiceBox
							.getSelectionModel()
							.getSelectedItem());
			CElectionDescription descr = new CElectionDescription(
					inputType, outputType, name);
			beastWorkspace.addElectionDescription(descr);
		}
	}

	private void letUserLoadDescr() {
		beastWorkspace.letUserLoadDescr();		
	}

	public void save() {
		beastWorkspace.saveDescr(currentDescr);
	}
	
	private void setFont(double font) {
		currentTextSize = font;
		String styleString = "-fx-font-size: " + font + "px;";
		electionCodeArea.setStyle(styleString);
		funcDeclArea.setStyle(styleString);
		closingBracketArea.setStyle(styleString);
		
		if(currentDescr == null) {
			return;
		}
		String declText = currentDisplayedFunction.getDeclCString(
				beastWorkspace.getCodeGenOptions());
		int amtLinesInDecl = declText
				.split("\n").length;
		AnchorPane.setTopAnchor(cEditorGUIElementVsp,
				currentTextSize * 1.3 * amtLinesInDecl);
	}

	public void applyOptions(CEditorOptions options) {
		setFont(options.getFontSize());
	}

}
