/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CBaseListener;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CBaseVisitor;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CParser;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import java.util.ArrayList;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Holger-Desktop
 */
public class CVariableErrorFinder extends CBaseListener implements ErrorFinder {

    private CAntlrHandler antlrHandler;
    private ArrayList<CodeError> foundErrors = new ArrayList<>();
    private CScopeHandler scopeHandler = new CScopeHandler();
    public CVariableErrorFinder(CAntlrHandler antlrHandler) {
        this.antlrHandler = antlrHandler;
    }
    
    @Override
    public ArrayList<CodeError> getErrors() {
        foundErrors.clear();
        scopeHandler = new CScopeHandler();
        scopeHandler.enterScope();
        ParseTree parsetree = antlrHandler.getCParseTree();
        ParseTreeWalker walker = new ParseTreeWalker();
        walker.walk(this, parsetree);
        return foundErrors;
    }
    
    @Override 
    public void exitAssignmentExpression(CParser.AssignmentExpressionContext ctx) { 
       
    }
    
    public void exitDeclaration(CParser.DeclarationContext ctx) {
        String[] type = getDeclarationType(ctx);
    }
    
    public String[] getDeclarationType(CParser.DeclarationContext ctx) {
        String type[] = new String[ctx.declarationSpecifiers().declarationSpecifier().size()];
        int i = 0;
        for(CParser.DeclarationSpecifierContext declSpec : ctx.declarationSpecifiers().declarationSpecifier()) {
            type[i] = declSpec.typeSpecifier().getText();
            ++i;
        }
        return type;
    }

    
}
