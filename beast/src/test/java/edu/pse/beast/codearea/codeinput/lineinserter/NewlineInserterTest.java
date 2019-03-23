//package edu.pse.beast.codearea.codeinput.lineinserter;
//
//import static org.junit.Assert.assertEquals;
//
//import javax.swing.JTextPane;
//import javax.swing.text.BadLocationException;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.pse.beast.codearea.codeinput.CurlyBracesLineBeginningTabHandler;
//import edu.pse.beast.codearea.codeinput.TabInserter;
//import edu.pse.beast.codearea.codeinput.lineinserter.StandardNewlineInserter;
//
///**
// *
// * @author Holger-Desktop
// */
//public class NewlineInserterTest {
//    private JTextPane pane;
//    private TabInserter tabsInserter;
//    private CurlyBracesLineBeginningTabHandler lineBeg;
//
//    public NewlineInserterTest() {
//    }
//
//    @BeforeClass
//    public static void setUpClass() {
//    }
//
//    @AfterClass
//    public static void tearDownClass() {
//    }
//
//    @Before
//    public void setUp() {
//        pane = new JTextPane();
//        tabsInserter = new TabInserter(pane);
//        lineBeg = new CurlyBracesLineBeginningTabHandler(pane);
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    @Test
//    public void standardInserterTest() throws BadLocationException {
//        StandardNewlineInserter ins = new StandardNewlineInserter();
//        String insert = "{}";
//        pane.getStyledDocument().insertString(0, insert, null);
//        ins.insertNewlineAtCurrentPosition(pane, tabsInserter, lineBeg, 1);
//        String code = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
//        String codeafter = "{\n" +
//                            "}";
//        assertEquals(codeafter, code);
//
//        ins.insertNewlineAtCurrentPosition(pane, tabsInserter, lineBeg, 1);
//        code = pane.getText();
//        codeafter = "{\r\n" +
//                    "        \r\n" +
//                    "}";
//        //assertEquals(codeafter, code);
//    }
//
//
//
//}