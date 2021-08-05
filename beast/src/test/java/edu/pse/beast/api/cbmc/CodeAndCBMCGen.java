package edu.pse.beast.api.cbmc;

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
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.testrunner.propertycheck.processes.process_handler.CBMCArgumentHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeAndCBMCGen {
    private static final String VOTING = "voting";
    private static final String BORDA = "borda";
    private static final String REINFORCE = "reinforce";
    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String RESOURCES = "/codegen/";
    private static final String FILE_ENDING = ".template";

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
    public void generateCodeAndCBMCCall() {
        final Class<?> clazz = this.getClass();
        final String bordaCode = getTemplate(BORDA, clazz);

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = getTemplate(REINFORCE + PRE, clazz);
        final String post = getTemplate(REINFORCE + POST, clazz);
        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);

        final int v = 5;
        final int c = 5;
        final int s = 5;

        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        final CBMCGeneratedCodeInfo codeInfo =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper, this.getClass());
        System.out.println(codeInfo.getCode());
        System.out.println(
                codeInfo.getLoopBoundHandler().generateCBMCString(v, c, s));
        System.out.println(
                CBMCArgumentHelper.getConstCommands(codeGenOptions, v, c, s));
    }
}
