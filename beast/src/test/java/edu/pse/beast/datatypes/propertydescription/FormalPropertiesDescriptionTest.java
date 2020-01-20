package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The tests for FormalPropertiesDescription.
 *
 * @author Niels Hanselmann
 */
public class FormalPropertiesDescriptionTest {
    /** A test string. */
    private static final String TEST = "test";

    /**
     * Test of getCode method, of class FormalPropertiesDescription.
     */
    @Test
    public void testGetCode() {
        System.out.println("getCode");
        FormalPropertiesDescription instance = new FormalPropertiesDescription(TEST);
        String expResult = TEST;
        String result = instance.getCode();
        assertEquals(expResult, result);
    }

    /**
     * Test of setCode method, of class FormalPropertiesDescription.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        String code = TEST;
        FormalPropertiesDescription instance = new FormalPropertiesDescription(null);
        instance.setCode(code);
        String result = instance.getCode();
        assertEquals(TEST, result);
    }
}
