package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import java.util.LinkedList;

import org.junit.Test;

/**
 *
 * @author Niels
 */
public class StringResourceProviderTest {

    private final StringResourceProviderImpl instance;

    /**
     * initializes the Test
     */
    public StringResourceProviderTest() {
        String test = "test";
        instance = new StringResourceProviderImpl(test);
    }

    /**
     * Test of changeLanguage method, of class StringResourceProvider.
     */
    @Test
    public void testChangeLanguage() {
        System.out.println("changeLanguage");
        String languageId = "test2";
        instance.changeLanguage(languageId);
        StringResourceLoader result = instance.getStringRes();
        assert (result.containsId("testo"));
        assert (result.containsId("testo2"));
        assertEquals("es un testo", result.getStringFromID("testo"));
        assertEquals("es un testo tambien", result.getStringFromID("testo2"));
        instance.changeLanguage("test");
    }

    /**
     * Test of initialize method, of class StringResourceProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        StringResourceLoader result = instance.getStringRes();
        assert (result.containsId("test"));
        assert (result.containsId("test2"));
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("also a test", result.getStringFromID("test2"));
    }

    /**
     * Test of getStringResourceLoaderFromModuleName method, of class
     * StringResourceProvider.
     */
    @Test
    public void testGetStringResourceLoaderFromModuleName() {
        System.out.println("getStringResourceLoaderFromModuleName");
        String moduleName = "Testmodul";
        StringResourceLoader result = instance.getStringResourceLoaderFromModuleName(moduleName);
        LinkedList<String> inputList = new LinkedList<>();
        inputList.add("test : this is a test");
        inputList.add("test2 : also a test");
        StringResourceLoader exp = new StringResourceLoader(inputList);
        assertEquals(exp.containsId("test"), result.containsId("test"));
        assertEquals(exp.containsId("test2"), result.containsId("test2"));
        assertEquals(exp.getIdForString("test"), result.getIdForString("test"));
        assertEquals(exp.getIdForString("test2"), result.getIdForString("test2"));
    }

    public class StringResourceProviderImpl extends StringResourceProvider {

        private StringResourceLoader stringRes;

        /**
         * Inner public Testclass
         *
         * @param test does not matter for the test
         */
        public StringResourceProviderImpl(String test) {
            super(test);
        }

        @Override
        public void initialize() {
            stringRes = this.getStringResourceLoaderFromModuleName("Testmodul");
        }

        /**
         * returns the stringRes for the languageChangeTest
         *
         * @return the stringRes
         */
        public StringResourceLoader getStringRes() {
            return stringRes;
        }
    }

}
