//package edu.pse.beast.gui;
//
//import java.awt.event.KeyEvent;
//import java.io.File;
//
//import org.junit.Assert;
//import org.junit.Before;
//import org.junit.Test;
//
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
//
///**
// * @author Holger Klein
// */
//public class GuiTestCEditor {
//    private static final long WAIT_TIME = 500;
//    GuiTestHelper helper = new GuiTestHelper();
//
//    @Before
//    public void setUp() throws InterruptedException {
//        helper.startNewBEASTInstance();
//    }
//
//    @Test
//    public void testSaveAndLoadFile() throws InterruptedException {
//        CElectionDescriptionEditor electionDescriptionEditor =
//            helper.getCEditorOfCurrentInstace();
//        electionDescriptionEditor.setVisible(true);
//        int[] keys = {
//                KeyEvent.VK_N, //name
//                KeyEvent.VK_ENTER //and save
//        };
//        helper.performKeystrokesConcurrently(keys, 500, 100);
//        helper.clickMenuItemInCEditor(0,1,50);
//        File createdfile = new File("./projectFiles/n.elec");
//        Assert.assertTrue(createdfile.exists());
//        int[] tabAndEnter = {
//                KeyEvent.VK_TAB,
//                KeyEvent.VK_TAB,
//                KeyEvent.VK_ENTER
//        };
//        helper.performKeystrokesConcurrently(tabAndEnter, WAIT_TIME, 100);
//        helper.clickMenuItemInCEditor(0, 3, 50);
//        createdfile.delete();
//        helper.endInstance();
//    }
//
//    @Test
//    public void testCreateNewfileCEditor() throws InterruptedException {
////        CElectionDescriptionEditor electionDescriptionEditor =
////            helper.getCEditorOfCurrentInstace();
////        ElectionDescription electionDescription =
////            electionDescriptionEditor.getElectionDescription();
////        Assert.assertEquals(electionDescription.getInputType().getInputID(),
////                ElectionTypeContainer.ElectionInputTypeIds.SINGLE_CHOICE);
////        electionDescriptionEditor.setVisible(true);
////        helper.clickMenuItemInCEditor(0, 0, WAIT_TIME);
////
////        int[] keys = {
////                KeyEvent.VK_DOWN,
////                KeyEvent.VK_DOWN,
////                KeyEvent.VK_ENTER, //choose new election input
////                KeyEvent.VK_TAB, //switch to name field
////                KeyEvent.VK_N, //type new name
////                KeyEvent.VK_ENTER //clicks apply
////        };
////        helper.performKeystrokes(keys, 50);
////        electionDescription = electionDescriptionEditor.getElectionDescription();
////        Assert.assertEquals(electionDescription.getInputType().getInputID(),
////                ElectionTypeContainer.ElectionInputTypeIds.PREFERENCE);
////        helper.endInstance();
//    }
//}