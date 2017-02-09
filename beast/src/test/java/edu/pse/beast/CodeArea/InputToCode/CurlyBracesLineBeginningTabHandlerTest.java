/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.CurlyBracesLineBeginningTabHandler;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
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
public class CurlyBracesLineBeginningTabHandlerTest {
    
    private JTextPane pane;
    private CurlyBracesLineBeginningTabHandler handler;
    
    public CurlyBracesLineBeginningTabHandlerTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        pane = new JTextPane();
        handler = new CurlyBracesLineBeginningTabHandler(pane);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of getTabsForLine method, of class CurlyBracesLineBeginningTabHandler.
     */
    @Test
    public void testGetZeroTabsForLine() throws BadLocationException {
        System.out.println("getTabsForLine");
        String insert = "asdasd" + System.lineSeparator() + "asdasd";
        pane.getStyledDocument().insertString(0, insert, null);
        
        assertEquals(15, pane.getText().length());
        
        int tabs = handler.getTabsForLine(0);
        assertEquals(0, tabs);
        
        tabs = handler.getTabsForLine(9);
        assertEquals(0, tabs);        
    }
    
    @Test
    public void testGetEasyCurlyTabsForLine() throws BadLocationException {
        System.out.println("getTabsForLine");
       pane.setText("{\n \n}");
        
        int tabs = handler.getTabsForLine(0);
        assertEquals(0, tabs);
        
        tabs = handler.getTabsForLine(2);
        assertEquals(1, tabs);    
        
        tabs = handler.getTabsForLine(3);
        assertEquals(1, tabs);  
        
        tabs = handler.getTabsForLine(4);
        assertEquals(0, tabs);    
    }
    
    @Test
    public void testGetTabsForLineClosingCurlyAtEnd() throws BadLocationException {
        System.out.println("getTabsForLine");
        
        pane.setText("{\n\n}");
        
        int tabs = handler.getTabsForLine(3);
        assertEquals(0, tabs);    
    }
    
    @Test
    public void testGetTabsForLineManyCurlysinLine() throws BadLocationException {
        System.out.println("getTabsForLine");
        String insert = "{{{{{{" + System.lineSeparator() + " " + System.lineSeparator() + "}";
        pane.getStyledDocument().insertString(0, insert, null);
        
        int tabs = handler.getTabsForLine(7);
        assertEquals(1, tabs);  
        tabs = handler.getTabsForLine(8);
        assertEquals(1, tabs);    
    }
    
    @Test
    public void testGetTabsForLineManyCurlysinManyLines() throws BadLocationException {
        System.out.println("getTabsForLine");
        String insert = "{" + System.lineSeparator() + 
                            "{" + System.lineSeparator() + 
                                "{" + System.lineSeparator() + 
                                    " " + System.lineSeparator() + 
                                "}";
        pane.setText("{\n{\n{\n \n}");
        int tabs = handler.getTabsForLine(6);
        assertEquals(3, tabs);  
        tabs = handler.getTabsForLine(7);
        assertEquals(3, tabs);    
        tabs = handler.getTabsForLine(8);
        assertEquals(2, tabs);  
        tabs = handler.getTabsForLine(9);
        assertEquals(2, tabs);  
    }

    /**
     * Test of getTabsForLine method, of class CurlyBracesLineBeginningTabHandler.
     */
    @Test
    public void testGetTabsForLine() {
    }
}
