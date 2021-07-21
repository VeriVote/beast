package edu.pse.beast.api.codegen;

import java.util.List;

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
    private static final String LINE_BREAK = "\n";
    private static final String VOTING = "voting";
    private static final String BORDA = "borda";
    private static final String REINFORCE_NAME = "reinforce";
    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    @Test
    public void testGenerateSimpleCode() {
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
                + "    }" + "    unsigned int max = 0;" + LINE_BREAK
                + "    for (i = 0; i < C; i++) {" + LINE_BREAK
                + "        if (max < res[i]) {" + LINE_BREAK
                + "            max = res[i];" + LINE_BREAK
                + "            for (j = 0; j < C; j++) {" + LINE_BREAK
                + "                r.arr[j] = 0;" + LINE_BREAK
                + "            }" + LINE_BREAK
                + "            r.arr[i] = 1;" + LINE_BREAK
                + "        } else if (max == res[i]) {" + LINE_BREAK
                + "            r.arr[i] = 1;" + LINE_BREAK
                + "        }" + LINE_BREAK
                + "    }";

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "VOTES2 == VOTES1;";
        final String post = "ELECT2 == ELECT1;";

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testGenerateBordaCode() {
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
                + "    }" + "    unsigned int max = 0;" + LINE_BREAK
                + "    for (i = 0; i < C; i++) {" + LINE_BREAK
                + "        if (max < res[i]) {" + LINE_BREAK
                + "            max = res[i];" + LINE_BREAK
                + "            for (j = 0; j < C; j++) {" + LINE_BREAK
                + "                r.arr[j] = 0;" + LINE_BREAK
                + "            }" + LINE_BREAK
                + "            r.arr[i] = 1;" + LINE_BREAK
                + "        } else if (max == res[i]) {" + LINE_BREAK
                + "            r.arr[i] = 1;" + LINE_BREAK
                + "        }" + LINE_BREAK
                + "    }";

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
        final String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";
        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testConstants() {
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
                + "    }" + "    unsigned int max = 0;" + LINE_BREAK
                + "    for (i = 0; i < C; i++) {" + LINE_BREAK
                + "        if (max < result[i]) {" + LINE_BREAK
                + "            max = result[i];" + LINE_BREAK
                + "            for (j = 0; j < C; j++) {" + LINE_BREAK
                + "                result[j] = 0;" + LINE_BREAK
                + "            }" + LINE_BREAK
                + "            result[i] = 1;" + LINE_BREAK
                + "        } else if (max == result[i]) {" + LINE_BREAK
                + "            result[i] = 1;" + LINE_BREAK
                + "        }" + LINE_BREAK
                + "    }" + LINE_BREAK;

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "V1 == V2;";
        final String post = "C1 == C2;";

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_NAME, pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }
}
