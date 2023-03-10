package edu.kit.kastel.formal.beast.api.codegen.ast;

import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BinaryRelationshipNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpIsEmptyNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.BooleanExpListElementNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ComparisonNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.FalseTrueNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ForAllNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.NotNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.bool.ThereExistsNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectIntersectionNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectPermutationNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.ElectTupleNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteIntersectionNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VotePermutationNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.election.VoteTupleNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.BinaryIntegerValuedNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.ConstantExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.IntegerNode;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.integer.VoteSumForCandExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic.SymbVarByPosExp;
import edu.kit.kastel.formal.beast.api.codegen.ast.expression.type.symbolic.SymbolicVarByNameExp;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
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
