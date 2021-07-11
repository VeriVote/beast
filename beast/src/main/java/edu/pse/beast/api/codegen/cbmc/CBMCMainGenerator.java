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

    // TODO make this dependand on the arrays accessed by the symb var
    private static void initSymbVar(SymbolicCBMCVar var, List<String> code,
            CodeGenOptions options, int highestVote) {
        code.add("unsigned int " + var.getName() + " = "
                + options.getCbmcNondetUintName() + "();\n");

        String template = "ASSUME(VAR_NAME < BOUND);";
        for (int i = 0; i < highestVote; ++i) {
            switch (var.getVarType()) {
            case VOTER: {
                String bound = SymbVarInitVoteHelper.getCurrentAmtVoter(i + 1);
                String boundCode = template
                        .replaceAll("ASSUME", options.getCbmcAssumeName())
                        .replaceAll("VAR_NAME", var.getName())
                        .replaceAll("BOUND", bound);
                code.add(boundCode);
                break;
            }
            case CANDIDATE: {
                String bound = SymbVarInitVoteHelper.getCurrentAmtCand(i + 1);
                String boundCode = template
                        .replaceAll("ASSUME", options.getCbmcAssumeName())
                        .replaceAll("VAR_NAME", var.getName())
                        .replaceAll("BOUND", bound);
                code.add(boundCode);
                break;
            }
            case SEAT: {
                String bound = SymbVarInitVoteHelper.getCurrentAmtSeat(i + 1);
                String boundCode = template
                        .replaceAll("ASSUME", options.getCbmcAssumeName())
                        .replaceAll("VAR_NAME", var.getName())
                        .replaceAll("BOUND", bound);
                code.add(boundCode);
                break;
            }
            default:
                break;
            }
        }
    }

    public static CFunction main(BooleanExpASTData preAstData,
            BooleanExpASTData postAstData, List<SymbolicCBMCVar> symCbmcVars,
            ElectionTypeCStruct voteArrStruct,
            ElectionTypeCStruct voteResultStruct,
            VotingInputTypes votingInputType,
            VotingOutputTypes votingOutputType, CodeGenOptions options,
            CodeGenLoopBoundHandler loopBoundHandler, String votingFunctionName,
            CBMCGeneratedCodeInfo cbmcGeneratedCode,
            InitVoteHelper initVoteHelper) {

        List<String> code = new ArrayList<>();

        // init votes
        int highestVote = Math.max(preAstData.getHighestVoteOrElect(),
                postAstData.getHighestVoteOrElect());
        highestVote = Math.max(highestVote, initVoteHelper.getHighestVote());       

        for (int i = 0; i < highestVote; ++i) {
            code.add(initVoteHelper.generateCode(i + 1, voteArrStruct,
                    votingInputType, options, loopBoundHandler,
                    cbmcGeneratedCode));
        }
        
        // init global symbolic vars
        for (SymbolicCBMCVar var : symCbmcVars) {
            initSymbVar(var, code, options, highestVote);
        }

        CodeGenASTVisitor visitor = new CodeGenASTVisitor(voteArrStruct,
                votingInputType, voteResultStruct, votingOutputType, options,
                loopBoundHandler, cbmcGeneratedCode);

        // preconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSUME);
        for (BooleanExpressionNode node : preAstData.getTopAstNode()
                .getBooleanNodes()) {
            String s = node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }

        // vote
        for (int i = 0; i < highestVote; ++i) {
            code.add(PerformVoteHelper.generateCode(i + 1, voteArrStruct,
                    voteResultStruct, options, votingFunctionName,
                    cbmcGeneratedCode));
        }

        // postconditions
        visitor.setMode(CodeGenASTVisitor.Mode.ASSERT);
        for (BooleanExpressionNode node : postAstData.getTopAstNode()
                .getBooleanNodes()) {
            String s = node.getTreeString(0);
            node.getVisited(visitor);
            code.add(visitor.getCodeBlock().generateCode());
        }

        code.add("return 0;");

        CFunction mainFunction = new CFunction("main",
                List.of("int argc", "char ** argv"), "int");
        mainFunction.setCode(code);
        return mainFunction;
    }

}
