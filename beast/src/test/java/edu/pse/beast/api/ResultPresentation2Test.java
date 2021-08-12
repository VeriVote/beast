package edu.pse.beast.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.PerformVoteHelper;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.pse.beast.api.io.InputOutputInterface;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.VotingInputType;
import edu.pse.beast.api.method.VotingOutputType;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.trace.JSONResultExtractor;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class ResultPresentation2Test {
    private static final String LINE_BREAK = "\n";
    private static final String DOT = ".";

    private static final int ZERO = 0;
    private static final int ONE = 1;
    private static final int TWO = 2;
    private static final int THREE = 3;

    private static final String RESOURCES_PATH = "./src/test/resources";
    private static final String TEST_FILE_1 = "output_complicated.txt";
    private static final String TEST_FILE_2 = "output2";
    private static final String TEST = "test";
    private static final int TEST_BOUND = 5;

    private static final String VOTE = "vote";
    private static final String VOTES = "votes";
    private static final String VOTE_SEQUENCE = "voteSequence";
    private static final String VOTE_PERMUTATION = "votePermutation";
    private static final String AMOUNT_VOTES = "amtVotes";
    private static final String ELECT_INTERSECTION = "electIntersection";

    private static File getResources(final String fileName) {
        final ResultPresentation2Test resultPresentation = new ResultPresentation2Test();
        final Class<? extends ResultPresentation2Test> c = resultPresentation.getClass();
        final String dir = File.separator + c.getCanonicalName().replace(DOT, File.separator);
        final String resource = dir.substring(0, dir.lastIndexOf(c.getSimpleName()));
        return new File(RESOURCES_PATH + resource + fileName);
    }

    private static List<String> getLines(final String output) {
        final String[] arr = output.split(LINE_BREAK);
        final List<String> rawOutput = new ArrayList<String>();
        for (int i = 0; i < arr.length; ++i) {
            rawOutput.add(arr[i]);
        }
        return rawOutput;
    }

    @Test
    public void testOutputParser() throws IOException {
        final String output =
                InputOutputInterface.readStringFromFile(getResources(TEST_FILE_1));

        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.APPROVAL,
                                         VotingOutputType.CANDIDATE_LIST,
                                         TEST);
        final PropertyDescription propDescr = new PropertyDescription(TEST);

        final GeneratedCodeInfo cbmcGeneratedCodeInfo = new GeneratedCodeInfo();
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

        final List<String> rawOutput = getLines(output);
        final JSONResultExtractor res =
                new JSONResultExtractor(descr, propDescr, cbmcGeneratedCodeInfo,
                                        TEST_BOUND, TEST_BOUND, TEST_BOUND);
        res.processJSONOutput(rawOutput);
        System.out.println(res.getGeneratedExample().toString());
    }

    @Test
    public void testOutputParser2() throws IOException {
        final String output =
                InputOutputInterface.readStringFromFile(getResources(TEST_FILE_2));

        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.APPROVAL,
                                         VotingOutputType.CANDIDATE_LIST,
                                         TEST);
        final PropertyDescription propDescr = new PropertyDescription(TEST);

        final GeneratedCodeInfo cbmcGeneratedCodeInfo = new GeneratedCodeInfo();
        final CodeGenOptions opts = new CodeGenOptions();
        cbmcGeneratedCodeInfo
        .addVotingVariableName(ONE, SymbVarInitVoteHelper.getVoteVarName(opts, ONE));
        cbmcGeneratedCodeInfo
        .addVotingVariableName(TWO, SymbVarInitVoteHelper.getVoteVarName(opts, TWO));
        cbmcGeneratedCodeInfo
        .addVotingVariableName(THREE, SymbVarInitVoteHelper.getVoteVarName(opts, THREE));

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

        final List<String> rawOutput = getLines(output);
        final JSONResultExtractor res =
                new JSONResultExtractor(descr, propDescr, cbmcGeneratedCodeInfo,
                                        TEST_BOUND, TEST_BOUND, TEST_BOUND);
        res.processJSONOutput(rawOutput);
    }
}
