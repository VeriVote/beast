package edu.pse.beast.celectiondescriptioneditor.celectioncodearea.antlr;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;
import org.antlr.v4.runtime.tree.ParseTree;

/**
 * This class provides easy access to a parsetree of the currently displayed
 * code in the supplied JTextPane. It uses an antlr C grammar
 *
 * @author Holger Klein
 */
public class CAntlrHandler {

    /** The pane. */
    private JTextPane pane;

    /** The lexer. */
    private CLexer lexer;

    /** The c parser. */
    private CParser cParser;

    /**
     * Constructor.
     *
     * @param textPane
     *            the pane to hold the handler
     */
    public CAntlrHandler(final JTextPane textPane) {
        this.pane = textPane;
        lexer = new CLexer(CharStreams.fromString(textPane.getText()));
        final CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
        cParser = new CParser(commonTokenStream);
    }

    /**
     * Gives the parse tree.
     *
     * @return the parse tree
     */
    public ParseTree getCParseTree() {
        try {
            final String code =
                    pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            lexer.setInputStream(CharStreams.fromString(code));
            final CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            cParser.setTokenStream(commonTokenStream);
            return cParser.compilationUnit();
        } catch (BadLocationException ex) {
            Logger.getLogger(CAntlrHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /**
     * Updates the parser.
     */
    public void updateParser() {
        try {
            final String code =
                    pane.getStyledDocument().getText(0, pane.getStyledDocument().getLength());
            lexer.setInputStream(CharStreams.fromString(code));
            final CommonTokenStream commonTokenStream = new CommonTokenStream(lexer);
            cParser.setTokenStream(commonTokenStream);
        } catch (BadLocationException ex) {
            Logger.getLogger(CAntlrHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Gets the parser.
     *
     * @return the parser
     */
    public CParser getParser() {
        return cParser;
    }

    /**
     * Gets the type literals.
     *
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
     * Gets the controll literals.
     *
     * @return the string of control literals
     */
    public String[] getControllLiterals() {
        return new String[] {
            "if", "else", "do", "while", "break", "switch",
            "continue", "default", "case", "return", "for"
        };
    }

    /**
     * Gets the string regex.
     *
     * @return the string regex
     */
    public String getStringRegex() {
        return "\"([^\"^\\\\]|\\\\(.))*\"";
    }

    // http://blog.ostermiller.org/find-comment
    /**
     * Gets the comment regex.
     *
     * @return a regex that searches for comments
     */
    public String getCommentRegex() {
        return "(/\\*([^*]|[\\r\\n]|(\\*+([^*/]|[\\r\\n])))*\\*+/)|(//.*)";
    }
}