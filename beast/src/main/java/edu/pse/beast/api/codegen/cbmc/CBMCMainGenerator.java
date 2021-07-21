package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;

/**
 * Uses the {@link CodeGenASTVisitor} to generate the main function:
 * Initializes voting structs and symbolic variables,
 * generates preconditions, calls the voting function,
 * generates postconditions
 *
 * @author Holger Klein
 *
 */
public class CBMCMainGenerator {
    private static final String MAIN = "main";
    private static final String ASSUME = "ASSUME";
    private static final String VAR_NAME = "VAR_NAME";
    private static final String BOUND = "BOUND";

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
                .replaceAll(ASSUME, options.getCbmcAssumeName())
                .replaceAll(VAR_NAME, var.getName())
                .replaceAll(BOUND, bound);
    }

    // TODO make this dependent on the arrays accessed by the symb var
    private static void initSymbVar(final SymbolicCBMCVar var,
                                    final List<String> code,
                                    final CodeGenOptions options,
                                    final int highestVote) {
        code.add("unsigned int " + var.getName() + " = "
                + options.getCbmcNondetUintName() + "();" + "\n");

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

    public static CFunction main(final CFunction.PropertyExpressions expressions,
                                 final CFunction.VotingFunction votingFunction,
                                 final CodeGenOptions options,
                                 final CodeGenLoopBoundHandler loopBoundHandler,
                                 final CBMCGeneratedCodeInfo cbmcGeneratedCode,
                                 final InitVoteHelper initVoteHelper) {
        final List<String> code = new ArrayList<>();

        // init votes
        int highestVote =
                Math.max(expressions.preAstData.getHighestVoteOrElect(),
                         expressions.postAstData.getHighestVoteOrElect());
        highestVote = Math.max(highestVote, initVoteHelper.getHighestVote());

        for (int i = 0; i < highestVote; ++i) {
            code.add(initVoteHelper.generateCode(i + 1, votingFunction.votes,
                                                 votingFunction.profileType, options,
                                                 loopBoundHandler,
                                                 cbmcGeneratedCode));
        }

        // init global symbolic vars
        for (final SymbolicCBMCVar var : expressions.symCbmcVars) {
            initSymbVar(var, code, options, highestVote);
        }
        final CodeGenASTVisitor visitor =
                new CodeGenASTVisitor(votingFunction.votes, votingFunction.profileType,
                                      votingFunction.result, votingFunction.resultType, options,
                                      loopBoundHandler, cbmcGeneratedCode);

        // preconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSUME);
        for (final BooleanExpressionNode node
                : expressions.preAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }

        // vote
        for (int i = 0; i < highestVote; ++i) {
            code.add(PerformVoteHelper.generateCode(i + 1, votingFunction.votes,
                                                    votingFunction.result, options,
                                                    votingFunction.function,
                                                    cbmcGeneratedCode));
        }

        // postconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSERT);
        for (final BooleanExpressionNode node
                : expressions.postAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }
        code.add("return 0;");

        final CFunction mainFunction = new CFunction(MAIN, List.of(), "int");
        mainFunction.setCode(code);
        return mainFunction;
    }
}
