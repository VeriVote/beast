package edu.pse.beast.gui.ceditor;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import org.apache.commons.lang3.NotImplementedException;
import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.electiondescription.function.CelectionDescriptionFunctionType;
import edu.pse.beast.api.electiondescription.function.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.FileDialogHelper;
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

public class CElectionEditor implements WorkspaceUpdateListener {
	private final String cssResource = "/edu/pse/beast/ceditor.css";
	private final String cssLockedClassName = "locked";

	private ListView<CElectionDescriptionFunction> functionList;
	private ListView<LoopBound> loopBoundList;

	private CodeArea funcDeclArea;
	private CodeArea closingBracketArea;
	private CEditorCodeElement electionCodeArea;
	private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;

	private CElectionDescription currentDescr;
	private CElectionDescriptionFunction currentDisplayedFunction;

	private BeastWorkspace beastWorkspace;

	private Stage primaryStage;
	private MenuButton addFunctionMenuButton;
	private VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp;
	
	
	public CElectionEditor(Stage primaryStage, 
			VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp,
			MenuButton addFunctionMenuButton,
			CEditorCodeElement electionCodeArea, 
			CodeArea funcDeclArea,
			CodeArea closingBracketArea,
			ListView<CElectionDescriptionFunction> functionList,
			ListView<LoopBound> loopBoundList,
			ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox,
			BeastWorkspace beastWorkspace) {
		final String stylesheet = this.getClass().getResource(cssResource)
				.toExternalForm();
		
		this.primaryStage = primaryStage;
		this.addFunctionMenuButton = addFunctionMenuButton;

		this.functionList = functionList;
		this.loopBoundList = loopBoundList;

		this.electionCodeArea = electionCodeArea;
		this.funcDeclArea = funcDeclArea;
		this.closingBracketArea = closingBracketArea;
		this.cEditorGUIElementVsp = cEditorGUIElementVsp;

		this.funcDeclArea.setEditable(false);
		this.closingBracketArea.setEditable(false);

		electionCodeArea.getStylesheets().add(stylesheet);
		funcDeclArea.getStylesheets().add(stylesheet);
		closingBracketArea.getStylesheets().add(stylesheet);

		this.beastWorkspace = beastWorkspace;
		this.openedElectionDescriptionChoiceBox = openedElectionDescriptionChoiceBox;

		addFunctionMenuButton.setText("Add Function");
		addFunctionMenuButton.getItems().clear();

		MenuItem addSimpleFuncMenuItem = new MenuItem(
				CelectionDescriptionFunctionType.SIMPLE.toString());
		MenuItem addVotingFuncMenuItem = new MenuItem(
				CelectionDescriptionFunctionType.VOTING.toString());

		addSimpleFuncMenuItem.setOnAction(e -> addSimpleFunction());
		addVotingFuncMenuItem.setOnAction(e -> addVotingFunction());

		addFunctionMenuButton.getItems()
				.addAll(List.of(addSimpleFuncMenuItem, addVotingFuncMenuItem));

		initListViews();
		initOpenedDescrChoiceBox();
		handleWorkspaceUpdateGeneric();
		beastWorkspace.registerUpdateListener(this);

		electionCodeArea.setChangeListener((text) -> {
			beastWorkspace.updateCodeForDescrFunction(currentDescr,
					currentDisplayedFunction, text);
		});
	}

