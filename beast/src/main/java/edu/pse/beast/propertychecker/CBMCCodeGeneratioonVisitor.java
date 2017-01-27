/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpNodeVisitor;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpressionNode;
import edu.pse.beast.datatypes.booleanExpAST.ComparisonNode;
import edu.pse.beast.datatypes.booleanExpAST.ConstantExp;
import edu.pse.beast.datatypes.booleanExpAST.ElectExp;
import edu.pse.beast.datatypes.booleanExpAST.EquivalencyNode;
import edu.pse.beast.datatypes.booleanExpAST.ForAllNode;
import edu.pse.beast.datatypes.booleanExpAST.ImplicationNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalAndNode;
import edu.pse.beast.datatypes.booleanExpAST.LogicalOrNode;
import edu.pse.beast.datatypes.booleanExpAST.NotNode;
import edu.pse.beast.datatypes.booleanExpAST.NumberExpression;
import edu.pse.beast.datatypes.booleanExpAST.SymbolicVarExp;
import edu.pse.beast.datatypes.booleanExpAST.ThereExistsNode;
import edu.pse.beast.datatypes.booleanExpAST.VoteExp;
import edu.pse.beast.datatypes.booleanExpAST.VoteSumForCandExp;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

/**
 *
 * @author Holger-Desktop
 */
public class CBMCCodeGeneratioonVisitor implements BooleanExpNodeVisitor{

    private String generated;
    private String asString;
    private Stack<String> booleanVars;
    private int varCounter = 0;
    private Stack<String> expString;
    
    
    public String generate(ArrayList<BooleanExpressionNode> nodes) {
        generated = "";
        booleanVars = new Stack<>();
        expString = new Stack<>();
        
        for(BooleanExpressionNode n : nodes) {
            booleanVars.clear();
            n.getVisited(this);
        }
        
        return generated;
    }
    
    @Override
    public void visitBooleanListNode(BooleanExpListNode node) {
        
    }

