package edu.pse.beast.booleanexpeditor.booleanexpcodearea;

import org.antlr.v4.runtime.CharStreams;
import org.antlr.v4.runtime.CommonTokenStream;

import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionLexer;
import edu.pse.beast.toolbox.antlr.booleanexp.FormalPropertyDescriptionParser;

/**
 * Class that uses precompiled ANTLR classes to analyse the code in
 * styledDocument. Used by BooleanExpEditorVariableErrorFinder and
 * BooleanExpEditorGrammarErrorFinder.
 *
 * @author Nikolai Schnell
 */
public class BooleanExpANTLRHandler {

    /** The input. */
    private String input;

    /** The lexer. */
    private FormalPropertyDescriptionLexer lexer;

    /** The parser. */
    private FormalPropertyDescriptionParser parser;

    /**
     * Constructor.
     *
     * @param inputString
     *            the StyledDocument instance to analyze
     */
    public BooleanExpANTLRHandler(final String inputString) {
        this.input = inputString;
        lexer = new FormalPropertyDescriptionLexer(CharStreams.fromString(inputString));
        final CommonTokenStream ts = new CommonTokenStream(lexer);
        parser = new FormalPropertyDescriptionParser(ts);
    }

    /**
     * Method that parses the current input of a BooleanExpCodeArea and returns
     * a FormalPropertyDescriptionParser.BooleanExpListContext object which can
     * then be used for building an AST out of the input.
     *
     * @return a BooleanExpListContext node from the ANTLR generated ParseTree.
     */
    public FormalPropertyDescriptionParser.BooleanExpListContext getParseTree() {
        lexer.setInputStream(CharStreams.fromString(input));
        final CommonTokenStream ts = new CommonTokenStream(lexer);
        parser.setTokenStream(ts);
        return parser.booleanExpList();
    }

    /**
     * Getter for the parser.
     *
     * @return the FormalPropertyDescriptionParser object
     */
    public FormalPropertyDescriptionParser getParser() {
        return parser;
    }

    /**
     * Method that gets the macro regular expressions.
     *
     * @return s a String array of regular expressions matching all possible
     *         macros. Used for SyntaxHL.
     */
    public String[] getMacroRegex() {
        return new String[] {
            "VOTER_AT_POS\\([0-9]+\\)",
            "CAND_AT_POS\\([0-9]+\\)",
            "SEAT_AT_POS\\([0-9]+\\)",
            "FOR_ALL_VOTERS\\([a-zA-Z_]*\\)",
            "FOR_ALL_CANDIDATES\\([a-zA-Z_]*\\)",
            "FOR_ALL_SEATS\\([a-zA-Z_]*\\)",
            "EXISTS_ONE_VOTER\\([a-zA-Z_]*\\)",
            "EXISTS_ONE_CANDIDATE\\([a-zA-Z_]*\\)",
            "EXISTS_ONE_SEAT\\([a-zA-Z_]*\\)",
            "VOTE_SUM_FOR_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)",
            "VOTE_SUM_FOR_UNIQUE_CANDIDATE[0-9]+\\([a-zA-Z_]*\\)"
        };
    }

    /**
     * Method that gets the comparison symbols.
     *
     * @return s a String array of regular expressions matching all possible
     *         comparison symbols. Used for SyntaxHL.
     */
    public String[] getComparisonSymbols() {
        return new String[] {"==", "!=", ">=", "<=", ">", "<"};
    }

    /**
     * Method that gets the logical operators.
     *
     * @return s a String array of regular expressions matching all possible
     *         logical operators. Used for SyntaxHL.
     */
    public String[] getLogicalOperators() {
        return new String[] {"==>", "<==>", "&&", "||"};
    }

    /**
     * Method that gets the constants.
     *
     * @return s a String array of regular expressions matching all possible
     *         constants. Used for SyntaxHL.
     */
    public String getConstants() {
        return "(V|C|S)";
    }
}
