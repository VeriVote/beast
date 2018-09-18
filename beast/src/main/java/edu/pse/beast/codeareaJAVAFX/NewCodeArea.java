package edu.pse.beast.codeareaJAVAFX;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CParser.SelectionStatementContext;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CVariableErrorFinder;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.Triplet;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.inputplugins.SingleChoice;
import edu.pse.beast.types.cbmctypes.outputplugins.SingleCandidate;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.KeyEvent;

public class NewCodeArea extends AutoCompletionCodeArea implements MenuBarInterface {

	private final SaverLoader saverLoader;

	private final ElectionDescriptionSaverLoader electionSaverLoader = new ElectionDescriptionSaverLoader();

	private static final String[] KEYWORDS = new String[] { "auto", "break", "case", "const", "continue", "default",
			"do", "else", "error", "const", "continue", "default", "do", "else", "enum", "extern", "for", "goto", "if",
			"return", "signed", "sizeof", "static", "struct", "switch", "typedef", "union", "unsigned", "volatile",
			"while" };

	private static final String[] PREPROCESSOR = new String[] { "#define", "#elif", "#endif", "#ifdef", "#ifndef",
			"#include" };

	private static final String[] DATATYPES = new String[] { "char", "double", "enum", "float", "int", "long",
			"register", "void" };

