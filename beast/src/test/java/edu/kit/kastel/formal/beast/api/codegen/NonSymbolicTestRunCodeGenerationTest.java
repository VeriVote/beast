package edu.kit.kastel.formal.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.kit.kastel.formal.beast.api.CreationHelper;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.init.InitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.init.TestInitVoteHelper;
import edu.kit.kastel.formal.beast.api.cparser.AntlrCLoopParser;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;
import edu.kit.kastel.formal.beast.api.test.PreferenceParameters;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class NonSymbolicTestRunCodeGenerationTest {
    private static final String NONE = "";
    private static final String BORDA = "borda";
    private static final String REINFORCE = "reinforce";
    private static final String FALSE_PROPERTY = "FALSE;";
    private static final String VOTING = "voting";
    private static final List<Integer> TEST_PARAMETERS = List.of(0, 1, 2, 3, 4);

    @Test
    public void testNumbers() {
        final String votingCode = NONE + "\n";
        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.PREFERENCE,
                                         VotingOutputType.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOpts = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, votingCode, codeGenOpts);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = NONE;
        final String post = FALSE_PROPERTY;

        final PropertyDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);

        final PreferenceParameters parameters = new PreferenceParameters(5);
        parameters.addVoter(TEST_PARAMETERS);
        parameters.addVoter(TEST_PARAMETERS);
        parameters.addVoter(TEST_PARAMETERS);
        parameters.addVoter(TEST_PARAMETERS);

        final InitVoteHelper initHelper = new TestInitVoteHelper(parameters);
        final String code =
                CBMCCodeGenerator.generateCodeForPropertyCheck(descr, propDescr, codeGenOpts,
                                                               initHelper).getCode();
        System.out.println(code);
    }
}
