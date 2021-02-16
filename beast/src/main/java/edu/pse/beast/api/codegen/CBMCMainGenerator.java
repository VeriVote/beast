package edu.pse.beast.api.codegen;

import java.util.ArrayList;
import java.util.List;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;

public class CBMCMainGenerator {

	private static final String voteTemplate = 
						"    TYPE voteNUMBER;\n";
	
	private static final String initVotesTemplate2D = 
			"    voteNUMBER.AMT_MEMBER = NONDET_UINT_CALL();\n"
			+ "    ASSUME(voteNUMBER.AMT_MEMBER <= AMT_VOTES);\n"
			+ "    for (int OUTER_COUNTER = 0; OUTER_COUNTER < AMT_VOTES; ++OUTER_COUNTER) {\n"
			+ "        for (int INNER_COUNTER = 0; INNER_COUNTER < ELEMENT_PER_VOTE; ++INNER_COUNTER) {\n"
			+ "            voteNUMBER.VOTE_ARR[OUTER_COUNTER][INNER_COUNTER] = NONDET_UINT_CALL();\n"
			+ "            ASSUME(voteNUMBER.VOTE_ARR[OUTER_COUNTER][INNER_COUNTER] <= UPPER_LIMIT);\n"
			+ "            ASSUME(voteNUMBER.VOTE_ARR[OUTER_COUNTER][INNER_COUNTER] >= LOWER_LIMIT);\n"
			+ "        }\n"
			+ "    }\n";

	private static String getInitVotesString(ElectionTypeCStruct voteArrStruct) {
		String code = null;
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			code = initVotesTemplate2D;
			
			code = code.replaceAll("OUTER_COUNTER", "i");
			code = code.replaceAll("INNER_COUNTER", "j");
			
			code = code.replaceAll("ELEMENT_PER_VOTE", voteArrStruct.getVotingType().getListSizes().get(1).toString());
			
			code = code.replaceAll("LOWER_LIMIT", voteArrStruct.getVotingType().getSimpleTypeLowerBound().toString());
			code = code.replaceAll("UPPER_LIMIT", voteArrStruct.getVotingType().getSimpleTypeUpperBound().toString());
		}
		return code;
	}
	
	private static List<String> establishVotes(BooleanExpASTData astData, ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct) {

		List<String> code = new ArrayList<>();
		for (int i = 0; i < astData.getHighestVoteOrElect(); ++i) {
			String voteCode = voteTemplate + getInitVotesString(voteArrStruct);
			
			voteCode = voteCode.replaceAll("TYPE", voteArrStruct.getStruct().getName());
			voteCode = voteCode.replaceAll("NUMBER", String.valueOf(i + 1));
			voteCode = voteCode.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			
			System.out.println(voteCode);
			code.add(voteCode);
		}
		return code;
	}

	// for the voting arrays declare the max votes variables. They depend on
	// the properties
	// create the voting arrays, depending on how often is voted
	// fill them with nondet int
	// depending on voting type, make sure the votes make sense (are between bounds)
	// other preconditions
	// call the voting func
	// post conditions
	public static CFunction main(BooleanExpASTData preAstData, BooleanExpASTData postAstData, ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct) {
		CFunction created = new CFunction("main", List.of("int argc", "char ** argv"), "int");
		List<String> code = new ArrayList<>();

		code.addAll(establishVotes(postAstData, voteArrStruct, voteResultStruct));
		
		CodeGenASTVisitor visitor = new CodeGenASTVisitor(voteArrStruct, voteResultStruct);
		int highestVote = postAstData.getHighestVote();
		visitor.setMode("assert");
		for (int i = 0; i < postAstData.getTopAstNode().getBooleanExpressions().size(); ++i) {
			for (BooleanExpressionNode node : postAstData.getTopAstNode().getBooleanExpressions().get(i)) {
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
