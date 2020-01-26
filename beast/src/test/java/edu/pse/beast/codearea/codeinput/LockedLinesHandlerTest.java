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
    /** The Constant ZERO. */
    private static final int ZERO = 0;
    /** The Constant ONE. */
    private static final int ONE = 1;
    /** The Constant TWO. */
    private static final int TWO = 2;
    /** The Constant THREE. */
    private static final int THREE = 3;
    /** The Constant FOUR. */
    private static final int FOUR = 4;
    /** The Constant FIVE. */
    private static final int FIVE = 5;
    /** The Constant EIGHT. */
    private static final int EIGHT = 8;
    /** The Constant NINE. */
    private static final int NINE = 9;
    /** The Constant ELEVEN. */
    private static final int ELEVEN = 11;

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
        final ActionList actionlist = new ActionList();
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
        pane.getStyledDocument().insertString(ZERO, TEXT, null);
        lockedLinesHandler.lockLine(ONE);
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
        pane.getStyledDocument().insertString(ZERO, LINE_BREAK, null);
        assertFalse(lockedLinesHandler.isLineLocked(ONE));
        assertTrue(lockedLinesHandler.isLineLocked(TWO));
        pane.getStyledDocument().insertString(ZERO, "asdasd\nasd\n\nasdasd", null);
        assertTrue(lockedLinesHandler.isLineLocked(FIVE));
    }

    /**
     * Test handles update directly at.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesUpdateDirectlyAt() throws BadLocationException {
        pane.getStyledDocument().insertString(ZERO, TEXT, null);
        lockedLinesHandler.lockLine(ONE);
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
        pane.getStyledDocument().insertString(FIVE, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
        pane.getStyledDocument().insertString(FOUR, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(TWO));
        pane.getStyledDocument().insertString(FIVE, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(THREE));
    }

    /**
     * Test handles removes.
     *
     * @throws BadLocationException
     *            the bad location exception
     */
    @Test
    public void testHandlesRemoves() throws BadLocationException {
        pane.getStyledDocument().insertString(ZERO, TEXT, null);
        lockedLinesHandler.lockLine(ONE);
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
        beforeRemove.save();
        pane.getStyledDocument().remove(THREE, ONE);
        assertTrue(lockedLinesHandler.isLineLocked(ZERO));
        lockedLinesHandler.lockLine(TWO);
        // String text = "asd\nasdasd\nasd\nasd\n\n\n";
        beforeRemove.save();
        pane.getStyledDocument().remove(EIGHT, THREE);
        assertTrue(lockedLinesHandler.isLineLocked(ZERO));
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
        // text = "asd\nasdaasd\nasd\n\n\n";
        beforeRemove.save();
        pane.getStyledDocument().remove(ELEVEN, ONE);
        assertTrue(lockedLinesHandler.isLineLocked(ZERO));
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
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
        pane.getStyledDocument().insertString(ZERO, text, null);
        lockedLinesHandler.lockLine(ONE);
        beforeRemove.save();
        pane.getStyledDocument().remove(TWO, ONE);
        assertEquals("\n}", pane.getStyledDocument().getText(ZERO, TWO));
        assertTrue(lockedLinesHandler.isLineLocked(ONE));
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
        pane.getStyledDocument().insertString(ZERO, text, null);
        lockedLinesHandler.lockLine(ZERO);
        lockedLinesHandler.lockLine(ONE);
        pane.getStyledDocument().insertString(NINE, LINE_BREAK, null);
        assertTrue(lockedLinesHandler.isLineLocked(ZERO));
        assertFalse(lockedLinesHandler.isLineLocked(ONE));
        assertTrue(lockedLinesHandler.isLineLocked(TWO));
    }
}
