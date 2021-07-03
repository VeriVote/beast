package edu.pse.beast.api.codegen.booleanExpAst;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BinaryRelationshipNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ComparisonNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.FalseNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ForAllNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.LogicalAndNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.NotNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ThereExistsNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectPermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.ElectTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteIntersectionNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VotePermutationNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.election.VoteTupleNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.BinaryIntegerValuedNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.ConstantExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.IntegerNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.IntegerValuedExpression;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.VoteSumForCandExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbVarByPosExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbolicVarByNameExp;
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
	public void visitExistsCandidateNode(ThereExistsNode node);
	public void visitNotNode(NotNode node);
	public void visitBinaryRelationNode(
			BinaryRelationshipNode node,
			String binaryCombinationSymbol);
	
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
	public void visitElectPermutation(ElectPermutationNode node);
	public void visitVoteTuple(VoteTupleNode node);
	public void visitElectTuple(ElectTupleNode node);
	public void visitSymbolicVarExp(SymbolicVarByNameExp node);
	public void visitVoteSumExp(VoteSumForCandExp node);
	public void visitIntegerExp(IntegerNode node);
	public void visitBinaryIntegerExpression(
			BinaryIntegerValuedNode binaryIntegerValuedNode);
	public void visitConstantExp(ConstantExp constantExp);
	public void visitEmptyNode(BooleanExpIsEmptyNode booleanExpIsEmptyNode);
	public void visitElectIntersectionNode(
			ElectIntersectionNode electIntersectionNode);
	public void visitBooleanExpFalseNode(FalseNode falseNode);
	public void visitSymbVarByPosExp(SymbVarByPosExp symbVarByPosExp);
	
	
	
}
