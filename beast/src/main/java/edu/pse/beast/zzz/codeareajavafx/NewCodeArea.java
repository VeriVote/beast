package edu.pse.beast.zzz.codeareajavafx;

import static edu.pse.beast.zzz.toolbox.CCodeHelper.AUTO;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.BREAK;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.CASE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.CHAR;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.CONST;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.CONTINUE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.DEFAULT;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.DO;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.DOUBLE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.ELSE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.ENUM;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.EXTERN;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.FLOAT;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.FOR;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.GOTO;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.IF;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.INT;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.LONG;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.REGISTER;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.RETURN;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.SIGNED;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.SIZE_OF;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.STATIC;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.STRUCT;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.SWITCH;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.TYPE_DEF;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.UNION;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.UNSIGNED;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.VOID;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.VOLATILE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.WHILE;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.generateSimpleDeclStringForCodeArea;
import static edu.pse.beast.zzz.toolbox.CCodeHelper.lineBreak;

import java.awt.HeadlessException;
import java.awt.Toolkit;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.StringSelection;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
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
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice;
import edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;
import edu.pse.beast.zzz.toolbox.Tuple;
import edu.pse.beast.zzz.toolbox.Tuple3;

/**
 * The Class NewCodeArea.
 *
 * @author Lukas Stapelbroek
 */
public final class NewCodeArea extends AutoCompletionCodeArea implements MenuBarInterface {
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
	private static final String[] KEYWORDS = { AUTO, BREAK, CASE, CONST, CONTINUE, DEFAULT, DO, ELSE, "error", CONST,
			CONTINUE, DEFAULT, DO, ELSE, ENUM, EXTERN, FOR, GOTO, IF, RETURN, SIGNED, SIZE_OF, STATIC, STRUCT, SWITCH,
			TYPE_DEF, UNION, UNSIGNED, VOLATILE, WHILE };

	/** The Constant PREPROCESSOR. */
	private static final String[] PREPROCESSOR = { "#define", "#elif", "#endif", "#ifdef", "#ifndef", "#include" };

	/** The Constant DATATYPES. */
	private static final String[] DATATYPES = { CHAR, DOUBLE, ENUM, FLOAT, INT, LONG, REGISTER, VOID };

