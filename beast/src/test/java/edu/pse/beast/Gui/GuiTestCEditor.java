package edu.pse.beast.Gui;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionTypeContainer;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.awt.event.KeyEvent;
import java.io.File;

/**
 * Created by holger on 07.03.17.
 */
public class GuiTestCEditor {

    private final long waittime = 500;
    GuiTestHelper helper = new GuiTestHelper();

    @Before
    public void setUp() throws InterruptedException {
        helper.startNewBEASTInstance();
    }

    @Test
    public void testSaveAndLoadFile() throws InterruptedException {
        CElectionDescriptionEditor electionDescriptionEditor = helper.getCEditorOfCurrentInstace();
        electionDescriptionEditor.setVisible(true);

        int[] keys = {
                KeyEvent.VK_N, //name
                KeyEvent.VK_ENTER //and save
        };

        helper.performKeystrokesConcurrently(keys, 500, 100);

        helper.clickMenuItemInCEditor(0,1,50);


        File createdfile = new File("./projectFiles/n.elec");
        Assert.assertTrue(createdfile.exists());

        int[] tabAndEnter = {
                KeyEvent.VK_TAB,
                KeyEvent.VK_TAB,
                KeyEvent.VK_ENTER
        };

        helper.performKeystrokesConcurrently(tabAndEnter, waittime,100);

        helper.clickMenuItemInCEditor(0, 3, 50);

        createdfile.delete();
    }

    @Test
    public void testCreateNewfileCEditor() throws InterruptedException {
        CElectionDescriptionEditor electionDescriptionEditor = helper.getCEditorOfCurrentInstace();
        ElectionDescription electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(),
                ElectionTypeContainer.ElectionTypeIds.SINGLE_CHOICE);

        electionDescriptionEditor.setVisible(true);
        helper.clickMenuItemInCEditor(0,0,waittime);

        int[] keys = {
                KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, //choose new election input
                KeyEvent.VK_TAB, //switch to name field
                KeyEvent.VK_N, //type new name
                KeyEvent.VK_ENTER //clicks apply
        };

        helper.performKeystrokes(keys, 50);
        electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(),
                ElectionTypeContainer.ElectionTypeIds.PREFERENCE);
    }

}
