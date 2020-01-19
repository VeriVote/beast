package edu.pse.beast.codeareajavafx;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.PlainTextChange;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;
import org.reactfx.collection.LiveList;

import com.google.gson.JsonSyntaxException;

import edu.pse.beast.celectiondescriptioneditor.celectioncodearea.errorhandling.CVariableErrorFinder;
import edu.pse.beast.codearea.errorhandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice;
import edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;

/**
 * The Class NewCodeArea.
 *
 * @author Lukas Stapelbroek
 */
public final class NewCodeArea extends AutoCompletionCodeArea
        implements MenuBarInterface {

    /** The Constant TAB_SPACES. */
    private static final int TAB_SPACES = 4;

    /** The Constant KEYWORDS. */
    // TODO maybe change to generic styled area
    private static final String[] KEYWORDS =
        {
        "auto", "break", "case", "const",
        "continue", "default", "do", "else",
        "error", "const", "continue",
        "default", "do", "else", "enum",
        "extern", "for", "goto", "if",
        "return", "signed", "sizeof",
        "static", "struct", "switch",
        "typedef", "union", "unsigned",
        "volatile", "while"
        };

    /** The Constant PREPROCESSOR. */
    private static final String[] PREPROCESSOR =
        {
        "#define", "#elif", "#endif",
        "#ifdef", "#ifndef", "#include"
        };

    /** The Constant DATATYPES. */
    private static final String[] DATATYPES =
        {
        "char", "double", "enum",
        "float", "int", "long",
        "register", "void"
        };

    /** The Constant KEYWORD_PATTERN. */
    private static final String KEYWORD_PATTERN =
            "\\b(" + String.join("|", KEYWORDS) + ")\\b";

    /** The Constant PREPROCESSOR_PATTERN. */
    private static final String PREPROCESSOR_PATTERN =
            "\\b(" + String.join("|", PREPROCESSOR) + ")\\b";

    /** The Constant DATATYPE_PATTERN. */
    private static final String DATATYPE_PATTERN =
            "\\b(" + String.join("|", DATATYPES) + ")\\b";

    /** The Constant POINTER_PATTERN. */
    private static final String POINTER_PATTERN =
            "\\b("
            + String.join("|", Arrays.stream(DATATYPES)
                    .map(s -> "\\*[\\s]*" + s).toArray(String[]::new))
            + ")\\b";

    /** The Constant METHOD_PATTERN. */
    private static final String METHOD_PATTERN =
            "[\\w]+[\\s]*\\(";

    /** The Constant INCLUDE_PATTERN. */
    private static final String INCLUDE_PATTERN =
            "[.]*include[\\s]+[<|\"].+\\.[\\w]*[>|\"]";

    /** The Constant PAREN_PATTERN. */
    private static final String PAREN_PATTERN = "\\(|\\)";

    /** The Constant BRACE_PATTERN. */
    private static final String BRACE_PATTERN = "\\{|\\}";

    /** The Constant BRACKET_PATTERN. */
    private static final String BRACKET_PATTERN = "\\[|\\]";

    /** The Constant SEMICOLON_PATTERN. */
    private static final String SEMICOLON_PATTERN = "\\;";

    /** The Constant STRING_PATTERN. */
    private static final String STRING_PATTERN =
            "\"([^\"\\\\]|\\\\.)*\"";

    /** The Constant COMMENT_PATTERN. */
    private static final String COMMENT_PATTERN =
            "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

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
    private static final Pattern PATTERN = Pattern
            .compile("(?<" + KEYWORD_STRING + ">"
                    + KEYWORD_PATTERN + ")"
                    + "|(?<" + PREPROCESSOR_STRING + ">"
                    + PREPROCESSOR_PATTERN
                    + ")" + "|(?<" + DATATYPE_STRING + ">"
                    + DATATYPE_PATTERN
                    + ")" + "|(?<" + POINTER_STRING + ">"
                    + POINTER_PATTERN
                    + ")" + "|(?<" + METHOD_STRING + ">"
                    + METHOD_PATTERN + ")"
                    + "|(?<" + INCLUDE_STRING + ">"
                    + INCLUDE_PATTERN + ")"
                    + "|(?<" + PAREN_STRING + ">"
                    + PAREN_PATTERN + ")" + "|(?<"
                    + BRACE_STRING + ">"
                    + BRACE_PATTERN + ")" + "|(?<"
                    + BRACKET_STRING + ">"
                    + BRACKET_PATTERN + ")" + "|(?<"
                    + SEMICOLON_STRING + ">"
                    + SEMICOLON_PATTERN + ")" + "|(?<"
                    + STRING_STRING + ">"
                    + STRING_PATTERN + ")" + "|(?<"
                    + COMMENT_STRING + ">"
                    + COMMENT_PATTERN + ")");

    /** The Constant RESOURCE. */
    private static final String RESOURCE = "codeAreaSyntaxHighlight.css";

    /** The recommendations. */
    private static Set<String> recommendations = new TreeSet<String>();

    /** The select all combination. */
    private final KeyCombination selectAllCombination =
            new KeyCodeCombination(KeyCode.A,
                                   KeyCombination.CONTROL_DOWN); // select all

    /** The backspace combination. */
    private final KeyCombination backspaceCombination =
            new KeyCodeCombination(KeyCode.BACK_SPACE); // backspace

    /** The delete combination. */
    private final KeyCombination deleteCombination =
            new KeyCodeCombination(KeyCode.DELETE); // delete

    /** The enter combination. */
    private final KeyCombination enterCombination =
            new KeyCodeCombination(KeyCode.ENTER); // enter

    /** The undo combination. */
    private final KeyCombination undoCombination =
            new KeyCodeCombination(KeyCode.Z,
                                   KeyCombination.CONTROL_DOWN); // undo

    /** The redo combination. */
    private final KeyCombination redoCombination =
            new KeyCodeCombination(KeyCode.Y,
                                   KeyCombination.CONTROL_DOWN); // redo

    /** The paste combination. */
    private final KeyCombination pasteCombination =
            new KeyCodeCombination(KeyCode.V,
                                   KeyCombination.CONTROL_DOWN); // paste

    /** The cut combination. */
    private final KeyCombination cutCombination =
            new KeyCodeCombination(KeyCode.X,
                                   KeyCombination.CONTROL_DOWN); // paste

    /** The tabulator combination. */
    private final KeyCombination tabulatorCombination =
            new KeyCodeCombination(KeyCode.TAB); // tab key

    /** The saver loader. */
    private final SaverLoader saverLoader;

    /** The election saver loader. */
    private final ElectionDescriptionSaverLoader electionSaverLoader =
            new ElectionDescriptionSaverLoader();

    /** The elec description. */
    private ElectionDescription elecDescription;

    /** The locked line start. */
    private int lockedLineStart = 0;

    /** The locked line end. */
    private int lockedLineEnd = 0;

    /** The locked brace pos. */
    private int lockedBracePos;

    /** The spaces per tab. */
    // private int amountTabs = 0;
    private int spacesPerTab = TAB_SPACES;

    /** The last char. */
    private String lastChar = "";

    /**
     * The constructor.
     */
    public NewCodeArea() {
        // add all standard recommendations
        recommendations.addAll(Arrays.asList(KEYWORDS));
        recommendations.addAll(Arrays.asList(PREPROCESSOR));
        recommendations.addAll(Arrays.asList(DATATYPES));
        saverLoader = new SaverLoader(".elec", "BEAST election description",
                                      this);
        ElectionDescription startElecDescription =
                new ElectionDescription("New description", new SingleChoice(),
                                        new SingleCandidate(), 0, 0, 0, true);
        this.setNewElectionDescription(startElecDescription);
        saverLoader.resetHasSaveFile();
        List<String> code = new ArrayList<String>();
        code.add("");
        // code.add(source.getContainer().getInputType().);
        String stylesheet =
                this.getClass().getResource(RESOURCE).toExternalForm();
        this.getStylesheets().add(stylesheet);
        final IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.addEventFilter(KeyEvent.KEY_TYPED, event -> {
            consume(event);
            String replacement = "";
            String value = (event.getCharacter()).replaceAll("\\p{Cntrl}", "");

            int step = 0;

            if (value.length() != 1) {
                return;
            } else {
                replacement = value;
                switch (value) {
                case "(":
                    replacement = "()";
                    step = -1;
                    break;
                case "[":
                    replacement = "[]";
                    step = -1;
                    break;
                case "{":
                    replacement = "{\n\n}";
                    break;
                default:
                    break;
                }
                lockedLineSafeInsertText(replacement, false, false, null);
                this.moveTo(Math.max(0, this.getCaretPosition() + step));
                this.setLastChar(value);
            }
        });

        this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
            consume(event);
        });

        this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {
            // System.out.println(event.getCode());
            if (selectAllCombination.match(event)) {
                // we just want to select all
                this.selectAll();
                consume(event);
            } else if (backspaceCombination.match(event)) {
                lockedLineSafeInsertText("", true, false, null);
                consume(event);
            } else if (deleteCombination.match(event)) {
                delete(event);
            } else if (enterCombination.match(event)) {
                lockedLineSafeInsertText("\n", false, false, null);
                consume(event);
            } else if (pasteCombination.match(event)) {
                paste(event);
            } else if (cutCombination.match(event)) {
                cut(event);
            } else if (tabulatorCombination.match(event)) {
                String whitespaces =
                        new String(new char[spacesPerTab]).replace("\0", " ");
                lockedLineSafeInsertText(whitespaces, false, false, null);
                consume(event);
            }
        });
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    this.setStyleSpans(0, computeHighlighting(this.getText()));
                    elecDescription.setCode(this.getText());
                });
    }

    /**
     * Consume.
     *
     * @param event
     *            the event
     */
    private static void consume(final KeyEvent event) {
        if (event != null) {
            event.consume();
        }
    }

    /**
     * Delete.
     *
     * @param event
     *            the event
     */
    private void delete(final KeyEvent event) {
        lockedLineSafeInsertText("", false, true, null);
        consume(event);
    }

    @Override
    public void delete() {
        this.delete(null);
    }

    /**
     * Paste.
     *
     * @param event
     *            the event
     */
    private void paste(final KeyEvent event) {
        String clipboardText = "";
        try {
            clipboardText = (String) Toolkit.getDefaultToolkit()
                    .getSystemClipboard().getData(DataFlavor.stringFlavor);
        } catch (HeadlessException | UnsupportedFlavorException
                | IOException e) {
            return;
        }
        lockedLineSafeInsertText(clipboardText, false, false, null);
        consume(event);
    }

    @Override
    public void paste() {
        this.paste(null);
    }

    /**
     * Cut.
     *
     * @param event
     *            the event
     */
    private void cut(final KeyEvent event) {
        String selectedText = this.getSelectedText();
        if (lockedLineSafeInsertText("", true, false, null)) {
            StringSelection selection = new StringSelection(selectedText);
            Toolkit.getDefaultToolkit().getSystemClipboard()
                    .setContents(selection, selection);
        }
        consume(event);
    }

    @Override
    public void cut() {
        cut(null);
    }

    /**
     * Redo.
     *
     * @param event
     *            the event
     */
    private void redo(final KeyEvent event) {
        LiveList<?> redoList =
                this.getUndoManager().nextRedoProperty().asList();
        if (redoList.size() == 1) {
            List<?> list = (List<?>) redoList.get(0);
            PlainTextChange change = (PlainTextChange) list.get(0);
            System.out.println("redo length:  " + change.getNetLength());
            updateLockedLineNumber(change.getRemovalEnd(),
                                   change.getRemoved().length());
        } else {
            consume(event);
        }
    }

    @Override
    public void redo() {
        redo(null);
        super.redo();
    }

    /**
     * Undo.
     *
     * @param event
     *            the event
     */
    private void undo(final KeyEvent event) {
        LiveList<?> undoList =
                this.getUndoManager().nextUndoProperty().asList();
        if (undoList.size() == 1) {
            List<?> list = (List<?>) undoList.get(0);
            PlainTextChange change = (PlainTextChange) list.get(0);
            updateLockedLineNumber(change.getInsertionEnd(),
                                   -1 * change.getNetLength());
        } else {
            consume(event);
        }
    }

    @Override
    public void undo() {
        undo(null);
        super.undo();
    }

    /**
     * tries to insert text into this text pane. It will not override locked
     * lines
     *
     * @param replacement
     *            the text to be inserted
     * @param backspace
     *            if the backspace key was pressed
     * @param delete
     *            if the delete key was pressed
     * @param tuple
     *            alternative variable to set the bounds where text should be
     *            replaced. If it is null, the area to replace will be inferred
     *            from the cursor or the marked area
     * @return true, if successful
     */
    private boolean lockedLineSafeInsertText(final String replacement,
                                             final boolean backspace,
                                             final boolean delete,
                                             final Tuple<Integer, Integer> tuple) {
        int selectionStart = 0;
        int selectionEnd = 0;
        if (tuple != null) {
            selectionStart = tuple.first();
            selectionEnd = tuple.second();
        } else {
            IndexRange selectionRange = this.getSelection();
            selectionStart = selectionRange.getStart();
            selectionEnd = selectionRange.getEnd();
        }

        int selectionLength = selectionEnd - selectionStart;
        if (selectionLength == 0) {
            selectionStart = this.getCaretPosition();
            selectionEnd = this.getCaretPosition();
        }
        if (backspace && delete) {
            return false;
        }
        final String r = (backspace || delete) ? "" : replacement;
        if (selectionLength == 0) { // update the values
            if (backspace) {
                selectionStart = Math.max(0, selectionStart - 1);
            } else if (delete) {
                selectionEnd = Math.min(this.getText().length(),
                                        selectionEnd + 1);
            }
            selectionLength = (backspace || delete)
                    ? selectionEnd - selectionStart
                    : selectionLength;
        }
        // if (selectionEnd - selectionStart > 0) { // we have a selected range
        // // find out, if the selected range overlaps with a locked line
        // anywhere
        boolean notOverlapping = ((selectionEnd < lockedLineStart)
                                    || (lockedLineEnd <= selectionStart))
                                && (((selectionEnd <= lockedBracePos)
                                        || (lockedBracePos < selectionStart)));
        if (notOverlapping
                || (selectionEnd == lockedLineStart
                    && (r.endsWith("\n") || backspace || delete))) {
            this.replaceText(selectionStart, selectionEnd, r);
            updateLockedLineNumber(selectionEnd, r.length() - selectionLength);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Update locked line number.
     *
     * @param changePosition
     *            the change position
     * @param lengthChange
     *            the length change
     */
    private void updateLockedLineNumber(final int changePosition,
                                        final int lengthChange) {
        if (changePosition <= lockedLineStart) {
            lockedLineStart += lengthChange;
            lockedLineEnd += lengthChange;
        }
        lockedBracePos += (changePosition <= lockedLineStart
                || changePosition <= lockedBracePos) ? lengthChange : 0;
        this.elecDescription.setLockedPositions(this.lockedLineStart,
                this.lockedLineEnd, this.lockedBracePos);
    }

    /**
     * Compute highlighting.
     *
     * @param text
     *            the text
     * @return the style spans
     */
    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder =
                new StyleSpansBuilder<Collection<String>>();
        while (matcher.find()) {
            final String[] styleClasses =
                    new String[] {
                        KEYWORD_STRING, PREPROCESSOR_STRING,
                        METHOD_STRING, DATATYPE_STRING,
                        POINTER_STRING, INCLUDE_STRING,
                        PAREN_STRING, BRACE_STRING,
                        BRACKET_STRING, SEMICOLON_STRING,
                        STRING_STRING, COMMENT_STRING
                    };
            String styleClass = null;
            for (String style : styleClasses) {
                if (styleClass == null && matcher.group(style) != null) {
                    styleClass = style.toLowerCase();
                }
            }
            /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(),
                             matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass),
                             matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }

    /**
     * Get election description.
     *
     * @return a deep copy of the election description, which does not update
     *         when the text field gets another input
     */
    public ElectionDescription getElectionDescription() {
        elecDescription.setLockedPositions(lockedLineStart, lockedLineEnd,
                                           lockedBracePos);
        return elecDescription.getDeepCopy();
    }

    /**
     * Display errors.
     *
     * @param codeErrors
     *            the code errors
     */
    public void displayErrors(final List<CodeError> codeErrors) {
        String toDisplay = "";
        for (CodeError codeError : codeErrors) {
            toDisplay += "line: " + codeError.getLine() + "| Message: "
                            + codeError.getMsg() + "\n";
        }
        GUIController.setErrorText(toDisplay);
    }

    /**
     * Sets the new election description.
     *
     * @param newDescription
     *            the new new election description
     */
    public void setNewElectionDescription(final ElectionDescription newDescription) {
        this.elecDescription = newDescription;
        if (newDescription.isNew()) {
            String declarationString = CCodeHelper
                    .generateSimpleDeclString(newDescription.getContainer());
            lockedLineStart = 0;
            lockedLineEnd = lockedLineStart + declarationString.length();
            this.replaceText(declarationString + "\n\n}");
            lockedBracePos = this.getText().length() - 1;
            this.elecDescription.setNotNew();
        } else {
            this.replaceText(newDescription.getCodeAsString());
            lockedLineStart = newDescription.getLockedLineStart();
            lockedLineEnd = newDescription.getLockedLineEnd();
            lockedBracePos = newDescription.getLockedBracePos();
            this.setStyleSpans(0, computeHighlighting(this.getText()));
            saverLoader.resetHasSaveFile();
        }
        this.elecDescription.setLockedPositions(lockedLineStart, lockedLineEnd,
                                                lockedBracePos);
        this.setStyleSpans(0, computeHighlighting(this.getText()));
        saverLoader.resetHasSaveFile();
        // force the undo manager to not be able to return to previous text
        this.getUndoManager().forgetHistory();
        String inputInfo =
                elecDescription.getContainer().getInputType().getInfo();
        String outputInfo =
                elecDescription.getContainer().getOutputType().getInfo();
        String combined = inputInfo + "\n\n\n" + outputInfo;
        GUIController.setInfoText(combined);
    }

    // public void setElectionDescription(ElectionDescription newDescription) {
    // this.elecDescription = newDescription;
    // this.replaceText(newDescription.getCodeAsString());
    // this.setStyleSpans(0, computeHighlighting(this.getText()));
    // saverLoader.resetHasSaveFile();
    // }

    /**
     * Bring to front.
     */
    public void bringToFront() {
        final GUIController cont = GUIController.getController();
        cont.getMainTabPane().getSelectionModel().select(cont.getCodeTab());
        displayErrors(CVariableErrorFinder.findErrors(
                elecDescription.getDeepCopy().getComplexCode(),
                elecDescription));
    }

    /**
     * Open.
     *
     * @param elecDescFile
     *            the elec desc file
     */
    public void open(final File elecDescFile) {
        openJson(saverLoader.load(elecDescFile), false);
    }

    @Override
    public void open() {
        String json = saverLoader.load();
        openJson(json, true);
    }

    /**
     * Open json.
     *
     * @param json
     *            the json
     * @param bringToFront
     *            the bring to front
     */
    private void openJson(final String json, final boolean bringToFront) {
        ElectionDescription newDescription = null;
        try {
            newDescription = "".equals(json) ? null
                    : electionSaverLoader.createFromSaveString(json);
        } catch (JsonSyntaxException e) {
            System.err.println(e.getMessage());
        }
        if (newDescription != null) {
            setNewElectionDescription(newDescription);
            if (bringToFront) {
                bringToFront();
            }
        }
    }

    @Override
    public void save() {
        saverLoader.save("", electionSaverLoader
                .createSaveString(elecDescription.getDeepCopy()));
    }

    @Override
    public void saveAs() {
        saverLoader.saveAs("", electionSaverLoader
                .createSaveString(elecDescription.getDeepCopy()));
    }

    /**
     * Save as.
     *
     * @param file
     *            the file
     */
    public void saveAs(final File file) {
        saverLoader.saveAs(file, electionSaverLoader
                .createSaveString(elecDescription.getDeepCopy()));
    }

    @Override
    public void copy() {
        super.copy();
    }

    /**
     * Reset save file.
     */
    public void resetSaveFile() {
        this.saverLoader.resetHasSaveFile();
    }

    @Override
    public void autoComplete() {
        Tuple3<List<String>, Integer, Integer> completions =
                getCompletions(recommendations);
        processAutoCompletion(completions);
    }

    @Override
    public void insertAutoCompletion(final int start, final int end,
                                     final String toInsert) {
        lockedLineSafeInsertText(
                toInsert, false, false,
                new Tuple<Integer, Integer>(start, end)
        );
    }

    /**
     * Gets the last char.
     *
     * @return the last char
     */
    public String getLastChar() {
        return lastChar;
    }

    /**
     * Sets the last char.
     *
     * @param lastCharString
     *            the new last char
     */
    public void setLastChar(final String lastCharString) {
        this.lastChar = lastCharString;
    }

    /**
     * Gets the redo combination.
     *
     * @return the redo combination
     */
    public KeyCombination getRedoCombination() {
        return redoCombination;
    }

    /**
     * Gets the undo combination.
     *
     * @return the undo combination
     */
    public KeyCombination getUndoCombination() {
        return undoCombination;
    }
}
