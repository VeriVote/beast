/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.ErrorHandling;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JTextPane;

/**
 * This class implements the runnable intrerface and continuously asks the
 * supplied errorfinderlist to find all errors in the code. If it finds errors,
 * it notifies the given ErrorController of this fact. It will run until another
 * thread calls the stop method. 
 * @author Holger-Desktop
 */
public class ErrorFinderThread implements Runnable {
    private volatile boolean keepRunning = true;
    private ErrorFinderList l;
    private ArrayList<CodeError> lastFoundErrors = new ArrayList<>();
    private Thread t;
    private JTextPane pane;
    private String lastCode;
    private ErrorController c;
    
    public ErrorFinderThread(ErrorFinderList l, JTextPane pane, ErrorController c) {
        this.l = l;
        this.pane = pane;
        lastCode = pane.getText();
        this.c = c;
        this.t = new Thread(this);
        this.t.start();
    }

    public void stop() {
        keepRunning = false;
    }
    
    @Override
    public void run() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ex) {
            Logger.getLogger(ErrorFinderThread.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(keepRunning) {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException ex) {
            }            
            lastFoundErrors = l.getErrors();
            c.foundNewErrors(lastFoundErrors);
        }
    }
    
    public ArrayList<CodeError> getLastFoundErrors() {
        return lastFoundErrors;
    }
    
    
}
