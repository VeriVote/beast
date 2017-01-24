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
    
    public ArrayList<String> getTypeLiterals() {
        String[] created = {};
        return Arrays.asList(created);
    }
}
