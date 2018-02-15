/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

/**
 *
 * @author Niels
 */
public class BooleanExpEditorStringResProviderTest {

    private final BooleanExpEditorStringResProvider instance;

    /**
     * sets up the testclass
     */
    public BooleanExpEditorStringResProviderTest() {
        instance = new BooleanExpEditorStringResProvider("test");
    }

    /**
     * Test of getMenuStringRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetMenuStringRes() {
        System.out.println("getMenuStringRes");
        StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("Datei", result.getStringFromID("fileMenu"));
    }

    /**
     * Test of getBooleanExpEditorSymbVarListRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpEditorSymbVarListRes() {
        System.out.println("getBooleanExpEditorSymbVarListRes");
        StringResourceLoader result = instance.getBooleanExpEditorSymbVarListRes();
        assertEquals("Wähler", result.getStringFromID("VOTER"));

    }

    /**
     * Test of getToolbarTipStringRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("Neue Eigenschaft", result.getStringFromID("new"));
    }

    /**
     * Test of getBooleanExpErrorStringRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpErrorStringRes() {
        System.out.println("getBooleanExpErrorStringRes");
        StringResourceLoader result = instance.getBooleanExpErrorStringRes();
        assertEquals("Fehler", result.getStringFromID("error"));
    }

    /**
     * Test of getBooleanExpEditorWindowStringRes method, of class
     * BooleanExpEditorStringResProvider.
     */
    @Test
    public void testGetBooleanExpEditorWindowStringRes() {
        System.out.println("getBooleanExpEditorWindowStringRes");
        StringResourceLoader result = instance.getBooleanExpEditorWindowStringRes();
        assertEquals("Eigenschafteneditor", result.getStringFromID("windowTitle"));
    }

    /**
     * Test of initialize method, of class BooleanExpEditorStringResProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        this.testGetBooleanExpEditorSymbVarListRes();
    }

}
