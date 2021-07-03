package edu.pse.beast.api.codegen.cbmc;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpressionNode;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.SymbolicCBMCVar.CBMCVarType;
import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.InitVoteHelper;
import edu.pse.beast.api.codegen.helperfunctions.PerformVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class CBMCMainGenerator {

	public static CFunction main(BooleanExpASTData preAstData,
			BooleanExpASTData postAstData, List<SymbolicCBMCVar> symCbmcVars,
			ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct,
			VotingInputTypes votingInputType,
			VotingOutputTypes votingOutputType, CodeGenOptions options,
			CodeGenLoopBoundHandler loopBoundHandler, String votingFunctionName,
			CBMCGeneratedCodeInfo cbmcGeneratedCode) {

		List<String> code = new ArrayList<>();

		// TODO cleanup
		// init global symbolic vars
		for (SymbolicCBMCVar var : symCbmcVars) {
			code.add("unsigned int " + var.getName() + " = "
					+ options.getCbmcNondetUintName() + "();\n");
		}

		// init votes
		int highestVote = Math.max(preAstData.getHighestVoteOrElect(),
				postAstData.getHighestVoteOrElect());

		for (int i = 0; i < highestVote; ++i) {
			code.add(InitVoteHelper.generateCode(i + 1, voteArrStruct,
					votingInputType, options, loopBoundHandler,
					cbmcGeneratedCode));
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
