/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.datatypes.propertydescription;

import org.junit.Test;
import static org.junit.Assert.*;

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
