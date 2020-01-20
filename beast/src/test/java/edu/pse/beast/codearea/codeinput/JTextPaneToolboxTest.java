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
    /** The Constant FOUR. */
    private static final int FOUR = 4;
    /** The Constant ABS_POS. */
    private static final int ABS_POS = 3;

    /** A test text string. */
    private static final String TEXT = "12\n\n34\n5\n6";
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
        assertEquals(1, lineno);
    }

    /**
     * Test of getLineBeginning method, of class JTextPaneToolbox.
     */
    @Test
    public void testGetDistanceToClosestLineBeginning() {
        JTextPane pane = new JTextPane();
        pane.setText("123456");
        pane.setCaretPosition(ABS_POS);
        int dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR);
        assertEquals(FOUR, dist);
        pane.setText("123\n456");
        dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, FOUR);
        assertEquals(0, dist);
        dist = JTextPaneToolbox.getDistanceToClosestLineBeginning(pane, 6);
        assertEquals(2, dist);
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
        ArrayList<Integer> linesBetween = JTextPaneToolbox.getLinesBetween(pane, 0, 5);
        int[] lines = {0, 1, 2};
        assertEquals(lines.length, linesBetween.size());
        for (int i = 0; i < lines.length; i++) {
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
        pane.setCaretPosition(0);
        String lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals("", lhs);
        pane.setCaretPosition(1);
        lhs = JTextPaneToolbox.getCharToTheLeftOfCaret(pane);
        assertEquals(A, lhs);
        pane.setCaretPosition(2);
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
        pane.setCaretPosition(0);
        String rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(A, rhs);
        pane.setCaretPosition(1);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(B, rhs);
        pane.setCaretPosition(2);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals(C, rhs);
        pane.setCaretPosition(ABS_POS);
        rhs = JTextPaneToolbox.getCharToTheRightOfCaret(pane);
        assertEquals("", rhs);
    }
}
