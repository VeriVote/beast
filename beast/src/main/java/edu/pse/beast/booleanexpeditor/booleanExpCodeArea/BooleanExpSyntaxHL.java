package edu.pse.beast.booleanexpeditor.booleanExpCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.codearea.SyntaxHL.RegexAndColor;
import edu.pse.beast.codearea.SyntaxHL.SyntaxHL;

import java.awt.*;
import java.util.ArrayList;

/**
 * @author NikolaiLMS
 */
public class BooleanExpSyntaxHL {
    private BooleanExpANTLRHandler antlrHandler;
    private SyntaxHL syntaxHL;

    public BooleanExpSyntaxHL(BooleanExpANTLRHandler antlrHandler, SyntaxHL syntaxHL) {
        this.antlrHandler = antlrHandler;
        this.syntaxHL = syntaxHL;

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
