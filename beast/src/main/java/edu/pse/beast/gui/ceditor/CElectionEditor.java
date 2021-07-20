package edu.pse.beast.gui.ceditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

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

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.CElectionDescriptionFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.CelectionDescriptionFunctionType;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.c_electiondescription.function.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

public class CElectionEditor implements WorkspaceUpdateListener {
    private static final String CSS_RESOURCE = "/edu/pse/beast/ceditor.css";
    private static final String CSS_LOCKED_CLASS_NAME = "locked";
    private static final String CSS_UNLOCKED_CLASS_NAME = "unlocked";

    private ListView<CElectionDescriptionFunction> functions;
    private ListView<ExtractedCLoop> loopBounds;

    private CodeArea functionDeclarationArea;
    private CodeArea closingBracketArea;
    private CEditorCodeElement electionCodeArea;
    private ChoiceBox<CElectionDescription> openedElectionDescriptionChoiceBox;

    private CElectionDescription currentDescription;
    private CElectionDescriptionFunction currentDisplayedFunction;

    private BeastWorkspace beastWorkspace;

    private Button addElectionDescriptionButton;
    private Button loadElectionDescriptionButton;
    private Button editDescriptionButton;

    private MenuButton addFunctionMenuButton;
    private Button removeFunctionButton;

    private Button testLoopBoundsButton;

    private VirtualizedScrollPane<CEditorCodeElement> cEditorVirtualizedScrollPane;

    private String codeStyleSheet;

    private double currentTextSize;

    public CElectionEditor(final Stage primaryStage,
                           final VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp,
                           final Button addElectDescrButton,
                           final Button deleteElectDescrButton,
                           final Button loadElectDescrButton,
                           final Button saveElectDescrButton,
                           final Button editDescrButton,
                           final MenuButton addFuncMenuButton,
                           final Button removeFuncButton,
                           final Button genLoopBoundsButton,
                           final Button editLoopBoundsButton,
                           final CEditorCodeElement electCodeArea,
                           final CodeArea funcDeclArea,
                           final CodeArea closBrackArea,
                           final ListView<CElectionDescriptionFunction> functionList,
                           final ListView<ExtractedCLoop> loopBoundList,
                           final ChoiceBox<CElectionDescription> openedElectDescrChoiceBox,
                           final BeastWorkspace workspace) {
        codeStyleSheet = this.getClass().getResource(CSS_RESOURCE).toExternalForm();
        this.addElectionDescriptionButton = addElectDescrButton;
        this.loadElectionDescriptionButton = loadElectDescrButton;
        this.editDescriptionButton = editDescrButton;

        editDescrButton.setOnAction(e -> {
            editDescription();
        });

        setupNewElectionButtons();

        this.testLoopBoundsButton = genLoopBoundsButton;
        genLoopBoundsButton.setOnAction(e -> {
            workspace.findLoopBounds(currentDescription,
                    currentDisplayedFunction);
        });
        editLoopBoundsButton.setDisable(true);
        editLoopBoundsButton.setOnAction(e -> {
            editSelectedLoopbound();
        });

        this.functions = functionList;
        this.loopBounds = loopBoundList;
        loopBoundList.getSelectionModel().selectedItemProperty()
                .addListener((e, oldVal, newVal) -> {
                    if (newVal == null) {
                        editLoopBoundsButton.setDisable(true);
                        return;
                    }
                    final int line = newVal.getLine();
                    final int position =
                            electCodeArea.position(line - 1, newVal.getPosInLine()).toOffset();
                    electCodeArea.moveTo(position);
                    electCodeArea.selectLine();
                    electCodeArea.requestFollowCaret();
                    editLoopBoundsButton.setDisable(false);
                });

        this.electionCodeArea = electCodeArea;
        this.functionDeclarationArea = funcDeclArea;
        this.closingBracketArea = closBrackArea;
        this.cEditorVirtualizedScrollPane = cEditorGUIElementVsp;

        this.functionDeclarationArea.setEditable(false);
        this.closingBracketArea.setEditable(false);

        electCodeArea.getStylesheets().add(codeStyleSheet);
        funcDeclArea.getStylesheets().add(codeStyleSheet);
        closBrackArea.getStylesheets().add(codeStyleSheet);

        this.beastWorkspace = workspace;
        this.openedElectionDescriptionChoiceBox = openedElectDescrChoiceBox;

        this.addFunctionMenuButton = addFuncMenuButton;
        this.removeFunctionButton = removeFuncButton;
        setupFunctionButtons();

        initListViews();
        initOpenedDescrChoiceBox();
        handleWorkspaceUpdateGeneric();
        workspace.registerUpdateListener(this);

        electCodeArea.setChangeListener(text -> {
            workspace.updateCodeForDescrFunction(currentDescription,
                    currentDisplayedFunction, text);
        });

    }

