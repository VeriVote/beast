package edu.pse.beast.datatypes.booleanExpAST;


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
    void visitAquivalencyNode(EquivalencyNode node);

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
    void visitVoteSumExp(VoteSumForCandExp exp);
    

    public void visitNumberExpNode(NumberExpression exp);
}
