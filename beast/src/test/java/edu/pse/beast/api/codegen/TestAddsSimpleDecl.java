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
import edu.pse.beast.api.descr.c_electiondescription.CElectionSimpleTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.c_electiondescription.function.SimpleTypeFunction;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

public class TestAddsSimpleDecl {
    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    @Test
    public void testSimpleFunctionDeclsAdded() {
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

        final SimpleTypeFunction simpleFunc =
                new SimpleTypeFunction("asd", List.of(CElectionSimpleTypes.BOOL,
                                                      CElectionSimpleTypes.DOUBLE),
                                       List.of("i", "j"), CElectionSimpleTypes.FLOAT);
        descr.addSimpleFunction(simpleFunc);

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
}
