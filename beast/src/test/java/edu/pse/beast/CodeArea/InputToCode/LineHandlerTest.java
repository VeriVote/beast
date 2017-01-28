/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.StandardNewlineInserter;
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
public class LineHandlerTest {
    
    private LineHandler handler;
    private JTextPane pane;
    
    public LineHandlerTest() {
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
        handler = new LineHandler(pane);
    }
    
    @After
    public void tearDown() {
        handler = null;
        pane = null;
    }
  
    @Test
    public void testTransformToLine() throws BadLocationException {
        String insert = "0" + System.lineSeparator() + "1" +
                System.lineSeparator() + "2" + System.lineSeparator() + "3";
        pane.getStyledDocument().insertString(0, insert, null);
        assertEquals(4 + 9, pane.getText().length());
        int line = handler.transformToLineNumber(0);
        assertEquals(0, line);
        line = handler.transformToLineNumber(1);
        assertEquals(0, line);
        line = handler.transformToLineNumber(2);
        assertEquals(0, line);
        
        line = handler.transformToLineNumber(4);
        assertEquals(1, line);        
    }
    
     @Test
    public void testCaretMovement() throws BadLocationException {
        int pos = pane.getCaretPosition();
        assertEquals(0, pos);
        String insert = "0" + System.lineSeparator() + "1" +
                System.lineSeparator() + "2" + System.lineSeparator() + "3";
        pane.getStyledDocument().insertString(0, insert, null);
        
        pos = pane.getCaretPosition();
        assertEquals(0, pos);
        
        pane = new JTextPane();
        pane.getStyledDocument().insertString(0, System.lineSeparator(), null);
        
        pos = pane.getCaretPosition();
        assertEquals(0, pos);
    }
    
     @Test
    public void testInsertion() throws BadLocationException {
        String insert = "\n" + "123" + "\n" + "123";
        pane.getStyledDocument().insertString(0, insert, null);
        int pos = handler.getDistanceToLineBeginning(2);
        
        assertEquals(0, pos);
        
        pos = handler.getDistanceToLineBeginning(3);
        assertEquals(1, pos);
        
        pos = handler.getDistanceToLineBeginning(7);
        assertEquals(0, pos);        
    }
    
    @Test 
    public void testTranformCaret() throws BadLocationException {
        String insert = "\n123\n123";
        pane.getStyledDocument().insertString(0, insert, null);
        int abs = handler.caretPosToAbsPos(1);
        assertEquals(2, abs);
        abs = handler.caretPosToAbsPos(5);
        assertEquals(7, abs);
    }

   

    /**
     * Test of transformToLineNumber method, of class LineHandler.
     */
    @Test
    public void testTransformToLineNumber() {
       
    }


    /**
     * Test of getClosestLineBeginning method, of class LineHandler.
     */
    @Test
    public void testGetClosestLineBeginning() throws BadLocationException {
        String insert = "\n\n\nasda|sd";
        pane.getStyledDocument().insertString(0, insert, null);
        int closest = handler.getClosestLineBeginning(7);
        assertEquals(2, closest);
    }
}
