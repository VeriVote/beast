package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 * The tests for OptionStringResProvider.
 *
 * @author Niels Hanselmann
 */
public class OptionStringResProviderTest {
    /** The instance. */
    private OptionStringResProvider instance;

    /**
     * Sets up the test.
     */
    public OptionStringResProviderTest() {
        instance = new OptionStringResProvider("test");
    }

    /**
     * Test of getOptionStringRes method, of class OptionStringResProvider.
     */
    @Test
    public void testGetOptionStringRes() {
        System.out.println("getOptionStringRes");
        final StringResourceLoader result = instance.getOptionStringRes();
        assertEquals("Sprache", result.getStringFromID("lang"));
    }

    /**
     * Test of initialize method, of class OptionStringResProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        this.testGetOptionStringRes();
    }

}
