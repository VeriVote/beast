package edu.kit.kastel.formal.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.PerformVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpressionNode;
import edu.kit.kastel.formal.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.kit.kastel.formal.beast.api.codegen.ccode.CFunction;
import edu.kit.kastel.formal.beast.api.codegen.init.InitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.init.SymbVarInitVoteHelper;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * This generator uses the {@link CodeGenASTVisitor} to generate the
 * main function. Therein, we initialize voting structs and symbolic variables,
 * generate preconditions, call the voting function, and generate postconditions.
 *
 * @author Holger Klein
 *
 */
public class CBMCMainGenerator {
    private static final String BOUND = "BOUND";
    private static final String FILE_KEY = BOUND;

    private static final String EMPTY = "";
    private static final String LINE_BREAK = "\n";
    private static final String EQUALS = " = ";
    private static final String UINT = "unsigned int ";
    private static final String NO_ARGS = "();";
    private static final String MAIN = "main";
    private static final String MAIN_RETURN = "return 0;";
    private static final String ASSUME = "ASSUME";
    private static final String VAR_NAME = "VAR_NAME";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    /**
     * This method returns an assumption for an upper bound
     * with the given variables, bounds, etc.
     *
     * @param var the bound variable
     * @param options the code generation options for statement name
     * @param bound the upper bound
     * @return assumption about upper variable bound
     */
    private static String replace(final SymbolicVariable var,
                                  final CodeGenOptions options,
                                  final String bound) {
        final CBMCMainGenerator generator = new CBMCMainGenerator();
        return generator.getTemplate()
                .replaceAll(ASSUME, options.getCbmcAssumeName())
                .replaceAll(VAR_NAME, var.getName())
                .replaceAll(BOUND, bound);
    }

    // TODO make this dependent on the arrays accessed by the symb var
    private static void initSymbVar(final SymbolicVariable var,
                                    final List<String> code,
                                    final CodeGenOptions options,
                                    final int lastElectionNumber) {
        code.add(UINT + var.getName() + EQUALS + options.getCbmcNondetUintName()
                    + NO_ARGS + LINE_BREAK);

        for (int i = 1; i <= lastElectionNumber; ++i) {
            final String amount;
            switch (var.getVarType()) {
            case VOTER:
                amount = SymbVarInitVoteHelper.getCurrentAmtVoter(options, i);
                break;
            case CANDIDATE:
                amount = SymbVarInitVoteHelper.getCurrentAmtCand(options, i);
                break;
            case SEAT:
                amount = SymbVarInitVoteHelper.getCurrentAmtSeat(options, i);
                break;
            default:
                amount = EMPTY;
            }
            code.add(replace(var, options, amount));
        }
    }

    public static CFunction main(final CFunction.PropertyExpressions expressions,
                                 final CFunction.VotingFunction votingFunction,
                                 final CodeGenOptions options,
                                 final CodeGenLoopBoundHandler loopBoundHandler,
                                 final GeneratedCodeInfo codeInfo,
                                 final InitVoteHelper initHelper) {
        final List<String> code = new ArrayList<String>();

        // init votes
        int numberOfLastElectionCall =
                Math.max(expressions.preAstData.getLastVoteOrElect(),
                         expressions.postAstData.getLastVoteOrElect());
        numberOfLastElectionCall =
                Math.max(numberOfLastElectionCall, initHelper.getLastElectionNumber());

        for (int i = 1; i <= numberOfLastElectionCall; ++i) {
            code.add(initHelper.generateCode(i, votingFunction.votes, votingFunction.profileType,
                                             options, loopBoundHandler, codeInfo));
        }

        // init global symbolic vars
        for (final SymbolicVariable var : expressions.symCbmcVars) {
            initSymbVar(var, code, options, numberOfLastElectionCall);
        }
        final CodeGenASTVisitor visitor =
                new CodeGenASTVisitor(votingFunction.votes, votingFunction.profileType,
                                      votingFunction.result, votingFunction.resultType, options,
                                      loopBoundHandler, codeInfo);

        // preconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSUME);
        for (final BooleanExpressionNode node
                : expressions.preAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }

        // vote
        for (int i = 1; i <= numberOfLastElectionCall; ++i) {
            code.add(PerformVoteHelper.generateCode(i, votingFunction.votes,
                                                    votingFunction.result, options,
                                                    votingFunction.function,
                                                    codeInfo));
        }

        // postconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSERT);
        for (final BooleanExpressionNode node
                : expressions.postAstData.getTopAstNode().getBooleanNodes()) {
            node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }
        code.add(MAIN_RETURN);

        final CFunction mainFunction = new CFunction(MAIN, List.of(), UINT.trim());
        mainFunction.setCode(code);
        return mainFunction;
    }

    public final String getTemplate() {
        return PathHandler.getTemplate(FILE_KEY, TEMPLATES, EMPTY, this.getClass());
    }
}
