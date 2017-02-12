package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.text.BadLocationException;
import javax.swing.text.StyledDocument;
import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

/**
 * Class that uses precompiled ANTLR classes to analyse the code in styledDocument.
 * Used by BooleanExpEditorVariableErrorFinder and BooleanExpEditorGrammarErrorFinder.
 * @author Nikolai
 */
public class BooleanExpANTLRHandler {
    private StyledDocument styledDocument;
    private FormalPropertyDescriptionLexer lexer;
    private FormalPropertyDescriptionParser parser;

    /**
     * Constructor
     * @param styledDocument the StyledDocument instance to analyse
     */
    public BooleanExpANTLRHandler(StyledDocument styledDocument) {
        try {
            this.styledDocument = styledDocument;
            lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(styledDocument.getText(0, styledDocument.getLength())));
            CommonTokenStream ts = new CommonTokenStream(lexer);
            parser = new FormalPropertyDescriptionParser(ts);
        } catch (BadLocationException ex) {
            Logger.getLogger(BooleanExpANTLRHandler.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Method that parses the current input of a BooleanExpCodeArea and returns a
     * FormalPropertyDescriptionParser.BooleanExpListContext object which can then be used for building an AST
     * out of the input.
     * @return a BooleanExpListContext node from the ANTLR generated ParseTree.
     */
    public FormalPropertyDescriptionParser.BooleanExpListContext getParseTree() {
        String text = null;
        try {
            text = styledDocument.getText(0, styledDocument.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
        lexer.setInputStream(new ANTLRInputStream(text));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser.setTokenStream(ts);
        return parser.booleanExpList();
    }
    
    public FormalPropertyDescriptionParser getParser() {
        return parser;
    }

    /**
     * Method that
     * @return s a String array of regular expressions matching all possible makros.
     * Used for SyntaxHL.
     */
    public String[] getMakroRegex() {
        return new String[]
                {"FOR_ALL_VOTERS\\([a-zA-Z_]*\\)", "FOR_ALL_CANDIDATES\\([a-zA-Z_]*\\)",
                        "FOR_ALL_SEATS\\([a-zA-Z_]*\\)", "EXISTS_ONE_VOTER\\([a-zA-Z_]*\\)",
                        "EXISTS_ONE_CANDIDATE\\([a-zA-Z_]*\\)", "EXISTS_ONE_SEAT\\([a-zA-Z_]*\\)",
                        "VOTE_SUM_FOR_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)"};
    }

    /**
     * Method that
     * @return s a String array of regular expressions matching all possible comparison symbols.
     * Used for SyntaxHL.
     */
    public String[] getComparisonSymbols() {
        return new String[]
                {"==", "!=", ">=", "<=", ">", "<"};
    }

    /**
     * Method that
     * @return s a String array of regular expressions matching all possible logical operators.
     * Used for SyntaxHL.
     */
    public String[] getLogicalOperators() {
        return new String[]
                {"==>", "<==>", "&&", "||"};
    }

    /**
     * Method that
     * @return s a String array of regular expressions matching all possible constants.
     * Used for SyntaxHL.
     */
    public String getConstants() {
        return "(V|C|S)";
    }
}
