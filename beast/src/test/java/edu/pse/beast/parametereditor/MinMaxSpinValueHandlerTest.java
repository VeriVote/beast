package edu.pse.beast.parametereditor;

import static org.junit.Assert.assertTrue;

import java.util.ArrayList;

import javax.swing.JSpinner;

import org.junit.Test;

/**
 *
 * @author Jonas Wohnig
 */
public class MinMaxSpinValueHandlerTest {
    /**
     * Test of getValues method, of class MinMaxSpinValueHandler with a simple
     * example.
     */
    @Test
    public void testGetValues() {
        System.out.println("getValues");
        MinMaxSpinValueHandler instance =
                new MinMaxSpinValueHandler(new JSpinner(), new JSpinner());
        instance.setMinAndMax(2, 15);
        ArrayList<Integer> expResult = new ArrayList<>();
        for (int i = 2; i <= 15; i++) {
            expResult.add(i);
        }
        ArrayList<Integer> result = instance.getValues();
        assertTrue(expResult.equals(result));
    }

    /**
     * Test of setMinAndMax method, of class MinMaxSpinValueHandler.
     */
    @Test
    public void testSetMinAndMax() {
        System.out.println("setMinAndMax");
        Integer min = 6;
        Integer max = 3;
        Integer badMin = 0;
        Integer badMax = 10001;
        MinMaxSpinValueHandler instance =
                new MinMaxSpinValueHandler(new JSpinner(), new JSpinner());
        instance.setMinAndMax(min, max);
        instance.setMinAndMax(badMin, max);
        instance.setMinAndMax(min, badMax);
        ArrayList<Integer> expResult = new ArrayList<>();
        for (int i = 3; i <= 6; i++) {
            expResult.add(i);
        }
        ArrayList<Integer> result = instance.getValues();
        assertTrue(expResult.equals(result));
    }
}