package edu.pse.beast.codeareajavafx;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.IntFunction;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.fxmisc.richtext.LineNumberFactory;
import org.fxmisc.richtext.model.StyleSpans;
import org.fxmisc.richtext.model.StyleSpansBuilder;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.highlevel.javafx.BooleanExpEditorNEW;
import edu.pse.beast.highlevel.javafx.MenuBarInterface;
import edu.pse.beast.toolbox.Triplet;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.Node;
import javafx.scene.control.IndexRange;

public class NewPropertyCodeArea extends AutoCompletionCodeArea implements MenuBarInterface {
    private static final String[] OPERATORS = {"\\*", "/", "\\+", "-"};
    private static final String[] COMPARISON =
        {"==", "\\!\\=", "\\<\\=", "\\>\\=", "\\<", "\\>"};
    private static final String[] RELATION =
        {"&&", "\\|\\|", "==>", "<==>", "<--", "\\+\\+"};
    private static final String[] MACROS =
    {
        "VOTES", "ELECT", "VOTE_SUM_FOR_CANDIDATE",
        "VOTE_SUM_FOR_UNIQUE_CANDIDATE"
    };
    private static final String[] QUANTIFIERS =
    {
        "FOR_ALL_VOTERS", "FOR_ALL_CANDIDATES", "FOR_ALL_SEATS",
        "EXISTS_ONE_VOTER", "EXISTS_ONE_CANDIDATE", "EXISTS_ONE_SEAT",
        "PERM", "SPLIT", "INTERSECT", "NOTEMPTY"
    };
    private static final String OPERATORS_PATTERN =
            "(" + String.join("|", OPERATORS) + ")";
    private static final String COMPARISON_PATTERN =
            "(" + String.join("|", COMPARISON) + ")";
    private static final String RELATION_PATTERN =
            "(" + String.join("|", RELATION) + ")";

    private static final String MACROS_PATTERN =
            "\\b("
            + String.join(
                "|",
                Arrays.stream(MACROS).map(s
                    -> s + "[0-9]+").toArray(String[]::new)) + ")\\b";
    private static final String QUANTIFIERS_PATTERN =
            "\\b(" + String.join("|", QUANTIFIERS) + ")\\b";

    private static final String PAREN_PATTERN = "\\(|\\)";
    private static final String SEMICOLON_PATTERN = "\\;";

    private static final String OPERATORS_STRING = "OPERATORS";
    private static final String COMPARISON_STRING = "COMPARISON";
    private static final String RELATION_STRING = "RELATION";
    private static final String MACROS_STRING = "MACROS";
    private static final String QUANTIFIERS_STRING = "QUANTIFIERS";
    private static final String PAREN_STRING = "PAREN";
    private static final String SEMICOLON_STRING = "SEMICOLON";

    private static final Pattern PATTERN = Pattern.compile(
            "(?<" + OPERATORS_STRING + ">" + OPERATORS_PATTERN + ")"
            + "|(?<" + COMPARISON_STRING + ">" + COMPARISON_PATTERN + ")"
            + "|(?<" + RELATION_STRING + ">" + RELATION_PATTERN + ")"
            + "|(?<" + MACROS_STRING + ">" + MACROS_PATTERN + ")"
            + "|(?<" + QUANTIFIERS_STRING + ">" + QUANTIFIERS_PATTERN + ")"
            + "|(?<" + PAREN_STRING + ">" + PAREN_PATTERN + ")"
            + "|(?<" + SEMICOLON_STRING + ">" + SEMICOLON_PATTERN + ")");

    private static final String RESOURCE = "propertyAreaSyntaxHighlight.css";

    private static Set<String> recommendations = new TreeSet<String>();

    private FormalPropertiesDescription description;

    private BooleanExpEditorNEW parent;

    public NewPropertyCodeArea() {
        NewPropertyCodeArea reference = this;
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> observable,
                                Boolean oldValue,
                                Boolean newValue) {
                if (newValue != null && newValue) {
                    parent.setFocused(reference);
                }
            }
        });
        // add all standard recommendations
        recommendations.addAll(Arrays.asList(MACROS));
        recommendations.addAll(Arrays.asList(QUANTIFIERS));
        String stylesheet = this.getClass().getResource(RESOURCE).toExternalForm();

        this.getStylesheets().add(stylesheet);
        IntFunction<Node> lineNumbers = LineNumberFactory.get(this);
        this.setParagraphGraphicFactory(lineNumbers);
        this.richChanges().filter(ch
            -> !ch.getInserted().equals(ch.getRemoved())).subscribe(change
                -> {
                this.setStyleSpans(0, computeHighlighting(this.getText()));
            });
        this.replaceText(0, 0, ""); // reset the text
    }

    private static StyleSpans<Collection<String>> computeHighlighting(String text) {
        Matcher matcher = PATTERN.matcher(text);
        int lastKwEnd = 0;
        StyleSpansBuilder<Collection<String>> spansBuilder = new StyleSpansBuilder<>();
        while (matcher.find()) {
            String styleClass =
                matcher.group(OPERATORS_STRING) != null
                ? OPERATORS_STRING.toLowerCase()
                    : matcher.group(COMPARISON_STRING) != null
                    ? COMPARISON_STRING.toLowerCase()
                        : matcher.group(RELATION_STRING) != null
                        ? RELATION_STRING.toLowerCase()
                            : matcher.group(MACROS_STRING) != null
                            ? MACROS_STRING.toLowerCase()
                                : matcher.group(QUANTIFIERS_STRING) != null
                                ? QUANTIFIERS_STRING.toLowerCase()
                                    : matcher.group(PAREN_STRING) != null
                                    ? PAREN_STRING.toLowerCase()
                                        : matcher.group(SEMICOLON_STRING) != null
                                        ? SEMICOLON_STRING.toLowerCase()
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
     * sets the description for this property codearea (either pre or post prop
     * description)
     *
     * @param description the description
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

    public void autoComplete() {
        Triplet<List<String>, Integer, Integer> completion = getCompletions(recommendations);
        processAutocompletion(completion.first, completion.second, completion.third);
    }

    @Override
    public void insertAutoCompletion(int start, int end, String toInsert) {
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
        IndexRange selectionRange = this.getSelection();
        this.replaceText(selectionRange, "");
    }

    public void setParent(BooleanExpEditorNEW parent) {
        this.parent = parent;
    }
}