package edu.pse.beast.codearea.actionlist.textaction;

/**
 * The Class TextDelta.
 *
 * @author Holger Klein
 */
public class TextDelta {

    /** The offset. */
    private final int offset;

    /** The text. */
    private final String text;

    /**
     * Instantiates a new text delta.
     *
     * @param offsetVal the offset val
     * @param textStr the text str
     */
    public TextDelta(final int offsetVal,
                     final String textStr) {
        this.offset = offsetVal;
        this.text = textStr;
    }

    /**
     * Gets the offset.
     *
     * @return the offset
     */
    public int getOffset() {
        return offset;
    }

    /**
     * Gets the text.
     *
     * @return the text
     */
    public String getText() {
        return text;
    }
}
