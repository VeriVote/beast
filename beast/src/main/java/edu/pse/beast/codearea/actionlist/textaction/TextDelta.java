package edu.pse.beast.codearea.actionlist.textaction;

/**
 *
 * @author Holger-Desktop
 */
public class TextDelta {
    private final int offset;
    private final String text;
    
    public TextDelta(int offset, String text) {
        this.offset = offset;
        this.text = text;
    }
    
    public int getOffset() {
        return offset;
    }
    
    public String getText() {
        return text;
    }
    
}
