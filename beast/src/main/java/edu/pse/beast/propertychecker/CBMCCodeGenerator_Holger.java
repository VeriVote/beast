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
        
        visitor = new CBMCCodeGeneratioonVisitor();
        visitor.setPreProperties();
        
        BooleanExpListNode ast = generateAST(properties.getPrePropertiesDescription().getCode());
        
        generatedCode += visitor.generate(ast.getBooleanExpressions().get(0));
        
        int i = 1;
        int highestElect = ast.getHighestElect();
        for(; i <= highestElect; ++i) {
            String code = "unsigned int ELECT = voting(VOTES, CANDIDATES, SEATS);\n";
            code = code.replace("ELECT", "elect" + i);
            code = code.replace("VOTES", "votes" + i);
            code = code.replace("CANDIDATES", "candidates");
            code = code.replace("SEATS", "seats");
            generatedCode += code;
            ArrayList<BooleanExpressionNode> list = ast.getBooleanExpressions().get(i);
            String generated = visitor.generate(list);
            generatedCode += generated;
        }
        
        ast = generateAST(properties.getPostPropertiesDescription().getCode());
        
        for(; i <= ast.getHighestElect(); ++i) {
            String code = "unsigned int ELECT = voting(VOTES, CANDIDATES, SEATS);\n";
            code = code.replace("ELECT", "elect" + i);
            code = code.replace("VOTES", "votes" + i);
            code = code.replace("CANDIDATES", "candidates");
            code = code.replace("SEATS", "seats");
            generatedCode += code;
        }
        
        visitor.setPostProperties();
        
        for(ArrayList<BooleanExpressionNode> list : ast.getBooleanExpressions()) {
            generatedCode += visitor.generate(list);
        }
        
        return generatedCode;
    }

}
