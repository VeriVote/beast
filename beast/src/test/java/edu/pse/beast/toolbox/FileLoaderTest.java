/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.io.File;
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
public class FileLoaderTest {
    
    /**
     * 
     */
    public FileLoaderTest() {
    }
    /**
     * 
     */
    @BeforeClass
    public static void setUpClass() {
    }
    /**
     * 
     */
    @AfterClass
    public static void tearDownClass() {
    }
    /**
     * 
     */
    @Before
    public void setUp() {
    }
    /**
     * 
     */
    @After
    public void tearDown() {
    }

    /**
     * Test of loadFileAsString method, of class FileLoader.
     * @throws java.lang.Exception throws an exception if the file is not read correctly or not available
     */
    @Test
    public void testLoadFileAsString() throws Exception {
        
        File file = new File("src/test/testfiles/fileLoaderFileAsStringTest.txt");
        LinkedList<String> expResult = new LinkedList<>();
        expResult.add("erste Zeile");
        expResult.add("zweite Zeile");
        expResult.add("ende");
        LinkedList<String> result = FileLoader.loadFileAsString(file);
        assertEquals(expResult, result);
        assertEquals(expResult.getFirst(), result.getFirst());
    }
    
}
