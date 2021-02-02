package edu.pse.beast.api.codegen;

import static edu.pse.beast.toolbox.CCodeHelper.functionCode;
import static edu.pse.beast.toolbox.CCodeHelper.intVar;
import static edu.pse.beast.toolbox.CCodeHelper.lineComment;
import static edu.pse.beast.toolbox.CCodeHelper.pointer;
import static edu.pse.beast.toolbox.CCodeHelper.space;
import static edu.pse.beast.toolbox.CCodeHelper.uintVarEqualsCode;
import static edu.pse.beast.toolbox.CCodeHelper.varAssignCode;

import java.util.ArrayList;
import java.util.List;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanCodeToAST;
import edu.pse.beast.api.codegen.booleanExpAst.BooleanExpASTData;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.helperfunctions.HelperFunctionMap;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanexpast.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.BooleanExpressionNode;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.UnifiedNameContainer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.FormalPropertySyntaxTreeToAstTranslator;

public class CBMCMainGenerator {

	private static final String voteTemplate = 
						"TYPE voteNUMBER;\n" + 
						"voteNUMBER = INITVOTE(voteNUMBER);\n";

	private static List<String> establishVotes(BooleanExpASTData astData, ElectionTypeCStruct voteArrStruct,
			ElectionTypeCStruct voteResultStruct, HelperFunctionMap functionMap) {

		List<String> code = new ArrayList<>();
		for (int i = 0; i < astData.getHighestVoteOrElect(); ++i) {
			String voteCode = voteTemplate;
			voteCode = voteCode.replaceAll("TYPE", voteArrStruct.getStruct().getName());
			voteCode = voteCode.replaceAll("NUMBER", String.valueOf(i + 1));
			voteCode = voteCode.replaceAll("INITVOTE", functionMap.getInitVoteFunction().uniqueName());
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
			ElectionTypeCStruct voteResultStruct, HelperFunctionMap functionMap) {
		CFunction created = new CFunction("main", List.of("int argc", "char ** argv"), "int");
		List<String> code = new ArrayList<>();

		code.addAll(establishVotes(postAstData, voteArrStruct, voteResultStruct, functionMap));
		
		CodeGenASTVisitor visitor = new CodeGenASTVisitor(voteArrStruct, voteResultStruct, functionMap);
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
