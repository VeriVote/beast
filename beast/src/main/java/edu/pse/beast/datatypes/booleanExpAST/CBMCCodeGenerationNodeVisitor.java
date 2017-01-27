package edu.pse.beast.datatypes.booleanExpAST;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class CBMCCodeGenerationNodeVisitor implements BooleanExpNodeVisitor {

    private final String assertOrAssume;
    private final ArrayList<String> code;
    private int andNodeCounter;
    private int orNodeCounter;
    private int implicationNodeCounter;
    private int aquivalencyNodeCounter;
    private int forAllNodeCounter;
    private int thereExistsNodeCounter;
    private int notNodeCounter;
    private int comparisonNodeCounter;
    private int symbVarExpressionCounter;
    private int constExpCounter;
    private int electExpCounter;
    private int voteExpCounter;
    private int voteSumExpressionCounter;
    private Stack<String> variableNames;

    /**
     *
     * @param assertOrAssume has to be either "assert" or "assume"
     */
    public CBMCCodeGenerationNodeVisitor(String assertOrAssume) {
        this.assertOrAssume = assertOrAssume;

        //start all counters at 1
        andNodeCounter = 1;
        orNodeCounter = 1;
        implicationNodeCounter = 1;
        aquivalencyNodeCounter = 1;
        forAllNodeCounter = 1;
        thereExistsNodeCounter = 1;
        notNodeCounter = 1;
        comparisonNodeCounter = 1;
        symbVarExpressionCounter = 1;
        constExpCounter = 1;
        electExpCounter = 1;
        voteExpCounter = 1;
        voteSumExpressionCounter = 1;

        variableNames = new Stack<>();

        code = new ArrayList<>();
    }

    @Override
    public void visitBooleanListNode(BooleanExpListNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAndNode(LogicalAndNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitOrNode(LogicalOrNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitImplicationNode(ImplicationNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitForAllNode(ForAllNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitNotNode(NotNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitComparisonNode(ComparisonNode node) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitSymbVarExp(SymbolicVarExp exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitConstExp(ConstantExp exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitElectExp(ElectExp exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitVoteExp(VoteExp exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public void visitVoteSumExp(VoteSumForCandExp exp) {
        // TODO Auto-generated method stub

    }

    @Override
    public List<String> getCode() {
        return code;
    }

    @Override
    public void visitNumberExpNode(NumberExpression exp) {
       
    }

}
