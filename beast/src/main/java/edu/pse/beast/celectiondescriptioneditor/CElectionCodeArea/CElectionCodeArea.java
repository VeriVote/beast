/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CGrammarErrorFinder;
import edu.pse.beast.codearea.CodeArea;
import java.util.List;
import javax.swing.JTextPane;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionCodeArea extends CodeArea {
    private CAntlrHandler antlrHandler;
    private CGrammarErrorFinder grammerErrorFinder;
    private CSyntaxHl cSyntaxHl;
    
    public CElectionCodeArea(CodeArea codeArea) {
        super(codeArea);
        antlrHandler = new CAntlrHandler(pane);
        grammerErrorFinder = new CGrammarErrorFinder(antlrHandler);
        
        errorCtrl.addErrorFinder(grammerErrorFinder);
        cSyntaxHl = new CSyntaxHl(antlrHandler, syntaxHL);
        
    }

    public void letUserEditCode(List<String> code) throws BadLocationException {
        String s = "";
        for(String c : code) {
            s += c + "\n";
        }
        pane.getStyledDocument().remove(0, pane.getStyledDocument().getLength());
        pane.getStyledDocument().insertString(0, s, null);
        actionList.clear();
    }

  
}
