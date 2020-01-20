package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertEquals;

import javax.swing.JSpinner;

import org.junit.Test;

/**
 * The tests for SingleValueSpinnerHandler.
 *
 * @author Jonas Wohnig
 */
public class SingleValueSpinnerHandlerTest {
    /** The Constant TEN_THOUSAND_ONE. */
    private static final int TEN_THOUSAND_ONE = 10001;
    /** The Constant FOURTY_TWO. */
    private static final int FOURTY_TWO = 42;

    /**
     * Test of setValue and getValue methods, of class SingleValueSpinnerHandler.
     */
    @Test
    public void testSetGetValue() {
        System.out.println("set/getValue");
        SingleValueSpinnerHandler instance = new SingleValueSpinnerHandler(new JSpinner());
        instance.setValue(FOURTY_TWO);
        instance.setValue(-1);
        instance.setValue(TEN_THOUSAND_ONE);
        final Integer expResult = FOURTY_TWO;
        final Integer result = instance.getValue();
        assertEquals(expResult, result);
    }

}
