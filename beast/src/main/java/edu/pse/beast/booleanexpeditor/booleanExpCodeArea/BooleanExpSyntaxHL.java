package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.codearea.SyntaxHL.RegexAndColor;
import edu.pse.beast.codearea.SyntaxHL.SyntaxHL;
import java.awt.*;
import java.util.ArrayList;

/**
 * Class that updates the DocumentFilter of the JTextPanes StyledDocuments in BooleanExpEditor responsible for
 * syntax highlighting.
 * Needs to be refactored if custom colors for syntax highlighting should be available.
 * @author NikolaiLMS
 */
public class BooleanExpSyntaxHL {

    /**
     * Constructor
     * @param antlrHandler the BooleanExpANTLRHandler, provides regular expressions StringLists.
     * @param syntaxHL the BooleanExpEditorCodeAreas SyntaxHL class.
     */
    public BooleanExpSyntaxHL(BooleanExpANTLRHandler antlrHandler, SyntaxHL syntaxHL) {

        ArrayList<RegexAndColor> regexAndColorList = new ArrayList<>();
        for(String s : antlrHandler.getComparisonSymbols()) {
            regexAndColorList.add(new RegexAndColor(s, Color.RED));
        }
        for(String s : antlrHandler.getLogicalOperators()) {
            regexAndColorList.add(new RegexAndColor(s, Color.RED));
        }
        for(String s : antlrHandler.getMakroRegex()) {
            regexAndColorList.add(new RegexAndColor(s, Color.BLUE));
        }
        regexAndColorList.add(new RegexAndColor("(ELECT[0-9]+\\([a-zA-Z_]*\\))|(VOTES[0-9]+\\([a-zA-Z_]*\\))",
                Color.GREEN));

        syntaxHL.updateFilter(regexAndColorList);
    }
}
