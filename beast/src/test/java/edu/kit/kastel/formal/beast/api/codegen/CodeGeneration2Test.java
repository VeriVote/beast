package edu.kit.kastel.formal.beast.api.codegen;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.kit.kastel.formal.beast.api.CreationHelper;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.SymbolicVariable.VariableType;
import edu.kit.kastel.formal.beast.api.codegen.init.InitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.kit.kastel.formal.beast.api.cparser.AntlrCLoopParser;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;
import edu.kit.kastel.formal.beast.api.method.CElectionDescription;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;
import edu.kit.kastel.formal.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGeneration2Test {
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
        final CElectionDescription descr =
                new CElectionDescription(VotingInputType.SINGLE_CHOICE,
                                         VotingOutputType.CANDIDATE_LIST,
                                         BORDA_STRING);

        final String votingCode = NONE;
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING_FUNCTION, votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = NONE;
        final String post = TEST_POSTCONDITION;

        final PropertyDescription propDescr =
                CreationHelper.createSimpleCondList(REINFORCE_STRING, pre, post).get(0);
        propDescr.addVariable(new SymbolicVariable(C1_VARIABLE, VariableType.CANDIDATE));
        propDescr.addVariable(new SymbolicVariable(C2_VARIABLE, VariableType.CANDIDATE));

        final String code =
                CBMCCodeGenerator
                .generateCodeForPropertyCheck(descr, propDescr, codeGenOptions, initVoteHelper)
                .getCode();
        System.out.println(code);
    }
}
