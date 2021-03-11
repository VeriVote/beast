package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;

public class CBMCCodeGeneratorTest {
	

	@Test
	public void reinforcementTest() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.PARLIAMENT);
		descr.getVotingFunction().getCode().add("result = 0;");

		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post1 = 
				     " (!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
		
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("reinforcementTest",
						pre, post1);
		
		propDescr.get(0).getCbmcVariables()
				.add(new SymbolicCBMCVar("c", SymbolicCBMCVar.CBMCVarType.CANDIDATE));

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");
		
		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}
	
	@Test
	public void majorityTest() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.SINGLE_CHOICE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");

		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("majorityTest",
						"VOTE_SUM_FOR_CANDIDATE1(c) > V/2;", "ELECT1 == c;");
		propDescr.get(0).getCbmcVariables()
				.add(new SymbolicCBMCVar("c", SymbolicCBMCVar.CBMCVarType.CANDIDATE));

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testVotesumAndIntegerElecCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.SINGLE_CHOICE, VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");

		final String exp = "EXISTS_ONE_CANDIDATE(c) . "
				+ "1 == V / 2 * (3 + VOTE_SUM_FOR_CANDIDATE1(c)) - C - S;";
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("Votesum", "", exp);

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testSimpleElecCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("ELECT1 != ELECT2", "",
						"ELECT1 != ELECT2;");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testIntersectionCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("intersect", "",
						"VOTES1 == CUT(VOTES4, VOTES2, VOTES3);");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testPermCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("intersect", "",
						"VOTES1 == PERM(VOTES4);");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testSplitCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("split", "",
						"[[VOTES1, VOTES2]] == VOTES3;");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}

	@Test
	public void testSimpleForAll() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
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

	@Test
	public void testUniqueVotesCodeGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper
				.createSimpleCondList("intersect", "", "");
		propDescr.get(0).getPostConditionsDescription().setCode(
				"FOR_ALL_VOTERS(v1) . !(EXISTS_ONE_CANDIDATE(c1) . EXISTS_ONE_CANDIDATE(c2) . c1 != c2 && "
						+ "VOTES1(v1)(c1) == VOTES1(v1)(c2));");

		CodeGenOptions options = new CodeGenOptions();
		options.setCbmcAmountCandidatesVarName("C");
		options.setCbmcAmountVotesVarName("V");

		LoopBoundHandler loopBoundHandler = new LoopBoundHandler();

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0),
				options, loopBoundHandler);
		System.out.println(c);
	}
}
