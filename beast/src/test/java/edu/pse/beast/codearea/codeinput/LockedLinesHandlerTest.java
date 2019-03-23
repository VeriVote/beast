package edu.pse.beast.codearea.codeinput;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.codearea.SaveTextBeforeRemove;
import edu.pse.beast.codearea.actionlist.Actionlist;

/**
 *
 * @author Holger-Desktop
 */
public class LockedLinesHandlerTest {
    private LockedLinesHandler lockedLinesHandler;
    private JTextPane pane;
    private SaveTextBeforeRemove beforeRemove;

    public LockedLinesHandlerTest() {
	this.pane = new JTextPane();
	Actionlist actionlist = new Actionlist();
	beforeRemove = new SaveTextBeforeRemove(pane, actionlist);

	this.lockedLinesHandler = new LockedLinesHandler(pane, beforeRemove);
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    @Test
    public void testHandlesUpdates() throws BadLocationException {
	String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
	pane.getStyledDocument().insertString(0, text, null);
	lockedLinesHandler.lockLine(1);
	assertTrue(lockedLinesHandler.isLineLocked(1));
	;
	pane.getStyledDocument().insertString(0, "\n", null);
	assertFalse(lockedLinesHandler.isLineLocked(1));
	assertTrue(lockedLinesHandler.isLineLocked(2));
	pane.getStyledDocument().insertString(0, "asdasd\nasd\n\nasdasd", null);
	assertTrue(lockedLinesHandler.isLineLocked(5));
    }

    @Test
    public void testHandlesUpdateDirectlyAt() throws BadLocationException {
	String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
	pane.getStyledDocument().insertString(0, text, null);
	lockedLinesHandler.lockLine(1);
	assertTrue(lockedLinesHandler.isLineLocked(1));
	pane.getStyledDocument().insertString(5, "\n", null);
	assertTrue(lockedLinesHandler.isLineLocked(1));
	pane.getStyledDocument().insertString(4, "\n", null);
	assertTrue(lockedLinesHandler.isLineLocked(2));
	pane.getStyledDocument().insertString(5, "\n", null);
	assertTrue(lockedLinesHandler.isLineLocked(3));
    }

    @Test
    public void testHandlesRemoves() throws BadLocationException {
	String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
	pane.getStyledDocument().insertString(0, text, null);
	lockedLinesHandler.lockLine(1);
	assertTrue(lockedLinesHandler.isLineLocked(1));
	beforeRemove.save();
	pane.getStyledDocument().remove(3, 1);
	assertTrue(lockedLinesHandler.isLineLocked(0));
	lockedLinesHandler.lockLine(2);
	text = "asd\nasdasd\nasd\nasd\n\n\n";
	beforeRemove.save();
	pane.getStyledDocument().remove(8, 3);
	assertTrue(lockedLinesHandler.isLineLocked(0));
	assertTrue(lockedLinesHandler.isLineLocked(1));
	text = "asd\nasdaasd\nasd\n\n\n";
	beforeRemove.save();
	pane.getStyledDocument().remove(11, 1);
	assertTrue(lockedLinesHandler.isLineLocked(0));
	assertTrue(lockedLinesHandler.isLineLocked(1));
    }

    @Test
    public void testHandlesRemovesOneBracket() throws BadLocationException {
	String text = "\n}\n";
	pane.getStyledDocument().insertString(0, text, null);
	lockedLinesHandler.lockLine(1);
	beforeRemove.save();
	pane.getStyledDocument().remove(2, 1);
	assertEquals("\n}", pane.getStyledDocument().getText(0, 2));
	assertTrue(lockedLinesHandler.isLineLocked(1));
    }

    @Test
    public void testHandlesAddNewlineAtEndOfLine() throws BadLocationException {
	String text = "asdasdasd\nasd";
	pane.getStyledDocument().insertString(0, text, null);
	lockedLinesHandler.lockLine(0);
	lockedLinesHandler.lockLine(1);
	pane.getStyledDocument().insertString(9, "\n", null);
	assertTrue(lockedLinesHandler.isLineLocked(0));
	assertFalse(lockedLinesHandler.isLineLocked(1));
	assertTrue(lockedLinesHandler.isLineLocked(2));
    }
}