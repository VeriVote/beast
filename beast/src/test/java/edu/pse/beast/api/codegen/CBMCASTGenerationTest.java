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
	}
}
