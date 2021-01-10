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
import edu.pse.beast.api.testrunner.propertycheck.CBMCXmlOutputHandler;
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
		
		CBMCXmlOutputHandler xmlOutputHandler = new CBMCXmlOutputHandler();
		BEASTPropertyCheckSession sess = beast.startPropertyCheck(new BEASTCallback() {		
			
			@Override
			public void onCompleteCommand(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int v, int c, int s, String uuid,
					String completeCommand) {
				// TODO Auto-generated method stub
				System.out.println(completeCommand);
			}
			
			@Override
			public void onPropertyTestRawOutputComplete(ElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c, int v, String uuid,
					List<String> cbmcOutput) {
				System.out.println("finished for s = " + s + " c = " + c + " v = " + v);
				xmlOutputHandler.onCompleteOutput(description, propertyDescr, s, c, v, uuid, cbmcOutput);
			}

		}, descr, propList, params);
		sess.await();
	}

}
