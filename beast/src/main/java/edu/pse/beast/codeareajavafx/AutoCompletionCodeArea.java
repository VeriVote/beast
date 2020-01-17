package edu.pse.beast.codeareajavafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.Tuple;
import edu.pse.beast.toolbox.Tuple3;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Bounds;

/**
 * The Class AutoCompletionCodeArea.
 */
public abstract class AutoCompletionCodeArea extends CodeArea {

    /** The Constant BOUND_OFFSET. */
    private static final int BOUND_OFFSET = 4;

    /** The Constant CONTROL_CHARACTERS. */
    private static final String CONTROL_CHARACTERS = "\\p{Cntrl}";

    /** The Constant WORD. */
    private static final String WORD = "([A-Za-z]+[A-Za-z0-9]*)";

    /** The Constant WHITESPACE. */
    private static final String WHITESPACE = " ";

    /** The Constant SPECIAL_CHARACTER_REGEX. */
    private static final String SPECIAL_CHARACTER_REGEX =
            ",|;|\\.|\\(|\\)|\\{|\\}|\\[|\\|\\|/|\\+|-|\\*";

    /** The start. */
    private int start;

    /** The end. */
    private int end;

    /**
     * The constructor.
     */
    public AutoCompletionCodeArea() {
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(final ObservableValue<? extends Boolean> ov,
                                final Boolean onHidden, final Boolean onShown) {
                if (onShown) {
                    GUIController.getController().getAutoCompleter().reset();
                }
            }
        });
    }

    /**
     * Gets the absolut caret position.
     *
     * @return the absolut caret position
     */
    private Tuple<Integer, Integer> getAbsolutCaretPosition() {
        final Bounds bounds = this.getCaretBounds().get();
        return new Tuple<Integer, Integer>(
                (int) bounds.getMaxX() + BOUND_OFFSET,
                (int) bounds.getMaxY() + BOUND_OFFSET);
    }

    /**
     * Process autocompletion.
     *
     * @param content
     *            the content
     * @param startIdx
     *            the start idx
     * @param endIdx
     *            the end idx
     */
    public void processAutoCompletion(final List<String> content,
                                      final Integer startIdx,
                                      final Integer endIdx) {
        Tuple<Integer, Integer> position = getAbsolutCaretPosition();
        this.start = startIdx;
        this.end = endIdx;
        if (content.size() == 1) {
            // there is only one element, so the user probably wants that one
            insertAutoCompletion(startIdx, endIdx, content.get(0));
        } else {
            GUIController.getController().getAutoCompleter()
                    .showAutoCompletionWindows(position.first(),
                                               position.second(),
                                               content, this);
        }
    }

    /**
     * Process autocompletion.
     *
     * @param completion
     *            the completion
     */
    public void processAutoCompletion(final Tuple3<List<String>,
                                                   Integer,
                                                   Integer> completion) {
        processAutoCompletion(completion.first(), completion.second(),
                              completion.third());
    }

    /**
     * Insert auto completion.
     *
     * @param startIdx
     *            the start idx
     * @param endIdx
     *            the end idx
     * @param toInsert
     *            the to insert
     */
    public abstract void insertAutoCompletion(int startIdx, int endIdx,
                                              String toInsert);

    /**
     * Gets the completions.
     *
     * @param recommendations
     *            the recommendations
     * @return the completions
     */
    protected Tuple3<List<String>, Integer, Integer>
                getCompletions(final Set<String> recommendations) {
        String completeText = this.getText();
        int prefixEnd = caretPositionProperty().getValue();
        int prefixStart = prefixEnd;
        String prefix = "";

        if (prefixEnd > 0) {
            for (int i = prefixEnd - 1; i >= 0; i--) {
                char tmp = completeText.charAt(i);

                if (tmp == ' ' | ("" + tmp).matches(SPECIAL_CHARACTER_REGEX)
                        | ("" + tmp).matches(CONTROL_CHARACTERS)) {
                    if (!prefix.matches(WORD)) {
                        prefix = "";
                    }
                    break;
                } else {
                    prefix = tmp + prefix;
                    prefixStart = i;
                }
            }
        } else {
            prefix = "";
        }

        if (!prefix.equals("")) {
            StringBuilder builder = new StringBuilder(completeText);
            builder.delete(prefixStart, prefixEnd);
            completeText = builder.toString();
        }

        completeText = completeText.replaceAll(CONTROL_CHARACTERS, WHITESPACE);
        completeText =
                completeText.replaceAll(SPECIAL_CHARACTER_REGEX, WHITESPACE);
        completeText = completeText.replaceAll("\\s+", WHITESPACE);

        Set<String> possibilities = new TreeSet<String>(recommendations);
        possibilities.addAll(GUIController.getController().getBooleanExpEditor()
                .getSymbolicVariableNames());
        // split on white spaces to extract the words
        String[] split = completeText.split(WHITESPACE);

        for (int i = 0; i < split.length; i++) {
            if (split[i].matches(WORD)) {
                possibilities.add(split[i]);
            }
        }

        List<String> possibleList = new ArrayList<String>();
        if (prefix.equals("")) {
            possibleList.addAll(possibilities);
        } else { // we already have started a word, so we have to filter out all
                 // non fitting
                 // words
            for (Iterator<String> iterator = possibilities.iterator();
                    iterator.hasNext();) {
                String str = iterator.next();
                if (str != null
                        && str.toLowerCase().startsWith(prefix.toLowerCase())) {
                    possibleList.add(str);
                }
            }
        }
        // if (possibleList.size() == 1) {
        // }

        return new Tuple3<List<String>, Integer, Integer>(possibleList,
                                                          prefixStart,
                                                          prefixEnd);
    }

    /**
     * Insert hidden auto completion.
     *
     * @param toInsert
     *            the to insert
     */
    public void insertHiddenAutoCompletion(final String toInsert) {
        insertAutoCompletion(start, end, toInsert);
    }
}
