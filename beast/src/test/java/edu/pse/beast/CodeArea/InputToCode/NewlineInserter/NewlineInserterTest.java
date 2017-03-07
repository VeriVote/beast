/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.CurlyBracesLineBeginningTabHandler;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.StandardNewlineInserter;
import edu.pse.beast.codearea.InputToCode.TabInserter;
import org.junit.*;

import javax.swing.*;
import javax.swing.text.BadLocationException;

import static org.junit.Assert.assertEquals;

/**
 *
 * @author Holger-Desktop
 */
public class NewlineInserterTest {
    private JTextPane pane;
    private TabInserter tabsInserter;
    private CurlyBracesLineBeginningTabHandler lineBeg;
    
    public NewlineInserterTest() {
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
        tabsInserter = new TabInserter(pane);
        lineBeg = new CurlyBracesLineBeginningTabHandler(pane);
    }
    
    @After
    public void tearDown() {
    }

    @Test 
    public void standardInserterTest() throws BadLocationException {
        StandardNewlineInserter ins = new StandardNewlineInserter();
        String insert = "{}";
        pane.getStyledDocument().insertString(0, insert, null);
        ins.insertNewlineAtCurrentPosition(pane, tabsInserter, lineBeg, 1);
        String code = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
        String codeafter = "{\n" +
                            "}";
        assertEquals(codeafter, code);
        
        ins.insertNewlineAtCurrentPosition(pane, tabsInserter, lineBeg, 1);
        code = pane.getText();
        codeafter = "{\r\n" +
                    "        \r\n" +
                    "}";        
        //assertEquals(codeafter, code);
    }

    
    
}
