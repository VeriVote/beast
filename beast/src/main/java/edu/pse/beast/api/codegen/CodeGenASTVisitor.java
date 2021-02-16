package edu.pse.beast.api.codegen;

import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {

	private CCodeBlock codeBlock;

	private Stack<String> expVarNameStack = new Stack<>();
	private Stack<Boolean> expIsVoteArr = new Stack<>();
	private Stack<String> booleanVarNameStack = new Stack<>();

	private ElectionTypeCStruct voteArrStruct;
	private ElectionTypeCStruct voteResultStruct;

	private String mode;

	private int level = 0;
	
	public CodeGenASTVisitor(ElectionTypeCStruct voteArrStruct, ElectionTypeCStruct voteResultStruct
			) {
		this.voteArrStruct = voteArrStruct;
		this.voteResultStruct = voteResultStruct;
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
		
	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
		expVarNameStack.push("vote" + node.getCount());
		expIsVoteArr.push(true);
	}
	
	

}
