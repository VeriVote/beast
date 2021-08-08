package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCCodeGenTest2 {
    private static final String NONE = "";
    private static final String VOTING_FUNCTION = "voting";

    private static final String C1_VARIABLE = "c1";
    private static final String C2_VARIABLE = "c2";

    private static final String BORDA_STRING = "borda";
    private static final String REINFORCE_STRING = "reinforce";
    private static final String TEST_POSTCONDITION = "ELECT1 == CUT(ELECT2, ELECT3);";

    private InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();

    @Test
    public void testNumbers() {
        final String votingCode = NONE;

        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.SINGLE_CHOICE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA_STRING);
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING_FUNCTION, votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = NONE;
        final String post = TEST_POSTCONDITION;

        final PreAndPostConditionsDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_STRING, pre, post).get(0);
        propDescr.addCBMCVar(new SymbolicCBMCVar(C1_VARIABLE, CBMCVarType.CANDIDATE));
        propDescr.addCBMCVar(new SymbolicCBMCVar(C2_VARIABLE, CBMCVarType.CANDIDATE));

        final String code =
                CBMCCodeGenerator
                .generateCodeForCBMCPropertyTest(descr, propDescr, codeGenOptions,
                                                 initVoteHelper, this.getClass()).getCode();
        System.out.println(code);
    }
}
