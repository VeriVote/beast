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

/**
 * Created by holger on 07.03.17.
 */
public class CreateNewFileTest {
    private BEASTCommunicator communicator = new BEASTCommunicator();
    private CentralObjectProvider centralObjectProvider = new PSECentralObjectProvider(communicator);
    private final long waittime = 100;

    @Before
    public void setUp() {
        communicator = new BEASTCommunicator();
        centralObjectProvider = new PSECentralObjectProvider(communicator);
        communicator.setCentralObjectProvider(centralObjectProvider);
    }

    @Test
    public void testCreateNewfileCEditor() throws InterruptedException, AWTException {
        ParameterEditor parameterEditor = (ParameterEditor) centralObjectProvider.getParameterSrc();
        while(!parameterEditor.getView().isVisible()) Thread.sleep(500);
        CElectionDescriptionEditor electionDescriptionEditor =
                (CElectionDescriptionEditor) centralObjectProvider.getElectionDescriptionSource();


        ElectionDescription electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(), "one_candidate_per_voter");

        electionDescriptionEditor.setVisible(true);
        JMenuBar menuBar = electionDescriptionEditor.getView().getMainMenuBar();
        Thread.sleep(waittime);
        menuBar.getMenu(0).doClick();
        Thread.sleep(waittime);
        menuBar.getMenu(0).getItem(0).doClick();
        Thread.sleep(waittime);

        Robot robot = new Robot();
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_DOWN);
        robot.keyRelease(KeyEvent.VK_DOWN);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        robot.keyPress(KeyEvent.VK_TAB);
        robot.keyRelease(KeyEvent.VK_TAB);
        Thread.sleep(waittime);
        robot.keyPress(KeyEvent.VK_N);
        robot.keyRelease(KeyEvent.VK_N);
        Thread.sleep(waittime);
        robot.keyPress(KeyEvent.VK_ENTER);
        robot.keyRelease(KeyEvent.VK_ENTER);
        Thread.sleep(waittime);

        electionDescription = electionDescriptionEditor.getElectionDescription();
        Assert.assertEquals(electionDescription.getInputType().getId(), "list_of_candidates_per_voter");
    }
}
