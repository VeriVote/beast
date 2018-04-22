package edu.pse.beast.codeareaJAVAFX;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.types.InputType;
import edu.pse.beast.types.OutputType;
import edu.pse.beast.types.cbmctypes.inputplugins.Approval;
import edu.pse.beast.types.cbmctypes.outputtypes.SingleCandidate;
import javafx.scene.Node;

public class NewCodeArea extends SaveLoadCodeArea {

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
	private static final String DATATYPE_PATTERN = "\\b(" + String.join("|", DATATYPES) + ")\\b";
	private static final String METHOD_PATTERN = "[\\w]+[\\s]*\\(";
	private static final String INCLUDE_PATTERN = "[.]*include[\\s]+[<|\"].+\\.[\\w]*[>|\"]";
	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String BRACE_PATTERN = "\\{|\\}";
	private static final String BRACKET_PATTERN = "\\[|\\]";
	private static final String SEMICOLON_PATTERN = "\\;";
	private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
	private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

	private static final Pattern PATTERN = Pattern.compile("(?<KEYWORD>" + KEYWORD_PATTERN + ")" + "|(?<PREPROCESSOR>"
			+ PREPROCESSOR_PATTERN + ")" + "|(?<DATATYPE>" + DATATYPE_PATTERN + ")" + "|(?<METHOD>" + METHOD_PATTERN
			+ ")" + "|(?<INCLUDE>" + INCLUDE_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<BRACE>"
			+ BRACE_PATTERN + ")" + "|(?<BRACKET>" + BRACKET_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")"
			+ "|(?<STRING>" + STRING_PATTERN + ")" + "|(?<COMMENT>" + COMMENT_PATTERN + ")");

	private ElectionDescription source;

	private List<ElectionDescriptionChangeListener> listeners = new ArrayList<ElectionDescriptionChangeListener>();

	public NewCodeArea() {
		super(".elec", "C:", "BEAST election description");

		source = new ElectionDescription("New description", new Approval(), new SingleCandidate(), 0);

		List<String> code = new ArrayList<String>();
		code.add("");
		// code.add(source.getContainer().getInputType().);

		String sampleCode = "";

		String stylesheet = this.getClass().getResource("newCodeAreaStyle.css").toExternalForm();

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
											: matcher.group("INCLUDE") != null ? "include"
													: matcher.group("PAREN") != null ? "paren"
															: matcher.group("BRACE") != null ? "brace"
																	: matcher.group("BRACKET") != null ? "bracket"
																			: matcher.group("SEMICOLON") != null
																					? "semicolon"
																					: matcher.group("STRING") != null
																							? "string"
																							: matcher.group(
																									"COMMENT") != null
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

	public ElectionDescription getElectionDescription() {
		return source;
	}

	public void displayErrors(List<CodeError> codeErrors) {
		System.out.println("TODO display code errors");
	}

	public void createNew(InputType newIn, OutputType newOut) {
		for (Iterator<ElectionDescriptionChangeListener> iterator = listeners.iterator(); iterator.hasNext();) {
			ElectionDescriptionChangeListener listener = (ElectionDescriptionChangeListener) iterator.next();
			listener.inputChanged(newIn);
			listener.outputChanged(newOut);
		}

		System.out.println("TODO fix creating new CElectionDescription");
	}

	// public void addListener(ElectionDescriptionChangeListener listener) {
	// listener.inputChanged(input);
	// listener.outputChanged(output);
	// }

	public ElectionDescription getSource() {
		return source;
	}

	public void addListener(ElectionDescriptionChangeListener listener) {
		this.listeners.add(listener);
	}

}
