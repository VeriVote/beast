package edu.pse.beast.api.codegen;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable;
import edu.pse.beast.api.codegen.cbmc.SymbolicVariable.VariableType;
import edu.pse.beast.api.codegen.init.InitVoteHelper;
import edu.pse.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.pse.beast.api.cparser.AntlrCLoopParser;
import edu.pse.beast.api.cparser.ExtractedCLoop;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.method.VotingInputTypes;
import edu.pse.beast.api.method.VotingOutputTypes;
import edu.pse.beast.api.property.PreAndPostConditions;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGenerationFalseTrue {
    private static final String NONE = "";
    private static final String BORDA = "borda";
    private static final String VOTING_FUNCTION = "voting";
    private static final String REINFORCE = "reinforce";
    private static final String SYMB_VAR_1 = "c1";
    private static final String SYMB_VAR_2 = "c2";

    @Test
    public void testTrue() {
        final String votingCode = NONE;
        final CElectionDescription descr =
                new CElectionDescription(VotingInputTypes.SINGLE_CHOICE,
                                         VotingOutputTypes.CANDIDATE_LIST,
                                         BORDA);
        descr.getVotingFunction().setCode(votingCode);

        final CodeGenOptions codeGenOptions = new CodeGenOptions();
        final List<ExtractedCLoop> loops =
                AntlrCLoopParser.findLoops(VOTING_FUNCTION, votingCode, codeGenOptions);
        descr.getVotingFunction().setExtractedLoops(loops);

        final String pre = NONE;
        final String post = "FALSE;";

        final PreAndPostConditions propDescr =
                CreationHelper.createSimpleCondList(REINFORCE, pre, post).get(0);
        propDescr.addVariable(new SymbolicVariable(SYMB_VAR_1, VariableType.CANDIDATE));
        propDescr.addVariable(new SymbolicVariable(SYMB_VAR_2, VariableType.CANDIDATE));

        final InitVoteHelper initVoteHelper = new SymbVarInitVoteHelper();
        final String code =
                CBMCCodeGenerator
                .generateCodeForPropertyCheck(descr, propDescr, codeGenOptions, initVoteHelper)
                .getCode();
        System.out.println(code);
    }
}