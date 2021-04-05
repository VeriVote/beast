package edu.pse.beast.api.codegen.ast;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.InputAndOutputElectionStructs;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class CBMCASTGenerationTest {
	@Test
	public void testSimpleElecASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");

		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("ELECT1 != ELECT2", "",
						"ELECT1 != ELECT2;")
				.get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		BooleanExpListNode topNode = ast.getTopAstNode();
		System.out.println(topNode.getTreeString());

		assertEquals(ast.getHighestElect(), 2);
		assertEquals(ast.getHighestVote(), 0);

	}

	@Test
	public void testFORALLASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "FOR_ALL_VOTERS(v) . " + "EXISTS_ONE_CANDIDATE(c) . "
				+ "VOTES1(v) == c && VOTES1(v) == c;";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("FOR_ALL_VOTERS", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		assertEquals(ast.getHighestElect(), 0);
		assertEquals(ast.getHighestVote(), 1);

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}

	@Test
	public void testVOTESUMASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "EXISTS_ONE_CANDIDATE(c) . "
				+ "1 == V / 2 * (3 + VOTE_SUM_FOR_CANDIDATE1(c)) - C - S;";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("VOTE_SUM_FOR_CANDIDATE", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		assertEquals(ast.getHighestElect(), 0);
		assertEquals(ast.getHighestVote(), 1);

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}

	@Test
	public void testTupleASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "[[VOTES1, VOTES2]] == VOTES3;";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("tuple", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}

	@Test
	public void testPermutationASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "VOTES1 == PERM(VOTES3);";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("perm", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}

	@Test
	public void testIntersectionASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "VOTES1 == CUT(VOTES1, VOTES2, VOTES3);";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("intersect", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}

	@Test
	public void testEmptyListASTGen() {
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.SINGLE_CANDIDATE,
				"test");
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "EMPTY(CUT(VOTES1, VOTES2, VOTES3));";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("emptyList", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(
				propDescr.getPostConditionsDescription().getCode(),
				propDescr.getCbmcVariables());

		BooleanExpListNode topNode = ast.getTopAstNode();

		System.out.println(topNode.getTreeString());
	}
}
