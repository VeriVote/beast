package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CBMCCodeGeneratorNEW;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.specificcbmcrun.PreferenceParameters;
import edu.pse.beast.api.specificcbmcrun.SpecificCBMCRunParametersInfo;

public class SpecificRunParamTest {

	@Test
	public void testGenerateCodeForSpecificParameters() {
		SpecificCBMCRunParametersInfo info = new SpecificCBMCRunParametersInfo();
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
		info.setDescr(descr);

		PreferenceParameters parameters = new PreferenceParameters(10);
		parameters.addVoter(List.of(1, 0, 1, 1, 1, 0, 0, 0, 1, 1));
		info.setVotingParameters(parameters);
		
		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountSeatsVarName("S");
		options.setCbmcAmountVotersVarName("V");
		
		CBMCCodeGeneratorNEW.generateCodeForCBMCRunWithParameters(info, options);		
	}
}
