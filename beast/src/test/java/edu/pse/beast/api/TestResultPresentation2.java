package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCJsonResultExampleExtractor;

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

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar("voteSequence0");
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar("votePermutation1");

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName("amtVotes");
        cbmcGeneratedCodeInfo.setVotesListMemberVarName("votes");

        cbmcGeneratedCodeInfo.addElectVariableName(1,
                PerformVoteHelper.getResultVarName(1));
        cbmcGeneratedCodeInfo.addElectVariableName(2,
                PerformVoteHelper.getResultVarName(2));
        cbmcGeneratedCodeInfo.addElectVariableName(3,
                PerformVoteHelper.getResultVarName(3));

        cbmcGeneratedCodeInfo.addedGeneratedElectVar("electIntersection0");
        cbmcGeneratedCodeInfo.addedGeneratedElectVar("electIntersection3");

        String arr[] = output.split("\n");
        List<String> rawOutput = new ArrayList<>();
        for (int i = 0; i < arr.length; ++i) {
            rawOutput.add(arr[i]);
        }
        CBMCJsonResultExampleExtractor res = new CBMCJsonResultExampleExtractor(
                descr, propDescr, cbmcGeneratedCodeInfo, 5, 5, 5);
        res.processCBMCJsonOutput(rawOutput);
        System.out.println(res.getGeneratedExample().toString());
    }

    @Test
    public void testOutputParser2() throws IOException {
        String output = SavingLoadingInterface
                .readStringFromFile(new File("./src/test/resources/output2"));

        CElectionDescription descr = new CElectionDescription(
                VotingInputTypes.APPROVAL, VotingOutputTypes.CANDIDATE_LIST,
                "test");
        PreAndPostConditionsDescription propDescr = new PreAndPostConditionsDescription(
                "test");

        CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
        cbmcGeneratedCodeInfo.addVotingVariableName(1,
                SymbVarInitVoteHelper.getVoteVarName(1));
        cbmcGeneratedCodeInfo.addVotingVariableName(2,
                SymbVarInitVoteHelper.getVoteVarName(2));
        cbmcGeneratedCodeInfo.addVotingVariableName(3,
                SymbVarInitVoteHelper.getVoteVarName(3));

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar("voteSequence0");
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar("votePermutation1");

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName("amtVotes");
        cbmcGeneratedCodeInfo.setVotesListMemberVarName("votes");

        cbmcGeneratedCodeInfo.addElectVariableName(1,
                PerformVoteHelper.getResultVarName(1));
        cbmcGeneratedCodeInfo.addElectVariableName(2,
                PerformVoteHelper.getResultVarName(2));
        cbmcGeneratedCodeInfo.addElectVariableName(3,
                PerformVoteHelper.getResultVarName(3));

        cbmcGeneratedCodeInfo.addedGeneratedElectVar("electIntersection0");
        cbmcGeneratedCodeInfo.addedGeneratedElectVar("electIntersection3");

        String arr[] = output.split("\n");
        List<String> rawOutput = new ArrayList<>();
        for (int i = 0; i < arr.length; ++i) {
            rawOutput.add(arr[i]);
        }
        CBMCJsonResultExampleExtractor res = new CBMCJsonResultExampleExtractor(
                descr, propDescr, cbmcGeneratedCodeInfo, 5, 5, 5);
        res.processCBMCJsonOutput(rawOutput);

    }
}
