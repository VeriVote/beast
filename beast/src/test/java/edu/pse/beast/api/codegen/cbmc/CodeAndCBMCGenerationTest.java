package edu.pse.beast.api.codegen.cbmc;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.codegen.init.InitVoteHelper;
import edu.pse.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.pse.beast.api.cparser.AntlrCLoopParser;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.VotingInputType;
import edu.pse.beast.api.method.VotingOutputType;
import edu.pse.beast.api.property.PropertyDescription;
import edu.pse.beast.api.runner.propertycheck.process.cbmc.CBMCArgumentHelper;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeAndCBMCGenerationTest {
    private static final String VOTING = "voting";
    private static final String BORDA = "borda";
    private static final String REINFORCE = "reinforce";
    private static final String PRE = "_pre";
    private static final String POST = "_post";
    private static final String EMPTY = "";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    @Test
    public void generateCodeAndCBMCCall() {
        final String bordaCode = getTemplate(BORDA);

        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.PREFERENCE,
                                         VotingOutputType.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = getTemplate(REINFORCE + PRE);
        final String post = getTemplate(REINFORCE + POST);
        final PropertyDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);

        final int v = 5;
        final int c = 5;
        final int s = 5;

        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        final GeneratedCodeInfo codeInfo =
                CBMCCodeGenerator.generateCodeForPropertyCheck(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper);
        System.out.println(codeInfo.getCode());
        System.out.println(
                codeInfo.getLoopBoundHandler().generateCBMCString(v, c, s));
        System.out.println(
                CBMCArgumentHelper.getConstCommands(codeGenOptions, v, c, s));
    }
}
