package edu.pse.beast.api.codegen;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class CBMCCodeGenTest {
    private static final String RESOURCES = "/codegen/";
    private static final String FILE_ENDING = ".template";

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

    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    private static String getTemplate(final String key, final Class<?> c) {
        final InputStream stream =
                c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
        final StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @Test
    public void testGenerateSimpleCode() {
        final Class<?> c = this.getClass();
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA, c);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = VOTES_VAR + TWO + EQ + ELECT_VAR + ONE + SEMI;
        final String post = ELECT_VAR + TWO + EQ + ELECT_VAR + ONE + SEMI;

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper,
                                                                  this.getClass()).getCode();
        System.out.println(code);
    }

    @Test
    public void testGenerateBordaCode() {
        final Class<?> c = this.getClass();
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA, c);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = getTemplate(REINFORCE_NAME + PRE, c);
        final String post = getTemplate(REINFORCE_NAME + POST, c);
        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator
                .generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                 initVoteHelper, this.getClass()).getCode();
        System.out.println(code);
    }

    @Test
    public void testConstants() {
        final Class<?> c = this.getClass();
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        final String bordaCode = getTemplate(BORDA, c);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = V + ONE + EQ + V + TWO + SEMI;
        final String post = C + ONE + EQ + C + TWO + SEMI;

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator
                .generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                 initVoteHelper, this.getClass()).getCode();
        System.out.println(code);
    }
}
