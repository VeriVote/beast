/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.OpenCloseChar;
import edu.pse.beast.codearea.InputToCode.OpenCloseCharList;
import org.junit.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharListTest {
    
    public OpenCloseCharListTest() {
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
     * Test of isOpenChar method, of class OpenCloseCharList.
     */
    @Test
    public void testIsOpenChar() {
        System.out.println("isOpenChar");
        
        OpenCloseCharList instance = new OpenCloseCharList();
        boolean result = instance.isOpenChar('{');
        assertTrue(result);
        result = instance.isOpenChar('(');
        assertTrue(result);
        result = instance.isOpenChar('"');
        assertTrue(result);
       
    }

    /**
     * Test of getOpenCloseChar method, of class OpenCloseCharList.
     */
    @Test
    public void testGetOpenCloseChar() {
        System.out.println("getOpenCloseChar");
       
        OpenCloseCharList instance = new OpenCloseCharList();
        
        OpenCloseChar result = instance.getOpenCloseChar('{');
        assertEquals('{', result.getOpen());
        assertEquals('}', result.getClose());   
    }
    
}
