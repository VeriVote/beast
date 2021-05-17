package edu.pse.beast.api.savingloading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.json.JSONArray;
import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.cbmc.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.savingloading.testruns.CBMCTestRunSaverLoaderHelper;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.CBMCTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCTestRunSaverLoaderTest {

	@Test
	public void testSavingLoadingCBMCTestRun() {

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.CANDIDATE_LIST,
				"asd");

		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("asd", "asdasd", "asdasd").get(0);

		int v = 5;
		int c = 5;
		int s = 5;

		CBMCCodeFileData cbmcCodeFile = new CBMCCodeFileData();

		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCodeInfo.setCode("asd");
		cbmcGeneratedCodeInfo
				.setVoteNumberToVariableName(Map.of(0, "0", 1, "1", 2, "2"));

		cbmcCodeFile.setCodeInfo(cbmcGeneratedCodeInfo);
		cbmcCodeFile.setFile(new File("./"));

		CodeGenOptions codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountVotersVarName("V");
		codeGenOptions.setCbmcAmountSeatsVarName("S");

		List<LoopBound> loopbounds = new ArrayList<>();

		CBMCTestRun r1 = new CBMCTestRun(v, s, c, codeGenOptions, loopbounds,
				cbmcCodeFile, descr, propDescr);

		JSONArray json = CBMCTestRunSaverLoaderHelper
				.cbmcTestRunListToJSON(List.of(r1));
		
		List<CBMCTestRun> loadedTestRuns = CBMCTestRunSaverLoaderHelper
				.cbmcTestRunListFromJsonArr(json, descr, propDescr);
		CBMCTestRun loadedR1 = loadedTestRuns.get(0);
		
		//TODO more testing
		assertEquals(r1.getTestOutput(), loadedR1.getTestOutput());
		assertEquals(r1.getCodeGenOptions().getCbmcAmountCandidatesVarName(), 
				loadedR1.getCodeGenOptions().getCbmcAmountCandidatesVarName());
		assertEquals(r1.getCodeGenOptions().getCbmcAmountSeatsVarName(), 
				loadedR1.getCodeGenOptions().getCbmcAmountSeatsVarName());
		assertEquals(r1.getCodeGenOptions().getCbmcAmountVotersVarName(), 
				loadedR1.getCodeGenOptions().getCbmcAmountVotersVarName());
	}
}
