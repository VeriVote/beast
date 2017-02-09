/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import edu.pse.beast.codearea.InputToCode.TabInserter;
import edu.pse.beast.codearea.InputToCode.LineHandler;
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
public class TabInserterTest {
    
    private TabInserter ins;
    private LineHandler lineHandler;
    private JTextPane pane;
    
    public TabInserterTest() {
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
        lineHandler = new LineHandler(pane);
        //ins = new TabInserter(pane, lineHandler);
    }
    
    @After
    public void tearDown() {
    }

    /**
     * Test of insertTabAtPos method, of class TabInserter.
     */
    @Test
    public void testInsertTabAtPos() throws Exception {
        System.out.println("insertTabAtPos");
    }
    
    @Test
    public void testCaretMovement() throws Exception {
        System.out.println("testCaretMovement");
        ins.insertTabAtPos(0);
        int pos = pane.getCaretPosition();
        assertEquals(0, pos);
    }

    @Test
    public void testAddTab() throws BadLocationException {
        System.out.println("AddTab");
        String insert = "";
        ins.insertTabAtPos(0);
        String code = pane.getText();
        String after = "        ";
        assertEquals(after, code);
        
        pane = new JTextPane();
        lineHandler = new LineHandler(pane);
        //ins = new TabInserter(pane, lineHandler);
        
        insert = "0123";
        pane.getStyledDocument().insertString(0, insert, null);
        
        ins.insertTabAtPos(2);
        String comp =   "01234567|";
        after =         "01      23";
        code = pane.getText();
        assertEquals(after, code);
    }

    @Test
    public void testAddAfterNewline() throws BadLocationException {
        pane.getStyledDocument().insertString(0, "\n", null);
        assertEquals("\r\n", pane.getText());
        ins.insertTabAtPos(1);
        assertEquals("\r\n" + "        ", pane.getText());
    }
  
    
    
    
}
