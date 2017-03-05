/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import org.junit.*;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Niels
 */
public class CodeArrayListBeautifierTest {

    public CodeArrayListBeautifierTest() {
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
     * Test of add method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAdd() {
        System.out.println("add");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));
    }

    /**
     * Test of addTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testAddTab() {
        System.out.println("addTab");
        System.out.println("add");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();
        instance.add(e);
        assertEquals("\ttest", (instance.getCodeArrayList().get(0)));
    }

    /**
     * Test of deleteTab method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testDeleteTab() {
        System.out.println("deleteTab");
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.addTab();
        
        instance.deleteTab();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));
    }

    /**
     * Test of getCodeArrayList method, of class CodeArrayListBeautifier.
     */
    @Test
    public void testGetCodeArrayList() {
        String e = "test";
        CodeArrayListBeautifier instance = new CodeArrayListBeautifier();
        instance.add(e);
        assertEquals("test", (instance.getCodeArrayList().get(0)));
    }

}
