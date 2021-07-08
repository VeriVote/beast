package edu.pse.beast.api.savingloading;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;

public class SavingLoadingTest {

	@Test
	public void testSavingLoadingOfElectionDescription() throws IOException {
		String bordaCode = "    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n" + "\n"
				+ "    for (i = 0; i < C; i++) {\n" + "        result[i] = 0;\n"
				+ "    }\n" + "    for (i = 0; i < V; i++) {\n"
				+ "        for (j = 0; j < C; j++) {\n"
				+ "            result[votes[i][j]] += (C - j) - 1;\n"
				+ "        }\n" + "    }" + "    unsigned int max = 0;\n"
				+ "    for (i = 0; i < C; i++) {\n"
				+ "        if (max < res[i]) {\n"
				+ "            max = res[i];\n"
				+ "            for (j = 0; j < C; j++) {\n"
				+ "                r.arr[j] = 0;\n" + "            }\n"
				+ "            r.arr[i] = 1;\n"
				+ "        } else if (max == res[i]) {\n"
				+ "            r.arr[i] = 1;\n" + "        }\n" + "    }";

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().setCode(bordaCode);

		SimpleTypeFunction simpleFunc = new SimpleTypeFunction("asd",
				List.of(CElectionSimpleTypes.BOOL, CElectionSimpleTypes.DOUBLE),
				List.of("i", "j"), CElectionSimpleTypes.FLOAT);
		descr.addSimpleFunction(simpleFunc);

		CodeGenOptions options = new CodeGenOptions();
		options.setCurrentAmountCandsVarName("C");
		options.setCurrentAmountVotersVarName("V");
		List<ExtractedCLoop> extractedCLoops = AntlrCLoopParser
				.findLoops("voting", bordaCode, options);

		descr.getVotingFunction().setExtractedLoops(extractedCLoops);

		File f = new File("testfiles");
		f.mkdirs();
		f = new File("testfiles/borda.belec");
		SavingLoadingInterface.storeCElection(descr, f);

		CElectionDescription loadedDescr = SavingLoadingInterface
				.loadCElection(f);

		assertEquals(descr.getInputType(), loadedDescr.getInputType());
		assertEquals(descr.getOutputType(), loadedDescr.getOutputType());
		assertEquals(descr.getVotingFunction().getName(),
				loadedDescr.getVotingFunction().getName());
		assertEquals(descr.getVotingFunction().getCode(),
				loadedDescr.getVotingFunction().getCode());

		assertEquals(descr.getVotingFunction().getExtractedLoops().size(),
				loadedDescr.getVotingFunction().getExtractedLoops().size());

		for (int i = 0; i < descr.getVotingFunction().getExtractedLoops()
				.size(); ++i) {
			ExtractedCLoop loop = descr.getVotingFunction().getExtractedLoops()
					.get(i);
			ExtractedCLoop loadedLoop = loadedDescr.getVotingFunction()
					.getExtractedLoops().get(i);
			assertEquals(loop.getChildrenLoops().size(),
					loadedLoop.getChildrenLoops().size());
			if (loop.getParentLoop() == null) {
				assertNull(loadedLoop.getParentLoop());
			} else {
				assertEquals(loop.getParentLoop().getUuid(), loadedLoop.getParentLoop().getUuid());
			}
		}
	}

	
	@Test
	public void testSavingLoadingOfPreAndPostConditionDescription()
			throws IOException {
		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";

		List<PreAndPostConditionsDescription> propDecsr = CreationHelper
				.createSimpleCondList("reinforce", pre, post);

		propDecsr.get(0).getCbmcVariables()
				.add(new SymbolicCBMCVar("v1", CBMCVarType.VOTER));
		propDecsr.get(0).getCbmcVariables()
				.add(new SymbolicCBMCVar("v2", CBMCVarType.VOTER));
		propDecsr.get(0).getCbmcVariables()
				.add(new SymbolicCBMCVar("c", CBMCVarType.CANDIDATE));

		File f = new File("testfiles");
		f.mkdirs();
		f = new File("testfiles/reinforcement.bprp");
		SavingLoadingInterface
				.storePreAndPostConditionDescription(propDecsr.get(0), f);
		PreAndPostConditionsDescription loadedPropDescr = SavingLoadingInterface
				.loadPreAndPostConditionDescription(f);
		assertEquals(propDecsr.get(0), loadedPropDescr);
	}
}
