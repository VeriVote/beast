package edu.pse.beast.api.codegen;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
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
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
				
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("ELECT1 != ELECT2", "", "ELECT1 != ELECT2;").get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(descr, propDescr.getPostConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());

		assertEquals(ast.getHighestElect(), 2);
		assertEquals(ast.getHighestVote(), 0);
		
		BooleanExpListNode topNode = ast.getTopAstNode();
		
		System.out.println(topNode.getTreeString());
	}
	
	@Test
	public void testFORALLASTGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp =
                "FOR_ALL_VOTERS(v) : "
              + "EXISTS_ONE_CANDIDATE(c) : "
              + "VOTES1(v) == c && VOTES1(v) == c;";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("FOR_ALL_VOTERS", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(descr, propDescr.getPostConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());

		assertEquals(ast.getHighestElect(), 0);
		assertEquals(ast.getHighestVote(), 1);
		
		BooleanExpListNode topNode = ast.getTopAstNode();
		
		System.out.println(topNode.getTreeString());
	}
	
	@Test
	public void testVOTESUMASTGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp =
                "EXISTS_ONE_CANDIDATE(c) : "
              + "1 == V / 2 * (3 + VOTE_SUM_FOR_CANDIDATE1(c)) - C - S;";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("VOTE_SUM_FOR_CANDIDATE", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(descr, propDescr.getPostConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());

		assertEquals(ast.getHighestElect(), 0);
		assertEquals(ast.getHighestVote(), 1);
		
		BooleanExpListNode topNode = ast.getTopAstNode();
		
		System.out.println(topNode.getTreeString());
	}
	
	@Test
	public void testPERMASTGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		final String exp = "VOTES2 == PERM(VOTES1)";
		PreAndPostConditionsDescription propDescr = CreationHelper
				.createSimpleCondList("PERM", "", exp).get(0);

		BooleanExpASTData ast = BooleanCodeToAST.generateAST(descr, propDescr.getPostConditionsDescription().getCode(),
				propDescr.getSymVarsAsScope());

		assertEquals(ast.getHighestElect(), 0);
		assertEquals(ast.getHighestVote(), 1);
		
		BooleanExpListNode topNode = ast.getTopAstNode();
		
		System.out.println(topNode.getTreeString());
	}
}
