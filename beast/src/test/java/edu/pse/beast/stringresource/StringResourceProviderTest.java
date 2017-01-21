/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import java.io.File;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Ignore;

/**
 *
 * @author Niels
 */
public class StringResourceProviderTest {
    
    public StringResourceProviderTest() {
      
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of changeLanguage method, of class StringResourceProvider.
     */
    @Test
    public void testChangeLanguage() {
        System.out.println("changeLanguage");
        OptionStringResProvider instance;
        instance = new OptionStringResProvider("testlanguage", "src/test/testfiles/StringResourcesTestfiles/");
        StringResourceLoader result = instance.getOptionStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Option", result.getStringFromID("filename"));
        String languageId = "testlanguage2";
        instance.changeLanguage(languageId);
        result = instance.getOptionStringRes();
        assertEquals("das ist ein Test", result.getStringFromID("test"));
        assertEquals("das ist der zweite Test", result.getStringFromID("test2"));
        assertEquals("Option", result.getStringFromID("filename"));
    }

    /**
     * Test of initialize method, of class StringResourceProvider.
     */
    @Test
    @Ignore("Initialize is abstract")
    public void testInitialize() {
        System.out.println("initialize");
        StringResourceProvider instance = null;
        instance.initialize();
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of getFileLocationString method, of class StringResourceProvider.
     */
    @Test
    public void testGetFileLocationString() {
        System.out.println("getFileLocationString");
        OptionStringResProvider instance;
        instance = new OptionStringResProvider("testlanguage", "src/test/testfiles/StringResourcesTestfiles/");
        String expResult = (instance.relativePath + instance.languageId + "/" + "Option" + "_" + instance.languageId + ".txt");
        String result = instance.getFileLocationString("Option");
        assertEquals(expResult, result);
    }

    /**
     * Test of errorFileNotFound method, of class StringResourceProvider.
     */
    @Test
    @Ignore ("Test not implemented jet")
    public void testErrorFileNotFound() {
        System.out.println("errorFileNotFound");
        File file = null;
        StringResourceProvider.errorFileNotFound(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }

    /**
     * Test of errorFileHasWrongFormat method, of class StringResourceProvider.
     */
    @Test
    @Ignore ("Test not implemented jet")
    public void testErrorFileHasWrongFormat() {
        System.out.println("errorFileHasWrongFormat");
        File file = null;
        StringResourceProvider.errorFileHasWrongFormat(file);
        // TODO review the generated test code and remove the default call to fail.
        fail("The test case is a prototype.");
    }
}
