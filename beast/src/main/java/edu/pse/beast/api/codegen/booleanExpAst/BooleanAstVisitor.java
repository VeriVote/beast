package edu.pse.beast.api.codegen.booleanExpAst;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser.BooleanExpListElementContext;

public interface BooleanAstVisitor {
	
	
	/*
	 * 
	 * Nodes returning a boolean value
	 * 
	 */
	
	public void visitBooleanExpListElementNode(BooleanExpListElementNode node);
	public void visitComparisonNode(ComparisonNode node);
	
	
	/*
	 * 
	 * Nodes returning some other value to be compared into a boolean value
	 * (such as voting types or integers)
	 * 
	 */
	
	
	public void visitVoteIntersectionNode(VoteIntersectionNode node);
	public void visitVoteExpNode(VoteExp node);
	
	
}
