/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

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
public class NewlineTest {
    private JTextPane pane;
    
   
    
    @Before
    public void setUp() {
        pane = new JTextPane();
    }
    
    @After
    public void tearDown() {
    }
    
    //on windows!
    @Test
    public void testAddNewline() throws BadLocationException {
        String insert = "\n";
        assertEquals(1, insert.length());
        pane.getDocument().insertString(0, insert, null);
        assertEquals(2, pane.getText().length());        
        assertEquals("\r\n", pane.getText()); //dafuq????
    }
    
}
