//package edu.pse.beast.codearea.codeinput;
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
///**
// *
// * @author Holger Klein
// */
//public class TabInserterTest {
//
//    private TabInserter ins;
//    private JTextPane pane;
//
//    public TabInserterTest() {
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
//        ins = new TabInserter(pane);
//    }
//
//    @After
//    public void tearDown() {
//    }
//
//    /**
//     * Test of insertTabAtPos method, of class TabInserter.
//     */
//    @Test
//    public void testInsertTabAtPos() throws Exception {
//        System.out.println("insertTabAtPos");
//    }
//
//    @Test
//    public void testCaretMovement() throws Exception {
//        System.out.println("testCaretMovement");
//        ins.insertTabAtPos(0);
//        int pos = pane.getCaretPosition();
//        assertEquals(0, pos);
//    }
//
//    @Test
//    public void testAddTab() throws BadLocationException {
//        System.out.println("AddTab");
//        String insert = "";
//        ins.insertTabAtPos(0);
//        String code = pane.getText();
//        String after = "        ";
//        assertEquals(after, code);
//
//        pane = new JTextPane();
//        ins = new TabInserter(pane);
//
//        insert = "0123";
//        pane.getStyledDocument().insertString(0, insert, null);
//
//        ins.insertTabAtPos(2);
//        String comp =   "01234567|";
//        after =         "01      23";
//        code = pane.getText();
//        assertEquals(after, code);
//    }
//
//    @Test
//    public void testAddAfterNewline() throws BadLocationException {
//        pane.getStyledDocument().insertString(0, "\n", null);
//        assertEquals(
//            "\n",
//            pane.getStyledDocument()
//                .getText(0, pane.getStyledDocument().getLength()));
//        ins.insertTabAtPos(1);
//        assertEquals(
//            "\n" + "        ",
//            pane.getStyledDocument()
//                .getText(0, pane.getStyledDocument().getLength()));
//    }
//}