	private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
	private static final String PREPROCESSOR_PATTERN = "(" + String.join("|", PREPROCESSOR) + ")";
	private static final String DATATYPE_PATTERN = "(" + String.join("|", DATATYPES) + ")";
	private static final String POINTER_PATTERN = "\\b("
			+ String.join("|", Arrays.stream(DATATYPES).map(s -> "\\*[\\s]*" + s).toArray(String[]::new)) + ")\\b";
	private static final String METHOD_PATTERN = "[\\w]+[\\s]*\\(";
	private static final String INCLUDE_PATTERN = "[.]*include[\\s]+[<|\"].+\\.[\\w]*[>|\"]";
	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String BRACE_PATTERN = "\\{|\\}";
	private static final String BRACKET_PATTERN = "\\[|\\]";
	private static final String SEMICOLON_PATTERN = "\\;";
	private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PREPROCESSOR>"
			+ PREPROCESSOR_PATTERN + ")" + "|(?<DATATYPE>" + DATATYPE_PATTERN + ")" + "|(?<POINTER>" + POINTER_PATTERN
			+ ")" + "|(?<METHOD>" + METHOD_PATTERN + ")" + "|(?<INCLUDE>" + INCLUDE_PATTERN + ")" + "|(?<PAREN>"
			+ PAREN_PATTERN + ")" + "|(?<BRACE>" + BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")"
			+ "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")" + "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>"
			+ COMMENT_PATTERN + ")");

	private static Set<String> recommendations = new TreeSet<String>();

	private ElectionDescription elecDescription;

	private List<ElectionDescriptionChangeListener> listeners = new ArrayList<ElectionDescriptionChangeListener>();

	private int lockedLineStart = 0;

	private int lockedLineEnd = 10;

	private int lockedBraceStart;

	private int lockedBraceEnd;

	public NewCodeArea() {

		// add all standard recommendations
		recommendations.addAll(Arrays.asList(KEYWORDS));
		recommendations.addAll(Arrays.asList(PREPROCESSOR));
		recommendations.addAll(Arrays.asList(DATATYPES));

		saverLoader = new SaverLoader(".elec", "BEAST election description");

		ElectionDescription startElecDescription = new ElectionDescription("New description", new SingleChoice(),
				new SingleCandidate(), 0);

		this.setNewElectionDescription(startElecDescription);

		saverLoader.resetHasSaveFile();

		List<String> code = new ArrayList<String>();
		code.add("");
		// code.add(source.getContainer().getInputType().);

		String sampleCode = "";

		String stylesheet = this.getClass().getResource("codeAreaSyntaxHighlight.css").toExternalForm();

		this.getStylesheets().add(stylesheet);

		IntFunction<Node> lineNumbers = LineNumberFactory.get(this);

		this.setParagraphGraphicFactory(lineNumbers);

		final KeyCombination selectAllCombination = new KeyCodeCombination(KeyCode.A, KeyCombination.CONTROL_DOWN); // select
																													// all

		final KeyCombination backspaceCombination = new KeyCodeCombination(KeyCode.BACK_SPACE); // backspace

		final KeyCombination deleteCombination = new KeyCodeCombination(KeyCode.DELETE); // delete

		final KeyCombination paranLeftCombination = new KeyCodeCombination(KeyCode.LEFT_PARENTHESIS); // (

		final KeyCombination braceLeftCombination = new KeyCodeCombination(KeyCode.BRACELEFT); // {

		final KeyCombination bracketLeftCombination = new KeyCodeCombination(KeyCode.OPEN_BRACKET); // [

		
		
		this.addEventFilter(KeyEvent.KEY_TYPED, event -> {
			System.out.println("typed: " + event.getCharacter());
		});

		this.addEventFilter(KeyEvent.KEY_RELEASED, event -> {
			event.consume();
		});

		this.addEventFilter(KeyEvent.KEY_PRESSED, event -> {

			System.out.println("pressed");

			System.out.println("text: " + event.getText());

			boolean remove = false;
			String replacement = "";

			if (selectAllCombination.match(event)) { // we just want to select all
				this.selectAll();
			} else if (backspaceCombination.match(event) || deleteCombination.match(event)) {
				remove = true;
			} else if (paranLeftCombination.match(event)) {
				replacement = "()";
			} else if (braceLeftCombination.match(event)) {
				replacement = "{\n\n}";
			} else if (bracketLeftCombination.match(event)) {
				replacement = "[]";
			} else if (event.getCode() == KeyCode.UNDEFINED) {
				return; // ignore all other special key presses
			} else {
				replacement = event.getText();
			}

			lockedLineSaveInsertText(replacement, remove);

			event.consume();

		});

		this.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change -> {
			this.setStyleSpans(0, computeHighlighting(this.getText()));
			elecDescription.setCode(this.getText());
		});
		this.replaceText(0, 0, sampleCode);

	}

	private void lockedLineSaveInsertText(String replacement, boolean delete) {
		IndexRange selectionRange = this.getSelection();

		int selectionStart = selectionRange.getStart();
		int selectionEnd = selectionRange.getEnd();

		int selectionLength = selectionEnd - selectionStart;

		// if (selectionEnd - selectionStart > 0) { // we have a selected range
		// // find out, if the selected range overlaps with a locked line anywhere

		if (selectionLength == 0) {
			selectionStart = this.getCaretPosition();
			selectionEnd = this.getCaretPosition();
		}

		boolean NotOverlapping = (((selectionEnd < lockedLineStart) || (lockedLineEnd < selectionStart)) // to test the
																											// function
																											// head
				&& ((selectionEnd <= lockedBraceStart) || (lockedBraceEnd <= selectionStart))); // to test the locked
																								// brace

		if (NotOverlapping) {
			this.replaceText(selectionStart, selectionEnd, replacement);

			lockedBraceStart++;
			lockedBraceEnd++;
		} else {
			System.out.println("overlaps");
		}

		// } else {
		// this.insertText(this.getCaretPosition(), replacement);
		// }

		System.out.println("start: " + selectionRange.getStart());
		System.out.println("end: " + selectionRange.getEnd());
	}

	private boolean updateLockedLines(int inputPosition, int inputSize) {
		if (inputSize > 0) {
			if (inputPosition > lockedLineStart && inputPosition < lockedLineEnd) {
				// the input is in the locked line, therefore it shouldn't be included
				return false;
			} else {
				if (inputPosition <= lockedLineStart) { // we have to shift all of them
					lockedLineStart = lockedLineStart + inputSize;
					lockedLineEnd = lockedLineEnd + inputSize;
					lockedBraceStart = lockedBraceStart + inputSize;
					lockedBraceEnd = lockedBraceEnd + inputSize;
				} else if (inputPosition <= lockedBraceStart) { // we only have to shift the last brace
					lockedBraceStart = lockedBraceStart + inputSize;
					lockedBraceEnd = lockedBraceEnd + inputSize;
				} else {
					// do nothing
				}
				return true;
			}
		} else if (inputSize < 0) {
			System.out.println("still have to find out in case of deletions");
			return true;
		} else {
			return false;
		}
	}

	private void test() {

		Set<String> style = Collections.singleton("fx-background-color: green");

		this.setStyle(lockedLineStart, lockedLineEnd, style);
	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
					: matcher.group("PREPROCESSOR") != null ? "preprocessor"
							: matcher.group("METHOD") != null ? "method"
									: matcher.group("DATATYPE") != null ? "datatype"
											: matcher.group("POINTER") != null ? "pointer"
													: matcher.group("INCLUDE") != null ? "include"
															: matcher.group("PAREN") != null ? "paren"
																	: matcher.group("BRACE") != null ? "brace"
																			: matcher.group("BRACKET") != null
																					? "bracket"
																					: matcher.group("SEMICOLON") != null
																							? "semicolon"
																							: matcher.group(
																									"STRING") != null
																											? "string"
																											: matcher
																													.group("COMMENT") != null
																															? "comment"
																															: null;
			/* never happens */ assert styleClass != null;

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
	}

	/**
	 * 
	 * @return a deep copy of the election description, which doesn't update when
	 *         the text field gets another input
	 */
	public ElectionDescription getElectionDescription() {
		return elecDescription.getDeepCopy();
	}

	public void displayErrors(List<CodeError> codeErrors) {

		String toDisplay = "";

		for (Iterator<CodeError> iterator = codeErrors.iterator(); iterator.hasNext();) {
			CodeError codeError = (CodeError) iterator.next();
			toDisplay = toDisplay + "line: " + codeError.getLine() + "| Message: " + codeError.getMsg() + "\n";
		}

		GUIController.setErrorText(toDisplay);
	}

	public void createNew(InputType newIn, OutputType newOut) {
		for (Iterator<ElectionDescriptionChangeListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ElectionDescriptionChangeListener listener = (ElectionDescriptionChangeListener) iterator.next();
			listener.inputChanged(newIn);
			listener.outputChanged(newOut);
		}
	}

	public void addListener(ElectionDescriptionChangeListener listener) {
		listeners.add(listener);
	}

	public void setNewElectionDescription(ElectionDescription newDescription) {
		this.elecDescription = newDescription;

		String declarationString = CCodeHelper.generateDeclString(newDescription.getContainer());

		lockedLineStart = 0;

		lockedLineEnd = declarationString.length();

		this.replaceText(declarationString + "\n\n}");

		lockedBraceStart = lockedLineEnd + 2;

		lockedBraceEnd = lockedBraceStart + 1;

		this.setStyleSpans(0, computeHighlighting(this.getText()));

		saverLoader.resetHasSaveFile();

		updateLockedLines(0, 0);

		test();
	}

	public void setElectionDescription(ElectionDescription newDescription) {
		this.elecDescription = newDescription;

		this.replaceText(newDescription.getCodeAsString());

		this.setStyleSpans(0, computeHighlighting(this.getText()));

		saverLoader.resetHasSaveFile();
	}

	public void bringToFront() {
		GUIController.getController().getMainTabPane().getSelectionModel()
				.select(GUIController.getController().getCodeTab());

		List<CodeError> errors = CVariableErrorFinder.findErrors(elecDescription.getDeepCopy().getCode());

		displayErrors(errors);
	}

	@Override
	public void open() {
		String json = saverLoader.load();

		openJson(json, true);
	}

	private void openJson(String json, boolean bringToFront) {
		if (!json.equals("")) {

			ElectionDescription newDescription = null;

			try {
				newDescription = electionSaverLoader.createFromSaveString(json);
			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

			if (newDescription != null) {
				setElectionDescription(newDescription);

				if (bringToFront) {
					bringToFront();
				}
			}

		}
	}

	public void open(File elecDescFile) {
		String json = saverLoader.load(elecDescFile);
		openJson(json, false);
	}

	@Override
	public void save() {
		ElectionDescription toSave = elecDescription.getDeepCopy();

		String json = electionSaverLoader.createSaveString(toSave);

		saverLoader.save("", json);
	}

	@Override
	public void saveAs() {

		ElectionDescription toSave = elecDescription.getDeepCopy();

		String json = electionSaverLoader.createSaveString(toSave);

		saverLoader.saveAs("", json);
	}

	public void saveAs(File file) {
		ElectionDescription toSave = elecDescription.getDeepCopy();

		String json = electionSaverLoader.createSaveString(toSave);

		saverLoader.saveAs(file, json);
	}

	@Override
	public void undo() {
		super.undo();
	}

	@Override
	public void redo() {
		super.redo();
	}

	@Override
	public void cut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void copy() {
		super.copy();
	}

	@Override
	public void paste() {
		super.paste();
	}

	@Override
	public void delete() {

	}

	public void resetSaveFile() {
		this.saverLoader.resetHasSaveFile();
	}

	@Override
	public void autoComplete() {
		Triplet<List<String>, Integer, Integer> completion = getCompletions(recommendations);
		processAutocompletion(completion.first, completion.second, completion.third);
	}
}
