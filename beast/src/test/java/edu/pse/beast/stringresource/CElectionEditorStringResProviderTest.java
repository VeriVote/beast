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
public class CElectionEditorStringResProviderTest {

    private final CElectionEditorStringResProvider instance;

    /**
     * sets up the testclass
     */
    public CElectionEditorStringResProviderTest() {
        instance = new CElectionEditorStringResProvider("test");
    }

    /**
     * Test of getMenuStringRes method, of class
     * CElectionEditorStringResProvider.
     */
    @Test
    public void testGetMenuStringRes() {
        System.out.println("getMenuStringRes");
        StringResourceLoader result = instance.getMenuStringRes();
        assertEquals("Datei", result.getStringFromID("file"));
    }

    /**
     * Test of getToolbarTipStringRes method, of class
     * CElectionEditorStringResProvider.
     */
    @Test
    public void testGetToolbarTipStringRes() {
        System.out.println("getToolbarTipStringRes");
        StringResourceLoader result = instance.getToolbarTipStringRes();
        assertEquals("Speichern", result.getStringFromID("save"));
    }

    /**
     * Test of getCErrorStringRes method, of class
     * CElectionEditorStringResProvider.
     */
    @Test
    public void testGetCErrorStringRes() {
        System.out.println("getCErrorStringRes");
        StringResourceLoader result = instance.getCErrorStringRes();
        assertEquals("Zeile", result.getStringFromID("line"));

    }

    /**
     * Test of initialize method, of class CElectionEditorStringResProvider.
     */
    @Test
    public void testInitialize() {
        System.out.println("initialize");
        instance.initialize();
        this.testGetCErrorStringRes();
    }

    /**
     * Test of getElectionStringRes method, of class
     * CElectionEditorStringResProvider.
     */
    @Test
    public void testGetElectionStringRes() {
        System.out.println("getElectionStringRes");
        StringResourceLoader result = instance.getElectionStringRes();
        assertEquals("Erstellen", result.getStringFromID("create"));
    }

}
