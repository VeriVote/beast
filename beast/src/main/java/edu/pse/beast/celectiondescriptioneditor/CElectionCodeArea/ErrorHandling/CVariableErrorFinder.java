/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CAntlrHandler;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CBaseListener;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CBaseVisitor;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.Antlr.CParser;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.DeepCheck.DeepErrorChecker;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import java.util.ArrayList;
import javax.swing.JTextPane;
import org.antlr.v4.runtime.tree.ParseTree;
import org.antlr.v4.runtime.tree.ParseTreeWalker;

/**
 *
 * @author Holger-Desktop
 */
public class CVariableErrorFinder implements ErrorFinder {
    private JTextPane pane;
    private DeepErrorChecker errorchecker;
    
    public CVariableErrorFinder(JTextPane pane) {
        this.pane = pane;
        this.errorchecker = new DeepErrorChecker();
    }
    
    @Override
    public ArrayList<CodeError> getErrors() {
        String code = JTextPaneToolbox.getText(pane);
        ArrayList<String> seperated = new ArrayList<>();
        seperated.add("#ifndef V\n #define V 1\n #endif");
        seperated.add("#ifndef C\n #define C 1\n #endif");
        seperated.add("#ifndef S\n #define S 1\n #endif");
        String codeSep[] = code.split("\n");
        for (int i = 0; i < codeSep.length; i++) {
            seperated.add(codeSep[i]);
        }
        ArrayList<CodeError> found = new ArrayList<>(errorchecker.checkCodeForErrors(seperated));
        return found;
    }
    
}
