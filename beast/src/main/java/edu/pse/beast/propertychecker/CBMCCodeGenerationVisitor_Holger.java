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
public class CBMCCodeGenerationVisitor_Holger implements BooleanExpNodeVisitor{

    private String generated;
    private String asString;
    private Stack<String> booleanVars;
    private int varCounter = 0;
    private Stack<String> expString;
    private int count;
    private int lhslistLevel;
    private int rhslistLevel;
    private boolean lhsVisited;
    
    public String generate(ArrayList<BooleanExpressionNode> nodes) {
        generated = "";
        booleanVars = new Stack<>();
        expString = new Stack<>();
        count = 0;
        for(BooleanExpressionNode n : nodes) {
            booleanVars.clear();
            n.getVisited(this);
        }
        
        return generated;
    }
    


    @Override
    public void visitAndNode(LogicalAndNode node) {
        ++count;
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
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitOrNode(LogicalOrNode node) {
         ++count;
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
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitImplicationNode(ImplicationNode node) {
         ++count;
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
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitAquivalencyNode(EquivalencyNode node) {
         ++count;
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
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitForAllNode(ForAllNode node) {
         ++count;
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
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitThereExistsNode(ThereExistsNode node) {
         ++count;
        String before = "unsigned int THEREEXISTS = 0;\n"  +
                "for(unsigned int VAR = 0; VAR < MAX && !THEREEXISTS; ++VAR) {\n";
        
        String booleanVar = "existsOne_" + varCounter++;
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
        before = before.replace("VAR", node.getDeclaredSymbolicVar().getId());
        before = before.replace("MAX", max);    
        
        generated += before;
        
        node.getFollowingExpNode().getVisited(this);
        
        String after = "THEREEXISTS = OTHER;\n" + 
                            "}\n";
        after = after.replace("THEREEXISTS", booleanVar);
        after = after.replace("OTHER", booleanVars.pop());
        
        generated += after;
        
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    @Override
    public void visitNotNode(NotNode node) {
         ++count;
        node.getNegatedExpNode().getVisited(this);
        String code = "unsigned int BOOL = !OTHER";
        String booleanVar = "not_" + varCounter++;
        code = code.replace("BOOL", booleanVar);
        code = code.replace("OTHER", booleanVars.pop());
        generated += code;
        
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
        booleanVars.add(booleanVar);
    }

    
    @Override
    public void visitComparisonNode(ComparisonNode node) {
         ++count;
        String booleanVar = "compare_" + varCounter++;
        
        this.lhslistLevel = 0;
        this.rhslistLevel = 0;
        InternalTypeContainer cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
        while(cont.isList()) {
            this.lhslistLevel++;
            cont = cont.getListedType();
        }
        cont = node.getRHSBooleanExpNode().getInternalTypeContainer();
        while(cont.isList()) {
            this.rhslistLevel++;
            cont = cont.getListedType();
        }
        
        lhsVisited = false;
        node.getLHSBooleanExpNode().getVisited(this);
        lhsVisited = true;
        node.getRHSBooleanExpNode().getVisited(this);
        int maxListLevel = lhslistLevel > rhslistLevel ? lhslistLevel : rhslistLevel;
        cont = lhslistLevel > rhslistLevel ? node.getLHSBooleanExpNode().getInternalTypeContainer() : node.getRHSBooleanExpNode().getInternalTypeContainer();
        
        String code = "unsigned int BOOL = 1;\n";
        code = code.replace("BOOL", booleanVar);
        ArrayList<String> counter = new ArrayList<>();
        
        for(int i = 0; i < maxListLevel; ++i) {
            String max = "";
            String countingVar = "count_" + varCounter++;
            counter.add(countingVar);
            if(null != cont.getAccesTypeIfList()) switch (cont.getAccesTypeIfList()) {
                 case VOTER:
                     max = "V";
                     break;
                 case CANDIDATE:
                     max = "C";
                     break;
                 case SEAT:
                     max = "S";
                     break;
                 default:
                     break;
             }
            String loop =  "for(unsigned int VAR = 0; VAR < MAX && BOOL; ++VAR) {\n";
            loop = loop.replaceAll("VAR", countingVar);
            loop = loop.replaceAll("MAX", max);
            loop = loop.replaceAll("BOOL", booleanVar);
            code += loop;
        }
        
        
        String rhs = expString.pop();
        String lhs = expString.pop();
        
        code += booleanVar + " = " + rhs;
        for(int i = 0; i < lhslistLevel; ++i) code += "[VAR]".replace("VAR", counter.get(i));
        code += " " + node.getComparisonSymbol().getCStringRep() + " ";
        code += lhs;
        for(int i = 0; i < rhslistLevel; ++i) code += "[VAR]".replace("VAR", counter.get(i));
        code += ";\n";
        
        cont = node.getLHSBooleanExpNode().getInternalTypeContainer();
        for(int i = 0; i < maxListLevel; ++i) {
            code += "}\n";
        }
        
        generated += code;
        
        --count;
        if(count == 0) generated += asString + "(BOOL);\n".replace("BOOL", booleanVar);
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
            if(lhsVisited) {
                rhslistLevel--;
            } else {
                lhslistLevel--;                
            }
        }
        expString.add(code);
    }

    @Override
    public void visitVoteExp(VoteExp exp) {
        String code = "votesNUMBER".replace("NUMBER", String.valueOf(exp.getCount()));
        SymbolicVariable[] accessVars =  exp.getAccessVar();
        for(int i = 0; i < accessVars.length; ++i) {
            code += "[VAR]".replace("VAR", accessVars[i].getId());
            if(lhsVisited) {
                rhslistLevel--;
            } else {
                lhslistLevel--;                
            }
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
