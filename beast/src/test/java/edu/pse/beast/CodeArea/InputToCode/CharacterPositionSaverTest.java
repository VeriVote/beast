/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.CharacterPositionSaver;
import javax.swing.JTextPane;
import javax.swing.event.DocumentEvent;
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
public class CharacterPositionSaverTest {
    private JTextPane pane;
    private CharacterPositionSaver saver;
    
    public CharacterPositionSaverTest() {
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
        saver = new CharacterPositionSaver('\n', pane);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testNoOccurence() throws BadLocationException {
        String insert = "01234567";
        pane.getStyledDocument().insertString(0, insert, null);
        int pos = saver.getClosestCharPosTo(5);
        assertEquals(Integer.MIN_VALUE, pos);
    }
    
    @Test
    public void testInsertionUpdate() throws BadLocationException {
        String insert = "012" + System.lineSeparator() + "67";
        pane.getStyledDocument().insertString(0, insert, null);
        pane.getStyledDocument().insertString(3, "34", null);
        assertEquals("01234\r\r\n67", pane.getText());
    }

    @Test
    public void testGetAmtBeforte() throws BadLocationException {
        String insert = "012" + System.lineSeparator() + "67";
        String paneString = "012\r\r\n67";
        
        pane.getStyledDocument().insertString(0, insert, null);        
        assertEquals(paneString, pane.getText());
        
        System.out.println(saver.toString());
        
        int amt = saver.getAmountOfOccurencesBefore(2);
        assertEquals(0, amt);
        
        amt = saver.getAmountOfOccurencesBefore(6);
        assertEquals(1, amt);
    }
   
    
}
