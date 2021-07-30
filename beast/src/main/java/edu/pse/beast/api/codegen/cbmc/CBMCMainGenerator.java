package edu.pse.beast.api.codegen.cbmc;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.SymbVarInitVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;

/**
 * This generator uses the {@link CodeGenASTVisitor} to generate the
 * main function. Therein, we initialize voting structs and symbolic variables,
 * generate preconditions, call the voting function, and generate postconditions.
 *
 * @author Holger Klein
 *
 */
public class CBMCMainGenerator {
    private static final String RESOURCES = "/edu/pse/beast/api/codegen/cbmc/";
    private static final String FILE_ENDING = ".template";
    private static final String BOUND = "BOUND";
    private static final String FILE_KEY = BOUND;

    private static final String LINE_BREAK = "\n";
    private static final String EQUALS = " = ";
    private static final String INT = "int";
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
     * @param c the current class for reading from the template file
     * @return assumption about upper variable bound
     */
    private static String replace(final SymbolicCBMCVar var,
                                  final CodeGenOptions options,
                                  final String bound,
                                  final Class<?> c) {
        return getTemplate(c)
                .replaceAll(ASSUME, options.getCbmcAssumeName())
                .replaceAll(VAR_NAME, var.getName())
                .replaceAll(BOUND, bound);
    }

    // TODO make this dependent on the arrays accessed by the symb var
    private static void initSymbVar(final SymbolicCBMCVar var,
                                    final List<String> code,
                                    final CodeGenOptions options,
                                    final int highestVote,
                                    final Class<?> c) {
        code.add(UINT + var.getName() + EQUALS + options.getCbmcNondetUintName()
                    + NO_ARGS + LINE_BREAK);

        for (int i = 1; i <= highestVote; ++i) {
            final String amount;
            switch (var.getVarType()) {
            case VOTER:
                amount = SymbVarInitVoteHelper.getCurrentAmtVoter(i);
                break;
            case CANDIDATE:
                amount = SymbVarInitVoteHelper.getCurrentAmtCand(i);
                break;
            case SEAT:
                amount = SymbVarInitVoteHelper.getCurrentAmtSeat(i);
                break;
            default:
                amount = "";
            }
            code.add(replace(var, options, amount, c));
        }
    }

    public static final String getTemplate(final Class<?> c) {
        final String key = FILE_KEY;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }

    public static CFunction main(final CFunction.PropertyExpressions expressions,
                                 final CFunction.VotingFunction votingFunction,
                                 final CodeGenOptions options,
                                 final CodeGenLoopBoundHandler loopBoundHandler,
                                 final CBMCGeneratedCodeInfo cbmcGeneratedCode,
                                 final InitVoteHelper initVoteHelper,
                                 final Class<?> c) {
        final List<String> code = new ArrayList<>();

        // init votes
        int highestVote =
                Math.max(expressions.preAstData.getHighestVoteOrElect(),
                         expressions.postAstData.getHighestVoteOrElect());
        highestVote = Math.max(highestVote, initVoteHelper.getHighestVote());

        for (int i = 1; i <= highestVote; ++i) {
            code.add(initVoteHelper.generateCode(i, votingFunction.votes,
                                                 votingFunction.profileType, options,
                                                 loopBoundHandler,
                                                 cbmcGeneratedCode));
        }

        // init global symbolic vars
        for (final SymbolicCBMCVar var : expressions.symCbmcVars) {
            initSymbVar(var, code, options, highestVote, c);
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
        for (int i = 1; i <= highestVote; ++i) {
            code.add(PerformVoteHelper.generateCode(i, votingFunction.votes,
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
        code.add(MAIN_RETURN);

        final CFunction mainFunction = new CFunction(MAIN, List.of(), INT);
        mainFunction.setCode(code);
        return mainFunction;
    }
}
