package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.cbmc_run_with_specific_values.PreferenceParameters;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SpecificValueInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class SpecificValuesCodeGen {
    private static final String NONE = "";
    private static final String BORDA = "borda";
    private static final String REINFORCE = "reinforce";
    private static final String FALSE_PROPERTY = "FALSE;";
    private static final String VOTING = "voting";
    private static final List<Integer> TEST_PARAMETERS = List.of(0, 1, 2, 3, 4);

    @Test
    public void testNumbers() {
        final String votingCode = NONE;
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = NONE;
        final String post = FALSE_PROPERTY;

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);

        final PreferenceParameters votingParameters = new PreferenceParameters(5);
        votingParameters.addVoter(TEST_PARAMETERS);
        votingParameters.addVoter(TEST_PARAMETERS);
        votingParameters.addVoter(TEST_PARAMETERS);
        votingParameters.addVoter(TEST_PARAMETERS);

        final InitVoteHelper initVoteHelper =
                new SpecificValueInitVoteHelper(votingParameters);
        final Class<?> c = this.getClass();
        final String code =
                CBMCCodeGenerator
                .generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                 initVoteHelper, c).getCode();
        System.out.println(code);
    }
}
