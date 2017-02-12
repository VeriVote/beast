/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr;

import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This class provides easy access to a parsetree of the currently
 * displayed code in the supplied JTextPane. It uses an antlr C grammar
 * @author Holger-Desktop
 */
public class CAntlrHandler {
    private JTextPane pane;
    private CLexer lexer;
    private CParser cParser;
    
    public CAntlrHandler(JTextPane pane) {
        this.pane = pane;
        lexer = new CLexer(new ANTLRInputStream(pane.getText()));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        cParser = new CParser(commonTokenStream);
    }
    
    public ParseTree getCParseTree() {
        try {
            String code = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            lexer.setInputStream(new ANTLRInputStream(code));
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            cParser.setTokenStream(commonTokenStream);
            return cParser.compilationUnit();
        } catch (BadLocationException ex) {
            Logger.getLogger(CAntlrHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public void updateParser() {
        try {
            String code = pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            lexer.setInputStream(new ANTLRInputStream(code));
            CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            cParser.setTokenStream(commonTokenStream);
        } catch (BadLocationException ex) {
            Logger.getLogger(CAntlrHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public CParser getParser() {
        return cParser;
    }
    
    public String[] getTypeLiterals() {
        return new String []
                {"void", "char", "short", "int", "long", "float", "double",
                    "signed", "unsigned", "_Bool", "_Complex", "__m128",
                    "__m128d", "__m128i"};
    }
    
    public String[] getControllLiterals() {
        return new String []
                {"if", "else", "do", "while", "break", "switch", "continue",
                    "default", "case", "return", "for"};
    }

    public String getStringRegex() {
        return "\"([^\"^\\\\]|\\\\(.))*\"";
    }

    // http://blog.ostermiller.org/find-comment
    public String getCommentRegex() {
        return "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)";
    }
}

