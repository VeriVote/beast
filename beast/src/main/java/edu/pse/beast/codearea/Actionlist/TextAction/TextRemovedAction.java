/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Actionlist.TextAction;

import edu.pse.beast.codearea.Actionlist.Action;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;

/**
 * This class represents the act of removing text from a Styleddocument
 * and makes it possible to undo this action
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
