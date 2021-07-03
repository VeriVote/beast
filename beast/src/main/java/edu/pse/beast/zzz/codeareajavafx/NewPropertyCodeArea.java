package edu.pse.beast.zzz.codeareajavafx;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.api.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.BooleanExpEditorNEW;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.zzz.toolbox.Tuple3;

/**
 * The Class NewPropertyCodeArea.
 *
 * @author Lukas Stapelbroek
 */
public final class NewPropertyCodeArea extends AutoCompletionCodeArea
        implements MenuBarInterface {
    /** The Constant PIPE. */
    private static final String PIPE = "|";
    /** The Constant QUERY. */
    private static final String QUERY = "?";

    /** The Constant LT_SIGN. */
    private static final String LT_SIGN = "<";
    /** The Constant GT_SIGN. */
    private static final String GT_SIGN = ">";

    /** The Constant OPENING_PARENTHESES. */
    private static final String OPENING_PARENTHESES = "(";
    /** The Constant CLOSING_PARENTHESES. */
    private static final String CLOSING_PARENTHESES = ")";

    /** The Constant OPENING_PATTERN. */
    private static final String OPENING_PATTERN = "\\b(";
    /** The Constant CLOSING_PATTERN. */
    private static final String CLOSING_PATTERN = ")\\b";

    /** The Constant OPERATORS. */
    private static final String[] OPERATORS =
        {
        "\\*", "/", "\\+", "-"
        };

    /** The Constant COMPARISON. */
    private static final String[] COMPARISON =
        {
        "==", "\\!\\=", "\\<\\=",
        "\\>\\=", "\\<", "\\>"
        };

    /** The Constant RELATION. */
    private static final String[] RELATION =
        {
        "&&", "\\|\\|", "==>", "<==>",
        "<--", "\\+\\+"
        };

    /** The Constant MACROS. */
    private static final String[] MACROS =
        {
        "VOTES", "ELECT",
        "VOTE_SUM_FOR_CANDIDATE", "VOTE_SUM_FOR_UNIQUE_CANDIDATE"
        };

    /** The Constant QUANTIFIERS. */
    private static final String[] QUANTIFIERS =
        {
        "FOR_ALL_VOTERS", "FOR_ALL_CANDIDATES",
        "FOR_ALL_SEATS", "EXISTS_ONE_VOTER",
        "EXISTS_ONE_CANDIDATE", "EXISTS_ONE_SEAT",
        "PERM", "SPLIT",
        "INTERSECT", "NOTEMPTY"
        };

    /** The Constant OPERATORS_PATTERN. */
    private static final String OPERATORS_PATTERN =
            OPENING_PARENTHESES + String.join(PIPE, OPERATORS) + CLOSING_PARENTHESES;

    /** The Constant COMPARISON_PATTERN. */
    private static final String COMPARISON_PATTERN =
            OPENING_PARENTHESES + String.join(PIPE, COMPARISON) + CLOSING_PARENTHESES;

    /** The Constant RELATION_PATTERN. */
    private static final String RELATION_PATTERN =
            OPENING_PARENTHESES + String.join(PIPE, RELATION) + CLOSING_PARENTHESES;

    /** The Constant MACROS_PATTERN. */
    private static final String MACROS_PATTERN =
            OPENING_PATTERN + String.join(PIPE,
                    Arrays.stream(MACROS).map(s -> s + "[0-9]+").toArray(String[]::new))
            + CLOSING_PATTERN;

    /** The Constant QUANTIFIERS_PATTERN. */
    private static final String QUANTIFIERS_PATTERN =
            OPENING_PATTERN + String.join(PIPE, QUANTIFIERS) + CLOSING_PATTERN;

    /** The Constant PAREN_PATTERN. */
    private static final String PAREN_PATTERN = "\\(|\\)";

    /** The Constant SEMICOLON_PATTERN. */
    private static final String SEMICOLON_PATTERN = "\\;";

    /** The Constant OPERATORS_STRING. */
    private static final String OPERATORS_STRING = "OPERATORS";

    /** The Constant COMPARISON_STRING. */
    private static final String COMPARISON_STRING = "COMPARISON";

    /** The Constant RELATION_STRING. */
    private static final String RELATION_STRING = "RELATION";

    /** The Constant MACROS_STRING. */
    private static final String MACROS_STRING = "MACROS";

    /** The Constant QUANTIFIERS_STRING. */
    private static final String QUANTIFIERS_STRING = "QUANTIFIERS";

    /** The Constant PAREN_STRING. */
    private static final String PAREN_STRING = "PAREN";

    /** The Constant SEMICOLON_STRING. */
    private static final String SEMICOLON_STRING = "SEMICOLON";

    /** The Constant PATTERN. */
    private static final Pattern PATTERN =
            Pattern.compile(OPENING_PARENTHESES + QUERY
                                + LT_SIGN + OPERATORS_STRING + GT_SIGN
                                + OPERATORS_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + RELATION_STRING + GT_SIGN
                                + RELATION_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + COMPARISON_STRING + GT_SIGN
                                + COMPARISON_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + MACROS_STRING + GT_SIGN
                                + MACROS_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + QUANTIFIERS_STRING + GT_SIGN
                                + QUANTIFIERS_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + PAREN_STRING + GT_SIGN
                                + PAREN_PATTERN + CLOSING_PARENTHESES
                                + PIPE + OPENING_PARENTHESES + QUERY
                                + LT_SIGN + SEMICOLON_STRING + GT_SIGN
                                + SEMICOLON_PATTERN + CLOSING_PARENTHESES);

    /** The Constant RESOURCE. */
    private static final String RESOURCE = "propertyAreaSyntaxHighlight.css";

    /** The recommendations. */
    private static Set<String> recommendations = new TreeSet<String>();

    /** The description. */
    private FormalPropertiesDescription description;

    /** The parent. */
    private BooleanExpEditorNEW parent;

    /**
     * The constructor.
     */
    public NewPropertyCodeArea() {
        final NewPropertyCodeArea reference = this;
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean>
                                        observable,
                                final Boolean oldValue,
                                final Boolean newValue) {
                if (newValue != null && newValue) {
                    parent.setFocused(reference);
                }
            }
        });
        // add all standard recommendations
        recommendations.addAll(Arrays.asList(MACROS));
        recommendations.addAll(Arrays.asList(QUANTIFIERS));
        final String stylesheet =
                this.getClass().getResource(RESOURCE).toExternalForm();

        this.getStylesheets().add(stylesheet);
        final IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.richChanges()
                .filter(ch -> !ch.getInserted().equals(ch.getRemoved()))
                .subscribe(change -> {
                    this.setStyleSpans(0, computeHighlighting(this.getText()));
                });
        this.replaceText(0, 0, ""); // reset the text
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
                    matcher.group(OPERATORS_STRING) != null
                    ? OPERATORS_STRING.toLowerCase() : matcher.group(COMPARISON_STRING) != null
                    ? COMPARISON_STRING.toLowerCase() : matcher.group(RELATION_STRING) != null
                    ? RELATION_STRING.toLowerCase() : matcher.group(MACROS_STRING) != null
                    ? MACROS_STRING.toLowerCase() : matcher.group(QUANTIFIERS_STRING) != null
                    ? QUANTIFIERS_STRING.toLowerCase() : matcher.group(PAREN_STRING) != null
                    ? PAREN_STRING.toLowerCase() : matcher.group(SEMICOLON_STRING) != null
                    ? SEMICOLON_STRING.toLowerCase() : null;
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
     * Sets the description for this property codearea (either pre or post prop
     * description).
     *
     * @param descr
     *            the description
     */
    public void setDescription(final FormalPropertiesDescription descr) {
        saveDescription(descr);
        this.description = descr;
        this.replaceText(0, this.getLength(), descr.getCode());
    }

    /**
     * Save description.
     *
     * @param newDescription
     *            the new description
     */
    public void saveDescription(
            final FormalPropertiesDescription newDescription) {
        if (this.description != null) {
            this.description.setCode(this.textProperty().getValue());
        }
    }

    @Override
    public void autoComplete() {
        final Tuple3<List<String>, Integer, Integer> completion =
                getCompletions(recommendations);
        processAutoCompletion(completion.first(), completion.second(),
                              completion.third());
    }

    @Override
    public void insertAutoCompletion(final int start, final int end,
                                     final String toInsert) {
        replaceText(start, end, toInsert);
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
    public void undo() {
        super.undo();
    }

    @Override
    public void redo() {
        super.redo();
    }

    @Override
    public void cut() {
        super.cut();
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
        final IndexRange selectionRange = this.getSelection();
        this.replaceText(selectionRange, "");
    }

    /**
     * Sets the parent.
     *
     * @param parentEditor
     *            the new parent
     */
    public void setParent(final BooleanExpEditorNEW parentEditor) {
        this.parent = parentEditor;
    }
}