package edu.pse.beast.stringresource;

import static org.junit.Assert.assertNotNull;

import org.junit.Test;

/**
 * The tests for the StringLoaderInterface.
 *
 * @author Niels Hanselmann
 */
public class StringLoaderInterfaceTest {
    /** A test string. */
    private static final String TEST = "test";
    /** The instance. */
    private final StringLoaderInterface instance;

    /**
     * Test setup.
     */
    public StringLoaderInterfaceTest() {
        this.instance = new StringLoaderInterface(TEST);
    }

    /**
     * Test of getPropertyListStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetPropertyListStringResProvider() {
        System.out.println("getPropertyListStringResProvider");
        final PropertyListStringResProvider result =
                instance.getPropertyListStringResProvider();
        assertNotNull(result);
    }

    /**
     * Test of getBooleanExpEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetBooleanExpEditorStringResProvider() {
        System.out.println("getBooleanExpEditorStringResProvider");
        final BooleanExpEditorStringResProvider result =
                instance.getBooleanExpEditorStringResProvider();
        assertNotNull(result);
    }

    /**
     * Test of getCElectionEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetCElectionEditorStringResProvider() {
        System.out.println("getCElectionEditorStringResProvider");
        final CElectionEditorStringResProvider result =
                instance.getCElectionEditorStringResProvider();
        assertNotNull(result);
    }

    /**
     * Test of getParameterEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetParameterEditorStringResProvider() {
        System.out.println("getParameterEditorStringResProvider");
        final ParameterEditorStringResProvider result =
                instance.getParameterEditorStringResProvider();
        assertNotNull(result);
    }

    /**
     * Test of getOptionStringResProvider method, of class StringLoaderInterface.
     */
    @Test
    public void testGetOptionStringResProvider() {
        System.out.println("getOptionStringResProvider");
        final OptionStringResProvider result = instance.getOptionStringResProvider();
        assertNotNull(result);
    }

    /**
     * Test of setLanguage method, of class StringLoaderInterface.
     */
    @Test
    public void testSetLanguage() {
        System.out.println("setLanguage");
        instance.setLanguage(TEST);
        // if there is no error message, this should be correct
    }
}
