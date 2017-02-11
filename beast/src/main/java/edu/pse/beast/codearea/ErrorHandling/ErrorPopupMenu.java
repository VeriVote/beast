/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.awt.Color;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 * This class is a simple popupmenu which displays an error message
 * @author Holger-Desktop
 */
public class ErrorPopupMenu extends JPopupMenu {
    JMenuItem errorItem;
    public ErrorPopupMenu(){
        errorItem = new JMenuItem("");
        errorItem.setBackground(Color.white);
        add(errorItem);
    }

    public JMenuItem getErrorItem() {
        return errorItem;
    }    
}
