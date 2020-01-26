package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.swing.JSpinner;

import org.junit.Test;

/**
 * The tests for MinMaxSpinValueHandler.
 *
 * @author Jonas Wohnig
 */
public class MinMaxSpinValueHandlerTest {
    /** The Constant MIN. */
    private static final Integer MIN = 6;
    /** The Constant MAX. */
    private static final Integer MAX = 3;
    /** The Constant BAD_MIN. */
    private static final Integer BAD_MIN = 0;
    /** The Constant BAD_MAX. */
    private static final Integer BAD_MAX = 10001;

    /** The Constant TEST_MIN. */
    private static final Integer TEST_MIN = 2;
    /** The Constant TEST_MAX. */
    private static final Integer TEST_MAX = 15;

    /**
     * Test of getValues method, of class MinMaxSpinValueHandler with a simple
     * example.
     */
    @Test
    public void testGetValues() {
        System.out.println("getValues");
        final MinMaxSpinValueHandler instance
              = new MinMaxSpinValueHandler(new JSpinner(), new JSpinner());
        instance.setMinAndMax(TEST_MIN, TEST_MAX);
        final ArrayList<Integer> expResult = new ArrayList<Integer>();
        for (int i = TEST_MIN; i <= TEST_MAX; i++) {
            expResult.add(i);
        }
        final ArrayList<Integer> result = instance.getValues();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of setMinAndMax method, of class MinMaxSpinValueHandler.
     */
    @Test
    public void testSetMinAndMax() {
        System.out.println("setMinAndMax");
        final MinMaxSpinValueHandler instance
              = new MinMaxSpinValueHandler(new JSpinner(), new JSpinner());
        instance.setMinAndMax(MIN, MAX);
        instance.setMinAndMax(BAD_MIN, MAX);
        instance.setMinAndMax(MIN, BAD_MAX);
        final ArrayList<Integer> expResult = new ArrayList<Integer>();
        for (int i = MAX; i <= MIN; i++) {
            expResult.add(i);
        }
        final ArrayList<Integer> result = instance.getValues();
        assertTrue(expResult.equals(result));
    }
}
