/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor.View;

/**
 * starts a CCodeEditorWindow
 * @author Holger-Desktop
 */
public class CEditorWindowStarter implements Runnable {
    
    private final CCodeEditorWindow window = new CCodeEditorWindow();
    
    public CCodeEditorWindow getGUIWindow() {
        return window;
    }
    
    public void start() {
        /* Create and display the form */
        java.awt.EventQueue.invokeLater(this);
    }

    @Override
    public void run() {
        window.setVisible(false);
    }
}
