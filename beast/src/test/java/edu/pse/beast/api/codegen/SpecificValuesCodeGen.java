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
    @Test
    public void testNumbers() {
        final String votingCode = "";
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "borda");
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops("voting", votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "";
        final String post = "FALSE;";

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList("reinforce", pre, post).get(0);

        final PreferenceParameters votingParameters = new PreferenceParameters(5);
        votingParameters.addVoter(List.of(0, 1, 2, 3, 4));
        votingParameters.addVoter(List.of(0, 1, 2, 3, 4));
        votingParameters.addVoter(List.of(0, 1, 2, 3, 4));
        votingParameters.addVoter(List.of(0, 1, 2, 3, 4));

        final InitVoteHelper initVoteHelper =
                new SpecificValueInitVoteHelper(votingParameters);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }
}
