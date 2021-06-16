package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.CBMCJsonOutputHandler;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;

public class TestResultPresentation2 {

	@Test
	public void testOutputParser() throws IOException {
		String output = SavingLoadingInterface.readStringFromFile(
				new File("./src/test/resources/output_complicated.txt"));

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

		cbmcGeneratedCodeInfo.addElectVariableName(1, PerformVoteHelper.getResultVarName(1));
		cbmcGeneratedCodeInfo.addElectVariableName(2, PerformVoteHelper.getResultVarName(2));
		cbmcGeneratedCodeInfo.addElectVariableName(3, PerformVoteHelper.getResultVarName(3));
		
		cbmcGeneratedCodeInfo.addedElectVar("electIntersection0");
		cbmcGeneratedCodeInfo.addedElectVar("electIntersection3");
		
		CBMCJsonOutputHandler res = new CBMCJsonOutputHandler(descr, propDescr,
				cbmcGeneratedCodeInfo, 5, 5, 5, List.of(output.split("\n")));
		System.out.println(res.getExampleText());
		// System.out.println(res.getAllAssignmentsText());
	}
	
	@Test
	public void testOutputParser2() throws IOException {
		String output = SavingLoadingInterface.readStringFromFile(
				new File("./src/test/resources/output2"));

		CElectionDescription descr = new CElectionDescription(
				VotingInputTypes.APPROVAL, VotingOutputTypes.CANDIDATE_LIST,
				"test");
		PreAndPostConditionsDescription propDescr = new PreAndPostConditionsDescription(
				"test");

		CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
		cbmcGeneratedCodeInfo.addVotingVariableName(1, InitVoteHelper.getVoteVarName(1));
		cbmcGeneratedCodeInfo.addVotingVariableName(2, InitVoteHelper.getVoteVarName(2));
		cbmcGeneratedCodeInfo.addVotingVariableName(3, InitVoteHelper.getVoteVarName(3));

		cbmcGeneratedCodeInfo.addedVotingVar("voteSequence0");
		cbmcGeneratedCodeInfo.addedVotingVar("votePermutation1");

		cbmcGeneratedCodeInfo.setAmtMemberVarName("amtVotes");
		cbmcGeneratedCodeInfo.setListMemberVarName("votes");

		cbmcGeneratedCodeInfo.addElectVariableName(1, PerformVoteHelper.getResultVarName(1));
		cbmcGeneratedCodeInfo.addElectVariableName(2, PerformVoteHelper.getResultVarName(2));
		cbmcGeneratedCodeInfo.addElectVariableName(3, PerformVoteHelper.getResultVarName(3));

		cbmcGeneratedCodeInfo.addedElectVar("electIntersection0");
		cbmcGeneratedCodeInfo.addedElectVar("electIntersection3");
		
		CBMCJsonOutputHandler res = new CBMCJsonOutputHandler(descr, propDescr,
				cbmcGeneratedCodeInfo, 5, 5, 5, List.of(output.split("\n")));
		System.out.println(res.getExampleText());
		// System.out.println(res.getAllAssignmentsText());
	}
}
