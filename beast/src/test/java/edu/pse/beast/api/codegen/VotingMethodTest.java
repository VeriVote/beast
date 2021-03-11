package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class VotingMethodTest {
	@Test
	public void testPreferenceVotingMethod() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.SINGLE_CHOICE,
				VotingOutputTypes.CANDIDATE_LIST);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("intersect", "", "");
		propDescr.get(0).getPostConditionsDescription()
				.setCode("FOR_ALL_VOTERS(v) . VOTES1(v) == VOTES2(v);");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}
}
