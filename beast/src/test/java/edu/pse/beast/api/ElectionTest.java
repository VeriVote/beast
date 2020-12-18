package edu.pse.beast.api;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.fileio.FileIOInterface;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.api.testrunner.propertycheck.PropertyTestResult;
import edu.pse.beast.codeareajavafx.SaverLoader;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.highlevel.javafx.GUIController;
import edu.pse.beast.saverloader.MinimalSaverInterface;
import edu.pse.beast.saverloader.ProjectSaverLoader;
import edu.pse.beast.toolbox.Project;

class ElectionTest {

	FileIOInterface fileIOInterface = new FileIOInterface();
	BEAST beast = new BEAST(new CBMCProcessStarterWindows());

	@Test
	void testBlack() throws Exception {
		File elecFile = new File("./projectFiles/Black/description.elec");
		ElectionDescription descr = fileIOInterface.loadElecDescrFromFile(elecFile);
		File propFile = new File("./projectFiles/Black/propertyList/Reinforcement_0/Reinforcement.prop");
		PreAndPostConditionsDescription prop = fileIOInterface.loadPropDescrFromFile(propFile);
		ElectionCheckParameter params = CreationHelper.createParams(1, 1, 1);

		List<PreAndPostConditionsDescription> propList = new ArrayList<>();
		propList.add(prop);

		beast.startTest(new BEASTCallback() {
			@Override
			public void onPropertyTestResult(ElectionDescription electionDescription,
					PreAndPostConditionsDescription preAndPostConditionsDescription, int v, int c, int s,
					PropertyTestResult result) {
				// TODO Auto-generated method stub
				System.out.println(preAndPostConditionsDescription.getName() + " V=" + v + " C=" + c + " S=" + s + " "
						+ result.isSuccess());
			}
		}, descr, propList, params);

		/*
		 * File projFile = new File("./projectFiles/Black.proj"); Project proj =
		 * fileIOInterface.loadProjectFromFile(projFile);
		 */

	}

}
