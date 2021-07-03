package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGeneratorNEW;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;

public class CBMCCodeGenTest2 {
	@Test
	public void testNumbers() {
		String votingCode = "";

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().setCode(votingCode);

		CodeGenOptions codeGenOptions = new CodeGenOptions();

		List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting", votingCode,
				codeGenOptions);
		descr.getVotingFunction().setExtractedLoops(loops);

		String pre = "v == VOTER_AT_POS(0);";
		String post = "";

		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("reinforce", pre, post).get(0);
		propDescr.addCBMCVar(new SymbolicCBMCVar("v", CBMCVarType.VOTER));

		String code = CBMCCodeGeneratorNEW.generateCodeForCBMCPropertyTest(
				descr, propDescr, codeGenOptions).getCode();
		
		System.out.println(code);
	}

}
