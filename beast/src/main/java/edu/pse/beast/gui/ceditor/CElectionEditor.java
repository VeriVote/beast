package edu.pse.beast.gui.ceditor;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;

import javafx.beans.binding.Bindings;
import javafx.beans.binding.BooleanBinding;
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

import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.CElectionSimpleType;
import edu.pse.beast.api.method.VotingInputType;
import edu.pse.beast.api.method.VotingOutputType;
import edu.pse.beast.api.method.function.CElectionDescriptionFunction;
import edu.pse.beast.api.method.function.CelectionDescriptionFunctionType;
import edu.pse.beast.api.method.function.SimpleTypeFunction;
import edu.pse.beast.api.method.function.VotingSigFunction;
import edu.pse.beast.gui.DialogHelper;
import edu.pse.beast.gui.options.ceditor.CEditorOptions;
import edu.pse.beast.gui.workspace.BeastWorkspace;
import edu.pse.beast.gui.workspace.WorkspaceUpdateListener;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CElectionEditor implements WorkspaceUpdateListener {
    private static final double LINE_SCALE = 1.3;
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String COMMA = ", ";
    private static final String UNDERSCORE = "_";
    private static final String LINE_BREAK = "\n";

    private static final String ADD_FUNCTION = "Add Function";
    private static final String ADD_ARGUMENT = "Add Argument";
    private static final String REMOVE_LAST = "Remove Last Argument";

    private static final String EDIT_ELECTION_DIALOG = "Edit Election";
    private static final String EDIT_LOOP_BOUND_DIALOG = "Edit Loop Bound";
    private static final String REMOVE_FUNCTION_DIALOG = "Remove Function";
    private static final String ADD_SIMPLE_FUNCTION_DIALOG = "Add Simple Function";
    private static final String CREATE_ELECTION_DIALOG = "Create New Election";

    private static final String RETURN_TYPE = "Return Type:";
    private static final String NAME = "Name:";
    private static final String INPUT_TYPE = "Input Type";
    private static final String OUTPUT_TYPE = "Output Type";
    private static final String LOOPBOUND_TYPE = "Loop Bound Type";
    private static final String MANUAL_VALUE = "Manual Value";

    private static final String CSS_RESOURCE = "/edu/pse/beast/gui/ceditor/ceditor.css";
    private static final String CSS_LOCKED_CLASS_NAME = "locked";
    private static final String CSS_UNLOCKED_CLASS_NAME = "unlocked";
    private static final String FX_FONT_SIZE = "-fx-font-size: ";
    private static final String PX = "px;";

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

    private Button computeLoopBoundsButton;

    private VirtualizedScrollPane<CEditorCodeElement> cEditorVirtualizedScrollPane;

    private String codeStyleSheet;

    private double currentTextSize;

    public CElectionEditor(final Stage primaryStage,
                           final VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp,
                           final ElectionDescriptionButtons electDescrButtons,
                           final FunctionEditor functionEditor,
                           final LoopBoundEditor loopBoundEditor,
                           final CodeAreas codeAreas,
                           // final CEditorCodeElement electCodeArea, final CodeArea closBrackArea,
                           final BeastWorkspace workspace) {
        codeStyleSheet = this.getClass().getResource(CSS_RESOURCE).toExternalForm();
        this.addElectionDescriptionButton = electDescrButtons.add;
        this.loadElectionDescriptionButton = electDescrButtons.load;
        this.editDescriptionButton = electDescrButtons.edit;

        this.editDescriptionButton.setOnAction(e -> {
            editDescription();
        });
        setupNewElectionButtons();

        this.computeLoopBoundsButton = loopBoundEditor.generateButton;
        loopBoundEditor.generateButton.setOnAction(e -> {
            workspace.findLoopBounds(currentDescription, currentDisplayedFunction);
        });
        loopBoundEditor.editButton.setDisable(true);
        loopBoundEditor.editButton.setOnAction(e -> {
            editSelectedLoopbound();
        });

        this.functions = functionEditor.list;
        this.loopBounds = loopBoundEditor.list;
        loopBoundEditor.list.getSelectionModel().selectedItemProperty()
                .addListener((e, oldVal, newVal) -> {
                    loopBoundEditor.editButton.setDisable(newVal == null);
                    if (newVal == null) {
                        return;
                    }
                    final int position =
                            codeAreas.elect.position(newVal.getLine() - 1,
                                                     newVal.getPosInLine())
                            .toOffset();
                    codeAreas.elect.moveTo(position);
                    codeAreas.elect.selectLine();
                    codeAreas.elect.requestFollowCaret();
                });
        this.electionCodeArea = codeAreas.elect;
        this.functionDeclarationArea = functionEditor.declarationArea;
        this.closingBracketArea = codeAreas.closeBrackets;
        this.cEditorVirtualizedScrollPane = cEditorGUIElementVsp;
        this.functionDeclarationArea.setEditable(false);
        this.closingBracketArea.setEditable(false);

        codeAreas.elect.getStylesheets().add(codeStyleSheet);
        functionEditor.declarationArea.getStylesheets().add(codeStyleSheet);
        codeAreas.closeBrackets.getStylesheets().add(codeStyleSheet);

        this.beastWorkspace = workspace;
        this.openedElectionDescriptionChoiceBox = electDescrButtons.choiceBox;
        this.addFunctionMenuButton = functionEditor.addButton;
        this.removeFunctionButton = functionEditor.removeButton;
        setupFunctionButtons();

        initListViews();
        initOpenedDescrChoiceBox();
        handleWorkspaceUpdateGeneric();
        workspace.registerUpdateListener(this);
        codeAreas.elect.setChangeListener(text -> {
            workspace.updateCodeForDescrFunction(currentDescription,
                    currentDisplayedFunction, text);
        });
    }

    private void editDescription() {
        if (currentDescription == null) {
            return;
        }
        final TextField nameTextField = new TextField();
        final BooleanBinding nameTextBlank = Bindings.createBooleanBinding(() -> {
            return nameTextField.getText().isBlank();
        }, nameTextField.textProperty());
        nameTextField.setText(currentDescription.getName());
        final ChoiceBox<VotingInputType> inputTypeCB = new ChoiceBox<VotingInputType>();
        inputTypeCB.getItems().addAll(VotingInputType.values());
        inputTypeCB.getSelectionModel().select(currentDescription.getInputType());
        final ChoiceBox<VotingOutputType> outTypeCB = new ChoiceBox<VotingOutputType>();
        outTypeCB.getItems().addAll(VotingOutputType.values());
        outTypeCB.getSelectionModel().select(currentDescription.getOutputType());

        final Optional<ButtonType> res =
                DialogHelper.generateDialog(EDIT_ELECTION_DIALOG, nameTextBlank,
                                            List.of(NAME, INPUT_TYPE, OUTPUT_TYPE),
                                            List.of(nameTextField, inputTypeCB, outTypeCB))
                .showAndWait();
        if (res.isPresent()
                && !res.get().getButtonData().isCancelButton()
                && !nameTextField.getText().isBlank()) {
            final String name = nameTextField.getText();
            final VotingInputType inType = inputTypeCB.getValue();
            final VotingOutputType outType = outTypeCB.getValue();
            beastWorkspace.editDescr(currentDescription, name, inType, outType);
        }
    }

    private void editSelectedLoopbound() {
        final ExtractedCLoop selectedLoop =
                loopBounds.getSelectionModel().getSelectedItem();
        if (selectedLoop != null) {
            final TextField manualBound = new TextField();
            final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
                return manualBound.getText().isBlank();
            }, manualBound.textProperty());
            manualBound.setVisible(false);
            final ChoiceBox<LoopBoundType> loopBoundChoiceBox = new ChoiceBox<LoopBoundType>();
            loopBoundChoiceBox.getItems().addAll(LoopBoundType.values());

            loopBoundChoiceBox.getSelectionModel().selectedItemProperty()
                .addListener((ob, o, n) -> {
                    final LoopBoundType type = LoopBoundType.MANUALLY_ENTERED;
                    manualBound.setVisible(n == type);
                    if (n == type && selectedLoop.getParsedLoopBoundType() == type) {
                        manualBound.setText(String.valueOf(selectedLoop.getManualInteger()));
                    }
                });
            loopBoundChoiceBox.getSelectionModel().select(selectedLoop.getParsedLoopBoundType());
            final Optional<ButtonType> res =
                    DialogHelper.generateDialog(EDIT_LOOP_BOUND_DIALOG, textBlank,
                                                List.of(LOOPBOUND_TYPE, MANUAL_VALUE),
                                                List.of(loopBoundChoiceBox, manualBound))
                    .showAndWait();

            if (res.isPresent() && !res.get().getButtonData().isCancelButton()) {
                final LoopBoundType selectedType =
                        loopBoundChoiceBox.getSelectionModel().getSelectedItem();
                if (selectedType == LoopBoundType.MANUALLY_ENTERED) {
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
    }

    private void setupNewElectionButtons() {
        addElectionDescriptionButton.setOnAction(e -> {
            createNewDescription();
        });
        loadElectionDescriptionButton.setOnAction(e -> {
            letUserLoadDescr();
        });
    }

    private void setupFunctionButtons() {
        addFunctionMenuButton.setText(ADD_FUNCTION);
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
        final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
            return nameField.getText().isBlank();
        }, nameField.textProperty());
        final Optional<ButtonType> res =
                DialogHelper
                .generateDialog(REMOVE_FUNCTION_DIALOG, textBlank, List.of(NAME),
                                List.of(nameField)).showAndWait();
        if (res.isPresent()
                && !res.get().getButtonData().isCancelButton()
                && !nameField.getText().isBlank()) {
            final String name = nameField.getText();
            beastWorkspace.addVotingSigFunctionToDescr(currentDescription, name);
        }
    }

    private static void setLabelsAndActions(final FunctionArgumentEditor fae,
                                            final List<CElectionSimpleType> argTypes,
                                            final List<String> argNames,
                                            final Button addArgButton,
                                            final Button removeArgButton) {
        final Consumer<Label> updateArgLabel = l -> {
            l.setVisible(!argNames.isEmpty());
            if (argNames.isEmpty()) {
                return;
            }
            String text = EMPTY;
            for (int i = 0; i < argNames.size(); ++i) {
                text += argTypes.get(i) + BLANK + argNames.get(i) + COMMA;
            }
            l.setText(text);
        };

        addArgButton.setOnAction(e -> {
            argTypes.add(fae.typeChoiceBox.getSelectionModel().getSelectedItem());
            argNames.add(fae.nameTextField.getText());
            updateArgLabel.accept(fae.label);
        });
        addArgButton.disableProperty().bind(fae.nameBlank);
        removeArgButton.setOnAction(e -> {
            if (!argTypes.isEmpty()) {
                final int idx = argTypes.size() - 1;
                argTypes.remove(idx);
                argNames.remove(idx);
                updateArgLabel.accept(fae.label);
            }
        });
    }

    // TODO(Holger) make this nicer, add error checking for wrong var names etc
    private void addSimpleFunction() {
        final TextField nameField = new TextField();
        final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
            return nameField.getText().isBlank();
        }, nameField.textProperty());
        final ChoiceBox<CElectionSimpleType> returnTypeChoiceBox =
                new ChoiceBox<CElectionSimpleType>();
        returnTypeChoiceBox.getItems().addAll(CElectionSimpleType.values());
        returnTypeChoiceBox.getSelectionModel().selectFirst();
        final Label argumentsLabel = new Label();

        final List<CElectionSimpleType> argTypes = new ArrayList<CElectionSimpleType>();
        final List<String> argNames = new ArrayList<String>();
        final TextField argsNameTextField = new TextField();
        final BooleanBinding argNameBlank = Bindings.createBooleanBinding(() -> {
            return argsNameTextField.getText().isBlank();
        }, argsNameTextField.textProperty());
        final ChoiceBox<CElectionSimpleType> argsTypeChoiceBox =
                new ChoiceBox<CElectionSimpleType>();
        argsTypeChoiceBox.getItems().addAll(CElectionSimpleType.values());
        argsTypeChoiceBox.getSelectionModel().selectFirst();

        final Button addArgButton = new Button(ADD_ARGUMENT);
        final Button removeArgButton = new Button(REMOVE_LAST);

        setLabelsAndActions(
                new FunctionArgumentEditor(argsTypeChoiceBox, argsNameTextField,
                                           argumentsLabel, argNameBlank),
                argTypes, argNames, addArgButton, removeArgButton
        );

        final Optional<ButtonType> res =
                DialogHelper.generateDialog(ADD_SIMPLE_FUNCTION_DIALOG, textBlank,
                                            List.of(NAME, RETURN_TYPE),
                                            List.of(nameField, returnTypeChoiceBox,
                                                    argsTypeChoiceBox, argsNameTextField,
                                                    addArgButton, removeArgButton,
                                                    argumentsLabel))
                .showAndWait();
        if (res.isPresent()
                && !res.get().getButtonData().isCancelButton()
                && !nameField.getText().isBlank()) {
            // TODO add error checking here to make sure the function data is
            // valid
            final CElectionSimpleType returnType =
                    returnTypeChoiceBox.getSelectionModel().getSelectedItem();
            final String name = nameField.getText();
            final SimpleTypeFunction function =
                    new SimpleTypeFunction(name, argTypes, argNames, returnType);
            beastWorkspace.addSimpleFunctionToDescr(currentDescription, function);
        }
    }

    private void selectedDescrChanged(final CElectionDescription descr) {
        loadElectionDescription(descr);
    }

    private void initOpenedDescrChoiceBox() {
        openedElectionDescriptionChoiceBox.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    selectedDescrChanged(newValue);
                });
    }

    /* ========== Handle Workspace Updates =========== */

    // TODO(Holger) This is where we would add undo/redo for higher level
    // changes such as adding/removing functions etc
    public final void handleWorkspaceUpdateGeneric() {
        openedElectionDescriptionChoiceBox.getItems().clear();
        for (final CElectionDescription descr : beastWorkspace.getLoadedDescrs()) {
            openedElectionDescriptionChoiceBox.getItems().add(descr);
        }
        // I think we need to do this here, because the selectLast does not
        // trigger our selection changed handler if the items are empty?
        if (openedElectionDescriptionChoiceBox.getItems().size() == 0) {
            loadElectionDescription(null);
        } else {
            openedElectionDescriptionChoiceBox.getSelectionModel().selectLast();
        }
    }

    @Override
    public final void handleDescrChangeAddedVotingSigFunction(final CElectionDescription descr,
                                                              final VotingSigFunction func) {
        if (descr.equals(currentDescription)) {
            functions.getItems().add(func);
        }
    }

    @Override
    public final void handleDescrChangeRemovedFunction(final CElectionDescription descr,
                                                       final CElectionDescriptionFunction func) {
        if (descr == currentDescription) {
            functions.getItems().remove(func);
        }
    }

    @Override
    public final void handleDescrChangeInOutName(final CElectionDescription descr) {
        handleWorkspaceUpdateGeneric();
        openedElectionDescriptionChoiceBox.getSelectionModel().select(descr);
    }

    @Override
    public final void handleDescrChangeAddedSimpleFunction(final CElectionDescription descr,
                                                     final SimpleTypeFunction f) {
        if (descr.equals(currentDescription)) {
            functions.getItems().add(f);
        }
    }

    @Override
    public final void
        handleDescrChangeUpdatedFunctionCode(final CElectionDescription descr,
                                             final CElectionDescriptionFunction function,
                                             final String code) {
        if (descr.equals(currentDescription)
                && function.equals(currentDisplayedFunction)) {
            displayLoopBounds(function.getExtractedLoops());
        }
    }

    @Override
    public final void handleExtractedFunctionLoops(final CElectionDescription descr,
                                                   final CElectionDescriptionFunction func) {
        if (descr.equals(currentDescription)
                && func.equals(currentDisplayedFunction)) {
            displayLoopBounds(func.getExtractedLoops());
        }
    }

    /* ===== Other Stuff ====== */

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
        addFunctionMenuButton.setDisable(descr == null);
        removeFunctionButton.setDisable(descr == null);
        if (descr == null) {
            functions.getItems().clear();
            selectedFunctionChanged(null);
        } else {
            final ObservableList<CElectionDescriptionFunction> observableList =
                    FXCollections.observableArrayList();

            for (final CElectionDescriptionFunction f : descr.getFunctions()) {
                observableList.add(f);
            }
            functions.setItems(observableList);
            functions.getSelectionModel().clearAndSelect(0);
        }
    }

    private static void setLockedColor(final CodeArea codeArea) {
        codeArea.setStyleClass(0, codeArea.getLength(), CSS_LOCKED_CLASS_NAME);
    }

    private static void setUnlockedColor(final CodeArea codeArea) {
        codeArea.setStyleClass(0, codeArea.getLength(), CSS_UNLOCKED_CLASS_NAME);
    }

    private void displayFunction(final CElectionDescriptionFunction func) {
        electionCodeArea.clear();
        functionDeclarationArea.clear();
        closingBracketArea.clear();
        electionCodeArea.setDisable(func == null);

        if (func == null) {
            setLockedColor(functionDeclarationArea);
            setLockedColor(electionCodeArea);
        } else {
            final String declText =
                    func.getDeclCString(beastWorkspace.getCodeGenOptions());
            functionDeclarationArea.insertText(0, declText);
            setLockedColor(functionDeclarationArea);
            final int amtLinesInDecl = declText.split(LINE_BREAK).length;
            AnchorPane.setTopAnchor(cEditorVirtualizedScrollPane,
                                    currentTextSize * LINE_SCALE * amtLinesInDecl);
            electionCodeArea.insertText(0, func.getCode());
            final String returnText =
                    func.getReturnText(beastWorkspace.getCodeGenOptions());
            closingBracketArea.insertText(0, returnText);
        }
        setLockedColor(closingBracketArea);
        displayLoopBounds(func != null ? func.getExtractedLoops() : null);
    }

    private void displayLoopBounds(final List<ExtractedCLoop> loops) {
        loopBounds.getItems().clear();
        computeLoopBoundsButton.setDisable(loops == null);
        if (loops != null) {
            loopBounds.getItems().addAll(loops);
        }
    }

    public final void loadElectionDescription(final CElectionDescription descr) {
        this.currentDescription = descr;
        editDescriptionButton.setDisable(descr == null);
        populateFunctionList(descr);
    }

    public final void createNewDescription() {
        final List<String> inputNames = List.of(NAME, INPUT_TYPE, OUTPUT_TYPE);
        final TextField nameField = new TextField();
        final BooleanBinding textBlank = Bindings.createBooleanBinding(() -> {
            return nameField.getText().isBlank();
        }, nameField.textProperty());

        final ChoiceBox<String> inputTypeChoiceBox = new ChoiceBox<String>();
        for (final VotingInputType it : VotingInputType.values()) {
            inputTypeChoiceBox.getItems().add(it.toString());
        }
        inputTypeChoiceBox.getSelectionModel().selectFirst();

        final ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<String>();
        for (final VotingOutputType ot : VotingOutputType.values()) {
            outputTypeChoiceBox.getItems().add(ot.toString());
        }
        outputTypeChoiceBox.getSelectionModel().selectFirst();
        final List<Node> nodes =
                List.of(nameField, inputTypeChoiceBox, outputTypeChoiceBox);
        final Optional<ButtonType> res =
                DialogHelper.generateDialog(CREATE_ELECTION_DIALOG, textBlank, inputNames, nodes)
                .showAndWait();
        if (res.isPresent()
                && (res.get().getButtonData().isCancelButton()
                        || !nameField.getText().isBlank())) {
            if (res.get().getButtonData().isCancelButton()) {
                return;
            }
            final String name = nameField.getText();
            final VotingInputType inputType =
                    VotingInputType.valueOf(
                            inputTypeChoiceBox.getSelectionModel().getSelectedItem()
                            .toUpperCase().replaceAll(BLANK, UNDERSCORE));
            final VotingOutputType outputType =
                    VotingOutputType.valueOf(
                            outputTypeChoiceBox.getSelectionModel().getSelectedItem()
                            .toUpperCase().replaceAll(BLANK, UNDERSCORE));
            final CElectionDescription descr =
                    new CElectionDescription(inputType, outputType, name);
            beastWorkspace.addElectionDescription(descr);
        }
    }

    private void letUserLoadDescr() {
        beastWorkspace.letUserLoadDescr();
    }

    public final void save() {
        if (currentDescription != null) {
            beastWorkspace.saveDescr(currentDescription);
        }
    }

    private void setFont(final double font) {
        currentTextSize = font;
        final String styleString = FX_FONT_SIZE + font + PX;
        electionCodeArea.setStyle(styleString);
        functionDeclarationArea.setStyle(styleString);
        closingBracketArea.setStyle(styleString);

        if (currentDescription == null) {
            return;
        }
        final String declText =
                currentDisplayedFunction.getDeclCString(beastWorkspace.getCodeGenOptions());
        final int amtLinesInDecl = declText.split(LINE_BREAK).length;
        AnchorPane.setTopAnchor(cEditorVirtualizedScrollPane,
                                currentTextSize * LINE_SCALE * amtLinesInDecl);
    }

    public final void applyOptions(final CEditorOptions options) {
        setFont(options.getFontSize());
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class ElectionDescriptionButtons {
        final Button add;
        final Button delete;
        final Button load;
        final Button save;
        final Button edit;
        final ChoiceBox<CElectionDescription> choiceBox;

        public ElectionDescriptionButtons(final Button addButton,
                                          final Button deleteButton,
                                          final Button loadButton,
                                          final Button saveButton,
                                          final Button editButton,
                                          final ChoiceBox<CElectionDescription> openedChoice) {
            this.add = addButton;
            this.delete = deleteButton;
            this.load = loadButton;
            this.save = saveButton;
            this.edit = editButton;
            this.choiceBox = openedChoice;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class FunctionEditor {
        final MenuButton addButton;
        final Button removeButton;
        final CodeArea declarationArea;
        final ListView<CElectionDescriptionFunction> list;

        public FunctionEditor(final MenuButton addFuncMenuButton,
                              final Button removeFuncButton,
                              final CodeArea funcDeclArea,
                              final ListView<CElectionDescriptionFunction> functionList) {
            this.addButton = addFuncMenuButton;
            this.removeButton = removeFuncButton;
            this.declarationArea = funcDeclArea;
            this.list = functionList;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class LoopBoundEditor {
        final Button generateButton;
        final Button editButton;
        final ListView<ExtractedCLoop> list;

        public LoopBoundEditor(final Button genLoopBoundsButton,
                               final Button editLoopBoundsButton,
                               final ListView<ExtractedCLoop> loopBoundList) {
            this.generateButton = genLoopBoundsButton;
            this.editButton = editLoopBoundsButton;
            this.list = loopBoundList;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static final class CodeAreas {
        final CEditorCodeElement elect;
        final CodeArea closeBrackets;

        public CodeAreas(final CEditorCodeElement electCodeArea,
                         final CodeArea closBrackArea) {
            this.elect = electCodeArea;
            this.closeBrackets = closBrackArea;
        }
    }

    /**
     * TODO: Write documentation.
     *
     * @author Michael Kirsten
     *
     */
    public static final class FunctionArgumentEditor {
        final ChoiceBox<CElectionSimpleType> typeChoiceBox;
        final TextField nameTextField;
        final Label label;
        final BooleanBinding nameBlank;

        public FunctionArgumentEditor(final ChoiceBox<CElectionSimpleType> argsTypeChoiceBox,
                                      final TextField argsNameTextField,
                                      final Label argumentsLabel,
                                      final BooleanBinding argNameBlank) {
            this.typeChoiceBox = argsTypeChoiceBox;
            this.nameTextField = argsNameTextField;
            this.label = argumentsLabel;
            this.nameBlank = argNameBlank;
        }
    }
}
