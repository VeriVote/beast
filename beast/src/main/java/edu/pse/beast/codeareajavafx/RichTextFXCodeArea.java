package edu.pse.beast.codeareajavafx;

import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.scene.Node;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

/**
 * The Class RichTextFXCodeArea.
 *
 * @author Lukas Stapelbroek
 */
public class RichTextFXCodeArea extends CodeArea {
    /** The pipe constant. */
    private static final String PIPE = "|";
    /** The ">" constant. */
    private static final String GREATER_THAN = ">";
    /** The ")" constant. */
    private static final String PAREN_CLOSE = ")";
    /** The "|(?<" symbols. */
    private static final String SYMBOLS = "|(?<";

    /** The Constant SAMPLE_CODE_SNIPPETS. */
    private static final String[] SAMPLE_CODE_SNIPPETS =
            new String[] {
                "package com.example;",
                "",
                "import java.util.*;",
                "",
                "public class Foo extends Bar implements Baz {",
                "",
                "    /*",
                "     * multi-line comment",
                "     */",
                "    public static void main(String[] args) {",
                "        // single-line comment",
                "        for(String arg: args) {",
                "            if(arg.length() != 0)",
                "                System.out.println(arg);",
                "            else",
                "                System.err.println(\"Warning: "
                        + "empty string as argument\");",
                "        }", "    }", "", "}"
                };

    /** The Constant KEYWORDS. */
    private static final String[] KEYWORDS =
        {
        "abstract", "assert", "boolean",
        "break", "byte", "case", "catch",
        "char", "class", "const",
        "continue", "default", "do",
        "double", "else", "enum", "extends",
        "final", "finally", "float", "for",
        "goto", "if", "implements",
        "import", "instanceof", "int",
        "interface", "long", "native", "new",
        "package", "private", "protected",
        "public", "return", "short",
        "static", "strictfp", "super",
        "switch", "synchronized", "this",
        "throw", "throws", "transient",
        "try", "void", "volatile", "while"
        };

    /** The Constant KEYWORD_PATTERN. */
    private static final String KEYWORD_PATTERN =
            "\\b(" + String.join(PIPE, KEYWORDS) + ")\\b";

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
            "//[^\n]*" + PIPE + "/\\*(.|\\R)*?\\*/";

    /** The Constant KEYWORD_STRING. */
    private static final String KEYWORD_STRING = "KEYWORD_STRING";

    /** The Constant PAREN_STRING. */
    private static final String PAREN_STRING = "PAREN_STRING";

    /** The Constant BRACE_STRING. */
    private static final String BRACE_STRING = "BRACE_STRING";

    /** The Constant BRACKET_STRING. */
    private static final String BRACKET_STRING = "BRACKET_STRING";

    /** The Constant SEMICOLON_STRING. */
    private static final String SEMICOLON_STRING = "SEMICOLON_STRING";

    /** The Constant STRING_STRING. */
    private static final String STRING_STRING = "STRING_STRING";

    /** The Constant COMMENT_STRING. */
    private static final String COMMENT_STRING = "COMMENT_STRING";

    /** The Constant PATTERN. */
    private static final Pattern PATTERN = Pattern
            .compile("(?<" + KEYWORD_STRING + GREATER_THAN
                    + KEYWORD_PATTERN + PAREN_CLOSE
                    + SYMBOLS + PAREN_STRING + GREATER_THAN
                    + PAREN_PATTERN + PAREN_CLOSE + SYMBOLS
                    + BRACE_STRING + GREATER_THAN
                    + BRACE_PATTERN + PAREN_CLOSE + SYMBOLS
                    + BRACKET_STRING + GREATER_THAN
                    + BRACKET_PATTERN + PAREN_CLOSE + SYMBOLS
                    + SEMICOLON_STRING + GREATER_THAN
                    + SEMICOLON_PATTERN + PAREN_CLOSE + SYMBOLS
                    + STRING_STRING + GREATER_THAN
                    + STRING_PATTERN + PAREN_CLOSE + SYMBOLS
                    + COMMENT_STRING + GREATER_THAN
                    + COMMENT_PATTERN + PAREN_CLOSE);

    /** The Constant RESOURCE. */
    private static final String RESOURCE = "codeAreaJAVAFXStyleSheet.css";

    /**
     * The constructor.
     */
    public RichTextFXCodeArea() {
        final String stylesheet =
                this.getClass().getResource(RESOURCE).toExternalForm();
        this.getStylesheets().add(stylesheet);
        // IntFunction<String> format = (digits -> " %" + digits + "d ");
        final IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    this.setStyleSpans(0, computeHighlighting(this.getText()));
                });

        final String sampleCode = String.join("\n", SAMPLE_CODE_SNIPPETS);
        this.replaceText(0, 0, sampleCode);
    }

    /**
     * Compute highlighting.
     *
     * @param text
     *            the text
     * @return the style spans
     */
    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        final Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        final StyleSpansBuilder<Collection<String>> spansBuilder =
                new StyleSpansBuilder<Collection<String>>();
        while (matcher.find()) {
            final String styleClass =
                    matcher.group(KEYWORD_STRING) != null
                    ? KEYWORD_STRING.toLowerCase() : matcher.group(PAREN_STRING) != null
                    ? PAREN_STRING.toLowerCase() : matcher.group(BRACE_STRING) != null
                    ? BRACE_STRING.toLowerCase() : matcher.group(BRACKET_STRING) != null
                    ? BRACKET_STRING.toLowerCase() : matcher.group(SEMICOLON_STRING) != null
                    ? SEMICOLON_STRING.toLowerCase() : matcher.group(STRING_STRING) != null
                    ? STRING_STRING.toLowerCase() : matcher.group(COMMENT_STRING) != null
                    ? COMMENT_STRING.toLowerCase() : null;
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
}