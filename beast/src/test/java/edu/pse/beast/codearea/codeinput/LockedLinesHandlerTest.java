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
import edu.pse.beast.codearea.actionlist.ActionList;

/**
 * The tests for LockedLinesHandler.
 *
 * @author Holger Klein
 */
public class LockedLinesHandlerTest {
    /** The line break string. */
    private static final String LINE_BREAK = "\n";
    /** A test text string. */
    private static final String TEXT = "asd\n\nasdasd\nasd\nasd\n\n\n";

    /** The instance. */
    private LockedLinesHandler lockedLinesHandler;
    /** A text pane. */
    private JTextPane pane;
    /** A SaveTextBeforeRemove instance. */
    private SaveTextBeforeRemove beforeRemove;

    /**
     * Instantiates a new locked lines handler test.
     */
    public LockedLinesHandlerTest() {
        this.pane = new JTextPane();
        ActionList actionlist = new ActionList();
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

    /**
     * Test handles updates.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesUpdates() throws BadLocationException {
        pane.getStyledDocument().insertString(0, TEXT, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1));
        pane.getStyledDocument().insertString(0, LINE_BREAK, null);
        assertFalse(lockedLinesHandler.isLineLocked(1));
        assertTrue(lockedLinesHandler.isLineLocked(2));
        pane.getStyledDocument().insertString(0, "asdasd\nasd\n\nasdasd", null);
        assertTrue(lockedLinesHandler.isLineLocked(5));
    }

    /**
     * Test handles update directly at.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesUpdateDirectlyAt() throws BadLocationException {
        pane.getStyledDocument().insertString(0, TEXT, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1));
        pane.getStyledDocument().insertString(5, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(1));
        pane.getStyledDocument().insertString(4, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(2));
        pane.getStyledDocument().insertString(5, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(3));
    }

    /**
     * Test handles removes.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesRemoves() throws BadLocationException {
        pane.getStyledDocument().insertString(0, TEXT, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1));
        beforeRemove.save();
        pane.getStyledDocument().remove(3, 1);
        assertTrue(lockedLinesHandler.isLineLocked(0));
        lockedLinesHandler.lockLine(2);
        // String text = "asd\nasdasd\nasd\nasd\n\n\n";
        beforeRemove.save();
        pane.getStyledDocument().remove(8, 3);
        assertTrue(lockedLinesHandler.isLineLocked(0));
        assertTrue(lockedLinesHandler.isLineLocked(1));
        // text = "asd\nasdaasd\nasd\n\n\n";
        beforeRemove.save();
        pane.getStyledDocument().remove(11, 1);
        assertTrue(lockedLinesHandler.isLineLocked(0));
        assertTrue(lockedLinesHandler.isLineLocked(1));
    }

    /**
     * Test handles removes one bracket.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesRemovesOneBracket() throws BadLocationException {
        final String text = "\n}\n";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(1);
        beforeRemove.save();
        pane.getStyledDocument().remove(2, 1);
        assertEquals("\n}", pane.getStyledDocument().getText(0, 2));
        assertTrue(lockedLinesHandler.isLineLocked(1));
    }

    /**
     * Test handles add newline at end of line.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesAddNewlineAtEndOfLine() throws BadLocationException {
        final String text = "asdasdasd\nasd";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(0);
        lockedLinesHandler.lockLine(1);
        pane.getStyledDocument().insertString(9, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(0));
        assertFalse(lockedLinesHandler.isLineLocked(1));
        assertTrue(lockedLinesHandler.isLineLocked(2));
    }
}
