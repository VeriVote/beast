package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

/**
 * The tests for the StringResourceProvider.
 *
 * @author Niels Hanselmann
 */
public class StringResourceProviderTest {
    /** A test string. */
    private static final String TEST = "test";
    /** Another test string. */
    private static final String TEST_TWO = "test2";
    /** Yet another test string. */
    private static final String TEST_THREE = "testo";
    /** And yet one more test string. */
    private static final String TEST_FOUR = "testo2";
    /** A test module name string. */
    private static final String TEST_MODULE = "Testmodul";

    /** The instance. */
    private final StringResourceProviderImpl instance;

    /**
     * Initializes the Test.
     */
    public StringResourceProviderTest() {
        instance = new StringResourceProviderImpl(TEST);
    }

    /**
     * Test of changeLanguage method, of class StringResourceProvider.
     */
    @Test
    public void testChangeLanguage() {
        System.out.println("changeLanguage");
        String languageId = TEST_TWO;
        instance.changeLanguage(languageId);
        StringResourceLoader result = instance.getStringRes();
        assertTrue(result.containsId(TEST_THREE));
        assertTrue(result.containsId(TEST_FOUR));
        assertEquals("es un testo", result.getStringFromID(TEST_THREE));
        assertEquals("es un testo tambien", result.getStringFromID(TEST_FOUR));
        instance.changeLanguage(TEST);
    }

    /**
     * Test of initialize method, of class StringResourceProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        StringResourceLoader result = instance.getStringRes();
        assertTrue(result.containsId(TEST));
        assertTrue(result.containsId(TEST_TWO));
        assertEquals("this is a test", result.getStringFromID(TEST));
        assertEquals("also a test", result.getStringFromID(TEST_TWO));
    }

    /**
     * Test of getStringResourceLoaderFromModuleName method, of class
     * StringResourceProvider.
     */
    @Test
    public void testGetStringResourceLoaderFromModuleName() {
        System.out.println("getStringResourceLoaderFromModuleName");
        String moduleName = TEST_MODULE;
        StringResourceLoader result = instance.getStringResourceLoaderFromModuleName(moduleName);
        LinkedList<String> inputList = new LinkedList<String>();
        inputList.add("test : this is a test");
        inputList.add("test2 : also a test");
        StringResourceLoader exp = new StringResourceLoader(inputList);
        assertEquals(exp.containsId(TEST), result.containsId(TEST));
        assertEquals(exp.containsId(TEST_TWO), result.containsId(TEST_TWO));
        assertEquals(exp.getIdForString(TEST), result.getIdForString(TEST));
        assertEquals(exp.getIdForString(TEST_TWO), result.getIdForString(TEST_TWO));
    }

    /**
     * Implementation of the StringResourceProvider type.
     *
     * @author Lukas Stapelbroek
     */
    public final class StringResourceProviderImpl extends StringResourceProvider {
        /** The string resource. */
        private StringResourceLoader stringRes;

        /**
         * Inner public Testclass.
         *
         * @param test does not matter for the test
         */
        public StringResourceProviderImpl(final String test) {
            super(test);
        }

        @Override
        public void initialize() {
            stringRes = this.getStringResourceLoaderFromModuleName(TEST_MODULE);
        }

        /**
         * Returns the stringRes for the languageChangeTest.
         *
         * @return the stringRes
         */
        public StringResourceLoader getStringRes() {
            return stringRes;
        }
    }
}
