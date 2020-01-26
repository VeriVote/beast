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
        final FormalPropertiesDescription instance =
                new FormalPropertiesDescription(TEST);
        final String result = instance.getCode();
        assertEquals(TEST, result);
    }

    /**
     * Test of setCode method, of class FormalPropertiesDescription.
     */
    @Test
    public void testSetCode() {
        System.out.println("setCode");
        final FormalPropertiesDescription instance =
                new FormalPropertiesDescription(null);
        instance.setCode(TEST);
        final String result = instance.getCode();
        assertEquals(TEST, result);
    }
}
