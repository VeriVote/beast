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
public class BooleanExpEditorStringResProviderTest {

    private final BooleanExpEditorStringResProvider instance;

    public BooleanExpEditorStringResProviderTest() {
        instance = new BooleanExpEditorStringResProvider("testlanguage", "src/test/testfiles/StringResourcesTestfiles/");
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
     * Test of getMenuStringRes method, of class
     * BooleanExpEditorStringResProvider.
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
     * Test of getToolbarTipStringRes method, of class
     * BooleanExpEditorStringResProvider.
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
     * Test of getBooleanExpErrorStringRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpErrorStringRes() {
        System.out.println("getBooleanExpErrorStringRes");
        StringResourceLoader result = instance.getBooleanExpErrorStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("BooleanExpError", result.getStringFromID("filename"));
    }

    /**
     * Test of initialize method, of class BooleanExpEditorStringResProvider.
     */
    @Test
    @Ignore("initialize is allready tested within the other tests")
    public void testInitialize() {
        System.out.println("initialize");

    }

    /**
     * Test of getWindowStringRes method, of class BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpEditorWindowStringRes() {
        System.out.println("getBooleanExpEditorWindowStringRes");
        StringResourceLoader result = instance.getBooleanExpEditorWindowStringRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("Window", result.getStringFromID("filename"));
    }

    /**
     * Test of getBooleanExpEditorSymbVarListRes method, of class BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpEditorSymbVarListRes() {
        System.out.println("getBooleanExpEditorSymbVarListRes");
        StringResourceLoader result = instance.getBooleanExpEditorSymbVarListRes();
        assertEquals("this is a test", result.getStringFromID("test"));
        assertEquals("this is test 2", result.getStringFromID("test2"));
        assertEquals("SymbVarList", result.getStringFromID("filename"));
    }

}
