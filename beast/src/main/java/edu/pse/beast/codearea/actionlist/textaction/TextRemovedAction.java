package edu.pse.beast.codearea.actionlist.textaction;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

import edu.pse.beast.codearea.actionlist.Action;

/**
 * This class represents the act of removing text from a styled document and
 * makes it possible to undo this action.
 *
 * @author Holger Klein
 */
public final class TextRemovedAction implements Action {

    /** The td. */
    private final TextDelta td;

    /** The doc. */
    private final StyledDocument doc;

    /**
     * Instantiates a new text removed action.
     *
     * @param textDelta
     *            the text delta
     * @param styledDoc
     *            the styled doc
     */
    public TextRemovedAction(final TextDelta textDelta,
                             final StyledDocument styledDoc) {
        this.doc = styledDoc;
        this.td = textDelta;
    }

    @Override
    public void undo() {
        try {
            doc.insertString(td.getOffset(), td.getText(), null);
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void redo() {
        try {
            doc.remove(td.getOffset(), td.getText().length());
        } catch (BadLocationException ex) {
            Logger.getLogger(TextAddedAction.class.getName())
                    .log(Level.SEVERE, null, ex);
        }
    }
}
