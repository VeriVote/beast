package edu.pse.beast.api.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class TestAddsSimpleDecl {
    private static final String EMPTY = "";
    private static final String VOTING = "voting";
    private static final String BORDA = "borda";
    private static final String REINFORCE = "reinforce";
    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String I_STRING = "i";
    private static final String J_STRING = "j";
    private static final String TEST_FUNCTION_NAME = "asd";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    @Test
    public void testSimpleFunctionDeclsAdded() {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final SimpleTypeFunction simpleFunc =
                new SimpleTypeFunction(TEST_FUNCTION_NAME, List.of(CElectionSimpleType.BOOL,
                                                      CElectionSimpleType.DOUBLE),
                                       List.of(I_STRING, J_STRING), CElectionSimpleType.FLOAT);
        descr.addSimpleFunction(simpleFunc);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = getTemplate(REINFORCE + PRE);
        final String post = getTemplate(REINFORCE + POST);

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);
        final String code =
                CBMCCodeGenerator
                .generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                 initVoteHelper, this.getClass()).getCode();
        System.out.println(code);
    }
}
