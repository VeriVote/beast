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
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.IOException;

/**
 *
 * @author Holger-Desktop
 */
public class PasteUserAction extends UserAction {
    private JTextPane pane;
    private Clipboard clipboard;

    public PasteUserAction(JTextPane pane) {
        super("paste");
        this.pane = pane;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }

    @Override
    public void perform() {
        try {
            String clipboardString = (String) clipboard.getData(DataFlavor.stringFlavor);
            if (pane.getSelectedText() == null) {
                pane.getStyledDocument().insertString(pane.getCaretPosition(), clipboardString, null);
            } else {
                int start = pane.getSelectionStart();
                int end = pane.getSelectionEnd();
                pane.getStyledDocument().remove(start, end - start);
                pane.getStyledDocument().insertString(start, clipboardString, null);
            }
        } catch (UnsupportedFlavorException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
    }
}
