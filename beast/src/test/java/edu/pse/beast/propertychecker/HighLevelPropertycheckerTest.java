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
import java.util.Iterator;
import java.util.List;

import javax.swing.JOptionPane;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import edu.pse.beast.Gui.GuiTestHelper;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.Project;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.parametereditor.ParameterEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.saverloader.ProjectSaverLoader;

public class HighLevelPropertycheckerTest {

	GuiTestHelper helper = new GuiTestHelper();

	@Before
	public void setUp() throws InterruptedException {
		helper.startNewBEASTInstance();
	}

	@Test
	public void testCBMCCheck() throws InterruptedException, AWTException {
		ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();

		String pathToProject = "./src/test/testfiles/SingleChoiceAnonymity.beast";

		ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();

		Project loadedProject = (Project) projectSaverLoader.createFromSaveString(readProject(pathToProject));

		parameterEditor.loadProject(loadedProject);

		parameterEditor.startCheck();

		boolean running = true;
		while (running) {
			if (parameterEditor.getReacts()) {
				running = false;
			} else {
				Thread.sleep(1000);
			}

		}

		PropertyList propList = helper.getPropListOfCurrentInstance();

		List<PropertyItem> list = propList.getList();

		for (Iterator<PropertyItem> iterator = list.iterator(); iterator.hasNext();) {
			PropertyItem propertyItem = (PropertyItem) iterator.next();
			assertNull(propertyItem.getExample());
		}

	}

	@Test
	public void testCBMCCheckTimeOut() throws InterruptedException, AWTException {


		ParameterEditor parameterEditor = helper.getParamEditorOfCurrentInstace();

		String pathToProject = "./src/test/testfiles/timeoutTest.beast";

		ProjectSaverLoader projectSaverLoader = new ProjectSaverLoader();

		Project loadedProject = (Project) projectSaverLoader.createFromSaveString(readProject(pathToProject));

		parameterEditor.loadProject(loadedProject);
		

		parameterEditor.startCheck();

		boolean running = true;
		while (running) {
			if (parameterEditor.getReacts()) {
				running = false;
			} else {
				Thread.sleep(1000);
			}

		}

		PropertyList propList = helper.getPropListOfCurrentInstance();

		List<PropertyItem> list = propList.getList();

		for (Iterator<PropertyItem> iterator = list.iterator(); iterator.hasNext();) {
			PropertyItem propertyItem = (PropertyItem) iterator.next();
			assertNull(propertyItem.getExample());
		}

	}

	private String readProject(String pathToProject) throws InterruptedException {
		helper.startNewBEASTInstance();

		// create a new saveString that contains a beast-project

		File selectedFile = new File(pathToProject);
		BufferedReader inputReader = null;

		String toReturn = "";

		try {
			inputReader = new BufferedReader(new InputStreamReader(new FileInputStream(selectedFile), "UTF8"));
		} catch (UnsupportedEncodingException e) {

		} catch (FileNotFoundException e) {

		}
		String sCurrentLine;
		try {
			while ((sCurrentLine = inputReader.readLine()) != null) {
				toReturn += (sCurrentLine + "\n");
			}
		} catch (IOException e) {

		}

		return toReturn;

	}

}
