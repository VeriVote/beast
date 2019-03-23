package edu.pse.beast.datatypes.booleanexpast;


import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ForAllNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.IntersectTypeExpNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.NotNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.AtPosExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.ElectExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.VoteExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.ConstantExp;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.IntegerNode;
import edu.pse.beast.datatypes.booleanexpast.othervaluednodes.integervaluednodes.VoteSumForCandExp;
import edu.pse.beast.propertychecker.ConcatenationExpNode;
import edu.pse.beast.propertychecker.IntersectContentNode;
import edu.pse.beast.propertychecker.IntersectExpNode;
import edu.pse.beast.propertychecker.NotEmptyContentNode;
import edu.pse.beast.propertychecker.NotEmptyExpressionNode;
import edu.pse.beast.propertychecker.PermutationExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingTupelChangeExpNode;

/**
 *
 * @author Lukas
 *
 */
public interface BooleanExpNodeVisitor {
    /**
     *
     * @param node the node
     */
    void visitAndNode(LogicalAndNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitOrNode(LogicalOrNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitImplicationNode(ImplicationNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitEquivalencyNode(EquivalencyNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitForAllNode(ForAllNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitThereExistsNode(ThereExistsNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitNotNode(NotNode node);

    /**
     *
     * @param node the node to visit
     */
    void visitComparisonNode(ComparisonNode node);

    /**
     *
     * @param exp the symbolic variable expression to visit
     */
    void visitSymbVarExp(SymbolicVarExp exp);

    /**
     *
     * @param exp the expression to visit
     */
    void visitConstExp(ConstantExp exp);

    /**
     *
     * @param exp the election expression to visit
     */
    void visitElectExp(ElectExp exp);

    /**
     *
     * @param exp the vote expression to visit
     */
    void visitVoteExp(VoteExp exp);

    /**
     *
     * @param exp the vote sum expression to visit
     */
    void visitVoteSumExp(VoteSumForCandExp exp, boolean unique);

    void visitIntegerNode(IntegerNode integerNode);

    void visitIntegerComparisonNode(IntegerComparisonNode listComparisonNode);

    void visitBinaryIntegerValuedNode(BinaryIntegerValuedNode binaryIntegerValuedNode);

    void visitAtPosNode(AtPosExp atPosExp);

    void visitVotingTupleChangeNode(VotingTupelChangeExpNode votingTupelChangeExp);

    void visitVotingListChangeNode(VotingListChangeExpNode node);

    void visitCandidateListChangeExpNode(CandidateListChangeExpNode candidateListChangeExpNode);

    void visitVoteEquivalentsNode(VoteEquivalentsNode voteEquivalentsNode);

    void visitConcatenationExpNode(ConcatenationExpNode concatenationExpNode);

    void visitPermutationExpNode(PermutationExpNode permutationExpNode);

    void visitIntersectExpNode(IntersectExpNode intersectExpNode);

    void visitIntersectContentNode(IntersectContentNode intersectContentNode);

    void visitNotEmptyExpNode(NotEmptyExpressionNode notEmptyExpressionNode);

    void visitNotEmptyContentNode(NotEmptyContentNode notEmptyContentNode);

    void visitIntersectTypeExpNode(IntersectTypeExpNode intersectTypeExpNode);
}