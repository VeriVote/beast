package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

/**
 * Uses the {@link CodeGenASTVisitor} to generate the main function:
 * Initializes voting structs and symbolic variables,
 * generates preconditions, calls the voting function,
 * generates postconditions
 * @author holge
 *
 */
public class CBMCMainGenerator {

    /**
     * @param var the bound variable
     * @param options the code generation options for statement name
     * @param bound the upper bound
     * @return assumption about upper variable bound
     */
    private static String replace(final SymbolicCBMCVar var,
                                  final CodeGenOptions options,
                                  final String bound) {
        return "ASSUME(VAR_NAME < BOUND);"
                .replaceAll("ASSUME", options.getCbmcAssumeName())
                .replaceAll("VAR_NAME", var.getName())
                .replaceAll("BOUND", bound);
    }

    // TODO make this dependent on the arrays accessed by the symb var
    private static void initSymbVar(final SymbolicCBMCVar var,
                                    final List<String> code,
                                    final CodeGenOptions options,
                                    final int highestVote) {
        code.add("unsigned int " + var.getName() + " = "
                + options.getCbmcNondetUintName() + "();\n");

        for (int i = 0; i < highestVote; ++i) {
            switch (var.getVarType()) {
            case VOTER:
                code.add(replace(var, options, SymbVarInitVoteHelper.getCurrentAmtVoter(i + 1)));
                break;
            case CANDIDATE:
                code.add(replace(var, options, SymbVarInitVoteHelper.getCurrentAmtCand(i + 1)));
                break;
            case SEAT:
                code.add(replace(var, options, SymbVarInitVoteHelper.getCurrentAmtSeat(i + 1)));
                break;
            default:
            }
        }
    }

    public static CFunction main(final BooleanExpASTData preAstData,
                                 final BooleanExpASTData postAstData,
                                 final List<SymbolicCBMCVar> symCbmcVars,
                                 final ElectionTypeCStruct voteArrStruct,
                                 final ElectionTypeCStruct voteResultStruct,
                                 final VotingInputTypes votingInputType,
                                 final VotingOutputTypes votingOutputType,
                                 final CodeGenOptions options,
                                 final CodeGenLoopBoundHandler loopBoundHandler,
                                 final String votingFunctionName,
                                 final CBMCGeneratedCodeInfo cbmcGeneratedCode,
                                 final InitVoteHelper initVoteHelper) {
        final List<String> code = new ArrayList<>();

        // init votes
        int highestVote =
                Math.max(preAstData.getHighestVoteOrElect(),
                         postAstData.getHighestVoteOrElect());
        highestVote = Math.max(highestVote, initVoteHelper.getHighestVote());

        for (int i = 0; i < highestVote; ++i) {
            code.add(initVoteHelper.generateCode(i + 1, voteArrStruct,
                                                 votingInputType, options,
                                                 loopBoundHandler,
                                                 cbmcGeneratedCode));
        }

        // init global symbolic vars
        for (final SymbolicCBMCVar var : symCbmcVars) {
            initSymbVar(var, code, options, highestVote);
        }
        final CodeGenASTVisitor visitor =
                new CodeGenASTVisitor(voteArrStruct, votingInputType,
                                      voteResultStruct, votingOutputType, options,
                                      loopBoundHandler, cbmcGeneratedCode);

        // preconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSUME);
        for (final BooleanExpressionNode node
                : preAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }

        // vote
        for (int i = 0; i < highestVote; ++i) {
            code.add(PerformVoteHelper.generateCode(i + 1, voteArrStruct,
                                                    voteResultStruct, options,
                                                    votingFunctionName,
                                                    cbmcGeneratedCode));
        }

        // postconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSERT);
        for (final BooleanExpressionNode node
                : postAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }
        code.add("return 0;");

        final CFunction mainFunction =
                new CFunction("main",
                              List.of("int argc", "char ** argv"),
                              "int");
        mainFunction.setCode(code);
        return mainFunction;
    }
}
