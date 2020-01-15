package edu.pse.beast.codeareajavafx;

import java.util.Collection;
import java.util.Collections;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.CodeArea;
import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import javafx.scene.Node;

public class RichTextFXCodeArea extends CodeArea {
    private static final String[] KEYWORDS
        = {
            "abstract", "assert", "boolean", "break", "byte", "case",
            "catch", "char", "class", "const", "continue", "default",
            "do", "double", "else", "enum", "extends", "final",
            "finally", "float", "for", "goto", "if", "implements",
            "import", "instanceof", "int", "interface", "long",
            "native", "new", "package", "private", "protected",
            "public", "return", "short", "static", "strictfp",
            "super", "switch", "synchronized", "this", "throw",
            "throws", "transient", "try", "void", "volatile", "while"
        };

    private static final String KEYWORD_PATTERN = "\\b(" + String.join("|", KEYWORDS) + ")\\b";
    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String BRACE_PATTERN = "\\{|\\}";
    private static final String BRACKET_PATTERN = "\\[|\\]";
    private static final String SEMICOLON_PATTERN = "\\;";
    private static final String STRING_PATTERN = "\"([^\"\\\\]|\\\\.)*\"";
    private static final String COMMENT_PATTERN = "//[^\n]*" + "|" + "/\\*(.|\\R)*?\\*/";

    private static final String KEYWORD_STRING = "KEYWORD_STRING";
    private static final String PAREN_STRING = "PAREN_STRING";
    private static final String BRACE_STRING = "BRACE_STRING";
    private static final String BRACKET_STRING = "BRACKET_STRING";
    private static final String SEMICOLON_STRING = "SEMICOLON_STRING";
    private static final String STRING_STRING = "STRING_STRING";
    private static final String COMMENT_STRING = "COMMENT_STRING";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<" + KEYWORD_STRING + ">" + KEYWORD_PATTERN + ")"
            + "|(?<" + PAREN_STRING + ">" + PAREN_PATTERN + ")"
            + "|(?<" + BRACE_STRING + ">" + BRACE_PATTERN + ")"
            + "|(?<" + BRACKET_STRING + ">" + BRACKET_PATTERN + ")"
            + "|(?<" + SEMICOLON_STRING + ">" + SEMICOLON_PATTERN + ")"
            + "|(?<" + STRING_STRING + ">" + STRING_PATTERN + ")"
            + "|(?<" + COMMENT_STRING + ">" + COMMENT_PATTERN + ")");

    private static final String RESOURCE = "codeAreaJAVAFXStyleSheet.css";

    public RichTextFXCodeArea() {
        String sampleCode
            = String.join("\n",
                          new String[]
                          {
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
                              "        }",
                              "    }",
                              "",
                              "}"
                          }
                      );
        String stylesheet = this.getClass().getResource(RESOURCE).toExternalForm();
        this.getStylesheets().add(stylesheet);
        // IntFunction<String> format = (digits -> " %" + digits + "d ");
        IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.richChanges().filter(ch
            -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change
                -> {
                this.setStyleSpans(0, computeHighlighting(this.getText()));
            });
        this.replaceText(0, 0, sampleCode);
    }

    private static StyleSpans<Collection<String>> computeHighlighting(final String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass
                = matcher.group(KEYWORD_STRING) != null
                ? KEYWORD_STRING.toLowerCase()
                    : matcher.group(PAREN_STRING) != null
                    ? PAREN_STRING.toLowerCase()
                        : matcher.group(BRACE_STRING) != null
                        ? BRACE_STRING.toLowerCase()
                            : matcher.group(BRACKET_STRING) != null
                            ? BRACKET_STRING.toLowerCase()
                                : matcher.group(SEMICOLON_STRING) != null
                                ? SEMICOLON_STRING.toLowerCase()
                                    : matcher.group(STRING_STRING) != null
                                    ? STRING_STRING.toLowerCase()
                                        : matcher.group(COMMENT_STRING) != null
                                        ? COMMENT_STRING.toLowerCase()
                                            : null;
            /* never happens */ assert styleClass != null;
            spansBuilder.add(Collections.emptyList(), matcher.start() - lastKwEnd);
            spansBuilder.add(Collections.singleton(styleClass), matcher.end() - matcher.start());
            lastKwEnd = matcher.end();
        }
        spansBuilder.add(Collections.emptyList(), text.length() - lastKwEnd);
        return spansBuilder.create();
    }
}
