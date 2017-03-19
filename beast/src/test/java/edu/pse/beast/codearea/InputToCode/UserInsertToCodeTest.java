package edu.pse.beast.codearea.InputToCode;

import static org.junit.Assert.*;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.junit.Before;
import org.junit.Test;

import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;

/**
 * Created by holger on 16.03.17.
 */
public class UserInsertToCodeTest {
    private UserInsertToCode userInsertToCode;
    private JTextPane pane = new JTextPane();

    @Before
    public void setUp() {
        pane = new JTextPane();
        userInsertToCode = new UserInsertToCode(
                pane,
                new OpenCloseCharList(),
                new SaveTextBeforeRemove(pane, new Actionlist()));
    }

    @Test
    public void testInsertNewlinesEmptyPane() throws BadLocationException {
        assertEquals(pane.getText(), "");
        userInsertToCode.insertNewline();
        assertEquals(JTextPaneToolbox.getText(pane), "\n");
    }

    @Test
    public void testEnterNewlineCurly() throws BadLocationException {
        userInsertToCode.insertChar('{');
        userInsertToCode.insertNewline();
        assertEquals(JTextPaneToolbox.getText(pane), "{\n        \n}");
    }

    @Test
    public void testClosableCharCompletion() throws BadLocationException {
        userInsertToCode.insertChar('{');
        assertEquals(JTextPaneToolbox.getText(pane) , "{}");
        setUp();
        userInsertToCode.insertChar('(');
        assertEquals(JTextPaneToolbox.getText(pane) , "()");
        setUp();
        userInsertToCode.insertChar('[');
        assertEquals(JTextPaneToolbox.getText(pane) , "[]");
        setUp();
        userInsertToCode.insertChar('"');
        assertEquals(JTextPaneToolbox.getText(pane) , "\"\"");
    }

    @Test
    public void testRemoveLeft() throws BadLocationException {
        userInsertToCode.insertChar('n');
        pane.setCaretPosition(1);
        userInsertToCode.removeToTheLeft();
        assertEquals(JTextPaneToolbox.getText(pane), "");
    }

     @Test
    public void testRemoveLeftWhileSelected() {
        pane.setText("asdasd");
        pane.select(0, 6);
        userInsertToCode.removeToTheLeft();
        assertEquals("",JTextPaneToolbox.getText(pane));
    }

    @Test
    public void testRemoveToTheRight() throws BadLocationException {
        userInsertToCode.insertChar('n');
        pane.setCaretPosition(0);
        userInsertToCode.removeToTheRight();
        assertEquals(JTextPaneToolbox.getText(pane), "");
    }


    @Test
    public void testRemoveEightWhileSelected() {
        pane.setText("asdasd");
        pane.select(0, 6);
        userInsertToCode.removeToTheRight();
        assertEquals("",JTextPaneToolbox.getText(pane));
    }

    @Test
    public void testInsertTab() throws BadLocationException {
        userInsertToCode.insertTab();
        assertEquals("        ",JTextPaneToolbox.getText(pane));
    }

    @Test
    public void testRemoveTab() throws BadLocationException {
        pane.setText("        ");
        pane.setCaretPosition(8);
        userInsertToCode.removeTab();
        assertEquals("",JTextPaneToolbox.getText(pane));
    }
}