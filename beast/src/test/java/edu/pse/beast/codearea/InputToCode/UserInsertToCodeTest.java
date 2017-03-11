package edu.pse.beast.codearea.InputToCode;

import org.junit.Test;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import static org.junit.Assert.*;

/**
 * Created by holger on 11.03.17.
 */
public class UserInsertToCodeTest {
    private JTextPane pane = new JTextPane();
    private UserInsertToCode userInsertToCode;

    @Test
    public void testCantRemoveLineAfterLockedLineIfNotEmpty() throws BadLocationException {
        pane.setText("first line;\nsecound Line;");
        userInsertToCode = new UserInsertToCode(pane, null, null);
        userInsertToCode.lockLine(0);
        pane.setCaretPosition(12);
        userInsertToCode.removeToTheLeft();
        assertEquals(
                "first line;\nsecound Line;",
                pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength()));
    }

    @Test
    public void testcantRemoveToTheRightIfNextLineIsntEmpty() throws BadLocationException {
        pane.setText("first line;\nsecound Line;");
        userInsertToCode = new UserInsertToCode(pane, null, null);
        userInsertToCode.lockLine(0);
        pane.setCaretPosition(11);
        userInsertToCode.removeToTheLeft();
        assertEquals(
                "first line;\nsecound Line;",
                pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength()));
    }
}