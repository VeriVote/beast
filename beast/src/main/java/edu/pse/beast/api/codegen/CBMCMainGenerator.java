package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.helperfunctions.InitVoteHelper;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

public class CBMCMainGenerator {

	// for the voting arrays declare the max votes variables. They depend on
	// the properties
	// create the voting arrays, depending on how often is voted
	// fill them with nondet int
	// depending on voting type, make sure the votes make sense (are between
	// bounds)
	// other preconditions
	// call the voting func
	// post conditions
	public static CFunction main(BooleanExpASTData preAstData,
			BooleanExpASTData postAstData, ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct, CodeGenOptions options,
			LoopBoundHandler loopBoundHandler) {
		CFunction created = new CFunction("main",
				List.of("int argc", "char ** argv"), "int");
		List<String> code = new ArrayList<>();

		String votesLowerBoundVarName = "votesLowerBound";
		String votesUpperBoundVarName = "votesUpperBound";

		String lower = "unsigned int VAR = 0;".replaceAll("VAR",
				votesLowerBoundVarName);
		String upper = "unsigned int VAR = C;".replaceAll("VAR",
				votesUpperBoundVarName);

		code.add(lower);
		code.add(upper);

		options.setVotesLowerBoundVarName(votesLowerBoundVarName);
		options.setVotesUpperBoundVarName(votesUpperBoundVarName);

		int highestVote = postAstData.getHighestVoteOrElect();

		for (int i = 0; i < highestVote; ++i) {
			String varName = "voteNUMBER".replaceAll("NUMBER",
					String.valueOf(i + 1));
			code.add("//initializing VAR".replaceAll("VAR", varName));
			code.add(InitVoteHelper.generateCode(varName, voteArrStruct,
					options, loopBoundHandler));
		}

		CodeGenASTVisitor visitor = new CodeGenASTVisitor(voteArrStruct,
				voteResultStruct, options, loopBoundHandler);
		visitor.setMode(CodeGenASTVisitor.Mode.ASSUME);
		for (int i = 0; i < postAstData.getTopAstNode().getBooleanExpressions()
				.size(); ++i) {
			for (BooleanExpressionNode node : postAstData.getTopAstNode()
					.getBooleanExpressions().get(i)) {
				String s = node.getTreeString(0);
				node.getVisited(visitor);
				code.add(visitor.getCodeBlock().generateCode());
			}
		}

		code.add("return 0;");

		created.setCode(code);
		return created;
	}

}