	private void addVotingFunction() {
		TextField nameField = new TextField();
		Optional<ButtonType> res = DialogHelper
				.generateDialog(List.of("name"), List.of(nameField))
				.showAndWait();
		if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
			String name = nameField.getText();
			beastWorkspace.addVotingSigFunctionToDescr(currentDescr, name);
		}
	}

	// TODO(Holger) make this nicer, add error checking for wrong var names etc
	private void addSimpleFunction() {
		TextField nameField = new TextField();

		ChoiceBox<CElectionSimpleTypes> returnTypeChoiceBox = new ChoiceBox<>();
		returnTypeChoiceBox.getItems().addAll(CElectionSimpleTypes.values());
		returnTypeChoiceBox.getSelectionModel().selectFirst();
		Label argumentsLabel = new Label();

		List<CElectionSimpleTypes> argTypes = new ArrayList<>();
		List<String> argNames = new ArrayList<>();

		TextField argsNameTextField = new TextField();
		ChoiceBox<CElectionSimpleTypes> argsTypeChoiceBox = new ChoiceBox();
		argsTypeChoiceBox.getItems().addAll(CElectionSimpleTypes.values());
		argsTypeChoiceBox.getSelectionModel().selectFirst();

		Button addArgButton = new Button("add argument");
		Button removeArgButton = new Button("remove Last Argument");

		Consumer<Label> updateArgLabel = l -> {
			String text = "";
			for (int i = 0; i < argNames.size(); ++i) {
				text += argTypes.get(i) + " " + argNames.get(i) + ", ";
			}
			l.setText(text);
		};

		addArgButton.setOnAction(e -> {
			if(!nameField.getText().isEmpty()) {
				argTypes.add(
						argsTypeChoiceBox.getSelectionModel().getSelectedItem());
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

		DialogHelper.generateDialog(List.of("name", "return type"),
				List.of(nameField, returnTypeChoiceBox, argsTypeChoiceBox,
						argsNameTextField, addArgButton, removeArgButton))
				.showAndWait();
	}

	private void selectedDescrChanged(CElectionDescription descr) {
		if (descr == null)
			return;
		loadElectionDescr(descr);
	}

	private void initOpenedDescrChoiceBox() {
		openedElectionDescriptionChoiceBox.getSelectionModel()
				.selectedItemProperty()
				.addListener((observable, oldValue, newValue) -> {
					selectedDescrChanged(newValue);
				});
	}

	/* ========== handle workspace updates =========== */

	// TODO(Holger) This is where we would add undo/redo for higher level
	// changes such as adding/removing functions etc
	public void handleWorkspaceUpdateGeneric() {
		openedElectionDescriptionChoiceBox.getItems().clear();
		for (CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
			openedElectionDescriptionChoiceBox.getItems().add(descr);
		}
		openedElectionDescriptionChoiceBox.getSelectionModel().selectLast();
	}

	@Override
	public void handleDescrChangeAddedVotingSigFunction(
			CElectionDescription descr, VotingSigFunction func) {
		if (descr.equals(currentDescr)) {
			functionList.getItems().add(func);
		}
	}

	@Override
	public void handleDescrChangeRemovedFunction(CElectionDescription descr,
			CElectionDescriptionFunction func) {
		if (descr == currentDescr) {
			functionList.getItems().remove(func);
		}
	}

	/* ===== other stuff ====== */
	private void initListViews() {
		functionList.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
		functionList.getSelectionModel().selectedItemProperty()
				.addListener((o, oldVal, newVal) -> {
					selectedFunctionChanged(newVal);
				});

		loopBoundList.getSelectionModel()
				.setSelectionMode(SelectionMode.SINGLE);

	}

	private void selectedFunctionChanged(CElectionDescriptionFunction func) {
		displayFunction(func);
		currentDisplayedFunction = func;
	}

	private void populateFunctionList(CElectionDescription descr) {
		ObservableList<CElectionDescriptionFunction> observableList = FXCollections
				.observableArrayList();

		observableList.add(descr.getVotingFunction());

		for (CElectionDescriptionFunction f : descr.getFunctions()) {
			observableList.add(f);
		}
		functionList.setItems(observableList);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	private void populateLoopBoundList(List<LoopBound> loopbounds) {
		ObservableList<LoopBound> observableList = FXCollections
				.observableArrayList();
		for (LoopBound b : loopbounds) {
			observableList.add(b);
		}
		loopBoundList.setItems(observableList);
	}

	private void setLockedColor() {
		funcDeclArea.setStyleClass(0, funcDeclArea.getLength(),
				cssLockedClassName);
		closingBracketArea.setStyleClass(0, closingBracketArea.getLength(),
				cssLockedClassName);
	}

	private void displayFunction(CElectionDescriptionFunction func) {
		electionCodeArea.clear();
		funcDeclArea.clear();
		closingBracketArea.clear();

		String declText = func.getDeclCString();
		funcDeclArea.insertText(0, declText);
		int amtLinesInDecl = declText.split("\n").length;
		
		//TODO(Holger) move this into an CeditorOptions Object
		double currentTextSize = 20;
		
		AnchorPane.setTopAnchor(
				cEditorGUIElementVsp, 
				currentTextSize * amtLinesInDecl);
		

		electionCodeArea.insertText(0, func.getCode());

		closingBracketArea.insertText(0, "}");
		setLockedColor();
	}

	public void loadElectionDescr(CElectionDescription descr) {
		this.currentDescr = descr;
		populateFunctionList(descr);
		functionList.getSelectionModel().clearAndSelect(0);
	}

	public void addLoopBound() {
		throw new NotImplementedException();
	}

	public void removeLoopBound() {
		throw new NotImplementedException();
	}

	public void removeFunction() {
		beastWorkspace.removeFunctionFromDescr(currentDescr,
				currentDisplayedFunction);
	}

	public void createNewDescr() {
		List<String> inputNames = List.of("name", "inputType", "outputType");
		TextField nameField = new TextField();

		ChoiceBox<String> inputTypeChoiceBox = new ChoiceBox<>();
		for (VotingInputTypes it : VotingInputTypes.values()) {
			inputTypeChoiceBox.getItems().add(it.toString());
		}
		inputTypeChoiceBox.getSelectionModel().selectFirst();

		ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<>();
		for (VotingOutputTypes ot : VotingOutputTypes.values()) {
			outputTypeChoiceBox.getItems().add(ot.toString());
		}
		outputTypeChoiceBox.getSelectionModel().selectFirst();

		List<Node> nodes = List.of(nameField, inputTypeChoiceBox,
				outputTypeChoiceBox);

		Optional<ButtonType> res = DialogHelper
				.generateDialog(inputNames, nodes).showAndWait();
		if (res.isPresent()) {
			if (res.get().getButtonData().isCancelButton())
				return;
			String name = nameField.getText();
			VotingInputTypes inputType = VotingInputTypes.valueOf(
					inputTypeChoiceBox.getSelectionModel().getSelectedItem());
			VotingOutputTypes outputType = VotingOutputTypes.valueOf(
					outputTypeChoiceBox.getSelectionModel().getSelectedItem());
			CElectionDescription descr = new CElectionDescription(inputType,
					outputType, name);
			beastWorkspace.addElectionDescription(descr);
		}
	}

	public void letUserLoad() throws NotImplementedException, IOException {
		CElectionDescription descr = FileDialogHelper
				.letUserLoadElectionDescription(beastWorkspace.getBaseDir(),
						primaryStage);
		if (descr != null)
			beastWorkspace.addElectionDescription(descr);
	}

	public void save() {
		beastWorkspace.saveDescr(currentDescr);
	}

}
