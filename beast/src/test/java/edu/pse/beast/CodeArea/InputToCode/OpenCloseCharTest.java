/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import com.pse.beast.codearea.InputToCode.OpenCloseChar;
import javax.swing.JTextPane;
import javax.swing.text.StyledDocument;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Holger-Desktop
 */
public class OpenCloseCharTest {
    
    public OpenCloseCharTest() {
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
     * Test of insertIntoDocument method, of class OpenCloseChar.
     */
    @Test
    public void testInsertIntoDocument() throws Exception {
        JTextPane pane = new JTextPane();
        OpenCloseChar occ = new OpenCloseChar('[', ']');
        assertEquals(0, pane.getText().length());
        occ.insertIntoDocument(0, pane.getStyledDocument());
        assertEquals(pane.getText(), "[]");
    }
    
}
