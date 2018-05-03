//package edu.pse.beast.codeareaJAVAFX;
//
//import java.util.Collection;
//import java.util.Collections;
//import java.util.function.IntFunction;
//import java.util.regex.Matcher;
//import java.util.regex.Pattern;
//
//import org.fxmisc.richtext.CodeArea;
//import org.fxmisc.richtext.LineNumberFactory;
//import org.fxmisc.richtext.model.StyleSpans;
//import org.fxmisc.richtext.model.StyleSpansBuilder;
//
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import javafx.scene.Node;
//
//public class NewPostPropertyCodeArea extends CodeArea {
//
//private static final String[] OPERATORS = new String[] {"*", "/", "+", "-"};
//	
//	private static final String[] COMPARISON = new String[] {"==" , "!=" , "<=" , ">=" , "<" , ">"};
//	
//	private static final String[] RELATION = new String[] {"&&" , "||" , "==>" , "<==>"};
//	
//	private static final String[] MAKROS = new String[] {"VOTES", "ELECT", "VOTE_SUM_FOR_CANDIDATE", "VOTE_SUM_FOR_UNIQUE_CANDIDATE"};
//
//	private static final String[] QUANTORS = new String[] {"FOR_ALL_VOTERS" , "FOR_ALL_CANDIDATES" , "FOR_ALL_SEATS" ,
//			"EXISTS_ONE_VOTER" , "EXISTS_ONE_CANDIDATE" , "EXISTS_ONE_SEAT"};		
//	
//	
//	private static final String OPERATORS_PATTERN = "\\b(" + String.join("|", OPERATORS) + ")\\b";
//	private static final String COMPARISON_PATTERN = "\\b(" + String.join("|", COMPARISON) + ")\\b";
//	private static final String RELATION_PATTERN = "\\b(" + String.join("|", RELATION) + ")\\b";
//	private static final String MAKROS_PATTERN = "\\b(" + String.join("|", MAKROS + "[0-9]+") + ")\\b";
//	private static final String QUANTORS_PATTERN = "\\b(" + String.join("|", QUANTORS) + ")\\b";
//	
//	private static final String PAREN_PATTERN = "\\(|\\)";
//	private static final String SEMICOLON_PATTERN = "\\;";
//
//	private static final Pattern PATTERN = Pattern.compile("(?<OPERATORS>" + OPERATORS_PATTERN + ")" + "|(?<COMPARISON>"
//			+ COMPARISON_PATTERN + ")" + "|(?<RELATION>" + RELATION_PATTERN + ")" + "|(?<MAKROS>" + MAKROS_PATTERN + ")"
//			+ "|(?<QUANTORS>" + QUANTORS_PATTERN + ")" + "|(?<PAREN>" + PAREN_PATTERN + ")" + "|(?<SEMICOLON>" + SEMICOLON_PATTERN
//			+ ")");
//	
//	
//	private FormalPropertiesDescription description = new FormalPropertiesDescription("");
//	
//	public NewPostPropertyCodeArea() {
//
//		String sampleCode = "";
//
//		String stylesheet = this.getClass().getResource("newCodeAreaStyle.css").toExternalForm();
//
//		this.getStylesheets().add(stylesheet);
//		
//		IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
//
//		this.setParagraphGraphicFactory(lineNumbers);
//
//		this.richChanges().filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
//				.subscribe(change -> {
//					this.setStyleSpans(0, computeHighlighting(this.getText()));
//				});
//		this.replaceText(0, 0, sampleCode);
//
//		//s.richChanges()NewPostPropert
//		
//	}
//
//	private static StyleSpans<Collection<String>> computeHighlighting(String text) {
//		Matcher matcher = PATTERN.matcher(text);
//		int lastKwEnd = 0;
//		StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
//		while (matcher.find()) {
//			String styleClass = matcher.group("KEYWORD") != null ? "keyword"
//					: matcher.group("METHOD") != null ? "method"
//						: matcher.group("DATATYPE") != null ? "datatype"
//								: matcher.group("INCLUDE") != null ? "include"
//							: matcher.group("PAREN") != null ? "paren"
//									: matcher.group("BRACE") != null ? "brace"
//											: matcher.group("BRACKET") != null ? "bracket"
//													: matcher.group("SEMICOLON") != null ? "semicolon"
//															: matcher.group("STRING") != null ? "string"
//																	: matcher.group("COMMENT") != null ? "comment" : null;
//			/* never happens */ assert styleClass != null;
//			spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
//			spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
//			lastKwEnd = matcher.end();
//		}
//		spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
//		return spansBuilder.create();
//	}
//
//	public void setPostDescription(FormalPropertiesDescription description) {
//		this.description = description;
//		this.replaceText(0,this.getLength(), description.getCode());
//	}
//	
//}
