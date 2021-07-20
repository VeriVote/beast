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
    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    @Test
    public void testGenerateSimpleCode() {
        final String bordaCode =
                "    unsigned int i = 0;\n"
                        + "    unsigned int j = 0;\n" + "\n"
                        + "    for (i = 0; i < C; i++) {\n" + "        result[i] = 0;\n"
                        + "    }\n" + "    for (i = 0; i < V; i++) {\n"
                        + "        for (j = 0; j < C; j++) {\n"
                        + "            result[votes[i][j]] += (C - j) - 1;\n"
                        + "        }\n" + "    }" + "    unsigned int max = 0;\n"
                        + "    for (i = 0; i < C; i++) {\n"
                        + "        if (max < res[i]) {\n"
                        + "            max = res[i];\n"
                        + "            for (j = 0; j < C; j++) {\n"
                        + "                r.arr[j] = 0;\n" + "            }\n"
                        + "            r.arr[i] = 1;\n"
                        + "        } else if (max == res[i]) {\n"
                        + "            r.arr[i] = 1;\n" + "        }\n" + "    }";

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "borda");
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops("voting", bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "VOTES2 == VOTES1;";
        final String post = "ELECT2 == ELECT1;";

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList("reinforce", pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testGenerateBordaCode() {
        final String bordaCode =
                "    unsigned int i = 0;\n"
                        + "    unsigned int j = 0;\n" + "\n"
                        + "    for (i = 0; i < C; i++) {\n" + "        result[i] = 0;\n"
                        + "    }\n" + "    for (i = 0; i < V; i++) {\n"
                        + "        for (j = 0; j < C; j++) {\n"
                        + "            result[votes[i][j]] += (C - j) - 1;\n"
                        + "        }\n" + "    }" + "    unsigned int max = 0;\n"
                        + "    for (i = 0; i < C; i++) {\n"
                        + "        if (max < res[i]) {\n"
                        + "            max = res[i];\n"
                        + "            for (j = 0; j < C; j++) {\n"
                        + "                r.arr[j] = 0;\n" + "            }\n"
                        + "            r.arr[i] = 1;\n"
                        + "        } else if (max == res[i]) {\n"
                        + "            r.arr[i] = 1;\n" + "        }\n" + "    }";

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
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testConstants() {
        final String bordaCode =
                "    unsigned int i = 0;\n"
                        + "    unsigned int j = 0;\n" + "\n"
                        + "    for (i = 0; i < C; i++) {\n" + "        result[i] = 0;\n"
                        + "    }\n" + "    for (i = 0; i < V; i++) {\n"
                        + "        for (j = 0; j < C; j++) {\n"
                        + "            result[votes[i][j]] += (C - j) - 1;\n"
                        + "        }\n" + "    }" + "    unsigned int max = 0;\n"
                        + "    for (i = 0; i < C; i++) {\n"
                        + "        if (max < result[i]) {\n"
                        + "            max = result[i];\n"
                        + "            for (j = 0; j < C; j++) {\n"
                        + "                result[j] = 0;\n" + "            }\n"
                        + "            result[i] = 1;\n"
                        + "        } else if (max == result[i]) {\n"
                        + "            result[i] = 1;\n" + "        }\n" + "    }\n";

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.PREFERENCE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         "borda");
        descr.getVotingFunction().setCode(bordaCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops("voting", bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = "V1 == V2;";
        final String post = "C1 == C2;";

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList("reinforce", pre, post).get(0);
        final String code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                                  initVoteHelper).getCode();
        System.out.println(code);
    }
}