	/** The Constant KEYWORD_PATTERN. */
	private static final String KEYWORD_PATTERN = B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, KEYWORDS)
			+ CLOSING_PARENTHESES + B_PATTERN;

	/** The Constant PREPROCESSOR_PATTERN. */
	private static final String PREPROCESSOR_PATTERN = B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, PREPROCESSOR)
			+ CLOSING_PARENTHESES + B_PATTERN;

	/** The Constant DATATYPE_PATTERN. */
	private static final String DATATYPE_PATTERN = B_PATTERN + OPENING_PARENTHESES + String.join(PIPE, DATATYPES)
			+ CLOSING_PARENTHESES + B_PATTERN;

	/** The Constant POINTER_PATTERN. */
	private static final String POINTER_PATTERN = B_PATTERN + OPENING_PARENTHESES
			+ String.join(PIPE, Arrays.stream(DATATYPES).map(s -> "\\*[\\s]*" + s).toArray(String[]::new))
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
	private static final Pattern PATTERN = Pattern.compile(OPENING_PARENTHESES + QUERY + LT_SIGN + KEYWORD_STRING
			+ GT_SIGN + KEYWORD_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN
			+ PREPROCESSOR_STRING + GT_SIGN + PREPROCESSOR_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES
			+ QUERY + LT_SIGN + DATATYPE_STRING + GT_SIGN + DATATYPE_PATTERN + CLOSING_PARENTHESES + PIPE
			+ OPENING_PARENTHESES + QUERY + LT_SIGN + POINTER_STRING + GT_SIGN + POINTER_PATTERN + CLOSING_PARENTHESES
			+ PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + METHOD_STRING + GT_SIGN + METHOD_PATTERN
			+ CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + INCLUDE_STRING + GT_SIGN
			+ INCLUDE_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + PAREN_STRING
			+ GT_SIGN + PAREN_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN
			+ BRACE_STRING + GT_SIGN + BRACE_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES + QUERY
			+ LT_SIGN + BRACKET_STRING + GT_SIGN + BRACKET_PATTERN + CLOSING_PARENTHESES + PIPE + OPENING_PARENTHESES
			+ QUERY + LT_SIGN + SEMICOLON_STRING + GT_SIGN + SEMICOLON_PATTERN + CLOSING_PARENTHESES + PIPE
			+ OPENING_PARENTHESES + QUERY + LT_SIGN + STRING_STRING + GT_SIGN + STRING_PATTERN + CLOSING_PARENTHESES
			+ PIPE + OPENING_PARENTHESES + QUERY + LT_SIGN + COMMENT_STRING + GT_SIGN + COMMENT_PATTERN
			+ CLOSING_PARENTHESES);

	/** The Constant RESOURCE. */
	private static final String RESOURCE = "codeAreaSyntaxHighlight.css";

	/** The recommendations. */
	private static Set<String> recommendations = new TreeSet<String>();

	/** The select all combination. */
	private final KeyCombination selectAllCombination = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN); // select
																														// all

	/** The backspace combination. */
	private final KeyCombination backspaceCombination = new KeyCodeCombination(KeyCode.BACK_SPACE); // backspace

	/** The delete combination. */
	private final KeyCombination deleteCombination = new KeyCodeCombination(KeyCode.DELETE); // delete

	/** The enter combination. */
	private final KeyCombination enterCombination = new KeyCodeCombination(KeyCode.ENTER); // enter

	/** The undo combination. */
	private final KeyCombination undoCombination = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_DOWN); // undo

	/** The redo combination. */
	private final KeyCombination redoCombination = new KeyCodeCombination(KeyCode.Y, KeyCombination.CONTROL_DOWN); // redo

	/** The paste combination. */
	private final KeyCombination pasteCombination = new KeyCodeCombination(KeyCode.V, KeyCombination.CONTROL_DOWN); // paste

	/** The cut combination. */
	private final KeyCombination cutCombination = new KeyCodeCombination(KeyCode.X, KeyCombination.CONTROL_DOWN); // paste

	/** The tabulator combination. */
	private final KeyCombination tabulatorCombination = new KeyCodeCombination(KeyCode.TAB); // tab key

	/** The saver loader. */
	private final SaverLoader saverLoader;

	/** The election saver loader. */
	private final ElectionDescriptionSaverLoader electionSaverLoader = new ElectionDescriptionSaverLoader();

	/** The elec description. */
	private ElectionDescription elecDescription;

	/** The locked line start. */
	private int lockedLineStart;

	/** The locked line end. */
	private int lockedLineEnd;

	/** The locked brace pos. */
	private int lockedBracePos;

	/** The spaces per tab. */
	private int spacesPerTab = TAB_SPACES;
	// private int amountTabs = 0;

	/** The last char. */
	private String lastChar = "";

	/**
	 * The constructor.
	 */
	public NewCodeArea() {
		// Add all standard recommendations
		recommendations.addAll(Arrays.asList(KEYWORDS));
		recommendations.addAll(Arrays.asList(PREPROCESSOR));
		recommendations.addAll(Arrays.asList(DATATYPES));
		saverLoader = new SaverLoader(SaverLoader.ELEC_DESCR_FILE_ENDING, "BEAST election description", this);
		final ElectionDescription startElecDescription = new ElectionDescription("New description", new SingleChoice(),
				new SingleCandidate(), 0, 0, 0, true);
		this.setNewElectionDescription(startElecDescription);
		saverLoader.resetHasSaveFile();
		// final List<String> code = new ArrayList<String>();
		// code.add(""); FIXME: What is this for? Can probably be removed.
		// code.add(source.getContainer().getInputType().);
		final String stylesheet = this.getClass().getResource(RESOURCE).toExternalForm();
		this.getStylesheets().add(stylesheet);
		final IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
		this.setParagraphGraphicFactory(lineNumbers);

		addKeyTypedFilter();
		addKeyReleasedFilter();
		addKeyPressedFilter();
		addSyntaxHighlighting();
	}

	/**
	 * Consume.
	 *
	 * @param event the event
	 */
	private static void consume(final KeyEvent event) {
		if (event != null) {
			event.consume();
		}
	}

	/**
	 * Delete.
	 *
	 * @param event the event
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
	 * @param event the event
	 */
	private void paste(final KeyEvent event) {
		String clipboardText = "";
		try {
			clipboardText = (String) Toolkit.getDefaultToolkit().getSystemClipboard().getData(DataFlavor.stringFlavor);
		} catch (HeadlessException | UnsupportedFlavorException | IOException e) {
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
	 * @param event the event
	 */
	private void cut(final KeyEvent event) {
		final String selectedText = this.getSelectedText();
		if (lockedLineSafeInsertText("", true, false, null)) {
			final StringSelection selection = new StringSelection(selectedText);
			Toolkit.getDefaultToolkit().getSystemClipboard().setContents(selection, selection);
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
	 * @param event the event
	 */
	private void redo(final KeyEvent event) {
		final LiveList<?> redoList = this.getUndoManager().nextRedoProperty().asList();
		if (redoList.size() == 1) {
			final List<?> list = (List<?>) redoList.get(0);
			final PlainTextChange change = (PlainTextChange) list.get(0);
			System.out.println("redo length:  " + change.getNetLength());
			updateLockedLineNumber(change.getRemovalEnd(), change.getRemoved().length());
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
	 * @param event the event
	 */
	private void undo(final KeyEvent event) {
		final LiveList<?> undoList = this.getUndoManager().nextUndoProperty().asList();
		if (undoList.size() == 1) {
			final List<?> list = (List<?>) undoList.get(0);
			final PlainTextChange change = (PlainTextChange) list.get(0);
			updateLockedLineNumber(change.getInsertionEnd(), -1 * change.getNetLength());
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
	 * Add filter for key type event.
	 */
	private void addKeyTypedFilter() {
		this.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			consume(event);
			String replacement = "";
			final String value = (event.getCharacter()).replaceAll("\\p{Cntrl}", "");

			int step = 0;

			if (value.length() != 1) {
				return;
			} else {
				replacement = value;
				switch (value) {
				case OPENING_PARENTHESES:
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
	}

	/**
	 * Add filter for key release event.
	 */
	private void addKeyReleasedFilter() {
		this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			consume(event);
		});
	}

	/**
	 * Add filter for key press event.
	 */
	private void addKeyPressedFilter() {
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
				lockedLineSafeInsertText(lineBreak(), false, false, null);
				consume(event);
			} else if (pasteCombination.match(event)) {
				paste(event);
			} else if (cutCombination.match(event)) {
				cut(event);
			} else if (tabulatorCombination.match(event)) {
				final String whitespaces = new String(new char[spacesPerTab]).replace("\0", " ");
				lockedLineSafeInsertText(whitespaces, false, false, null);
				consume(event);
			}
		});
	}

	/**
	 * Add syntax highlighting.
	 */
	private void addSyntaxHighlighting() {
		this.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change -> {
			this.setStyleSpans(0, computeHighlighting(this.getText()));
			elecDescription.setCode(this.getText());
		});
	}

	/**
	 * Tries to insert text into this text pane. It will not override locked lines.
	 *
	 * @param replacement the text to be inserted
	 * @param backspace   if the backspace key was pressed
	 * @param delete      if the delete key was pressed
	 * @param tuple       alternative variable to set the bounds where text should
	 *                    be replaced. If it is null, the area to replace will be
	 *                    inferred from the cursor or the marked area
	 * @return true, if successful
	 */
	private boolean lockedLineSafeInsertText(
			final String replacement, final boolean backspace, final boolean delete,
			final Tuple<Integer, Integer> tuple) {
		int selectionStart = 0;
		int selectionEnd = 0;
		if (tuple != null) {
			selectionStart = tuple.first();
			selectionEnd = tuple.second();
		} else {
			final IndexRange selectionRange = this.getSelection();
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
				selectionEnd = Math.min(this.getText().length(), selectionEnd + 1);
			}
			selectionLength = (backspace || delete) ? selectionEnd - selectionStart : selectionLength;
		}
		// if (selectionEnd - selectionStart > 0) { // we have a selected range
		// // find out, if the selected range overlaps with a locked line
		// anywhere
		final boolean notOverlapping = ((selectionEnd < lockedLineStart) || (lockedLineEnd <= selectionStart))
				&& (((selectionEnd <= lockedBracePos) || (lockedBracePos < selectionStart)));
		if (notOverlapping || (selectionEnd == lockedLineStart && (r.endsWith(lineBreak()) || backspace || delete))) {
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
	 * @param changePosition the change position
	 * @param lengthChange   the length change
	 */
	private void updateLockedLineNumber(final int changePosition, final int lengthChange) {
		if (changePosition <= lockedLineStart) {
			lockedLineStart += lengthChange;
			lockedLineEnd += lengthChange;
		}
		lockedBracePos += (changePosition <= lockedLineStart || changePosition <= lockedBracePos) ? lengthChange : 0;
		this.elecDescription.setLockedPositions(this.lockedLineStart, this.lockedLineEnd, this.lockedBracePos);
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
		final StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<Collection<String>>();
		while (matcher.find()) {
			final String[] styleClasses = new String[] { KEYWORD_STRING, PREPROCESSOR_STRING, METHOD_STRING,
					DATATYPE_STRING, POINTER_STRING, INCLUDE_STRING, PAREN_STRING, BRACE_STRING, BRACKET_STRING,
					SEMICOLON_STRING, STRING_STRING, COMMENT_STRING };
			String styleClass = null;
			for (String style : styleClasses) {
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
	 * Get election description.
	 *
	 * @return a deep copy of the election description, which does not update when
	 *         the text field gets another input
	 */
	public ElectionDescription getElectionDescription() {
		elecDescription.setLockedPositions(lockedLineStart, lockedLineEnd, lockedBracePos);
		return elecDescription.getDeepCopy();
	}

	/**
	 * Display errors.
	 *
	 * @param codeErrors the code errors
	 */
	public void displayErrors(final List<CodeError> codeErrors) {
		String toDisplay = "";
		for (CodeError codeError : codeErrors) {
			toDisplay += "line: " + codeError.getLine() + "| Message: " + codeError.getMsg() + lineBreak();
		}
		
		GUIController.setErrorText(toDisplay);
	}

	/**
	 * Sets the new election description.
	 *
	 * @param newDescription the new new election description
	 */
	public void setNewElectionDescription(final ElectionDescription newDescription) {
		this.elecDescription = newDescription;
		if (newDescription.isNew()) {
			final String declarationString = generateSimpleDeclStringForCodeArea(newDescription.getContainer());
			lockedLineStart = 0;
			lockedLineEnd = lockedLineStart + declarationString.length();
			this.replaceText(declarationString + "\n\n}");
			lockedBracePos = this.getText().length() - 1;
			newDescription.setCode(this.getText());
			this.elecDescription.setNotNew();
		} else {
			this.replaceText(newDescription.getCodeAsString());
			lockedLineStart = newDescription.getLockedLineStart();
			lockedLineEnd = newDescription.getLockedLineEnd();
			lockedBracePos = newDescription.getLockedBracePos();
			this.setStyleSpans(0, computeHighlighting(this.getText()));
			saverLoader.resetHasSaveFile();
		}
		this.elecDescription.setLockedPositions(lockedLineStart, lockedLineEnd, lockedBracePos);
		this.setStyleSpans(0, computeHighlighting(this.getText()));
		saverLoader.resetHasSaveFile();
		// force the undo manager to not be able to return to previous text
		this.getUndoManager().forgetHistory();
		final String inputInfo = elecDescription.getContainer().getInputType().getInfo();
		final String outputInfo = elecDescription.getContainer().getOutputType().getInfo();
		final String combined = inputInfo + "\n\n\n" + outputInfo;
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
		displayErrors(CVariableErrorFinder.findErrors(elecDescription));
	}

	/**
	 * Open.
	 *
	 * @param elecDescFile the elec desc file
	 */
	public void open(final File elecDescFile) {
		openJson(saverLoader.load(elecDescFile), false);
	}

	@Override
	public void open() {
		final String json = saverLoader.load();
		openJson(json, true);
	}

	/**
	 * Open json.
	 *
	 * @param json         the json
	 * @param bringToFront the bring to front
	 */
	private void openJson(final String json, final boolean bringToFront) {
		ElectionDescription newDescription = null;
		try {
			newDescription = "".equals(json) ? null : electionSaverLoader.createFromSaveString(json);
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
		saverLoader.save("", electionSaverLoader.createSaveString(elecDescription.getDeepCopy()));
	}

	@Override
	public void saveAs() {
		saverLoader.saveAs("", electionSaverLoader.createSaveString(elecDescription.getDeepCopy()));
	}

	/**
	 * Save as.
	 *
	 * @param file the file
	 */
	public void saveAs(final File file) {
		saverLoader.saveAs(file, electionSaverLoader.createSaveString(elecDescription.getDeepCopy()));
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
		final Tuple3<List<String>, Integer, Integer> completions = getCompletions(recommendations);
		processAutoCompletion(completions);
	}

	@Override
	public void insertAutoCompletion(final int start, final int end, final String toInsert) {
		lockedLineSafeInsertText(toInsert, false, false, new Tuple<Integer, Integer>(start, end));
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
	 * @param lastCharString the new last char
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
