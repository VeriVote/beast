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
public class ParameterEditorStringResProviderTest {
    private final ParameterEditorStringResProvider instance;
    
    public ParameterEditorStringResProviderTest() {
        instance = new ParameterEditorStringResProvider("testlanguage", "src/test/testfiles/StringResourcesTestfiles/");
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
     * Test of getMenuStringRes method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testGetMenuStringRes() {
        System.out.println("getMenuStringRes");
        StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Menu", result.getStringFromID("filename"));
        
    }

    /**
     * Test of getToolbarTipStringRes method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Toolbar", result.getStringFromID("filename"));
        
    }

    /**
     * Test of getOtherStringRes method, of class ParameterEditorStringResProvider.
     */
    @Test
    public void testGetOtherStringRes() {
        System.out.println("getOtherStringRes");
        StringResourceLoader result = instance.getOtherStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Other", result.getStringFromID("filename"));
    }

    /**
     * Test of initialize method, of class ParameterEditorStringResProvider.
     */
    @Test
    @Ignore("initialize is allready tested within the other tests")
    public void testInitialize() {
        System.out.println("initialize");
        
    }
    
}
