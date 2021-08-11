package edu.pse.beast.api.codegen;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.init.InitVoteHelper;
import edu.pse.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.pse.beast.api.cparser.AntlrCLoopParser;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.VotingInputTypes;
import edu.pse.beast.api.method.VotingOutputTypes;
import edu.pse.beast.api.property.PreAndPostConditions;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGeneration {
    private static final String EMPTY = "";

    private static final String EQ = " == ";
    private static final String SEMI = ";";
    private static final String ONE = "1";
    private static final String TWO = "2";

    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String VOTING = "voting";
    private static final String BORDA = "borda";
    private static final String REINFORCE_NAME = "reinforce";
    private static final String VOTES_VAR = "VOTES";
    private static final String ELECT_VAR = "ELECT";
    private static final String V = "V";
    private static final String C = "C";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    @Test
    public void testGenerateSimpleCode() {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = VOTES_VAR + TWO + EQ + ELECT_VAR + ONE + SEMI;
        final String post = ELECT_VAR + TWO + EQ + ELECT_VAR + ONE + SEMI;

        final PreAndPostConditions propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForPropertyCheck(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testGenerateBordaCode() {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = getTemplate(REINFORCE_NAME + PRE);
        final String post = getTemplate(REINFORCE_NAME + POST);
        final PreAndPostConditions propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator
                .generateCodeForPropertyCheck(descr, propDescr, codeGenOptions, initVoteHelper)
                .getCode();
        System.out.println(code);
    }

    @Test
    public void testConstants() {
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = V + ONE + EQ + V + TWO + SEMI;
        final String post = C + ONE + EQ + C + TWO + SEMI;

        final PreAndPostConditions propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator
                .generateCodeForPropertyCheck(descr, propDescr, codeGenOptions, initVoteHelper)
                .getCode();
        System.out.println(code);
    }
}
