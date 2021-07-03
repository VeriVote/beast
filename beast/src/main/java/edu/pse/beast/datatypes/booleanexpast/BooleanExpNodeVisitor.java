package edu.pse.beast.datatypes.booleanexpast;

import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanexpast.booleanvaluednodes.EquivalenceNode;
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
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.CandidateListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VoteEquivalentsNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingListChangeExpNode;
import edu.pse.beast.toolbox.antlr.booleanexp.generateast.VotingTupleChangeExpNode;
import edu.pse.beast.zzz.propertychecker.ConcatenationExpNode;
import edu.pse.beast.zzz.propertychecker.IntersectContentNode;
import edu.pse.beast.zzz.propertychecker.IntersectExpNode;
import edu.pse.beast.zzz.propertychecker.NotEmptyContentNode;
import edu.pse.beast.zzz.propertychecker.NotEmptyExpressionNode;
import edu.pse.beast.zzz.propertychecker.PermutationExpNode;

/**
 * The Interface BooleanExpNodeVisitor.
 *
 * @author Lukas Stapelbroek
 */
public interface BooleanExpNodeVisitor {

    /**
     * Visit and node.
     *
     * @param node
     *            the node
     */
    void visitAndNode(LogicalAndNode node);

    /**
     * Visit or node.
     *
     * @param node
     *            the node to visit
     */
    void visitOrNode(LogicalOrNode node);

    /**
     * Visit implication node.
     *
     * @param node
     *            the node to visit
     */
    void visitImplicationNode(ImplicationNode node);

    /**
     * Visit equivalence node.
     *
     * @param node
     *            the node to visit
     */
    void visitEquivalenceNode(EquivalenceNode node);

    /**
     * Visit for all node.
     *
     * @param node
     *            the node to visit
     */
    void visitForAllNode(ForAllNode node);

    /**
     * Visit there exists node.
     *
     * @param node
     *            the node to visit
     */
    void visitThereExistsNode(ThereExistsNode node);

    /**
     * Visit not node.
     *
     * @param node
     *            the node to visit
     */
    void visitNotNode(NotNode node);

    /**
     * Visit comparison node.
     *
     * @param node
     *            the node to visit
     */
    void visitComparisonNode(ComparisonNode node);

    /**
     * Visit symb var exp.
     *
     * @param exp
     *            the symbolic variable expression to visit
     */
    void visitSymbVarExp(SymbolicVarExp exp);

    /**
     * Visit const exp.
     *
     * @param exp
     *            the expression to visit
     */
    void visitConstExp(ConstantExp exp);

    /**
     * Visit elect exp.
     *
     * @param exp
     *            the election expression to visit
     */
    void visitElectExp(ElectExp exp);

    /**
     * Visit vote exp.
     *
     * @param exp
     *            the vote expression to visit
     */
    void visitVoteExp(VoteExp exp);

    /**
     * Visit vote sum exp.
     *
     * @param exp
     *            the vote sum expression to visit
     * @param unique
     *            unique or not
     */
    void visitVoteSumExp(VoteSumForCandExp exp, boolean unique);

    /**
     * Visit integer node.
     *
     * @param integerNode
     *            the integer node
     */
    void visitIntegerNode(IntegerNode integerNode);

    /**
     * Visit integer comparison node.
     *
     * @param listComparisonNode
     *            the list comparison node
     */
    void visitIntegerComparisonNode(IntegerComparisonNode listComparisonNode);

    /**
     * Visit binary integer valued node.
     *
     * @param binaryIntegerValuedNode
     *            the binary integer valued node
     */
    void visitBinaryIntegerValuedNode(BinaryIntegerValuedNode binaryIntegerValuedNode);

    /**
     * Visit at pos node.
     *
     * @param atPosExp
     *            the at pos exp
     */
    void visitAtPosNode(AtPosExp atPosExp);

    /**
     * Visit voting tuple change node.
     *
     * @param votingTupleChangeExp
     *            the voting tuple change exp
     */
    void visitVotingTupleChangeNode(VotingTupleChangeExpNode votingTupleChangeExp);

    /**
     * Visit voting list change node.
     *
     * @param node
     *            the node
     */
    void visitVotingListChangeNode(VotingListChangeExpNode node);

    /**
     * Visit candidate list change exp node.
     *
     * @param candidateListChangeExpNode
     *            the candidate list change exp node
     */
    void visitCandidateListChangeExpNode(CandidateListChangeExpNode candidateListChangeExpNode);

    /**
     * Visit vote equivalents node.
     *
     * @param voteEquivalentsNode
     *            the vote equivalents node
     */
    void visitVoteEquivalentsNode(VoteEquivalentsNode voteEquivalentsNode);

    /**
     * Visit concatenation exp node.
     *
     * @param concatenationExpNode
     *            the concatenation exp node
     */
    void visitConcatenationExpNode(ConcatenationExpNode concatenationExpNode);

    /**
     * Visit permutation exp node.
     *
     * @param permutationExpNode
     *            the permutation exp node
     */
    void visitPermutationExpNode(PermutationExpNode permutationExpNode);

    /**
     * Visit intersect exp node.
     *
     * @param intersectExpNode
     *            the intersect exp node
     */
    void visitIntersectExpNode(IntersectExpNode intersectExpNode);

    /**
     * Visit intersect content node.
     *
     * @param intersectContentNode
     *            the intersect content node
     */
    void visitIntersectContentNode(IntersectContentNode intersectContentNode);

    /**
     * Visit not empty exp node.
     *
     * @param notEmptyExpressionNode
     *            the not empty expression node
     */
    void visitNotEmptyExpNode(NotEmptyExpressionNode notEmptyExpressionNode);

    /**
     * Visit not empty content node.
     *
     * @param notEmptyContentNode
     *            the not empty content node
     */
    void visitNotEmptyContentNode(NotEmptyContentNode notEmptyContentNode);

    /**
     * Visit intersect type exp node.
     *
     * @param intersectTypeExpNode
     *            the intersect type exp node
     */
    void visitIntersectTypeExpNode(IntersectTypeExpNode intersectTypeExpNode);
}
