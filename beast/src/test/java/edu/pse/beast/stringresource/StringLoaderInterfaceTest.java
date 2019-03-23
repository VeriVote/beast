package edu.pse.beast.stringresource;

import org.junit.Test;

/**
 *
 * @author Niels
 */
public class StringLoaderInterfaceTest {

    private final StringLoaderInterface instance;

    /**
     * Test setup
     */
    public StringLoaderInterfaceTest() {
        this.instance = new StringLoaderInterface("test");
    }

    /**
     * Test of getPropertyListStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetPropertyListStringResProvider() {
        System.out.println("getPropertyListStringResProvider");
        PropertyListStringResProvider result = instance.getPropertyListStringResProvider();
        assert (result != null);
    }

    /**
     * Test of getBooleanExpEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetBooleanExpEditorStringResProvider() {
        System.out.println("getBooleanExpEditorStringResProvider");
        BooleanExpEditorStringResProvider result = instance.getBooleanExpEditorStringResProvider();
        assert (result != null);
    }

    /**
     * Test of getCElectionEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetCElectionEditorStringResProvider() {
        System.out.println("getCElectionEditorStringResProvider");
        CElectionEditorStringResProvider result = instance.getCElectionEditorStringResProvider();
        assert (result != null);
    }

    /**
     * Test of getParameterEditorStringResProvider method, of class
     * StringLoaderInterface.
     */
    @Test
    public void testGetParameterEditorStringResProvider() {
        System.out.println("getParameterEditorStringResProvider");
        ParameterEditorStringResProvider result = instance.getParameterEditorStringResProvider();
        assert (result != null);
    }

    /**
     * Test of getOptionStringResProvider method, of class StringLoaderInterface.
     */
    @Test
    public void testGetOptionStringResProvider() {
        System.out.println("getOptionStringResProvider");
        OptionStringResProvider result = instance.getOptionStringResProvider();
        assert (result != null);
    }

    /**
     * Test of setLanguage method, of class StringLoaderInterface.
     */
    @Test
    public void testSetLanguage() {
        System.out.println("setLanguage");
        instance.setLanguage("test");
        // if there isn't an error message this should be correct
    }

}
