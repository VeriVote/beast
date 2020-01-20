package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

/**
 * The tests for StringResourceLoader.
 *
 * @author Niels Hanselmann
 */
public class StringResourceLoaderTest {
    /** A test string. */
    private static final String TEST = "test";
    /** Another test string. */
    private static final String TEST_TWO = "test2";
    /** A test sentence string. */
    private static final String TEST_SENTENCE = "this is a test";
    /** Another test sentence string. */
    private static final String TEST_SENTENCE_TWO = "also a test";

    /** The list. */
    private final LinkedList<String> list;
    /** The instance. */
    private final StringResourceLoader instance;

    /**
     * Sets up a StringResourceLoader with 2 LinkedList.
     */
    public StringResourceLoaderTest() {
        list = new LinkedList<String>();
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
        String id = TEST;
        String id2 = TEST_TWO;
        String result = instance.getStringFromID(id);
        assertEquals(TEST_SENTENCE, result);
        String result2 = instance.getStringFromID(id2);
        assertEquals(TEST_SENTENCE_TWO, result2);
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
        result = instance.getIdForString(TEST_SENTENCE);
        assertEquals(TEST, result);
        result = instance.getIdForString(TEST_SENTENCE_TWO);
        assertEquals(TEST_TWO, result);
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
        result = instance.containsId(TEST);
        assertEquals(true, result);
        result = instance.containsId(TEST_TWO);
        assertEquals(true, result);
        instance.containsId(null);
    }

}
