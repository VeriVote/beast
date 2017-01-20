/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.LineHandler;
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

    /**
     * Test of getClosestLineBeginning method, of class LineHandler.
     */
    @Test
    public void testGetClosestLineBeginning() throws BadLocationException {        
        System.out.println("getClosestLineBeginning");
        pane.getStyledDocument().insertString(0, "\n1\n345\n789\n", null);
        
        int closest = handler.getClosestLineBeginning(0);
        assertEquals(0, closest);
        closest = handler.getClosestLineBeginning(1);
        assertEquals(0, closest);
        closest = handler.getClosestLineBeginning(5);
        assertEquals(2, closest);
        closest = handler.getClosestLineBeginning(8);
        assertEquals(6, closest);
        closest = handler.getClosestLineBeginning(9);
        assertEquals(6, closest);
    }

    /**
     * Test of getDistanceToLineBeginning method, of class LineHandler.
     */
    @Test
    public void testGetDistanceToLineBeginning() throws BadLocationException {
        System.out.println("getDistanceToLineBeginning");
        pane.getStyledDocument().insertString(0, "\n1\n345\n789\n", null);
        
        int dist = handler.getDistanceToLineBeginning(0);
        assertEquals(0, dist);
        
        dist = handler.getDistanceToLineBeginning(1);
        assertEquals(1, dist);
        
        dist = handler.getDistanceToLineBeginning(4);
        assertEquals(2, dist);
        
        dist = handler.getDistanceToLineBeginning(5);
        assertEquals(3, dist);
    } 
    
    @Test
    public void testHandleRemovedLines() throws BadLocationException {
        System.out.println("handleRemovedLines");
        pane.getStyledDocument().insertString(0, "\n1\n345\n789\n", null);
        pane.getStyledDocument().remove(2, 5); // \n1789\n
        handler.printLineNumbers();
        System.out.println(pane.getText());
        
        int dist = handler.getDistanceToLineBeginning(0);
        assertEquals(0, dist);
        
        dist = handler.getDistanceToLineBeginning(1);
        assertEquals(1, dist);
        
        dist = handler.getDistanceToLineBeginning(3);
        assertEquals(3, dist);
    }
    
}
