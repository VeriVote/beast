package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Niels Hanselmann
 */
public class ParameterEditorStringResProviderTest {

    private final ParameterEditorStringResProvider instance;

    /**
     * initializes the testclass
     */
    public ParameterEditorStringResProviderTest() {
        this.instance = new ParameterEditorStringResProvider("test");
    }

    /**
     * Test of getMenuStringRes method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testGetMenuStringRes() {
        System.out.println("getMenuStringRes");
        StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("Neues Projekt", result.getStringFromID("new"));
    }

    /**
     * Test of getToolbarTipStringRes method, of class
     * ParameterEditorStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("Stoppe Analyse", result.getStringFromID("stop"));

    }

    /**
     * Test of getOtherStringRes method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testGetOtherStringRes() {
        System.out.println("getOtherStringRes");
        StringResourceLoader result = instance.getOtherStringRes();
        assertEquals("Dauer", result.getStringFromID("timeout"));
    }

    /**
     * Test of initialize method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        this.testGetMenuStringRes();
    }

}
