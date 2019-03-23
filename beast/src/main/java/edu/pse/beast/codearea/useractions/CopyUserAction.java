package edu.pse.beast.codearea.useractions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTextPane;

import edu.pse.beast.toolbox.UserAction;

/**
 * This useraction takes the selected string in the given textpane and copies it
 * to the clipboard
 *
 * @author Holger-Desktop
 */
public class CopyUserAction extends UserAction {
    private JTextPane pane;
    private Clipboard clipboard;

    public CopyUserAction(JTextPane pane) {
        super("copy");
        this.pane = pane;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    @Override
    public void perform() {
        StringSelection stringSelection = new StringSelection(pane.getSelectedText());
        clipboard.setContents(stringSelection, null);
    }
}