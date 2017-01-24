/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr;

import java.util.ArrayList;
import java.util.Arrays;
import javax.swing.JTextPane;
import org.antlr.v4.parse.ANTLRParser;
import org.antlr.v4.runtime.ANTLRFileStream;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 *
 * @author Holger-Desktop
 */
public class CAntlrHandler {
    private JTextPane pane;
    private CLexer lexer;
    private CParser cParser;
    
    public CAntlrHandler(JTextPane pane) {
        System.out.println(pane == null);
        this.pane = pane;
        lexer = new CLexer(new ANTLRInputStream(pane.getText()));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        cParser = new CParser(commonTokenStream);
    }
    
    public ParseTree getCParseTree() {
        lexer.setInputStream(new ANTLRInputStream(pane.getText()));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        cParser.setTokenStream(commonTokenStream);
        return cParser.primaryExpression();
    }
    
    public CParser getParser() {
        return cParser;
    }
    
    public String[] getTypeLiterals() {
        String literals[] =
                {"void", "char", "short", "int", "long", "float", "double",
                    "signed", "unsigned", "_Bool", "_Complex", "__m128",
                    "__m128d", "__m128i"};
        return literals;
    }
    
    public String[] getControllLiterals() {
        String literals[] =
                {"if", "else", "do", "while", "break", "switch", "continue",
                    "default", "case", "return"};
        return literals;
    }
}

