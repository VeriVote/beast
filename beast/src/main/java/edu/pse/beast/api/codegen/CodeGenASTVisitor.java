package edu.pse.beast.api.codegen;

import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeNameBrackets;
import edu.pse.beast.api.codegen.helperfunctions.HelperFunctionMap;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	private CCodeBlock codeBlock;

	private Stack<String> expVarNameStack = new Stack<>();
	private Stack<Boolean> expIsVoteArr = new Stack<>();
	private Stack<String> booleanVarNameStack = new Stack<>();

	private ElectionTypeCStruct voteArrStruct;
	private ElectionTypeCStruct voteResultStruct;
	private HelperFunctionMap functionMap;

	private String mode;

	public CodeGenASTVisitor(ElectionTypeCStruct voteArrStruct, ElectionTypeCStruct voteResultStruct,
			HelperFunctionMap functionMap) {
		this.voteArrStruct = voteArrStruct;
		this.voteResultStruct = voteResultStruct;
		this.functionMap = functionMap;
	}

	public CCodeBlock getCodeBlock() {
		return codeBlock;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	@Override
	public void visitBooleanExpListElementNode(BooleanExpListElementNode node) {
		codeBlock = new CCodeBlock();
		codeBlock.addComment(node.getCompleteCode());
		node.getFirstChild().getVisited(this);
		if(booleanVarNameStack.size() == 0) return;
		codeBlock.addSnippet(mode + "(" + booleanVarNameStack.pop() + ");");
	}

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);
		
		if(expVarNameStack.size() < 2) return;

		String rhsVarName = expVarNameStack.pop();
		String lhsVarName = expVarNameStack.pop();

		String booleanVarName = codeBlock.newVarName();
		codeBlock.addVarDecl("bool", booleanVarName);

		if (expIsVoteArr.size() == 2) {
			// we are comparing 2 vote arrays
			String funcCall = functionMap.getCompareVotesFunction().uniqueName();
			funcCall += "(" + lhsVarName + "," + rhsVarName + ")";
			codeBlock.addAssignment(booleanVarName, funcCall);
		}

		booleanVarNameStack.push(booleanVarName);
	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName();
		codeBlock.addVarDecl(voteArrStruct.getStruct().getName(), generatedVarName);
		
		String funcCall = functionMap.getIntersectVotesFunction().uniqueName();
		String lhs = "vote" + node.getNumbers().get(0);
		String rhs = "vote" + node.getNumbers().get(1);
		codeBlock.addAssignment(generatedVarName, funcCall + "(" + lhs + "," + rhs + ")");
		
		for (int i = 2; i < node.getNumbers().size(); ++i) {
			funcCall = functionMap.getIntersectVotesFunction().uniqueName();
			lhs = generatedVarName;
			rhs = "vote" + node.getNumbers().get(i);		
			codeBlock.addAssignment(generatedVarName, funcCall + "(" + lhs + "," + rhs + ")");
		}
		
		expVarNameStack.push(generatedVarName);
		expIsVoteArr.push(true);
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		expVarNameStack.push("vote" + node.getCount());
		expIsVoteArr.push(true);
	}
	
	

}
