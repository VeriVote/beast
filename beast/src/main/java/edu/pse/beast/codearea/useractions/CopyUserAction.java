package edu.pse.beast.codearea.useractions;

import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

import javax.swing.JTextPane;

import edu.pse.beast.toolbox.UserAction;

/**
 * This useraction takes the selected string in the given textpane and copies it
 * to the clipboard.
 *
 * @author Holger Klein
 */
public class CopyUserAction extends UserAction {

    /** The pane. */
    private JTextPane pane;

    /** The clipboard. */
    private Clipboard clipboard;

    /**
     * Instantiates a new copy user action.
     *
     * @param textPane the text pane
     */
    public CopyUserAction(final JTextPane textPane) {
        super("copy");
        this.pane = textPane;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    @Override
    public void perform() {
        StringSelection stringSelection = new StringSelection(pane.getSelectedText());
        clipboard.setContents(stringSelection, null);
    }
}
