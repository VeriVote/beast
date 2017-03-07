package edu.pse.beast.Gui;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.highlevel.BEASTCommunicator;
import edu.pse.beast.highlevel.CentralObjectProvider;
import edu.pse.beast.highlevel.PSECentralObjectProvider;
import edu.pse.beast.parametereditor.ParameterEditor;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.io.File;
import java.security.Key;

/**
 * Created by holger on 07.03.17.
 */
public class GuiTestCEditor {

    private final long waittime = 100;
    GuiTestHelper helper = new GuiTestHelper();

    @Before
    public void setUp() throws InterruptedException {
        helper.startNewBEASTInstance();
    }

    @Test
    public void testSaveFile() throws InterruptedException, AWTException {
        CElectionDescriptionEditor electionDescriptionEditor = helper.getCEditorOfCurrentInstace();
        electionDescriptionEditor.setVisible(true);

        int[] keys = {
                KeyEvent.VK_N, //name
                KeyEvent.VK_ENTER //and save
        };

        helper.performKeystrokesConcurrently(keys, 500, 50);

        helper.clickMenuItemInCEditor(0,1,50);


        File createdfile = new File("./projectFiles/n.elec");
        Assert.assertTrue(createdfile.exists());
        createdfile.delete();
    }

    @Test
    public void testCreateNewfileCEditor() throws InterruptedException, AWTException {
        CElectionDescriptionEditor electionDescriptionEditor = helper.getCEditorOfCurrentInstace();
        ElectionDescription electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(), "one_candidate_per_voter");

        electionDescriptionEditor.setVisible(true);
        helper.clickMenuItemInCEditor(0,0,100);

        int[] keys = {
                KeyEvent.VK_DOWN, KeyEvent.VK_DOWN, KeyEvent.VK_ENTER, //choose new election input
                KeyEvent.VK_TAB, //switch to name field
                KeyEvent.VK_N, //type new name
                KeyEvent.VK_ENTER //clicks apply
        };

        helper.performKeystrokes(keys, 50);
        electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(), "list_of_candidates_per_voter");
    }
}
