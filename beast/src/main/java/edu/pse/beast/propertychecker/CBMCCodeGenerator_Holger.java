/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.propertychecker;

import edu.pse.beast.datatypes.booleanExpAST.BooleanExpListNode;
import edu.pse.beast.datatypes.booleanExpAST.BooleanExpressionNode;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariable;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.BooleanExpScope;
import edu.pse.beast.toolbox.antlr.booleanexp.GenerateAST.FormalPropertySyntaxTreeToAstTranslater;
import java.util.ArrayList;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 *
 * @author Holger-Desktop
 */
public class CBMCCodeGenerator_Holger {
    
    private String generatedCode;
    private PostAndPrePropertiesDescription properties;
    private ElectionDescription electionDescription;
    private CBMCCodeGeneratioonVisitor visitor;
    private FormalPropertySyntaxTreeToAstTranslater translater = new FormalPropertySyntaxTreeToAstTranslater();
    
    private BooleanExpListNode generateAST(String code) {
        FormalPropertyDescriptionLexer l = new FormalPropertyDescriptionLexer(new ANTLRInputStream(code));
        CommonTokenStream ts = new CommonTokenStream(l);
        FormalPropertyDescriptionParser p = new FormalPropertyDescriptionParser(ts);
        
        BooleanExpScope declaredVars = new BooleanExpScope();
        
        for(SymbolicVariable v : properties.getSymbolicVariableList()) {
            declaredVars.addTypeForId(v.getId(), v.getInternalTypeContainer());
        }
        
        return  translater.generateFromSyntaxTree(
                p.booleanExpList(),
                electionDescription.getInputType().getType(), 
                electionDescription.getOutputType().getType(),
                declaredVars);
    }
    
    public String generateCode(PostAndPrePropertiesDescription properties, ElectionDescription electionDescription) {
        generatedCode = "";
        
        this.properties = properties;
        this.electionDescription = electionDescription;
        String testFuncName = properties.getName();
        ElectionTypeContainer input = electionDescription.getInputType();
        ElectionTypeContainer output = electionDescription.getOutputType();
        
        generateHeader();
        
        for(String s : electionDescription.getCode()) {
            generatedCode += s + "\n";
        }
        
        String funcDecl = "void NAME() {\n".replace("NAME", testFuncName);
        
        generatedCode += funcDecl;
        
        for(SymbolicVariable v : properties.getSymbolicVariableList()) {
            String max = "";
            if(v.getInternalTypeContainer().getInternalType() == InternalTypeRep.VOTER) {
                max = "V";
            } else if(v.getInternalTypeContainer().getInternalType() == InternalTypeRep.CANDIDATE) {
                max = "C";
            } else if(v.getInternalTypeContainer().getInternalType() == InternalTypeRep.SEAT) {
                max = "S";
            }
            String varDecl = "unsigned int VAR;\n" +
                            "assume(0 <= VAR <= MAX);\n";
            varDecl = varDecl.replaceAll("MAX", max);
            varDecl = varDecl.replaceAll("VAR", v.getId());
            generatedCode += varDecl;
        }       
                    
        BooleanExpListNode preast = generateAST(properties.getPrePropertiesDescription().getCode());
        BooleanExpListNode postast = generateAST(properties.getPostPropertiesDescription().getCode());
        
        int maxVote = preast.getMaxVoteLevel() > postast.getMaxVoteLevel() ? preast.getMaxVoteLevel() : postast.getMaxVoteLevel();
        maxVote = preast.getHighestElect() > maxVote ? preast.getHighestElect() : maxVote;
        maxVote = postast.getHighestElect() > maxVote ? postast.getHighestElect() : maxVote;
        
        for(int i = 1; i <= maxVote; ++i) {
            generatedCode += "unsigned int VOTES[V];\n".replace("VOTES", "votes" + i);
        }
        
        for(int i = 1; i <= maxVote; ++i) {
            String voteStr = "for(unsigned int i = 0; i < V; ++i) {\n" +
                                "VOTES[i] = nondet_uint();\n" +
                                    "}\n";
            voteStr = voteStr.replaceAll("VOTES", "votes" + i);
            generatedCode += voteStr;
        }
        
        visitor = new CBMCCodeGeneratioonVisitor();
        visitor.setPreProperties();
        
        
        generatedCode += visitor.generate(preast.getBooleanExpressions().get(0));
        
        int i = 1;
        int highestElect = preast.getHighestElect();
        for(; i <= highestElect; ++i) {
            String code = "unsigned int ELECT = voting(VOTES, CANDIDATES, SEATS);\n";
            code = code.replace("ELECT", "elect" + i);
            code = code.replace("VOTES", "votes" + i);
            code = code.replace("CANDIDATES", "candidates");
            code = code.replace("SEATS", "seats");
            generatedCode += code;
            ArrayList<BooleanExpressionNode> list = preast.getBooleanExpressions().get(i);
            String generated = visitor.generate(list);
            generatedCode += generated;
        }
        
        
        for(; i <= postast.getHighestElect(); ++i) {
            String code = "unsigned int ELECT = voting(VOTES, CANDIDATES, SEATS);\n";
            code = code.replace("ELECT", "elect" + i);
            code = code.replace("VOTES", "votes" + i);
            code = code.replace("CANDIDATES", "candidates");
            code = code.replace("SEATS", "seats");
            generatedCode += code;
        }
        
        visitor.setPostProperties();
        
        for(ArrayList<BooleanExpressionNode> list : postast.getBooleanExpressions()) {
            generatedCode += visitor.generate(list);
        }
        
        generatedCode += "}\n";
        
        generateMain();
        
        return generatedCode;
    }

    private void generateHeader() {
        String header = "#include <stdlib.h>\n#include <stdint.h>\n#include <assert.h>" +
                            "int nondet_uint();\n" +
                            "#define assert2(x, y) __CPROVER_assert(x, y)\n" +
                "#define assume(x) __CPROVER_assume(x)" +
                "#ifndef V\n#define V 3\n#endif\n\n" + 
                "#ifndef C\n#define C 3\n #endif\n\n" +
               "#ifndef S\n#define S 3\n #endif\n\n";       
        generatedCode += header;

    }

    private void generateMain() {
        String main = "int main() {\n" + 
                properties.getName() + "();\n" +
                "return 0;\n"+ 
                "}\n";
        generatedCode += main;
    }

}
