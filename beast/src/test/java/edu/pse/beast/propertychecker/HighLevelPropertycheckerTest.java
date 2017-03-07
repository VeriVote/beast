package edu.pse.beast.propertychecker;

import static org.junit.Assert.*;

import java.awt.AWTException;
import java.awt.event.KeyEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;

import javax.swing.JOptionPane;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.pse.beast.Gui.GuiTestHelper;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.saverloader.ProjectSaverLoader;

public class HighLevelPropertycheckerTest {

    GuiTestHelper helper = new GuiTestHelper();
    String content = "";

    @Before
    public void setUp() throws InterruptedException {
        helper.startNewBEASTInstance();
        
        //create a new saveString that contains a beast-project
        
        File selectedFile = new File("./src/test/testfiles/SingleChoiceAnonymity.beast");
        BufferedReader inputReader = null;
        try {
            inputReader = new BufferedReader(
                    new InputStreamReader(
                            new FileInputStream(selectedFile), "UTF8"));
        } catch (UnsupportedEncodingException e) {
            
        } catch (FileNotFoundException e) {

        }
        String sCurrentLine;
        try {
            while ((sCurrentLine = inputReader.readLine()) != null) {
                content += (sCurrentLine + "\n");
            }
        } catch (IOException e) {
            
        } 
    }

    @Test
    public void testCBMCCheck() throws InterruptedException, AWTException {
        ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();
        
        ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();
        Project loadedProject = (Project) projectSaverLoader .createFromSaveString(content);
        parameterEditor.loadProject(loadedProject);
        
        parameterEditor.startCheck();
        
        Thread.sleep(10000);
    }
}
