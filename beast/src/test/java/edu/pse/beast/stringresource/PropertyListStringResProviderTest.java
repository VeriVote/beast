package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The tests for PropertyListStringResProvider.
 *
 * @author Niels Hanselmann
 */
public class PropertyListStringResProviderTest {
    /** The instance. */
    private final PropertyListStringResProvider instance;

    /**
     * Sets up the testclass.
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
        final StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("Ergebnis anzeigen", result.getStringFromID("showResult"));

    }

    /**
     * Test of getToolbarTipStringRes method, of class
     * PropertyListStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        final StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("Neu", result.getStringFromID("addNew"));

    }

    /**
     * Test of getOtherStringRes method, of class PropertyListStringResProvider.
     */
    @Test
    public void testGetOtherStringRes() {
        System.out.println("getOtherStringRes");
        final StringResourceLoader result = instance.getOtherStringRes();
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
