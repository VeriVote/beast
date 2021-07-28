package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.savingloading.SavingLoadingInterface;
import edu.pse.beast.api.testrunner.propertycheck.jsonoutput.counter_examples.CBMCJsonResultExampleExtractor;

public class TestResultPresentation2 {
    private static final String LINE_BREAK = "\n";

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    private static final String RESOURCES_PATH = "./src/test/resources/";
    private static final String TEST = "test";
    private static final int TEST_BOUND = 5;
    private static final String VOTE = "vote";
    private static final String VOTES = "votes";
    private static final String VOTE_SEQUENCE = "voteSequence";
    private static final String VOTE_PERMUTATION = "votePermutation";
    private static final String AMOUNT_VOTES = "amtVotes";
    private static final String ELECT_INTERSECTION = "electIntersection";

    @Test
    public void testOutputParser() throws IOException {
        final String output =
                SavingLoadingInterface.readStringFromFile(
                        new File(RESOURCES_PATH + "output_complicated.txt"));

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.APPROVAL,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         TEST);
        final PreAndPostConditionsDescription propDescr =
                new PreAndPostConditionsDescription(TEST);

        final CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
        cbmcGeneratedCodeInfo.addVotingVariableName(ONE, VOTE + ONE);
        cbmcGeneratedCodeInfo.addVotingVariableName(TWO, VOTE + TWO);
        cbmcGeneratedCodeInfo.addVotingVariableName(THREE, VOTE + THREE);

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_SEQUENCE + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_PERMUTATION + ONE);

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName(AMOUNT_VOTES);
        cbmcGeneratedCodeInfo.setVotesListMemberVarName(VOTES);

        cbmcGeneratedCodeInfo
        .addElectVariableName(ONE, PerformVoteHelper.getResultVarName(ONE));
        cbmcGeneratedCodeInfo
        .addElectVariableName(TWO, PerformVoteHelper.getResultVarName(TWO));
        cbmcGeneratedCodeInfo
        .addElectVariableName(THREE, PerformVoteHelper.getResultVarName(THREE));

        cbmcGeneratedCodeInfo.addedGeneratedElectVar(ELECT_INTERSECTION + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedElectVar(ELECT_INTERSECTION + THREE);

        final String[] arr = output.split(LINE_BREAK);
        final List<String> rawOutput = new ArrayList<>();
        for (int i = 0; i < arr.length; ++i) {
            rawOutput.add(arr[i]);
        }
        final CBMCJsonResultExampleExtractor res =
                new CBMCJsonResultExampleExtractor(descr, propDescr,
                                                   cbmcGeneratedCodeInfo,
                                                   TEST_BOUND, TEST_BOUND, TEST_BOUND);
        res.processCBMCJsonOutput(rawOutput);
        System.out.println(res.getGeneratedExample().toString());
    }

    @Test
    public void testOutputParser2() throws IOException {
        final String output =
                SavingLoadingInterface.readStringFromFile(new File(RESOURCES_PATH + "output2"));

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.APPROVAL,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         TEST);
        final PreAndPostConditionsDescription propDescr =
                new PreAndPostConditionsDescription(TEST);

        final CBMCGeneratedCodeInfo cbmcGeneratedCodeInfo = new CBMCGeneratedCodeInfo();
        cbmcGeneratedCodeInfo
        .addVotingVariableName(ONE, SymbVarInitVoteHelper.getVoteVarName(ONE));
        cbmcGeneratedCodeInfo
        .addVotingVariableName(TWO, SymbVarInitVoteHelper.getVoteVarName(TWO));
        cbmcGeneratedCodeInfo
        .addVotingVariableName(THREE, SymbVarInitVoteHelper.getVoteVarName(THREE));

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_SEQUENCE + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_PERMUTATION + ONE);

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName(AMOUNT_VOTES);
        cbmcGeneratedCodeInfo.setVotesListMemberVarName(VOTES);

        cbmcGeneratedCodeInfo
        .addElectVariableName(ONE, PerformVoteHelper.getResultVarName(ONE));
        cbmcGeneratedCodeInfo
        .addElectVariableName(TWO, PerformVoteHelper.getResultVarName(TWO));
        cbmcGeneratedCodeInfo
        .addElectVariableName(THREE, PerformVoteHelper.getResultVarName(THREE));

        cbmcGeneratedCodeInfo.addedGeneratedElectVar(ELECT_INTERSECTION + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedElectVar(ELECT_INTERSECTION + THREE);

        final String[] arr = output.split(LINE_BREAK);
        final List<String> rawOutput = new ArrayList<>();
        for (int i = 0; i < arr.length; ++i) {
            rawOutput.add(arr[i]);
        }
        final CBMCJsonResultExampleExtractor res =
                new CBMCJsonResultExampleExtractor(descr, propDescr,
                                                   cbmcGeneratedCodeInfo,
                                                   TEST_BOUND, TEST_BOUND, TEST_BOUND);
        res.processCBMCJsonOutput(rawOutput);
    }
}
