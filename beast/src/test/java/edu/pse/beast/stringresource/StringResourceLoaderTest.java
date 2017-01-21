/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import java.util.LinkedList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Niels
 */
public class StringResourceLoaderTest {
    
    public StringResourceLoaderTest() {
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
     * Test of getStringFromID method, of class StringResourceLoader.
     */
    @Test
    public void testGetStringFromID() {
        System.out.println("getStringFromID");
        LinkedList<String> stringRes = new LinkedList<String>();
        stringRes.add("test : das ist ein Test");
        stringRes.add("test2 : das ist ein Test");
        StringResourceLoader instance = new StringResourceLoader(stringRes);
        assertEquals("das ist ein Test", instance.getStringFromID("test"));
        assertEquals("das ist ein Test", instance.getStringFromID("test2"));
       
    }
    
}
