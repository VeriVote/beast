/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

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
public class OptionStringResProviderTest {
    
    private final OptionStringResProvider instance;
    public OptionStringResProviderTest() {
        instance = new OptionStringResProvider("testlanguage", "src/test/testfiles/StringResourcesTestfiles/");
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
     * Test of getOptionStringRes method, of class OptionStringResProvider.
     */
    @Test
    public void testGetOptionStringRes() {
        System.out.println("getOptionStringRes");
        StringResourceLoader result = instance.getOptionStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Option", result.getStringFromID("filename"));
    }

    /**
     * Test of initialize method, of class OptionStringResProvider.
     */
    @Test
    @Ignore("initialize is allready tested within the other tests")
    public void testInitialize() {
        System.out.println("initialize");
        
    }
    
}
