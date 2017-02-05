package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

import java.util.ArrayList;
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
    
    public FormalPropertyDescriptionParser.BooleanExpListContext getParseTree() throws BadLocationException {
        String text= styledDocument.getText(0, styledDocument.getLength());
        lexer.setInputStream(new ANTLRInputStream(text));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser.setTokenStream(ts);
        return parser.booleanExpList();
    }
    
    public FormalPropertyDescriptionParser getParser() {
        return parser;
    }

    public String[] getMakroRegex() {
        return new String[]
                {"FOR_ALL_VOTERS\\([a-zA-Z_]*\\)", "FOR_ALL_CANDIDATES\\([a-zA-Z_]*\\)",
                        "FOR_ALL_SEATS\\([a-zA-Z_]*\\)", "EXISTS_ONE_VOTER\\([a-zA-Z_]*\\)",
                        "EXISTS_ONE_CANDIDATE\\([a-zA-Z_]*\\)", "EXISTS_ONE_SEAT\\([a-zA-Z_]*\\)",
                        "VOTE_SUM_FOR_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)"};
    }

    public String[] getComparisonSymbols() {
        return new String[]
                {"==", "!=", ">=", "<=", ">", "<"};
    }

    public String[] getLogicalOperators() {
        return new String[]
                {"==>", "<==>", "&&", "||"};
    }
    public String getConstants() {
        return "(V|C|S)";
    }
}
