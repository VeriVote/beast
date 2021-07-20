package edu.pse.beast.api.codegen.booleanExpAst;

import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BinaryRelationshipNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpIsEmptyNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.BooleanExpListElementNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ComparisonNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.FalseTrueNode;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.booleanExp.ForAllNode;
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
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.others.integers.VoteSumForCandExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbVarByPosExp;
import edu.pse.beast.api.codegen.booleanExpAst.nodes.types.symbolic_var.SymbolicVarByNameExp;

public interface BooleanAstVisitor {

    /*
     *
     * Nodes returning a boolean value
     *
     */

    void visitBooleanExpListElementNode(BooleanExpListElementNode node);

    void visitComparisonNode(ComparisonNode node);

    void visitForAllVotersNode(ForAllNode node);

    void visitExistsCandidateNode(ThereExistsNode node);

    void visitNotNode(NotNode node);

    void visitBinaryRelationNode(BinaryRelationshipNode node,
                                 String binaryCombinationSymbol);

    /*
     *
     * Nodes returning some other value to be compared into a boolean value
     * (such as voting types or integers)
     *
     */

    void visitVoteIntersectionNode(VoteIntersectionNode node);

    void visitVoteExpNode(VoteExp node);

    void visitElectExpNode(ElectExp node);

    void visitVotePermutation(VotePermutationNode node);

    void visitElectPermutation(ElectPermutationNode node);

    void visitVoteTuple(VoteTupleNode node);

    void visitElectTuple(ElectTupleNode node);

    void visitSymbolicVarExp(SymbolicVarByNameExp node);

    void visitVoteSumExp(VoteSumForCandExp node);

    void visitIntegerExp(IntegerNode node);

    void visitBinaryIntegerExpression(BinaryIntegerValuedNode binaryIntegerValuedNode);

    void visitConstantExp(ConstantExp constantExp);

    void visitEmptyNode(BooleanExpIsEmptyNode booleanExpIsEmptyNode);

    void visitElectIntersectionNode(ElectIntersectionNode electIntersectionNode);

    void visitBooleanExpFalseTrueNode(FalseTrueNode falseTrueNode);

    void visitSymbVarByPosExp(SymbVarByPosExp symbVarByPosExp);

}