    @Override
    public void visitAndNode(LogicalAndNode node) {
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        
        String rhs = booleanVars.pop();
        String lhs = booleanVars.pop();
        
        String booleanVar = "and_" + varCounter++;
        
        String code = "unsigned int BOOL = LHS && RHS;\n";
        code = code.replace("LHS", lhs);
        code = code.replace("LHS", rhs);
        code = code.replace("BOOL", booleanVar);
        
        generated += code;
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitOrNode(LogicalOrNode node) {
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        
        String rhs = booleanVars.pop();
        String lhs = booleanVars.pop();
        
        String booleanVar = "or_" + varCounter++;
        
        String code = "unsigned int BOOL = LHS || RHS;\n";
        code = code.replace("LHS", lhs);
        code = code.replace("LHS", rhs);
        code = code.replace("BOOL", booleanVar);        
        
        generated += code;
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitImplicationNode(ImplicationNode node) {
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        
        String rhs = booleanVars.pop();
        String lhs = booleanVars.pop();
        
        String booleanVar = "impl_" + varCounter++;
        
        String code = "unsigned int BOOL = !LHS || RHS;\n";
        code = code.replace("LHS", lhs);
        code = code.replace("LHS", rhs);
        code = code.replace("BOOL", booleanVar);
        
        generated += code;
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
         node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        
        String rhs = booleanVars.pop();
        String lhs = booleanVars.pop();
        
        String booleanVar = "equi_" + varCounter++;
        
        String code = "unsigned int BOOL = (!LHS || RHS) && (!RHS || LHS);\n";
        code = code.replace("LHS", lhs);
        code = code.replace("LHS", rhs);
        code = code.replace("BOOL", booleanVar);        
        
        generated += code;
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitForAllNode(ForAllNode node) {
        String before = "unsigned int FORALL = 1;\n"  +
                "for(unsigned int VAR = 0; VAR < MAX && FORALL; ++VAR) {\n";
        
        String booleanVar = "forall_" + varCounter++;
        String var =  node.getDeclaredSymbolicVar().getId();
        String max = "";
        InternalTypeRep type = node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType();
        
        if(type == InternalTypeRep.VOTER) {
            max = "V";
        } else if(type == InternalTypeRep.CANDIDATE) {
            max = "C";
        } else if(type == InternalTypeRep.SEAT) {
            max = "S";
        }
        
        before = before.replaceAll("FORALL", booleanVar);
        before = before.replaceAll("VAR", var);
        before = before.replaceAll("MAX", max);    
        
        generated += before;
        
        node.getFollowingExpNode().getVisited(this);
        
        String after = "FORALL = OTHER;\n" + 
                            "}\n";
        after = after.replace("FORALL", booleanVar);
        after = after.replace("OTHER", booleanVars.pop());
        
        generated += after;
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
        String before = "unsigned int THEREEXISTS = 0;\n"  +
                "for(unsigned int VAR = 0; VAR < MAX && !THEREEXISTS; ++VAR) {\n";
        
        String booleanVar = node.getDeclaredSymbolicVar().getId();
        String max = "";
        InternalTypeRep type = node.getDeclaredSymbolicVar().getInternalTypeContainer().getInternalType();
        if(type == InternalTypeRep.VOTER) {
            max = "V";
        } else if(type == InternalTypeRep.CANDIDATE) {
            max = "C";
        } else if(type == InternalTypeRep.SEAT) {
            max = "S";
        }
        
        before = before.replace("THEREEXISTS", booleanVar);
        before = before.replace("VAR", "counter_" + varCounter++);
        before = before.replace("MAX", max);    
        
        generated += before;
        
        node.getFollowingExpNode().getVisited(this);
        
        String after = "FORALL = OTHER;\n" + 
                            "}\n";
        after = after.replace("THEREEXISTS", booleanVar);
        after = after.replace("OTHER", booleanVars.pop());
        
        generated += after;
        
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitNotNode(NotNode node) {
        node.getNegatedExpNode().getVisited(this);
        String code = "unsigned int BOOL = !OTHER";
        String booleanVar = "not_" + varCounter++;
        code = code.replace("BOOL", booleanVar);
        code = code.replace("OTHER", booleanVars.pop());
        generated += code;
        
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitComparisonNode(ComparisonNode node) {
        String booleanVar = "compare_" + varCounter++;
        InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
        String code = "unsigned int BOOL = 1;\n";
        code = code.replace("BOOL", booleanVar);
        ArrayList<String> counter = new ArrayList<>();
        
        while(cont.isList()) {
            String max = "";
            String countingVar = "count_" + varCounter++;
            counter.add(countingVar);
            if(cont.getAccesTypeIfList() == InternalTypeRep.VOTER) {
                max = "V";
            } else if(cont.getAccesTypeIfList() == InternalTypeRep.CANDIDATE) {
                max = "C";
            } else if(cont.getAccesTypeIfList() == InternalTypeRep.SEAT) {
                max = "S";
            }
            String loop =  "for(unsigned int VAR = 0; VAR < MAX && BOOL; ++var) {\n";
            loop = loop.replace("VAR", countingVar);
            loop = loop.replace("MAX", max);
            loop = loop.replace("BOOL", booleanVar);
            code += loop;
            cont = cont.getListedType();
        }
        
        node.getLHSBooleanExpNode().getVisited(this);
        node.getRHSBooleanExpNode().getVisited(this);
        
        String rhs = expString.pop();
        String lhs = expString.pop();
        
        code += booleanVar + " = " + rhs;
        for(String s : counter) code += "[VAR]".replace("VAR", s);
        code += " " + node.getComparisonSymbol().getCStringRep() + " ";
        code += lhs;
        for(String s : counter) code += "[VAR]".replace("VAR", s);
        code += ";\n";
        
        generated += code;
        
        
        if(booleanVars.empty()) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitSymbVarExp(SymbolicVarExp exp) {        
        expString.add(exp.getSymbolicVar().getId());
    }

    @Override
    public void visitConstExp(ConstantExp exp) {
        expString.add(exp.getConstant());
    }

    @Override
    public void visitElectExp(ElectExp exp) {
        String code = "electNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        SymbolicVariable[] accessVars =  exp.getAccessVar();
        for(int i = 0; i < accessVars.length; ++i) {
            code += "[VAR]".replace("VAR", accessVars[i].getId());
        }
        expString.add(code);
    }

    @Override
    public void visitVoteExp(VoteExp exp) {
        String code = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        SymbolicVariable[] accessVars =  exp.getAccessVar();
        for(int i = 0; i < accessVars.length; ++i) {
            code += "[VAR]".replace("VAR", accessVars[i].getId());
        }
        expString.add(code);
    }

    @Override
    public void visitVoteSumExp(VoteSumForCandExp exp) {
        expString.add("calculateVoteSum(VAR)".replace("VAR", exp.getSymbolicVariable().getId()));
    }
    
    @Override
    public void visitNumberExpNode(NumberExpression exp) {
        
        expString.add(String.valueOf(exp.getNumber()));
    }

    void setPreProperties() {
        asString = "assume";
    }

    void setPostProperties() {
        asString = "assert";
    }
    
}
