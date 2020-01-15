package edu.pse.beast.codearea.actionlist.textaction;

/**
 *
 * @author Holger Klein
 */
public class TextDelta {
    private final int offset;
    private final String text;

    public TextDelta(final int offsetVal,
                     final String textStr) {
        this.offset = offsetVal;
        this.text = textStr;
    }

    public int getOffset() {
        return offset;
    }

    public String getText() {
        return text;
    }
}
