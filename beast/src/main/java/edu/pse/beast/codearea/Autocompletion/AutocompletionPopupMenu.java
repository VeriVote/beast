/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Autocompletion;

import java.awt.Color;
import java.util.ArrayList;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Holger-Desktop
 */
public class AutocompletionPopupMenu extends JPopupMenu {
    private AutocompletionController controller;
    
    public AutocompletionPopupMenu(AutocompletionController controller) {        
        this.controller = controller;
    }
    
    public void showUserOptions(String[] options) {
        removeAll();
        for (int i = 0; i < options.length; i++) {
            String opt = options[i];
            add(opt).addActionListener((ae) -> {
                controller.choseCompletion(opt);
            });            
        } 
    }
    
    
}
