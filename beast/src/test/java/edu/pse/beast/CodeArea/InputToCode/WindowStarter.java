/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.CodeArea.InputToCode;

import javax.swing.*;

/**
 *
 * @author Holger-Desktop
 */
public class WindowStarter implements Runnable {
    JFrame frame;
    
    public WindowStarter(JFrame frame){
        this.frame = frame;        
    }

    public void show() {
        java.awt.EventQueue.invokeLater(this);
    }
    
    @Override
    public void run() {
        frame.setVisible(true);
    }
    
    
}
