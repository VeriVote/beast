/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import edu.pse.beast.codearea.InputToCode.JTextPaneToolbox;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class FindWordsConcurrently implements Runnable {
    private ArrayList<String> words = new ArrayList<>();
    private JTextPane pane;
    private volatile boolean run = true;
    private AutocompletionController controller;
    public FindWordsConcurrently(JTextPane pane, AutocompletionController controller) {
        this.pane = pane;
        this.controller = controller;
    }
    
    public void stop() {
        run = false;
    }

    public ArrayList<String> getWords() {
        return words;
    }
        
    @Override
    public void run() {
        while(run) {
            try {
                Thread.sleep(5000);
            } catch (InterruptedException ex) {
                
            }
            String code = JTextPaneToolbox.getText(pane);
            String w = "";
            for(int i = 0; i < code.length(); ++i) {
                w = "";
                for(; Character.isLetterOrDigit(code.charAt(i)); ++i) {
                    w += code.charAt(i);
                }
                if(w.length() != 0) {
                    controller.addAutocompletionString(w);
                }
            }
        }
    }
    
}
