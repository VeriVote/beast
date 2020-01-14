package edu.pse.beast.codearea.actionlist.textaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.codearea.actionlist.Action;

/**
 * This class represents the act of adding text to a Styleddocument and makes
 * undoing this action possible
 *
 * @author Holger Klein
 */
public class TextAddedAction implements Action {

    private final TextDelta td;
    private final StyledDocument doc;

    public TextAddedAction(TextDelta td, StyledDocument doc) {
        this.doc = doc;
        this.td = td;
    }

    @Override
    public void undo() {
        try {
            doc.remove(td.getOffset(), td.getText().length());
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redo() {
        try {
            doc.insertString(td.getOffset(), td.getText(), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
