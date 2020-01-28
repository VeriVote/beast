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

    /** The Constant TEST_STRING_ONE. */
    private static final String TEST_STRING_ONE = "test : this is a test";
    /** The Constant TEST_STRING_TWO. */
    private static final String TEST_STRING_TWO = "test2 : also a test";

    /** The list. */
    private final LinkedList<String> list;
    /** The instance. */
    private final StringResourceLoader instance;

    /**
     * Sets up a StringResourceLoader with 2 LinkedList.
     */
    public StringResourceLoaderTest() {
        list = new LinkedList<String>();
        list.add(TEST_STRING_ONE);
        list.add(TEST_STRING_TWO);
        instance = new StringResourceLoader(list);
    }

    /**
     * Test of getStringFromID method, of class StringResourceLoader.
     */
    @Test
    public void testGetStringFromID() {
        System.out.println("getStringFromID");
        assertEquals(TEST_SENTENCE, instance.getStringFromID(TEST));
        assertEquals(TEST_SENTENCE_TWO,
                     instance.getStringFromID(TEST_TWO));
        instance.getStringFromID(null);
    }

    /**
     * Test of getIdForString method, of class StringResourceLoader.
     */
    @Test
    public void testGetIdForString() {
        System.out.println("getIdForString");
        final String s = "not in the testfile";
        assertEquals(null, instance.getIdForString(s));
        assertEquals(TEST, instance.getIdForString(TEST_SENTENCE));
        assertEquals(TEST_TWO, instance.getIdForString(TEST_SENTENCE_TWO));
        instance.getStringFromID(null);
    }

    /**
     * Test of containsId method, of class StringResourceLoader.
     */
    @Test
    public void testContainsId() {
        System.out.println("containsId");
        final String id = "teeeest";
        assertEquals(false, instance.containsId(id));
        assertEquals(true, instance.containsId(TEST));
        assertEquals(true, instance.containsId(TEST_TWO));
        instance.containsId(null);
    }

}
