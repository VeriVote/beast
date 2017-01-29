/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.UserActions;

import edu.pse.beast.toolbox.UserAction;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import java.awt.*;
import java.awt.datatransfer.Clipboard;
import java.awt.datatransfer.StringSelection;

/**
 *
 * @author Holger-Desktop
 */
public class CutUserAction extends UserAction  {
    private JTextPane pane;
    private Clipboard clipboard;

    public CutUserAction(JTextPane pane) {
        super("cut");
        this.pane = pane;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    @Override
    public void perform() {
        StringSelection stringSelection = new StringSelection(pane.getSelectedText());
        clipboard.setContents(stringSelection, null);
        int selectionStart = pane.getSelectionStart();
        int selectionEnd = pane.getSelectionEnd();
        try {
            pane.getStyledDocument().remove(selectionStart, selectionEnd - selectionStart);
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
