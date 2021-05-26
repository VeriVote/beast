package edu.pse.beast.api.codegen;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGeneratorNEW;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCCodeGenTest {
	@Test
	public void testGenerateBordaCode() {
		String bordaCode = "    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n" + "\n"
				+ "    for (i = 0; i < C; i++) {\n" + "        result[i] = 0;\n"
				+ "    }\n" + "    for (i = 0; i < V; i++) {\n"
				+ "        for (j = 0; j < C; j++) {\n"
				+ "            if (votes[i][j] < C) {\n"
				+ "                result[votes[i][j]] += (C - j) - 1;\n"
				+ "            }\n" + "        }\n" + "    }";

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().setCode(bordaCode);

		CodeGenOptions codeGenOptions = new CodeGenOptions();
		codeGenOptions.setCbmcAmountVotersVarName("V");
		codeGenOptions.setCbmcAmountCandidatesVarName("C");
		codeGenOptions.setCbmcAmountSeatsVarName("S");

		List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting",
				bordaCode, codeGenOptions);
		descr.getVotingFunction().setExtractedLoops(loops);

		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";

		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("reinforce", pre, post).get(0);

		ElectionCheckParameter params = CreationHelper.createParamsOnlyOneRun(5,
				10, 10, 10);

		CodeGenLoopBoundHandler lbh = descr.generateLoopBoundHandler();
		String code = CBMCCodeGeneratorNEW.generateCodeForCBMCPropertyTest(descr, propDescr,
				codeGenOptions, lbh).getCode();
		System.out.println(code);
	}
}