    private void editDescription() {
        if (currentDescription == null) {
            return;
        }
        final TextField nameTextField = new TextField();
        nameTextField.setText(currentDescription.getName());
        final ChoiceBox<VotingInputTypes> inputTypeCB = new ChoiceBox<>();
        inputTypeCB.getItems().addAll(VotingInputTypes.values());
        inputTypeCB.getSelectionModel().select(currentDescription.getInputType());
        final ChoiceBox<VotingOutputTypes> outTypeCB = new ChoiceBox<>();
        outTypeCB.getItems().addAll(VotingOutputTypes.values());
        outTypeCB.getSelectionModel().select(currentDescription.getOutputType());

        final Optional<ButtonType> res =
                DialogHelper.generateDialog(List.of("name", "input type", "output type"),
                                            List.of(nameTextField, inputTypeCB, outTypeCB))
                .showAndWait();
        if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
            final String name = nameTextField.getText();
            final VotingInputTypes inType = inputTypeCB.getValue();
            final VotingOutputTypes outType = outTypeCB.getValue();
            if (!name.isBlank()) {
                beastWorkspace.editDescr(currentDescription, name, inType, outType);
            }
        }
    }

    private void editSelectedLoopbound() {
        final ExtractedCLoop selectedLoop =
                loopBounds.getSelectionModel().getSelectedItem();
        if (selectedLoop == null) {
            return;
        }
        final TextField manualBound = new TextField();
        manualBound.setVisible(false);
        final ChoiceBox<LoopBoundType> loopBoundChoiceBox = new ChoiceBox<>();
        loopBoundChoiceBox.getItems().addAll(LoopBoundType.values());

        loopBoundChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    if (n == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
                        manualBound.setVisible(true);
                        if (selectedLoop.getParsedLoopBoundType()
                                == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
                            manualBound.setText(String.valueOf(selectedLoop.getManualInteger()));
                        }
                    } else {
                        manualBound.setVisible(false);
                    }
                });
        loopBoundChoiceBox.getSelectionModel().select(selectedLoop.getParsedLoopBoundType());
        final Optional<ButtonType> res =
                DialogHelper.generateDialog(List.of("Loopbound type", "manual value"),
                                            List.of(loopBoundChoiceBox, manualBound))
                .showAndWait();

        if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
            final LoopBoundType selectedType =
                    loopBoundChoiceBox.getSelectionModel().getSelectedItem();
            if (selectedType == LoopBoundType.MANUALLY_ENTERED_INTEGER) {
                try {
                    final int bound = Integer.valueOf(manualBound.getText());
                    selectedLoop.setParsedLoopBoundType(selectedType);
                    selectedLoop.setManualInteger(bound);
                } catch (NumberFormatException e) {
                    return;
                }
            } else {
                selectedLoop.setParsedLoopBoundType(selectedType);
            }
        }
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

        final MenuItem addSimpleFuncMenuItem =
                new MenuItem(CelectionDescriptionFunctionType.SIMPLE.toString());
        /*
         * MenuItem addVotingFuncMenuItem = new MenuItem(
         * CelectionDescriptionFunctionType.VOTING.toString());
         */

        addSimpleFuncMenuItem.setOnAction(e -> addSimpleFunction());
        // addVotingFuncMenuItem.setOnAction(e -> addVotingFunction());

        addFunctionMenuButton.getItems().add(addSimpleFuncMenuItem);

        /*
         * addFunctionMenuButton.getItems()
         * .addAll(List.of(addSimpleFuncMenuItem, addVotingFuncMenuItem));
         */

        removeFunctionButton.setOnAction(e -> {
            removeSelectedFunction();
        });
    }

    private void removeSelectedFunction() {
        beastWorkspace.removeFunctionFromDescr(currentDescription,
                                               currentDisplayedFunction);
    }

    private void addVotingFunction() {
        final TextField nameField = new TextField();
        final Optional<ButtonType> res =
                DialogHelper
                .generateDialog(List.of("name"), List.of(nameField))
                .showAndWait();
        if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
            final String name = nameField.getText();
            beastWorkspace.addVotingSigFunctionToDescr(currentDescription, name);
        }
    }

    // TODO(Holger) make this nicer, add error checking for wrong var names etc
    private void addSimpleFunction() {
        final TextField nameField = new TextField();

        final ChoiceBox<CElectionSimpleTypes> returnTypeChoiceBox = new ChoiceBox<>();
        returnTypeChoiceBox.getItems().addAll(CElectionSimpleTypes.values());
        returnTypeChoiceBox.getSelectionModel().selectFirst();
        final Label argumentsLabel = new Label();

        final List<CElectionSimpleTypes> argTypes = new ArrayList<>();
        final List<String> argNames = new ArrayList<>();

        final TextField argsNameTextField = new TextField();
        final ChoiceBox<CElectionSimpleTypes> argsTypeChoiceBox =
                new ChoiceBox<CElectionSimpleTypes>();
        argsTypeChoiceBox.getItems().addAll(CElectionSimpleTypes.values());
        argsTypeChoiceBox.getSelectionModel().selectFirst();

        final Button addArgButton = new Button("add argument");
        final Button removeArgButton = new Button("remove Last Argument");

        final Consumer<Label> updateArgLabel = l -> {
            if (argNames.isEmpty()) {
                l.setVisible(false);
                return;
            }
            l.setVisible(true);
            String text = "";
            for (int i = 0; i < argNames.size(); ++i) {
                text += argTypes.get(i) + " " + argNames.get(i) + ", ";
            }
            l.setText(text);
        };

        addArgButton.setOnAction(e -> {
            argTypes.add(argsTypeChoiceBox.getSelectionModel().getSelectedItem());
            argNames.add(argsNameTextField.getText());
            updateArgLabel.accept(argumentsLabel);
        });

        removeArgButton.setOnAction(e -> {
            if (!argTypes.isEmpty()) {
                final int idx = argTypes.size() - 1;
                argTypes.remove(idx);
                argNames.remove(idx);
                updateArgLabel.accept(argumentsLabel);
            }
        });

        final Optional<ButtonType> res =
                DialogHelper.generateDialog(List.of("name", "return type"),
                                            List.of(nameField, returnTypeChoiceBox,
                                                    argsTypeChoiceBox, argsNameTextField,
                                                    addArgButton, removeArgButton,
                                                    argumentsLabel))
                .showAndWait();
        if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
            // TODO add error checking here to make sure the function data is
            // valid
            final CElectionSimpleTypes returnType =
                    returnTypeChoiceBox.getSelectionModel().getSelectedItem();
            final String name = nameField.getText();

            final SimpleTypeFunction function =
                    new SimpleTypeFunction(name, argTypes, argNames, returnType);
            beastWorkspace.addSimpleFunctionToDescr(currentDescription, function);
        }
    }

    private void selectedDescrChanged(final CElectionDescription descr) {
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
        for (final CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
            openedElectionDescriptionChoiceBox.getItems().add(descr);
        }
        // I think we need to do this here, because the selectLast does not
        // trigger
        // our selection changed handler if the items are empty?
        if (openedElectionDescriptionChoiceBox.getItems().size() == 0) {
            loadElectionDescr(null);
        } else {
            openedElectionDescriptionChoiceBox.getSelectionModel().selectLast();
        }
    }

    @Override
    public void handleDescrChangeAddedVotingSigFunction(final CElectionDescription descr,
                                                        final VotingSigFunction func) {
        if (descr.equals(currentDescription)) {
            functions.getItems().add(func);
        }
    }

    @Override
    public void handleDescrChangeRemovedFunction(final CElectionDescription descr,
                                                 final CElectionDescriptionFunction func) {
        if (descr == currentDescription) {
            functions.getItems().remove(func);
        }
    }

    @Override
    public void handleDescrChangeInOutName(final CElectionDescription descr) {
        handleWorkspaceUpdateGeneric();
        openedElectionDescriptionChoiceBox.getSelectionModel().select(descr);
    }

    @Override
    public void handleDescrChangeAddedSimpleFunction(final CElectionDescription descr,
                                                     final SimpleTypeFunction f) {
        if (descr.equals(currentDescription)) {
            functions.getItems().add(f);
        }
    }

    @Override
    public void handleDescrChangeUpdatedFunctionCode(final CElectionDescription descr,
                                                     final CElectionDescriptionFunction function,
                                                     final String code) {
        if (descr.equals(currentDescription)
                && function.equals(currentDisplayedFunction)) {
            displayLoopBounds(function.getExtractedLoops());
        }
    }

    @Override
    public void handleExtractedFunctionLoops(final CElectionDescription descr,
                                             final CElectionDescriptionFunction func) {
        if (descr.equals(currentDescription)
                && func.equals(currentDisplayedFunction)) {
            displayLoopBounds(func.getExtractedLoops());
        }
    }

    /* ===== other stuff ====== */
    private void initListViews() {
        functions.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        functions.getSelectionModel().selectedItemProperty()
                .addListener((o, oldVal, newVal) -> {
                    selectedFunctionChanged(newVal);
                });

        loopBounds.getSelectionModel()
                .setSelectionMode(SelectionMode.SINGLE);

    }

    private void selectedFunctionChanged(final CElectionDescriptionFunction func) {
        currentDisplayedFunction = func;
        displayFunction(func);
    }

    private void populateFunctionList(final CElectionDescription descr) {
        if (descr == null) {
            functions.getItems().clear();
            addFunctionMenuButton.setDisable(true);
            removeFunctionButton.setDisable(true);
            selectedFunctionChanged(null);
        } else {
            addFunctionMenuButton.setDisable(false);
            removeFunctionButton.setDisable(false);

            final ObservableList<CElectionDescriptionFunction> observableList =
                    FXCollections.observableArrayList();

            for (final CElectionDescriptionFunction f : descr.getFunctions()) {
                observableList.add(f);
            }
            functions.setItems(observableList);
            functions.getSelectionModel().clearAndSelect(0);
        }
    }

    private void setLockedColor(final CodeArea codeArea) {
        codeArea.setStyleClass(0, codeArea.getLength(), CSS_LOCKED_CLASS_NAME);
    }

    private void setUnlockedColor(final CodeArea codeArea) {
        codeArea.setStyleClass(0, codeArea.getLength(), CSS_UNLOCKED_CLASS_NAME);
    }

    private void displayFunction(final CElectionDescriptionFunction func) {
        electionCodeArea.clear();
        functionDeclarationArea.clear();
        closingBracketArea.clear();

        if (func == null) {
            electionCodeArea.setDisable(true);
            setLockedColor(functionDeclarationArea);
            setLockedColor(electionCodeArea);
            setLockedColor(closingBracketArea);
            displayLoopBounds(null);
        } else {
            final String declText =
                    func.getDeclCString(beastWorkspace.getCodeGenOptions());
            functionDeclarationArea.insertText(0, declText);
            setLockedColor(functionDeclarationArea);
            final int amtLinesInDecl = declText.split("\n").length;
            AnchorPane.setTopAnchor(cEditorVirtualizedScrollPane,
                                    currentTextSize * 1.3 * amtLinesInDecl);
            electionCodeArea.setDisable(false);
            electionCodeArea.insertText(0, func.getCode());
            final String returnText =
                    func.getReturnText(beastWorkspace.getCodeGenOptions());
            closingBracketArea.insertText(0, returnText);
            setLockedColor(closingBracketArea);
            displayLoopBounds(func.getExtractedLoops());
        }
    }

    private void displayLoopBounds(final List<ExtractedCLoop> loops) {
        loopBounds.getItems().clear();
        if (loops == null) {
            testLoopBoundsButton.setDisable(true);
        } else {
            testLoopBoundsButton.setDisable(false);
            loopBounds.getItems().addAll(loops);
        }
    }

    private void disableControls() {
        electionCodeArea.setDisable(true);

    }

    public void loadElectionDescr(final CElectionDescription descr) {
        this.currentDescription = descr;
        if (descr == null) {
            editDescriptionButton.setDisable(true);
        } else {
            editDescriptionButton.setDisable(false);
        }
        populateFunctionList(descr);
    }

    public void createNewDescr() {
        final List<String> inputNames = List.of("name", "inputType", "outputType");
        final TextField nameField = new TextField();

        final ChoiceBox<String> inputTypeChoiceBox = new ChoiceBox<>();
        for (final VotingInputTypes it : VotingInputTypes.values()) {
            inputTypeChoiceBox.getItems().add(it.toString());
        }
        inputTypeChoiceBox.getSelectionModel().selectFirst();

        final ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<>();
        for (final VotingOutputTypes ot : VotingOutputTypes.values()) {
            outputTypeChoiceBox.getItems().add(ot.toString());
        }
        outputTypeChoiceBox.getSelectionModel().selectFirst();
        final List<Node> nodes =
                List.of(nameField, inputTypeChoiceBox, outputTypeChoiceBox);
        final Optional<ButtonType> res =
                DialogHelper.generateDialog(inputNames, nodes).showAndWait();
        if (res.isPresent()) {
            if (res.get().getButtonData().isCancelButton()) {
                return;
            }
            final String name = nameField.getText();
            final VotingInputTypes inputType =
                    VotingInputTypes.valueOf(
                            inputTypeChoiceBox.getSelectionModel().getSelectedItem());
            final VotingOutputTypes outputType =
                    VotingOutputTypes.valueOf(
                            outputTypeChoiceBox.getSelectionModel().getSelectedItem());
            final CElectionDescription descr =
                    new CElectionDescription(inputType, outputType, name);
            beastWorkspace.addElectionDescription(descr);
        }
    }

    private void letUserLoadDescr() {
        beastWorkspace.letUserLoadDescr();
    }

    public void save() {
        beastWorkspace.saveDescr(currentDescription);
    }

    private void setFont(final double font) {
        currentTextSize = font;
        final String styleString = "-fx-font-size: " + font + "px;";
        electionCodeArea.setStyle(styleString);
        functionDeclarationArea.setStyle(styleString);
        closingBracketArea.setStyle(styleString);

        if (currentDescription == null) {
            return;
        }
        final String declText =
                currentDisplayedFunction.getDeclCString(beastWorkspace.getCodeGenOptions());
        final int amtLinesInDecl = declText.split("\n").length;
        AnchorPane.setTopAnchor(cEditorVirtualizedScrollPane,
                                currentTextSize * 1.3 * amtLinesInDecl);
    }

    public void applyOptions(final CEditorOptions options) {
        setFont(options.getFontSize());
    }
}
