package edu.pse.beast.gui.ceditor;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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

import org.fxmisc.flowless.VirtualizedScrollPane;
import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

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
    /** The Constant QUERY. */
    private static final String QUERY = "?";
    /** The Constant PIPE. */
    private static final String PIPE = "|";

    /** The Constant LT_SIGN. */
    private static final String LT_SIGN = "<";
    /** The Constant GT_SIGN. */
    private static final String GT_SIGN = ">";
    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";

    /** The Constant B_PATTERN. */
    private static final String B_PATTERN = "\\b";

    /** The Constant TAB_SPACES. */
    private static final int TAB_SPACES = 4;

    /**
     * The Constant KEYWORDS. TODO maybe change to generic styled area.
     */
    private static final String[] KEYWORDS =
        { C.AUTO, C.BREAK, C.CASE, C.CONST, C.CONTINUE, C.DEFAULT, C.DO, C.ELSE, "error", C.CONST,
                C.CONTINUE, C.DEFAULT, C.DO, C.ELSE, C.ENUM, C.EXTERN, C.FOR, C.GOTO, C.IF,
                C.RETURN, C.SIGNED, C.SIZE_OF, C.STATIC, C.STRUCT, C.SWITCH, C.TYPE_DEF, C.UNION,
                C.UNSIGNED, C.VOLATILE, C.WHILE };

    /** The Constant PREPROCESSOR. */
    private static final String[] PREPROCESSOR =
        { "#define", "#elif", "#endif", "#ifdef", "#ifndef", "#include" };

    /** The Constant DATATYPES. */
    private static final String[] DATATYPES =
        { C.CHAR, C.DOUBLE, C.ENUM, C.FLOAT, C.INT, C.LONG, C.REGISTER, C.VOID };

    /** The Constant KEYWORD_PATTERN. */
    private static final String KEYWORD_PATTERN =
            B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, KEYWORDS)
            + CLOSING_PARENTHESES + B_PATTERN;

    /** The Constant PREPROCESSOR_PATTERN. */
    private static final String PREPROCESSOR_PATTERN =
            B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, PREPROCESSOR)
            + CLOSING_PARENTHESES + B_PATTERN;

    /** The Constant DATATYPE_PATTERN. */
    private static final String DATATYPE_PATTERN =
            B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, DATATYPES)
            + CLOSING_PARENTHESES + B_PATTERN;

    /** The Constant POINTER_PATTERN. */
    private static final String POINTER_PATTERN =
            B_PATTERN + OPENING_PARENTHESES
            + String.join(PIPE, Arrays.stream(DATATYPES)
                    .map(s -> "\\*[\\s]*" + s).toArray(String[]::new))
            + CLOSING_PARENTHESES + B_PATTERN;

    /** The Constant METHOD_PATTERN. */
    private static final String METHOD_PATTERN = "[\\w]+[\\s]*\\(";

    /** The Constant INCLUDE_PATTERN. */
    private static final String INCLUDE_PATTERN = "[.]*include[\\s]+[<|\"].+\\.[\\w]*[>|\"]";

    /** The Constant PAREN_PATTERN. */
    private static final String PAREN_PATTERN = "\\(|\\)";

    /** The Constant BRACE_PATTERN. */
    private static final String BRACE_PATTERN = "\\{|\\}";

    /** The Constant BRACKET_PATTERN. */
    private static final String BRACKET_PATTERN = "\\[|\\]";

    /** The Constant SEMICOLON_PATTERN. */
    private static final String SEMICOLON_PATTERN = "\\;";

    /** The Constant STRING_PATTERN. */
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";

    /** The Constant COMMENT_PATTERN. */
    private static final String COMMENT_PATTERN = "//[^\n]*" + PIPE + "/\\*(.|\\R)*?\\*/";

    /** The Constant KEYWORD_STRING. */
    private static final String KEYWORD_STRING = "KEYWORD";

    /** The Constant PREPROCESSOR_STRING. */
    private static final String PREPROCESSOR_STRING = "PREPROCESSOR";

    /** The Constant DATATYPE_STRING. */
    private static final String DATATYPE_STRING = "DATATYPE";

    /** The Constant POINTER_STRING. */
    private static final String POINTER_STRING = "POINTER";

    /** The Constant METHOD_STRING. */
    private static final String METHOD_STRING = "METHOD";

    /** The Constant INCLUDE_STRING. */
    private static final String INCLUDE_STRING = "INCLUDE";

    /** The Constant PAREN_STRING. */
    private static final String PAREN_STRING = "PAREN";

    /** The Constant BRACE_STRING. */
    private static final String BRACE_STRING = "BRACE";

    /** The Constant BRACKET_STRING. */
    private static final String BRACKET_STRING = "BRACKET";

    /** The Constant SEMICOLON_STRING. */
    private static final String SEMICOLON_STRING = "SEMICOLON";

    /** The Constant STRING_STRING. */
    private static final String STRING_STRING = "STRING";

    /** The Constant COMMENT_STRING. */
    private static final String COMMENT_STRING = "COMMENT";

    /** The Constant PATTERN. */
    private static final Pattern PATTERN =
            Pattern.compile(OPENING_PARENTHESES + QUERY + LT_SIGN + KEYWORD_STRING + GT_SIGN
                    + KEYWORD_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
                    + LT_SIGN + PREPROCESSOR_STRING + GT_SIGN + PREPROCESSOR_PATTERN
                    + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN
                    + DATATYPE_STRING + GT_SIGN + DATATYPE_PATTERN + CLOSING_PARENTHESES + PIPE
                    + OPENING_PARENTHESES + QUERY + LT_SIGN + POINTER_STRING + GT_SIGN
                    + POINTER_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
                    + LT_SIGN + METHOD_STRING + GT_SIGN + METHOD_PATTERN + CLOSING_PARENTHESES
                    + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + INCLUDE_STRING + GT_SIGN
                    + INCLUDE_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
                    + LT_SIGN + PAREN_STRING + GT_SIGN + PAREN_PATTERN + CLOSING_PARENTHESES + PIPE
                    + OPENING_PARENTHESES + QUERY + LT_SIGN + BRACE_STRING + GT_SIGN
                    + BRACE_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
                    + LT_SIGN + BRACKET_STRING + GT_SIGN + BRACKET_PATTERN + CLOSING_PARENTHESES
                    + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + SEMICOLON_STRING + GT_SIGN
                    + SEMICOLON_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
                    + LT_SIGN + STRING_STRING + GT_SIGN + STRING_PATTERN + CLOSING_PARENTHESES
                    + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + COMMENT_STRING + GT_SIGN
                    + COMMENT_PATTERN + CLOSING_PARENTHESES);

    private static final double LINE_SCALE = 1.3;
    private static final String EMPTY = "";
    private static final String BLANK = " ";
    private static final String NONE = "–";
    private static final String COMMA = ", ";
    private static final String UNDERSCORE = "_";
    private static final String LINE_BREAK = "\n";

    private static final String ADD_ARGUMENT = "Add Argument";
    private static final String REMOVE_LAST = "Remove Last Argument";

    private static final String EDIT_ELECTION_DIALOG = "Edit Election";
    private static final String EDIT_LOOP_BOUND_DIALOG = "Edit Loop Bound";
    private static final String REMOVE_FUNCTION_DIALOG = "Remove Function";
    private static final String ADD_SIMPLE_FUNCTION_DIALOG = "Add Simple Function";
    private static final String CREATE_ELECTION_DIALOG = "Create New Election";

    private static final String RETURN_TYPE = "Return Type:";
    private static final String NAME = "Name:";
    private static final String INPUT_TYPE = "Input Type:";
    private static final String OUTPUT_TYPE = "Output Type:";
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
    private Button saveDescriptionButton;
    private Button removeDescriptionButton;

    private Label inputTypeLabel;
    private Label outputTypeLabel;

    private MenuButton addFunctionMenuButton;
    private Button removeFunctionButton;

    private Button computeLoopBoundsButton;

    private VirtualizedScrollPane<CEditorCodeElement> cEditorVirtualizedScrollPane;

    private String codeStyleSheet;

    private double currentTextSize;

    public CElectionEditor(final VirtualizedScrollPane<CEditorCodeElement> cEditorGUIElementVsp,
                           final ElectionDescriptionButtons electDescrButtons,
                           final FunctionEditor functionEditor,
                           final LoopBoundEditor loopBoundEditor,
                           final CodeAreas codeAreas,
                           final BeastWorkspace workspace) {
        codeAreas.elect.setStyleSpans(0, computeHighlighting(codeAreas.elect.getText()));
        codeStyleSheet = this.getClass().getResource(CSS_RESOURCE).toExternalForm();
        this.addElectionDescriptionButton = electDescrButtons.add;
        this.loadElectionDescriptionButton = electDescrButtons.load;
        this.editDescriptionButton = electDescrButtons.edit;
        this.saveDescriptionButton = electDescrButtons.save;
        this.removeDescriptionButton = electDescrButtons.delete;

        setupNewElectionButtons();

        this.computeLoopBoundsButton = loopBoundEditor.generateButton;
        this.computeLoopBoundsButton.setOnAction(e -> {
            workspace.findLoopBounds(currentDescription, currentDisplayedFunction);
        });
        loopBoundEditor.editButton.setDisable(true);
        loopBoundEditor.editButton.setOnAction(e -> {
            editSelectedLoopbound();
        });

        this.functions = functionEditor.list;
        this.loopBounds = loopBoundEditor.list;
        this.loopBounds.getSelectionModel().selectedItemProperty()
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
        this.inputTypeLabel = codeAreas.electInputStatus;
        this.outputTypeLabel = codeAreas.electOutputStatus;
        this.cEditorVirtualizedScrollPane = cEditorGUIElementVsp;
        this.functionDeclarationArea.setEditable(false);
        this.closingBracketArea.setEditable(false);

        this.electionCodeArea.getStylesheets().add(codeStyleSheet);
        this.functionDeclarationArea.getStylesheets().add(codeStyleSheet);
        this.closingBracketArea.getStylesheets().add(codeStyleSheet);

        this.beastWorkspace = workspace;
        this.openedElectionDescriptionChoiceBox = electDescrButtons.choiceBox;
        this.addFunctionMenuButton = functionEditor.addButton;
        this.removeFunctionButton = functionEditor.removeButton;
        setupFunctionButtons();

        initListViews();
        initOpenedDescrChoiceBox();
        // addSyntaxHighlighting(this.electionCodeArea);
        handleWorkspaceUpdateGeneric();
        workspace.registerUpdateListener(this);
        this.electionCodeArea.setChangeListener(text -> {
            workspace.updateCodeForDescrFunction(currentDescription,
                    currentDisplayedFunction, text);
        });
    }

    /**
     * Compute highlighting.
     *
     * @param text the text
     * @return the style spans
     */
    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        final Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        final StyleSpansBuilder<Collection<String>> spansBuilder =
                new StyleSpansBuilder<Collection<String>>();
        while (matcher.find()) {
            final String[] styleClasses =
                    new String[] { KEYWORD_STRING, PREPROCESSOR_STRING, METHOD_STRING,
                            DATATYPE_STRING, POINTER_STRING, INCLUDE_STRING, PAREN_STRING,
                            BRACE_STRING, BRACKET_STRING, SEMICOLON_STRING, STRING_STRING,
                            COMMENT_STRING };
            String styleClass = null;
            for (final String style : styleClasses) {
                if (styleClass == null && matcher.group(style) != null) {
                    styleClass = style.toLowerCase();
                }
            }
            /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    /**
     * Add syntax highlighting.
     */
    private void addSyntaxHighlighting(final CodeArea area) {
        area.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
        .subscribe(change -> {
            area.setStyleSpans(0, computeHighlighting(area.getText()));
            // currentDisplayedFunction.setCode(area.getText());
            // area.insertText(0, currentDisplayedFunction.getCode());
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
            inputTypeLabel.setText(inType.toNiceString());
            outputTypeLabel.setText(inType.toNiceString());
            beastWorkspace.editDescr(currentDescription, name, inType, outType);
        }
    }

    private void removeDescription() {
        if (currentDescription == null) {
            return;
        }
        if (beastWorkspace.removeDescr(currentDescription)) {
            inputTypeLabel.setText(NONE);
            outputTypeLabel.setText(NONE);
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
        editDescriptionButton.setOnAction(e -> {
            editDescription();
        });
        saveDescriptionButton.setOnAction(e -> {
            saveDescription();
        });
        removeDescriptionButton.setOnAction(e -> {
            removeDescription();
        });
    }

    private void setupFunctionButtons() {
        addFunctionMenuButton.getItems().clear();

        final MenuItem addSimpleFuncMenuItem =
                new MenuItem(CelectionDescriptionFunctionType.SIMPLE.toNiceString());
        /*
         * MenuItem addVotingFuncMenuItem = new MenuItem(
         * CelectionDescriptionFunctionType.VOTING.toNiceString());
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
        openedElectionDescriptionChoiceBox.setDisable(true);
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
        final boolean noChoice = openedElectionDescriptionChoiceBox.getItems().size() < 1;
        openedElectionDescriptionChoiceBox.setDisable(noChoice);
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
        final String in = descr == null ? NONE : descr.getInputType().toNiceString();
        final String out = descr == null ? NONE : descr.getOutputType().toNiceString();
        this.inputTypeLabel.setText(in);
        this.outputTypeLabel.setText(out);
        editDescriptionButton.setDisable(descr == null);
        saveDescriptionButton.setDisable(descr == null);
        removeDescriptionButton.setDisable(descr == null);
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
            inputTypeChoiceBox.getItems().add(it.toNiceString());
        }
        inputTypeChoiceBox.getSelectionModel().selectFirst();

        final ChoiceBox<String> outputTypeChoiceBox = new ChoiceBox<String>();
        for (final VotingOutputType ot : VotingOutputType.values()) {
            outputTypeChoiceBox.getItems().add(ot.toNiceString());
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

    public final void saveDescription() {
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
        final Label electInputStatus;
        final Label electOutputStatus;

        public CodeAreas(final CEditorCodeElement electCodeArea,
                         final CodeArea closBrackArea,
                         final Label electInput,
                         final Label electOutput) {
            this.elect = electCodeArea;
            this.closeBrackets = closBrackArea;
            this.electInputStatus = electInput;
            this.electOutputStatus = electOutput;
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

    public static final class C {
        /** The Constant AUTO. */
        public static final String AUTO = "auto";
        /** The Constant BREAK. */
        public static final String BREAK = "break";
        /** The Constant CASE. */
        public static final String CASE = "case";
        /** The Constant CHAR. */
        public static final String CHAR = "char";
        /** The Constant CONST. */
        public static final String CONST = "const";
        /** The Constant CONTINUE. */
        public static final String CONTINUE = "continue";
        /** The Constant DEFAULT. */
        public static final String DEFAULT = "default";
        /** The Constant DEFINE. */
        public static final String DEFINE = "#define";
        /** The Constant DO. */
        public static final String DO = "do";
        /** The Constant DOUBLE. */
        public static final String DOUBLE = "double";
        /** The Constant ELSE. */
        public static final String ELSE = "else";
        /** The Constant ENUM. */
        public static final String ENUM = "enum";
        /** The Constant EXTERN. */
        public static final String EXTERN = "extern";
        /** The Constant FLOAT. */
        public static final String FLOAT = "float";
        /** The Constant FOR. */
        public static final String FOR = "for";
        /** The Constant GOTO. */
        public static final String GOTO = "goto";
        /** The Constant IF. */
        public static final String IF = "if";
        /** The Constant INCLUDE. */
        public static final String INCLUDE = "#include";
        /** The Constant INLINE. */
        public static final String INLINE = "inline";
        /** The Constant INT. */
        public static final String INT = "int";
        /** The Constant LONG. */
        public static final String LONG = "long";
        /** The Constant REGISTER. */
        public static final String REGISTER = "register";
        /** The Constant RESTRICT. */
        public static final String RESTRICT = "restrict";
        /** The Constant RETURN. */
        public static final String RETURN = "return";
        /** The Constant SHORT. */
        public static final String SHORT = "short";
        /** The Constant SIGNED. */
        public static final String SIGNED = "signed";
        /** The Constant SIZE_OF. */
        public static final String SIZE_OF = "sizeof";
        /** The Constant STATIC. */
        public static final String STATIC = "static";
        /** The Constant STRUCT. */
        public static final String STRUCT = "struct";
        /** The Constant SWITCH. */
        public static final String SWITCH = "switch";
        /** The Constant TYPE_OF. */
        public static final String TYPE_DEF = "typedef";
        /** The Constant UNION. */
        public static final String UNION = "union";
        /** The Constant UNSIGNED. */
        public static final String UNSIGNED = "unsigned";
        /** The Constant VOID. */
        public static final String VOID = "void";
        /** The Constant VOLATILE. */
        public static final String VOLATILE = "volatile";
        /** The Constant WHILE. */
        public static final String WHILE = "while";
        /** The Constant ALIGN_AS. */
        public static final String ALIGN_AS = "_Alignas";
        /** The Constant ALIGN_OF. */
        public static final String ALIGN_OF = "_Alignof";
        /** The Constant ATOMIC. */
        public static final String ATOMIC = "_Atomic";
        /** The Constant BOOL. */
        public static final String BOOL = "_Bool";
        /** The Constant COMPLEX. */
        public static final String COMPLEX = "_Complex";
        /** The Constant GENERIC. */
        public static final String GENERIC = "_Generic";
        /** The Constant IMAGINARY. */
        public static final String IMAGINARY = "_Imaginary";
        /** The Constant NO_RETURN. */
        public static final String NO_RETURN = "_Noreturn";
        /** The Constant STATIC_ASSERT. */
        public static final String STATIC_ASSERT = "_Static_assert";
        /** The Constant THREAD_LOCAL. */
        public static final String THREAD_LOCAL = "_Thread_local";

        /** The Constant OPENING_PARENTHESES. */
        public static final String OPENING_PARENTHESES = "(";
        /** The Constant CLOSING_PARENTHESES. */
        public static final String CLOSING_PARENTHESES = ")";
        /** The Constant OPENING_BRACES. */
        public static final String OPENING_BRACES = "{";
        /** The Constant CLOSING_BRACES. */
        public static final String CLOSING_BRACES = "}";
        /** The Constant SEMICOLON. */
        public static final String SEMICOLON = ";";
    }
}
