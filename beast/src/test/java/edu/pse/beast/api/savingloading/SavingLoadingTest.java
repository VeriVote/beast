package edu.pse.beast.api.savingloading;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;
import java.nio.file.attribute.FileAttribute;
import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class SavingLoadingTest {
	
	
	@Test
	public void testSavingLoadingOfElectionDescription() 
			throws IOException {
		String bordaCode = 
				  "    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n"
				+ "\n"
				+ "    for (i = 0; i < C; i++) {\n"
				+ "        result[i] = 0;\n"
				+ "    }\n"
				+ "    for (i = 0; i < V; i++) {\n"
				+ "        for (j = 0; j < C; j++) {\n"
				+ "            if (votes[i][j] < C) {\n"
				+ "                result[votes[i][j]] += (C - j) - 1;\n"
				+ "            }\n"
				+ "        }\n"
				+ "    }";
		
		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
		
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.PREFERENCE,
				VotingOutputTypes.CANDIDATE_LIST,
				"borda");
		descr.getVotingFunction().getCode().add(bordaCode);
	
		File f = new File("testfiles");
		f.mkdirs();
		f = new File("testfiles/borda.belec");
		SavingLoadingInterface.storeCElection(descr, f);
		
		CElectionDescription loadedDescr = SavingLoadingInterface.loadCElection(f);
		
		assertEquals(descr.getInputType(), loadedDescr.getInputType());
		assertEquals(descr.getOutputType(), loadedDescr.getOutputType());
		assertEquals(descr.getVotingFunction().getName(), loadedDescr.getVotingFunction().getName());
		assertEquals(descr.getVotingFunction().codeAsString(), loadedDescr.getVotingFunction().codeAsString());
	}
	
	@Test
	public void testSavingLoadingOfPreAndPostConditionDescription() throws IOException {
		String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
		String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
		
		List<PreAndPostConditionsDescription> propDecsr =
				CreationHelper.createSimpleCondList("reinforce", pre, post);
		
		File f = new File("testfiles");
		f.mkdirs();
		f = new File("testfiles/reinforcement.bprp");
		SavingLoadingInterface.storePreAndPostConditionDescription(propDecsr.get(0), f);
		PreAndPostConditionsDescription loadedPropDescr = 
				SavingLoadingInterface.loadPreAndPostConditionDescription(f);
		assertEquals(propDecsr.get(0), loadedPropDescr);
	}
	
	
	
}
