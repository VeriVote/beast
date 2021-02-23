package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;

public class CBMCCodeGeneratorTest {
	@Test
	public void testSimpleElecCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("ELECT1 != ELECT2", "",
				"ELECT1 != ELECT2;");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0), options);
		System.out.println(c);
	}

	@Test
	public void testIntersectionCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("intersect", "",
				"VOTES1 == CUT(VOTES4, VOTES2, VOTES3);");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0), options);
		System.out.println(c);
	}
	
	@Test
	public void testPermCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("intersect", "",
				"VOTES1 == PERM(VOTES4);");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0), options);
		System.out.println(c);
	}
	
	@Test
	public void testSplitCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("split", "",
				"[[VOTES1, VOTES2]] == VOTES3;");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0), options);
		System.out.println(c);
	}
	
	@Test
	public void testUniqueVotesCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("intersect", "",
				"");
		propDescr.get(0).getPostConditionsDescription().setCode("FOR_ALL_VOTERS(v1) : !(EXISTS_ONE_VOTER(v2) : v2 != v1 && VOTES1(v1) == VOTES1(v2));");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0), options);
		System.out.println(c);
	}
}
