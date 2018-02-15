package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertEquals;

import javax.swing.JSpinner;

import org.junit.Test;

/**
 *
 * @author Jonas
 */
public class SingleValueSpinnerHandlerTest {
    
    /**
     * Test of setValue and getValue methods, of class SingleValueSpinnerHandler.
     */
    @Test
    public void testSetGetValue() {
        System.out.println("set/getValue");
        SingleValueSpinnerHandler instance = new SingleValueSpinnerHandler(new JSpinner());
        Integer expResult = 42;
        instance.setValue(42);
        instance.setValue(-1);
        instance.setValue(10001);
        Integer result = instance.getValue();
        assertEquals(expResult, result);
    }

}
