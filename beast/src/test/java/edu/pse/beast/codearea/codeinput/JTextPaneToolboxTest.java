package edu.pse.beast.codearea.codeinput;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;

import javax.swing.JTextPane;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * The tests for JTextPaneToolBox.
 *
 * @author Holger Klein
 */
public class JTextPaneToolboxTest {
    /** The Constant ZERO. */
    private static final int ZERO = 0;
    /** The Constant ONE. */
    private static final int ONE = 1;
    /** The Constant TWO. */
    private static final int TWO = 2;
    /** The Constant FOUR. */
    private static final int FOUR = 4;
    /** The Constant FIVE. */
    private static final int FIVE = 5;
    /** The Constant SIX. */
    private static final int SIX = 6;

    /** The Constant ABS_POS. */
    private static final int ABS_POS = 3;

    /** A test text string. */
    private static final String TEXT = "12\n\n34\n5\n6";
    /** Another test text string. */
    private static final String TEXT_TWO = "123456";
    /** Yet another test text string. */
    private static final String TEXT_THREE = "123\n456";

    /** The test string "abc". */
    private static final String ABC = "abc";
    /** The test string "a". */
    private static final String A = "a";
    /** The test string "b". */
    private static final String B = "b";
    /** The test string "c". */
    private static final String C = "c";

    /**
     * Instantiates a new j text pane tool box test.
     */
    public JTextPaneToolboxTest() {
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
     * Test of transformToLineNumber method, of class JTextPaneToolbox.
     */
    @Test
    public void testTransformToLineNumber() {
        final JTextPane pane = new JTextPane();
        pane.setText(TEXT);
        assertEquals(ONE, JTextPaneToolbox.transformToLineNumber(pane, ABS_POS));
    }

    /**
     * Test of getLineBeginning method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetDistanceToClosestLineBeginning() {
        final JTextPane pane = new JTextPane();
        pane.setText(TEXT_TWO);
        pane.setCaretPosition(ABS_POS);
        assertEquals(FOUR, JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR));
        pane.setText(TEXT_THREE);
        assertEquals(ZERO, JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR));
        assertEquals(TWO, JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, SIX));
    }

    /**
     * Test of getLinesBetween method, of class JTextPaneToolbox.
     *
     * @throws Exception when location does not exist
     */
    @Test
    public void testGetLinesBetween() throws Exception {
        final JTextPane pane = new JTextPane();
        pane.setText(TEXT);
        final ArrayList<Integer> linesBetween =
                JTextPaneToolbox.getLinesBetween(pane, ZERO, FIVE);
        final int[] lines = {ZERO, ONE, TWO};
        assertEquals(lines.length, linesBetween.size());
        for (int i = ZERO; i < lines.length; i++) {
            assertEquals(lines[i], (int) linesBetween.get(i));
        }
    }

    /**
     * Test of getCharToTheLeftOfCaret method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetCharToTheLeftOfCaret() {
        final JTextPane pane = new JTextPane();
        pane.setText(ABC);
        pane.setCaretPosition(ZERO);
        assertEquals("", JTextPaneToolbox.getCharToTheLeftOfCaret(pane));
        pane.setCaretPosition(ONE);
        assertEquals(A, JTextPaneToolbox.getCharToTheLeftOfCaret(pane));
        pane.setCaretPosition(TWO);
        assertEquals(B, JTextPaneToolbox.getCharToTheLeftOfCaret(pane));
        pane.setCaretPosition(ABS_POS);
        assertEquals(C, JTextPaneToolbox.getCharToTheLeftOfCaret(pane));
    }

    /**
     * Test of getCharToTheRightOfCaret method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetCharToTheRightOfCaret() {
        final JTextPane pane = new JTextPane();
        pane.setText(ABC);
        pane.setCaretPosition(ZERO);
        assertEquals(A, JTextPaneToolbox.getCharToTheRightOfCaret(pane));
        pane.setCaretPosition(ONE);
        assertEquals(B, JTextPaneToolbox.getCharToTheRightOfCaret(pane));
        pane.setCaretPosition(TWO);
        assertEquals(C, JTextPaneToolbox.getCharToTheRightOfCaret(pane));
        pane.setCaretPosition(ABS_POS);
        assertEquals("", JTextPaneToolbox.getCharToTheRightOfCaret(pane));
    }
}
