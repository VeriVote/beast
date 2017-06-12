/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;

import javax.swing.*;
import java.util.ArrayList;

/**
 * This class uses an external compiler (either gcc on linux or cl on windows)
 * to find errors in the c code
 * 
 * @author Holger-Desktop
 */
public class CVariableErrorFinder implements ErrorFinder {
    private final JTextPane pane;

    /**
     * constructor
     * 
     * @param pane the pane the C-errors get shown
     */
    public CVariableErrorFinder(JTextPane pane) {
        this.pane = pane;
    }

    @Override
    public ArrayList<CodeError> getErrors() {
        String code = JTextPaneToolbox.getText(pane);
        ArrayList<String> seperated = new ArrayList<>();
        seperated.add("#ifndef V\n #define V 1\n #endif");
        seperated.add("#ifndef C\n #define C 1\n #endif");
        seperated.add("#ifndef S\n #define S 1\n #endif");
        seperated.add("int main() {");
        seperated.add("}");
        String codeSep[] = code.split("\n");
        for (int i = 0; i < codeSep.length; i++) {
            seperated.add(codeSep[i]);
        }
        ArrayList<CodeError> found = new ArrayList<>(DeepErrorChecker.checkCodeForErrors(seperated));
        return found;
    }

}
