package edu.pse.beast.codeareaJAVAFX;

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

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.Triplet;
import javafx.scene.Node;

public class NewPropertyCodeArea extends AutoCompletionCodeArea {
	private static final String[] OPERATORS = new String[] { "\\*", "/", "\\+", "-" };

	private static final String[] COMPARISON = new String[] { "==", "\\!\\=", "\\<\\=", "\\>\\=", "\\<", "\\>" };

	private static final String[] RELATION = new String[] { "&&", "\\|\\|", "==>", "<==>", "<--", "\\+\\+"};

	private static final String[] MAKROS = new String[] { "VOTES", "ELECT", "VOTE_SUM_FOR_CANDIDATE",
			"VOTE_SUM_FOR_UNIQUE_CANDIDATE" };

	private static final String[] QUANTORS = new String[] { "FOR_ALL_VOTERS", "FOR_ALL_CANDIDATES", "FOR_ALL_SEATS",
			"EXISTS_ONE_VOTER", "EXISTS_ONE_CANDIDATE", "EXISTS_ONE_SEAT", "PERM", "SPLIT", "INTERSECT", "NOTEMPTY" };

	private static final String OPERATORS_PATTERN = "(" + String.join("|", OPERATORS) + ")";
	private static final String COMPARISON_PATTERN = "(" + String.join("|", COMPARISON) + ")";
	private static final String RELATION_PATTERN = "(" + String.join("|", RELATION) + ")";

	private static final String MAKROS_PATTERN = "\\b("
			+ String.join("|", Arrays.stream(MAKROS).map(s -> s + "[0-9]+").toArray(String[]::new)) + ")\\b";
	private static final String QUANTORS_PATTERN = "\\b(" + String.join("|", QUANTORS) + ")\\b";

	private static final String PAREN_PATTERN = "\\(|\\)";
	private static final String SEMICOLON_PATTERN = "\\;";

	private static final Pattern PATTERN = Pattern.compile(
			"(?<OPERATORS>" + OPERATORS_PATTERN + ")" + "|(?<COMPARISON>" + COMPARISON_PATTERN + ")" + "|(?<RELATION>"
					+ RELATION_PATTERN + ")" + "|(?<MAKROS>" + MAKROS_PATTERN + ")" + "|(?<QUANTORS>" + QUANTORS_PATTERN
					+ ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN + ")");

	
	private static Set<String> recommendations = new TreeSet<String>();
	
	
	private FormalPropertiesDescription description;

	public NewPropertyCodeArea() {

		// add all standard recommendations
		recommendations.addAll(Arrays.asList(MAKROS));
		
		String stylesheet = this.getClass().getResource("propertyAreaSyntaxHighlight.css").toExternalForm();

		this.getStylesheets().add(stylesheet);

		IntFunction<Node> lineNumbers = LineNumberFactory.get(this);

		this.setParagraphGraphicFactory(lineNumbers);

		this.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change -> {
			this.setStyleSpans(0, computeHighlighting(this.getText()));
		});

		this.replaceText(0, 0, ""); // reset the text
	}

	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
		Matcher matcher = PATTERN.matcher(text);
		int lastKwEnd = 0;
		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
		while (matcher.find()) {
			String styleClass = matcher.group("OPERATORS") != null ? "operators"
					: matcher.group("COMPARISON") != null ? "comparison"
							: matcher.group("RELATION") != null ? "relation"
									: matcher.group("MAKROS") != null ? "makros"
											: matcher.group("QUANTORS") != null ? "quantors"
													: matcher.group("PAREN") != null ? "paren"
															: matcher.group("SEMICOLON") != null ? "semicolon" : null;

			/* never happens */ assert styleClass != null;

			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
			lastKwEnd = matcher.end();
		}
		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
		return spansBuilder.create();
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
	
	private Triplet<List<String>, Integer, Integer> getCompletions() {
		String completeText = this.getText();

		int prefixEnd = caretPositionProperty().getValue();
		int prefixStart = prefixEnd;
		
		String prefix = "";

		
		
		if (prefixEnd > 0) {
			for (int i = prefixEnd - 1; i >= 0; i--) {

				char tmp = completeText.charAt(i);

				if (tmp == ' ' | ("" + tmp).matches(",|;|\\.|\\(|\\)|\\{|\\}|\\|/|\\+|-|\\*")
						| ("" + tmp).matches("\\p{Cntrl}")) {

					if (!prefix.matches("([A-Za-z]+[A-Za-z0-9]*)")) {
						prefix = "";
					}
					break;
				} else {
					prefix = tmp + prefix;

					prefixStart = i;
				}

			}
		} else

		{
			prefix = "";
		}

		completeText = completeText.replaceAll("\\p{Cntrl}", " "); // replace control characters by whitespaces
		completeText = completeText.replaceAll(",|;|\\.|\\(|\\)|\\{|\\}|\\|/|\\+|-|\\*", " ");
		completeText = completeText.replaceAll("\\s+", " ");

		Set<String> possibilities = new TreeSet<String>(recommendations);
		
		possibilities.addAll(GUIController.getController().getBooleanExpEditor().getSymbolicVariableNames());

		String[] split = completeText.split(" "); // split on whitespaces to extract the words

		for (int i = 0; i < split.length; i++) {
			if (split[i].matches("([A-Za-z]+[A-Za-z0-9]*)")) {
				possibilities.add(split[i]);
			}
		}

		List<String> possibleList = new ArrayList<String>();

		if (prefix.equals("")) {
			possibleList.addAll(possibilities);
		} else { // we already have started a word, so we have to filter out all non fitting
					// words
			for (Iterator<String> iterator = possibilities.iterator(); iterator.hasNext();) {
				String str = (String) iterator.next();
				if (str != null && str.toLowerCase().startsWith(prefix.toLowerCase())) {
					possibleList.add(str);
				}

			}

		}

		return new Triplet<List<String>, Integer, Integer>(possibleList, prefixStart, prefixEnd);
	}

	public void autoComplete() {
		Triplet<List<String>, Integer, Integer> completion = getCompletions();
		processAutocompletion(completion.first, completion.second, completion.third);
	}
}
