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
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;
import edu.pse.beast.api.propertydescription.PreAndPostConditionsDescription;

public class CBMCCodeGenTest {
    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    @Test
    public void testGenerateSimpleCode() {
        String bordaCode = "    unsigned int i = 0;\n"
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

        CElectionDescription descr = new CElectionDescription(
                VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
                "borda");
        descr.getVotingFunction().setCode(bordaCode);

        CodeGenOptions codeGenOptions = new CodeGenOptions();

        List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting",
                bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        String pre = "VOTES2 == VOTES1;";
        String post = "ELECT2 == ELECT1;";

        PreAndPostConditionsDescription propDescr = CreationHelper
                .createSimpleCondList("reinforce", pre, post).get(0);

        String code = CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr,
                propDescr, codeGenOptions, initVoteHelper).getCode();
        System.out.println(code);
    }

    @Test
    public void testGenerateBordaCode() {
        String bordaCode = "    unsigned int i = 0;\n"
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

        CElectionDescription descr = new CElectionDescription(
                VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
                "borda");
        descr.getVotingFunction().setCode(bordaCode);

        CodeGenOptions codeGenOptions = new CodeGenOptions();

        List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting",
                bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        String pre = "[[VOTES2, VOTES3]] == PERM(VOTES1);";
        String post = "(!EMPTY(CUT(ELECT2, ELECT3))) ==> (ELECT1 == CUT(ELECT2, ELECT3));";

        PreAndPostConditionsDescription propDescr = CreationHelper
                .createSimpleCondList("reinforce", pre, post).get(0);

        String code = CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr,
                propDescr, codeGenOptions, initVoteHelper).getCode();
        // System.out.println(code);
    }

    @Test
    public void testConstants() {
        String bordaCode = "    unsigned int i = 0;\n"
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

        CElectionDescription descr = new CElectionDescription(
                VotingInputTypes.PREFERENCE, VotingOutputTypes.CANDIDATE_LIST,
                "borda");
        descr.getVotingFunction().setCode(bordaCode);

        CodeGenOptions codeGenOptions = new CodeGenOptions();

        List<ExtractedCLoop> loops = AntlrCLoopParser.findLoops("voting",
                bordaCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        String pre = "V1 == V2;";
        String post = "C1 == C2;";

        PreAndPostConditionsDescription propDescr = CreationHelper
                .createSimpleCondList("reinforce", pre, post).get(0);

        String code = CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr,
                propDescr, codeGenOptions, initVoteHelper).getCode();
        // System.out.println(code);
    }
}
