package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

/**
 *
 * @author Niels Hanselmann
 */
public class StringResourceLoaderTest {

    private final LinkedList<String> list;
    private final StringResourceLoader instance;

    /**
     * sets up a StringResourceLoader with 2 LinkedList
     */
    public StringResourceLoaderTest() {
        list = new LinkedList<>();
        list.add("test : this is a test");
        list.add("test2 : also a test");
        instance = new StringResourceLoader(list);
    }

    /**
     * Test of getStringFromID method, of class StringResourceLoader.
     */
    @Test
    public void testGetStringFromID() {
        System.out.println("getStringFromID");
        String id = "test";
        String id2 = "test2";
        String result = instance.getStringFromID(id);
        assertEquals("this is a test", result);
        String result2 = instance.getStringFromID(id2);
        assertEquals("also a test", result2);
        instance.getStringFromID(null);
    }

    /**
     * Test of getIdForString method, of class StringResourceLoader.
     */
    @Test
    public void testGetIdForString() {
        System.out.println("getIdForString");
        String s = "not in the testfile";
        String result = instance.getIdForString(s);
        assertEquals(null, result);
        result = instance.getIdForString("this is a test");
        assertEquals("test", result);
        result = instance.getIdForString("also a test");
        assertEquals("test2", result);
        instance.getStringFromID(null);
    }

    /**
     * Test of containsId method, of class StringResourceLoader.
     */
    @Test
    public void testContainsId() {
        System.out.println("containsId");
        String id = "teeeest";
        boolean result = instance.containsId(id);
        assertEquals(false, result);
        result = instance.containsId("test");
        assertEquals(true, result);
        result = instance.containsId("test2");
        assertEquals(true, result);
        instance.containsId(null);
    }

}
