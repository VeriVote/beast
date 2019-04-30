package edu.pse.beast.codeareajavafx;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import org.fxmisc.richtext.CodeArea;

import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.toolbox.Tuple3;
import edu.pse.beast.toolbox.Tuple;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public abstract class AutoCompletionCodeArea extends CodeArea {
    private static final String CONTROL_CHARACTERS = "\\p{Cntrl}";
    private static final String WORD = "([A-Za-z]+[A-Za-z0-9]*)";
    private static final String WHITESPACE = " ";
    private static final String SPECIAL_CHARACTER_REGEX
        = ",|;|\\.|\\(|\\)|\\{|\\}|\\[|\\|\\|/|\\+|-|\\*";

    private int start;
    private int end;

    public AutoCompletionCodeArea() {
        this.focusedProperty().addListener(new ChangeListener<Boolean>() {
            @Override
            public void changed(ObservableValue<? extends Boolean> ov,
                                Boolean onHidden, Boolean onShown) {
                if (onShown) {
                    GUIController.getController().getAutoCompleter().reset();
                }
            }
        });
    }

    private Tuple<Integer, Integer> getAbsolutCaretPosition() {
        return new Tuple<Integer, Integer>((int) this.getCaretBounds().get().getMaxX() + 4,
                (int) this.getCaretBounds().get().getMaxY() + 4);
    }

    public void processAutocompletion(List<String> content,
                                      Integer start,
                                      Integer end) {
        Tuple<Integer, Integer> position = getAbsolutCaretPosition();
        this.start = start;
        this.end = end;
        if (content.size() == 1) { // there is only one element, so the user probably want that one
            insertAutoCompletion(start, end, content.get(0));
        } else {
            GUIController.getController().getAutoCompleter()
            .showAutoCompletionWindows(position.x, position.y, content, this);
        }
    }

    public abstract void insertAutoCompletion(int start, int end,
                                              String toInsert);

    protected Tuple3<List<String>, Integer, Integer>
                getCompletions(Set<String> recommendations) {
        String completeText = this.getText();
        int prefixEnd = caretPositionProperty().getValue();
        int prefixStart = prefixEnd;
        String prefix = "";

        if (prefixEnd > 0) {
            for (int i = prefixEnd - 1; i >= 0; i--) {
                char tmp = completeText.charAt(i);

                if (tmp == ' '
                        | ("" + tmp).matches(SPECIAL_CHARACTER_REGEX)
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
        completeText = completeText.replaceAll(SPECIAL_CHARACTER_REGEX, WHITESPACE);
        completeText = completeText.replaceAll("\\s+", WHITESPACE);

        Set<String> possibilities = new TreeSet<String>(recommendations);
        possibilities.addAll(
                GUIController.getController().getBooleanExpEditor().getSymbolicVariableNames()
        );
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
        } else { // we already have started a word, so we have to filter out all non fitting
                 // words
            for (Iterator<String> iterator = possibilities.iterator(); iterator.hasNext();) {
                String str = (String) iterator.next();
                if (str != null && str.toLowerCase().startsWith(prefix.toLowerCase())) {
                    possibleList.add(str);
                }
            }
        }
        if (possibleList.size() == 1) {
        }

        return new Tuple3<List<String>, Integer, Integer>(possibleList, prefixStart, prefixEnd);
    }

    public void insertHiddenAutoCompletion(String toInsert) {
        insertAutoCompletion(start, end, toInsert);
    }
}