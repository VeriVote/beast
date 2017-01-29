/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.codearea.InputToCode.LockedLinesHandler;
import edu.pse.beast.codearea.InputToCode.LockedLinesListener;
import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.codearea.SaveTextBeforeRemove;
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
public class LockedLinesHandlerTest {
    private LockedLinesHandler lockedLinesHandler;
    private JTextPane pane;
    private LineHandler lineHandler; 
    private SaveTextBeforeRemove beforeRemove;
    public LockedLinesHandlerTest() {
        this.pane = new JTextPane();
        this.lineHandler = new LineHandler(pane);
        Actionlist actionlist = new Actionlist();
        beforeRemove = new SaveTextBeforeRemove(pane, actionlist);
        
        this.lockedLinesHandler = new LockedLinesHandler(pane.getStyledDocument(),
                lineHandler, beforeRemove);
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

    @Test
    public void testHandlesUpdates() throws BadLocationException {
        String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1));;
        pane.getStyledDocument().insertString(0, "\n", null);
        assertFalse(lockedLinesHandler.isLineLocked(1));
        assertTrue(lockedLinesHandler.isLineLocked(2));
        pane.getStyledDocument().insertString(0, "asdasd\nasd\n\nasdasd", null);
        assertTrue(lockedLinesHandler.isLineLocked(5));            
    }
    
    @Test 
     public void testHandlesUpdateDirectlyAt() throws BadLocationException {
        String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1));        
        pane.getStyledDocument().insertString(5, "\n", null);
        assertTrue(lockedLinesHandler.isLineLocked(1));
        pane.getStyledDocument().insertString(4, "\n", null);
        assertTrue(lockedLinesHandler.isLineLocked(2)); 
        pane.getStyledDocument().insertString(5, "\n", null);
        assertTrue(lockedLinesHandler.isLineLocked(3));
    }
     
    @Test
    public void testHandlesRemoves() throws BadLocationException {
        String text = "asd\n\nasdasd\nasd\nasd\n\n\n";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(1);
        assertTrue(lockedLinesHandler.isLineLocked(1)); 
        beforeRemove.save();
        pane.getStyledDocument().remove(3, 1);
        assertTrue(lockedLinesHandler.isLineLocked(0));  
        lockedLinesHandler.lockLine(2);        
        text = "asd\nasdasd\nasd\nasd\n\n\n";
        beforeRemove.save();
        pane.getStyledDocument().remove(8, 3);
        assertTrue(lockedLinesHandler.isLineLocked(0));  
        assertTrue(lockedLinesHandler.isLineLocked(1)); 
        text = "asd\nasdaasd\nasd\n\n\n"; 
        beforeRemove.save();
        pane.getStyledDocument().remove(11, 1);        
        assertTrue(lockedLinesHandler.isLineLocked(0));  
        assertTrue(lockedLinesHandler.isLineLocked(1));        
    }
    
    @Test
    public void testHandlesRemovesOneBracket() throws BadLocationException {
        String text = "\n}\n";
        pane.getStyledDocument().insertString(0, text, null);
        lockedLinesHandler.lockLine(1);
        beforeRemove.save();
        pane.getStyledDocument().remove(2, 1);  
        assertEquals("\n}", pane.getStyledDocument().getText(0, 2));
        assertTrue(lockedLinesHandler.isLineLocked(1));
    }
    
}
