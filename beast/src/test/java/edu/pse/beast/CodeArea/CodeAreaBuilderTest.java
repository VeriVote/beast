/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea;

import edu.pse.beast.CodeArea.InputToCode.JTextPaneTestFrame;
import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.codearea.CodeAreaBuilder;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import javax.swing.JScrollPane;
import javax.swing.JTextPane;
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
public class CodeAreaBuilderTest {
    public CodeAreaBuilderTest() {
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

    /**
     * Test of createCodeArea method, of class CodeAreaBuilder.
     */
    @Test
    public void testCreateCodeArea() {  
        
    }
    
    public static void main(String[] args) {
        JTextPaneTestFrame frame = new  JTextPaneTestFrame();
        CodeAreaBuilder b = new CodeAreaBuilder();
        CodeArea ca = b.createCodeArea(frame.getTextPane(), frame.getScrollPane(), new ObjectRefsForBuilder(new StringLoaderInterface("de")));
        frame.setVisible(true);
    }
    
}
