package edu.pse.beast.api;

import java.io.File;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarter;
import edu.pse.beast.api.testrunner.propertycheck.CBMCProcessStarterWindows;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class TestCompletePropertyCheckPipeline {
	
	CBMCProcessStarter cbmcProcessStarter = new CBMCProcessStarterWindows();			
	BEAST beast = new BEAST(cbmcProcessStarter);
	
	@Test
	public void testBorda() throws InterruptedException {
		String bordaCode = 
				  "    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n"
				+ "\n"
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
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().getCode().add(bordaCode);
		
		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
		
		List<PreAndPostConditionsDescription> propDecsr =
				CreationHelper.createSimpleCondList("reinforce", pre, post);
		
		ElectionCheckParameter params = CreationHelper.createParamsOnlyOneRun(5, 10, 10, 10);
		
		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");
		
		BEASTPropertyCheckSession sess = beast.startPropertyCheck(
				new BEASTCallback() {
					@Override
					public void onCompleteCommand(
							CElectionDescription description,
							PreAndPostConditionsDescription propertyDescr,
							int v, int c, int s, String uuid,
							String completeCommand) {
						System.out.println(completeCommand);
					}
					
					@Override
					public void onTestFileCreated(
							CElectionDescription description,
							PreAndPostConditionsDescription propertyDescr,
							int v, int c, int s, String uuid, File cbmcFile) {
						System.out.println(cbmcFile.getAbsolutePath());
					}
					
					@Override
					public void onPropertyTestRawOutput(
							CElectionDescription description,
							PreAndPostConditionsDescription propertyDescr,
							int s, int c, int v, String uuid,
							String output) {
						System.out.println(output);
					}					
				},
			descr, propDecsr, params,  
			options);
		sess.await();
	}
	
}
