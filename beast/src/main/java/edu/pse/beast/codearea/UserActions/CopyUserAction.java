/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.UserActions;

import edu.pse.beast.toolbox.UserAction;
import java.awt.Toolkit;
import java.awt.datatransfer.Clipboard;
import javax.swing.JTextPane;

/**
 *
 * @author Holger-Desktop
 */
public class CopyUserAction extends UserAction {

    private JTextPane pane;
    private Clipboard clipboard;
    
    public CopyUserAction(JTextPane pane) {
        super("copy");
        this.pane = pane;
        this.clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
    }
    
    @Override
    public void perform() {

    }
    
}
