package edu.pse.beast.codeareaJAVAFX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import javafx.scene.Node;

public class BoundedVarCodeArea extends AutoCompletionCodeArea implements MenuBarInterface {

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

	private List<ElectionDescriptionChangeListener> listeners = new ArrayList<ElectionDescriptionChangeListener>();

	private FormalPropertiesDescription description;

	public BoundedVarCodeArea() {

		List<String> code = new ArrayList<String>();
		code.add("");

		String sampleCode = "";

		String stylesheet = this.getClass().getResource("codeAreaSyntaxHighlight.css").toExternalForm();

		this.getStylesheets().add(stylesheet);

		IntFunction<Node> lineNumbers = LineNumberFactory.get(this);

		this.setParagraphGraphicFactory(lineNumbers);

		this.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change -> {
			this.setStyleSpans(0, computeHighlighting(this.getText()));
		});
		this.replaceText(0, 0, sampleCode);

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

	/**
	 * sets the description for this property code are (either pre or post prop
	 * description)
	 * 
	 * @param description
	 */
	public void setDescription(FormalPropertiesDescription description) {

		saveDescription(description);

		this.description = description;
		this.replaceText(0, this.getLength(), description.getCode());
	}

	public void saveDescription(FormalPropertiesDescription newDescription) {
		if (this.description != null) {
			this.description.setCode(this.textProperty().getValue());
		}
	}

	@Override
	public void undo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void redo() {
		// TODO Auto-generated method stub

	}

	@Override
	public void cut() {
		// TODO Auto-generated method stub

	}

	@Override
	public void copy() {
		// TODO Auto-generated method stub

	}

	@Override
	public void paste() {
		// TODO Auto-generated method stub

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void open() {
		// TODO Auto-generated method stub

	}

	@Override
	public void save() {
		// TODO Auto-generated method stub

	}

	@Override
	public void saveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void autoComplete() {
		System.out.println("wird das hier gebraucht?");
	}
}
