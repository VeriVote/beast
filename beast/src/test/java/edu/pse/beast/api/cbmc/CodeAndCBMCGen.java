package edu.pse.beast.api.cbmc;

import java.util.List;

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

public class CodeAndCBMCGen {
    private static final String LINE_BREAK = "\n";

    @Test
    public void generateCodeAndCBMCCall() {
        final String bordaCode =
                "    unsigned int i = 0;" + LINE_BREAK
                        + "    unsigned int j = 0;" + LINE_BREAK + LINE_BREAK
                        + "    for (i = 0; i < C; i++) {" + LINE_BREAK
                        + "        result[i] = 0;" + LINE_BREAK
                        + "    }" + LINE_BREAK
                        + "    for (i = 0; i < V; i++) {" + LINE_BREAK
                        + "        for (j = 0; j < C; j++) {" + LINE_BREAK
                        + "            result[votes[i][j]] += (C - j) - 1;" + LINE_BREAK
                        + "        }" + LINE_BREAK
                        + "    }";

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "borda");
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops("voting", bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
        final String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList("reinforce", pre, post).get(0);

        final int v = 5;
        final int c = 5;
        final int s = 5;

        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        final CBMCGeneratedCodeInfo codeInfo =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr,
                                                                  codeGenOptions,
                                                                  initVoteHelper);
        System.out.println(codeInfo.getCode());
        System.out.println(
                codeInfo.getLoopBoundHandler().generateCBMCString(v, c, s));
        System.out.println(
                CBMCArgumentHelper.getConstCommands(codeGenOptions, v, c, s));
    }
}
