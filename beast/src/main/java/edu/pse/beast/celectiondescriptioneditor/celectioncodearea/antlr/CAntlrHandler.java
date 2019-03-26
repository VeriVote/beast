package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This class provides easy access to a parsetree of the currently displayed
 * code in the supplied JTextPane. It uses an antlr C grammar
 *
 * @author Holger Klein
 */
public class CAntlrHandler {
    private JTextPane pane;
    private CLexer lexer;
    private CParser cParser;

    /**
     * constructor
     *
     * @param pane the pane to hold the handler
     */
    public CAntlrHandler(JTextPane pane) {
        this.pane = pane;
        lexer = new CLexer(new ANTLRInputStream(pane.getText()));
        CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        cParser = new CParser(commonTokenStream);
    }

    /**
     * gives the parse tree
     *
     * @return the parse tree
     */
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

    /**
     * updates the parser
     */
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

    /**
     * @return the parser
     */
    public CParser getParser() {
        return cParser;
    }

    /**
     * @return the string of type literals
     */
    public String[] getTypeLiterals() {
        return new String[] {
            "void", "char", "short", "int", "long", "float",
            "double", "signed", "unsigned", "_Bool",
            "_Complex", "__m128", "__m128d", "__m128i"
        };
    }

    /**
     * @return the string of control literals
     */
    public String[] getControllLiterals() {
        return new String[] {
            "if", "else", "do", "while", "break", "switch",
            "continue", "default", "case", "return",
            "for"
        };
    }

    /**
     * @return the string regex
     */
    public String getStringRegex() {
        return "\"([^\"^\\\\]|\\\\(.))*\"";
    }

    // http://blog.ostermiller.org/find-comment
    /**
     * @return a regex that searches for comments
     */
    public String getCommentRegex() {
        return "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)";
    }
}