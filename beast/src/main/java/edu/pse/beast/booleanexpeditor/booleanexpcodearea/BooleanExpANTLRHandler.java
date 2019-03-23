package edu.pse.beast.booleanexpeditor.booleanexpcodearea;

import org.antlr.v4.runtime.ANTLRInputStream;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

/**
 * Class that uses precompiled ANTLR classes to analyse the code in styledDocument.
 * Used by BooleanExpEditorVariableErrorFinder and BooleanExpEditorGrammarErrorFinder.
 * @author Nikolai
 */
public class BooleanExpANTLRHandler {
    private String input;
    private FormalPropertyDescriptionLexer lexer;
    private FormalPropertyDescriptionParser parser;

    /**
     * Constructor
     * @param styledDocument the StyledDocument instance to analyze
     */
    public BooleanExpANTLRHandler(String input) {
        this.input = input;
        lexer = new FormalPropertyDescriptionLexer(new ANTLRInputStream(input));
		CommonTokenStream ts = new CommonTokenStream(lexer);
		parser = new FormalPropertyDescriptionParser(ts);
    }

    /**
     * Method that parses the current input of a BooleanExpCodeArea and returns a
     * FormalPropertyDescriptionParser.BooleanExpListContext object which can then be used for building an AST
     * out of the input.
     * @return a BooleanExpListContext node from the ANTLR generated ParseTree.
     */
    public FormalPropertyDescriptionParser.BooleanExpListContext getParseTree() {
        lexer.setInputStream(new ANTLRInputStream(input));
        CommonTokenStream ts = new CommonTokenStream(lexer);
        parser.setTokenStream(ts);
        return parser.booleanExpList();
    }

    /**
     * Getter
     * @return the FormalPropertyDescriptionParser object
     */
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
                { "VOTER_AT_POS\\([0-9]+\\)", "CAND_AT_POS\\([0-9]+\\)", "SEAT_AT_POS\\([0-9]+\\)",
                        "FOR_ALL_VOTERS\\([a-zA-Z_]*\\)", "FOR_ALL_CANDIDATES\\([a-zA-Z_]*\\)",
                        "FOR_ALL_SEATS\\([a-zA-Z_]*\\)", "EXISTS_ONE_VOTER\\([a-zA-Z_]*\\)",
                        "EXISTS_ONE_CANDIDATE\\([a-zA-Z_]*\\)", "EXISTS_ONE_SEAT\\([a-zA-Z_]*\\)",
                        "VOTE_SUM_FOR_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)",
                        "VOTE_SUM_FOR_UNIQUE_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)"};
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