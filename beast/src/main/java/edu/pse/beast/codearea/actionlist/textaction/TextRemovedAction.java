package edu.pse.beast.codearea.actionlist.textaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.codearea.actionlist.Action;

/**
 * This class represents the act of removing text from a Styleddocument and
 * makes it possible to undo this action
 *
 * @author Holger-Desktop
 */
public class TextRemovedAction implements Action {
    private final TextDelta td;
    private final StyledDocument doc;

    public TextRemovedAction(TextDelta td, StyledDocument doc) {
        this.doc = doc;
        this.td = td;
    }

    @Override
    public void undo() {
        try {
            doc.insertString(td.getOffset(), td.getText(), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redo() {
        try {
            doc.remove(td.getOffset(), td.getText().length());
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}