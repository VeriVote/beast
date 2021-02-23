package edu.pse.beast.api.codegen.booleanExpAst;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
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
	public void visitForAllVotersNode(ForAllNode node);
	
	/*
	 * 
	 * Nodes returning some other value to be compared into a boolean value
	 * (such as voting types or integers)
	 * 
	 */
	
	
	public void visitVoteIntersectionNode(VoteIntersectionNode node);
	public void visitVoteExpNode(VoteExp node);
	public void visitElectExpNode(ElectExp node);
	public void visitVotePermutation(VotePermutationNode node);
	public void visitVoteTuple(VoteTupleNode node);
	
	
}
