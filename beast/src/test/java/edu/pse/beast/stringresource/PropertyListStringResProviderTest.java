/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class PropertyListStringResProviderTest {

    private final PropertyListStringResProvider instance;

    /**
     * sets up the testclass
     */
    public PropertyListStringResProviderTest() {
        this.instance = new PropertyListStringResProvider("test");
    }

    /**
     * Test of getMenuStringRes method, of class PropertyListStringResProvider.
     */
    @Test
    public void testGetMenuStringRes() {
        System.out.println("getMenuStringRes");
        StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("Ergebnis anzeigen", result.getStringFromID("showResult"));

    }

    /**
     * Test of getToolbarTipStringRes method, of class
     * PropertyListStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("Neu", result.getStringFromID("addNew"));

    }

    /**
     * Test of getOtherStringRes method, of class PropertyListStringResProvider.
     */
    @Test
    public void testGetOtherStringRes() {
        System.out.println("getOtherStringRes");
        StringResourceLoader result = instance.getOtherStringRes();
        assertEquals("Stimmenauszählung", result.getStringFromID("electionpoints"));

    }

    /**
     * Test of initialize method, of class PropertyListStringResProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        this.testGetMenuStringRes();
    }

}
