package edu.pse.beast.codearea.codeinput;

import javax.swing.JTextPane;

/**
 * This TabHandler calculates the amount of tabs needed at the beginning of
 * any given line by the amount of open curly braces in lines before. This
 * is sufficient for 99% of cases
 * @author Holger-Desktop
 */
public class CurlyBracesLineBeginningTabHandler implements LineBeginningTabsHandler {
    private JTextPane pane;
    
    public CurlyBracesLineBeginningTabHandler(JTextPane pane) {
        this.pane = pane;
    }

    @Override
    public int getTabsForLine(int caretPos) {
        int amt = 0;
        String code = JTextPaneToolbox.getText(pane);
        for (int pos = caretPos - 1; pos >= 0; --pos) {
            if (code.charAt(pos) == '{') {
                ++amt;
                while (pos >= 0 && code.charAt(pos) != '\n') --pos;
            } else if (code.charAt(pos) == '}') --amt;
        }

        if (caretPos < code.length() && code.charAt(caretPos) == '}') --amt;
        return amt;
    }
}
