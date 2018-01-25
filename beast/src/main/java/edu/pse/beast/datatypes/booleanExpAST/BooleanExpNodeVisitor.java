package edu.pse.beast.datatypes.booleanExpAST;


import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.IntegerComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanValuedNodes.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.AtPosExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.BinaryIntegerValuedNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.IntegerNode;
import edu.pse.beast.datatypes.booleanExpAST.otherValuedNodes.integerValuedNodes.VoteSumForCandExp;

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
}
