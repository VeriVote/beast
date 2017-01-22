/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.ActionAdder;

import edu.pse.beast.codearea.ActionAdder.TextChangedActionAdder;
import edu.pse.beast.codearea.Actionlist.Actionlist;
import java.awt.event.KeyEvent;
import javax.swing.JTextPane;
import javax.swing.event.CaretEvent;
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
public class TextChangedActionAdderTest {
    private TextChangedActionAdder adder;
    private Actionlist list;
    private JTextPane pane;
    
    public TextChangedActionAdderTest() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
        this.pane = new JTextPane();
        this.list = new Actionlist();
        this.adder = new TextChangedActionAdder(pane, list);
    }
    
    @After
    public void tearDown() {
    }

    @Test
    public void testCreateActions() throws BadLocationException {
        String insert = "insertion";
        for(int i = 0; i < insert.length(); ++i) {
            pane.getStyledDocument().insertString(pane.getCaretPosition(),
                    Character.toString(insert.charAt(i)),
                    null);
            pane.setCaretPosition(pane.getCaretPosition() + 1);
        }
        pane.setCaretPosition(0);
        list.undoLast();
        assertEquals("", pane.getText());
        list.redoLast();
        assertEquals("insertion", pane.getText());
        pane.getStyledDocument().insertString(0, insert, null);
        list.undoLast();
        assertEquals("insertion", pane.getText());
        list.redoLast();
        assertEquals("insertioninsertion", pane.getText());
    }
    
}
