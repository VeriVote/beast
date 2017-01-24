/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.codearea.SyntaxHL.RegexAndColor;
import edu.pse.beast.codearea.SyntaxHL.SyntaxHL;
import java.awt.Color;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class CSyntaxHl {
    private CAntlrHandler antlrHandler;
    private SyntaxHL syntaxHL;
    
    public CSyntaxHl(CAntlrHandler antlrHandler, SyntaxHL syntaxHL) {
        this.antlrHandler = antlrHandler;
        this.syntaxHL = syntaxHL;
        ArrayList<RegexAndColor> regexAndColorList = new ArrayList<>();
        for(String s : antlrHandler.getTypeLiterals()) {
            regexAndColorList.add(new RegexAndColor(s, Color.GREEN));
        }
        for(String s : antlrHandler.getControllLiterals()) {
            regexAndColorList.add(new RegexAndColor(s, Color.BLUE));
        }
        syntaxHL.updateFilter(regexAndColorList);
    }
}
