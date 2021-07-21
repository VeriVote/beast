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

    private static final String ZERO = "0";
    private static final String ONE = "1";
    private static final String TWO = "2";
    private static final String THREE = "3";

    private static final String RESOURCES_PATH = "./src/test/resources/";
    private static final String TEST = "test";
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
        cbmcGeneratedCodeInfo.addVotingVariableName(1, VOTE + ONE);
        cbmcGeneratedCodeInfo.addVotingVariableName(2, VOTE + TWO);
        cbmcGeneratedCodeInfo.addVotingVariableName(3, VOTE + THREE);

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_SEQUENCE + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_PERMUTATION + ONE);

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName(AMOUNT_VOTES);
        cbmcGeneratedCodeInfo.setVotesListMemberVarName(VOTES);

        cbmcGeneratedCodeInfo.addElectVariableName(1, PerformVoteHelper.getResultVarName(1));
        cbmcGeneratedCodeInfo.addElectVariableName(2, PerformVoteHelper.getResultVarName(2));
        cbmcGeneratedCodeInfo.addElectVariableName(3, PerformVoteHelper.getResultVarName(3));

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
                                                   5, 5, 5);
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
        cbmcGeneratedCodeInfo.addVotingVariableName(1, SymbVarInitVoteHelper.getVoteVarName(1));
        cbmcGeneratedCodeInfo.addVotingVariableName(2, SymbVarInitVoteHelper.getVoteVarName(2));
        cbmcGeneratedCodeInfo.addVotingVariableName(3, SymbVarInitVoteHelper.getVoteVarName(3));

        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_SEQUENCE + ZERO);
        cbmcGeneratedCodeInfo.addedGeneratedVotingVar(VOTE_PERMUTATION + ONE);

        cbmcGeneratedCodeInfo.setVotesAmtMemberVarName(AMOUNT_VOTES);
        cbmcGeneratedCodeInfo.setVotesListMemberVarName(VOTES);

        cbmcGeneratedCodeInfo.addElectVariableName(1, PerformVoteHelper.getResultVarName(1));
        cbmcGeneratedCodeInfo.addElectVariableName(2, PerformVoteHelper.getResultVarName(2));
        cbmcGeneratedCodeInfo.addElectVariableName(3, PerformVoteHelper.getResultVarName(3));

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
                                                   5, 5, 5);
        res.processCBMCJsonOutput(rawOutput);
    }
}
