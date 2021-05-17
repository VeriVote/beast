package edu.pse.beast.api;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.specificcbmcrun.PreferenceParameters;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.CBMCCodeFileGeneratorNEW;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCProcessHandlerWindows;
import edu.pse.beast.api.testrunner.run_with_specific_params.CBMCRunWithSpecificParamsWorkUnit;
import edu.pse.beast.api.testrunner.run_with_specific_params.SpecificParamsTestRun;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class SpecificRunTest {
	@Test
	public void testRunWithParams() throws IOException {
		SpecificCBMCRunParametersInfo info = new SpecificCBMCRunParametersInfo();
		String bordaCode = 
				"    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n" + "\n"
				+ "    for (i = 0; i < C; i++) {\n" 
				+ "        result[i] = 0;\n"
				+ "    }\n" 
				+ "    for (i = 0; i < V; i++) {\n"
				+ "        for (j = 0; j < C; j++) {\n"
				+ "            if (votes[i][j] < C) {\n"
				+ "                result[votes[i][j]] += (C - j) - 1;\n"
				+ "            }\n" 
				+ "        }\n" 
				+ "    }";

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().setCode(bordaCode);
		
		descr.getLoopBoundHandler().addLoopBoundForFunction(
				"voting",
				0,
				LoopBoundType.LOOP_BOUND_AMT_CANDS, 
				Optional.empty());
		descr.getLoopBoundHandler().addLoopBoundForFunction(
				"voting",
				0,
				LoopBoundType.LOOP_BOUND_AMT_VOTERS, 
				Optional.empty());
		descr.getLoopBoundHandler().addLoopBoundForFunction(
				"voting",
				0,
				LoopBoundType.LOOP_BOUND_AMT_CANDS, 
				Optional.empty());
		
		info.setDescr(descr);		

		PreferenceParameters parameters = new PreferenceParameters(10);
		parameters.addVoter(List.of(1, 0, 1, 1, 1, 0, 0, 0, 1, 1));
		parameters.addVoter(List.of(1, 1, 1, 0, 1, 0, 0, 0, 1, 1));
		info.setVotingParameters(parameters);

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountSeatsVarName("S");
		options.setCbmcAmountVotersVarName("V");

		CBMCCodeFileData cbmcCodeFileData = CBMCCodeFileGeneratorNEW
				.createCodeFileSpecificParameters(info, options);
		SpecificParamsTestRun testRun = new SpecificParamsTestRun(info,
				cbmcCodeFileData, options);

		CBMCProcessHandlerWindows processHandler = new CBMCProcessHandlerWindows(
				"D:\\Visual studio\\Common7\\Tools\\VsDevCmd.bat");
		CBMCRunWithSpecificParamsWorkUnit workUnit = new CBMCRunWithSpecificParamsWorkUnit(
				processHandler);

		testRun.setWorkUnit(workUnit);
		workUnit.setCb(new CBMCTestCallback() {
			@Override
			public void onPropertyTestRawOutput(String sessionUUID,
					CElectionDescription description,
					PreAndPostConditionsDescription propertyDescr, int s, int c,
					int v, String uuid, String output) {
				System.out.println(output);
			}
		});
		
		workUnit.doWork();

	}
}
