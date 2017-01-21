/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode.NewlineInserter;

import edu.pse.beast.codearea.InputToCode.CurlyBracesLineBeginningTabHandler;
import edu.pse.beast.codearea.InputToCode.LineBeginningTabsHandler;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.NewlineInserter;
import edu.pse.beast.codearea.InputToCode.LineHandler;
import edu.pse.beast.codearea.InputToCode.NewlineInserter.StandardNewlineInserter;
import edu.pse.beast.codearea.InputToCode.TabInserter;
import edu.pse.beast.codearea.InputToCode.UserInsertToCode;
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
public class NewlineInserterTest {
    private JTextPane pane;
    private LineHandler lineHandler;
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
        lineHandler= new LineHandler(pane);
        tabsInserter = new TabInserter(pane, lineHandler);
        lineBeg = new CurlyBracesLineBeginningTabHandler(pane, lineHandler);
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
        String code = pane.getText();
        String codeafter = "{\r\n" + 
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
