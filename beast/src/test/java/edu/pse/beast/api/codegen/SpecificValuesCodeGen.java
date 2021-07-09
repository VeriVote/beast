package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SpecificValueInitVoteHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.specificcbmcrun.PreferenceParameters;

public class SpecificValuesCodeGen {
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

		String pre = "";
		String post = "FALSE;";

		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("reinforce", pre, post).get(0);
		
		PreferenceParameters votingParameters = new PreferenceParameters(5);
		votingParameters.addVoter(List.of(0,1,2,3,4));
		votingParameters.addVoter(List.of(0,1,2,3,4));
		votingParameters.addVoter(List.of(0,1,2,3,4));
		votingParameters.addVoter(List.of(0,1,2,3,4));
		
		InitVoteHelper initVoteHelper = new SpecificValueInitVoteHelper(votingParameters);

		String code = CBMCCodeGenerator.generateCodeForCBMCPropertyTest(
				descr, propDescr, codeGenOptions, initVoteHelper).getCode();
		
		System.out.println(code);
	}
}
