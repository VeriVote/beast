package edu.pse.beast.booleanexpeditor.booleanexpcodearea;

import java.awt.Color;
import java.util.ArrayList;

import edu.pse.beast.codearea.syntaxhighlighting.RegexAndColor;
import edu.pse.beast.codearea.syntaxhighlighting.SyntaxHL;

/**
 * Class that updates the DocumentFilter of the JTextPanes StyledDocuments in
 * BooleanExpEditor responsible for syntax highlighting. Needs to be refactored
 * if custom colors for syntax highlighting should be available.
 *
 * @author Nikolai Schnell
 */
public class BooleanExpSyntaxHL {

    /** The Constant REGEX. */
    private static final String REGEX =
            "(ELECT[0-9]+\\([a-zA-Z_]*\\))|(VOTES[0-9]+\\([a-zA-Z_]*\\))";

    /**
     * Constructor.
     *
     * @param antlrHandler the BooleanExpANTLRHandler, provides regular expressions
     *                     StringLists.
     * @param syntaxHL     the BooleanExpEditorCodeAreas SyntaxHL class.
     */
    public BooleanExpSyntaxHL(final BooleanExpANTLRHandler antlrHandler,
                              final SyntaxHL syntaxHL) {
        ArrayList<RegexAndColor> regexAndColorList = new ArrayList<>();
        for (String s : antlrHandler.getComparisonSymbols()) {
            regexAndColorList.add(new RegexAndColor(s, Color.RED));
        }
        for (String s : antlrHandler.getLogicalOperators()) {
            regexAndColorList.add(new RegexAndColor(s, Color.RED));
        }
        for (String s : antlrHandler.getMacroRegex()) {
            regexAndColorList.add(new RegexAndColor(s, Color.BLUE));
        }
        regexAndColorList.add(
                new RegexAndColor(REGEX, Color.GREEN.darker()));
        syntaxHL.updateFilter(regexAndColorList);
    }
}
