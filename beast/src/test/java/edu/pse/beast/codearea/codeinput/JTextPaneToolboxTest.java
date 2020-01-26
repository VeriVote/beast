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
        JTextPane pane = new JTextPane();
        pane.setText(TEXT);
        int lineno = JTextPaneToolbox.transformToLineNumber(pane, ABS_POS);
        assertEquals(ONE, lineno);
    }

    /**
     * Test of getLineBeginning method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetDistanceToClosestLineBeginning() {
        JTextPane pane = new JTextPane();
        pane.setText(TEXT_TWO);
        pane.setCaretPosition(ABS_POS);
        int dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR);
        assertEquals(FOUR, dist);
        pane.setText(TEXT_THREE);
        dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR);
        assertEquals(ZERO, dist);
        dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, SIX);
        assertEquals(TWO, dist);
    }

    /**
     * Test of getLinesBetween method, of class JTextPaneToolbox.
     *
     * @throws Exception when location does not exist
     */
    @Test
    public void testGetLinesBetween() throws Exception {
        JTextPane pane = new JTextPane();
        pane.setText(TEXT);
        ArrayList<Integer> linesBetween =
                JTextPaneToolbox.getLinesBetween(pane, ZERO, FIVE);
        int[] lines = {ZERO, ONE, TWO};
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
        JTextPane pane = new JTextPane();
        pane.setText(ABC);
        pane.setCaretPosition(ZERO);
        String lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals("", lhs);
        pane.setCaretPosition(ONE);
        lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals(A, lhs);
        pane.setCaretPosition(TWO);
        lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals(B, lhs);
        pane.setCaretPosition(ABS_POS);
        lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals(C, lhs);
    }

    /**
     * Test of getCharToTheRightOfCaret method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetCharToTheRightOfCaret() {
        JTextPane pane = new JTextPane();
        pane.setText(ABC);
        pane.setCaretPosition(ZERO);
        String rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(A, rhs);
        pane.setCaretPosition(ONE);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(B, rhs);
        pane.setCaretPosition(TWO);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(C, rhs);
        pane.setCaretPosition(ABS_POS);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals("", rhs);
    }
}
