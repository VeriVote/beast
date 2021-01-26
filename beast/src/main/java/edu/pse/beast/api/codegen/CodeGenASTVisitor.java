package edu.pse.beast.api.codegen;

import java.util.Stack;

import edu.pse.beast.api.codegen.booleanExpAst.BooleanAstVisitor;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.c_code.CCodeBlock;
import edu.pse.beast.api.codegen.c_code.CStruct;
import edu.pse.beast.api.codegen.c_code.CTypeAndName;
import edu.pse.beast.api.electiondescription.CElectionDescription;
import edu.pse.beast.api.electiondescription.CElectionVotingType;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;

public class CodeGenASTVisitor implements BooleanAstVisitor {
	
	private CCodeBlock codeBlock;
	
	private Stack<CTypeAndName> varStack = new Stack<>();	
	
	private CStruct voteArrStruct;
	private CStruct voteResultStruct;
	
	public CodeGenASTVisitor(CStruct voteArrStruct, CStruct voteResultStruct) {
		this.voteArrStruct = voteArrStruct;
		this.voteResultStruct = voteResultStruct;
	}

	public CCodeBlock getCodeBlock() {
		return codeBlock;
	}
	
	@Override
	public void visitBooleanExpListElementNode(BooleanExpListElementNode node) {	
		codeBlock = new CCodeBlock();
		codeBlock.addComment(node.getCompleteCode());
	}

	@Override
	public void visitComparisonNode(ComparisonNode node) {
		node.getLhsTypeExp().getVisited(this);
		node.getRhsTypeExp().getVisited(this);		
	}

	@Override
	public void visitVoteIntersectionNode(VoteIntersectionNode node) {
		String generatedVarName = codeBlock.newVarName();
		
		
		varStack.push(new CTypeAndName(voteArrStruct.getName(), generatedVarName));
	}

	@Override
	public void visitVoteExpNode(VoteExp node) {
	}

}
