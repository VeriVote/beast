package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.CBMCCodeFileData;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonOutputHandler;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class TestResultPresentation2 {
	
		@Test
	public void testOutputParser() throws IOException {
		String output = SavingLoadingInterface.readStringFromFile(new File(
					"./src/test/resources/output_complicated.txt"
				));	
		
		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.CANDIDATE_LIST,
				"test");
		PreAndPostConditionsDescription propDescr = new PreAndPostConditionsDescription(
				"test");
		
		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCodeInfo.addVotingVariableName(1, "vote1");
		cbmcGeneratedCodeInfo.addVotingVariableName(2, "vote2");
		cbmcGeneratedCodeInfo.addVotingVariableName(3, "vote3");
		

		cbmcGeneratedCodeInfo.addedVotingVar("voteSequence0");
		cbmcGeneratedCodeInfo.addedVotingVar("votePermutation1");
		
		cbmcGeneratedCodeInfo.setAmtMemberVarName("amtVotes");
		cbmcGeneratedCodeInfo.setListMemberVarName("votes");
		
		cbmcGeneratedCodeInfo.addElectVariable(1, "elect1");
		cbmcGeneratedCodeInfo.addElectVariable(2, "elect2");
		cbmcGeneratedCodeInfo.addElectVariable(3, "elect3");

		CBMCJsonOutputHandler res = new CBMCJsonOutputHandler(
				descr, 
				propDescr, 
				cbmcGeneratedCodeInfo,
				5, 5, 5, 
				List.of(output.split("\n")));
		System.out.println(res.getExampleText());
		System.out.println(res.getAllAssignmentsText());		
	}
}
