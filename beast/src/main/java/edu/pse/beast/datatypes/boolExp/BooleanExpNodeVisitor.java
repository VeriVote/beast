package edu.pse.beast.datatypes.boolExp;

public interface BooleanExpNodeVisitor {

    public void visitBooleanListNode(BooleanListNode node);

    public void visitAndNode(LogicalAndNode node);

    public void visitOrNode(LogialOrNode node);

    public void visitImplicationNode(ImplicationNode node);

    public void visitAquivalencyNode(AquivalencyNode node);

    public void visitForAllNode(ForAllNode node);

    public void visitThereExistsNode(ThereExistsNode node);

    public void visitNotNode(NotNode node);

    public void visitComparisonNode(ComparisonNode node);

    public void visitSymbVarExp(SymbVarExp exp);

    public void visitElectExp(ElectExp exp);

    public void visitVoteExp(VoteExp exp);
    
    public void visitVoteSumExp(VoteSumForCandExp exp);
}
