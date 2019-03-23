package edu.pse.beast.codearea.codeinput;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 * This class is utilized by userinserttocode to automatically close chars
 * which have a closing complements, such as { and }
 * @author Holger-Desktop
 */
public class OpenCloseChar {
    private final char open;
    private final char close;
    
    public OpenCloseChar(char open, char close) {
        this.open = open;
        this.close = close;
    }
    
    public char getOpen() {
        return this.open;
    }
    
    public char getClose() {
        return this.close;
    }

    /**
     * the open char is inserted into the given pane at the given pos. It also 
     * automatically inserts the closing char and centers the caret pos between them
     * asd | becomes asd {|}
     * @param pane the JTextPane in which the open and close chars should be isnerted
     * @param pos the position at which the chars should be inserted
     * @throws BadLocationException if the position isn't valid
     */
    public void insertIntoDocument(JTextPane pane, int pos) throws BadLocationException {        
        String stringToInsert = Character.toString(open) + Character.toString(close);
        pane.getStyledDocument().insertString(pos, stringToInsert, null);
        pane.setCaretPosition(pos + 1);
    }
}
