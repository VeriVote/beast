package edu.pse.beast.api.savingloading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.apache.commons.lang3.NotImplementedException;
import org.json.JSONException;
import org.junit.jupiter.api.Test;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.testrunner.propertycheck.process_starter.CBMCProcessStarterWindows;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.gui.testruneditor.testconfig.TestConfiguration;
import edu.pse.beast.gui.testruneditor.testconfig.cbmc.CBMCPropertyTestConfiguration;
import edu.pse.beast.gui.workspace.BeastWorkspace;

public class WorkspaceSaverLoaderTest {
	@Test
	public void testSavingLoading() throws IOException {
		BeastWorkspace ws = new BeastWorkspace();

		String name = "test";
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"test");
		descr.getVotingFunction()
				.setCode("for(int i = 0; i < V; ++i) {}\n" + "return 0;\n");
		descr.addLoopBoundForFunction("voting", 0, "V");

		descr.createNewVotingSigFunctionAndAdd("votehelper");

		File descrFile = new File("testfiles/borda.belec");
		try {
			descr = SavingLoadingInterface.loadCElection(descrFile);
		} catch (NotImplementedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		File propDescrFile = new File("testfiles/reinforcement.bprp");
		PreAndPostConditionsDescription propDescr = null;
		try {
			propDescr = SavingLoadingInterface
					.loadPreAndPostConditionDescription(propDescrFile);

		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		TestConfiguration testConfig = new TestConfiguration(descr, propDescr,
				"test");
		CBMCPropertyTestConfiguration cc = new CBMCPropertyTestConfiguration();

		cc.setMinVoters(5);
		cc.setMinCands(5);
		cc.setMinSeats(5);

		cc.setMaxCands(5);
		cc.setMaxVoters(5);
		cc.setMaxSeats(5);

		cc.setDescr(descr);
		cc.setPropDescr(propDescr);

		cc.setName("test five");
		testConfig.addCBMCTestConfiguration(cc);

		cc.setStartRunsOnCreation(false);

		CodeGenOptions codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountVotersVarName("V");
		codeGenOptions.setCbmcAmountSeatsVarName("S");

		BeastWorkspace beastWorkspace = new BeastWorkspace();

		beastWorkspace.setCodeGenOptions(codeGenOptions);

		Path currentRelativePath = Paths.get("");
		String s = currentRelativePath.toAbsolutePath().toString();
		File baseDir = new File(s);

		beastWorkspace.setBaseDir(baseDir);
		beastWorkspace.addElectionDescription(descr);
		beastWorkspace.addFileForDescr(descr, descrFile);

		beastWorkspace.addPropertyDescription(propDescr);
		beastWorkspace.addFileForPropDescr(propDescr, propDescrFile);

		beastWorkspace.addTestConfiguration(testConfig);
		
		beastWorkspace.setCbmcProcessStarter(new CBMCProcessStarterWindows());

		File f = new File("testfiles");
		f.mkdirs();
		f = new File("testfiles/test.beastws");

		SavingLoadingInterface.storeBeastWorkspace(beastWorkspace, f);
		BeastWorkspace loadedBeastWorkspace = SavingLoadingInterface
				.loadBeastWorkspace(f);
		

		int i = 0;
	}
}
