/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling;

import java.util.ArrayList;

import javax.swing.JTextPane;

import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.codearea.ErrorHandling.ErrorFinder;
import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;

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
        //because we want to reserver the function name "verify" we define it here
        seperated.add("void verify() {}");
        
        
        
        //WORKAROUND: Will change, if I think of a more elegant solution (if there is one) (look at issue 49 on github)
        //Maybe it is possible to include all CBMC functions, but I will have to see. At least I can extract it to a file 
        //which would make updating easier.
        
        seperated.add("void __CPROVER_assert(int x, int y) {}");
        seperated.add("void __CPROVER_assume(int x) {}");
        
        seperated.add("struct result { unsigned int arr[S]; };");
        
        seperated.add("void assume(int x) {}");
        seperated.add("void assert(int x) {}");
        seperated.add("void assert2(int x, int y) {}");
        
        seperated.add("int nondet_int() {return 0;}");
        seperated.add("unsigned int nondet_uint() {return 0;}");
        seperated.add("unsigned char nondet_uchar() {return 0;}");
        seperated.add("char nondet_char() {return 0;}");
        
        //WORKAROUND ende
        
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
