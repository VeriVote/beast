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

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0));
	}
	
	@Test
	public void testIntersectionCodeGen() {
		CElectionDescription descr = new CElectionDescription(VotingInputTypes.APPROVAL,
				VotingOutputTypes.SINGLE_CANDIDATE);
		descr.getVotingFunction().getCode().add("result = 0;");
		List<PreAndPostConditionsDescription> propDescr = CreationHelper.createSimpleCondList("intersect", "",
				"VOTES1 == CUT(VOTES4, VOTES2, VOTES3);");

		String c = CBMCCodeGeneratorNEW.generateCode(descr, propDescr.get(0));
		System.out.println(c);
	}
}
