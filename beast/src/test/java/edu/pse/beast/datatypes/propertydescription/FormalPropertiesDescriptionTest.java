package edu.pse.beast.datatypes.propertydescription;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Niels
 */
public class FormalPropertiesDescriptionTest {

    /**
     * Test of getCode method, of class FormalPropertiesDescription.
     */
    @Test
    public void testGetCode() {
	System.out.println("getCode");
	FormalPropertiesDescription instance = new FormalPropertiesDescription("test");
	String expResult = "test";
	String result = instance.getCode();
	assertEquals(expResult, result);
    }

    /**
     * Test of setCode method, of class FormalPropertiesDescription.
     */
    @Test
    public void testSetCode() {
	System.out.println("setCode");
	String code = "test";
	FormalPropertiesDescription instance = new FormalPropertiesDescription(null);
	instance.setCode(code);
	String result = instance.getCode();
	assertEquals("test", result);
    }

}
