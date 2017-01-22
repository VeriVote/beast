/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.awt.event.ActionListener;

/**
 *
 * @author Holger-Desktop & Lukas
 */
public class ActionIdAndListener {
    private final String id;
    private final ActionListener listener;
    
    /**
     * 
     * @param id the id
     * @param listener the listener to be save here
     */
    public ActionIdAndListener(String id, ActionListener listener) {
        this.listener = listener;
        this.id = id;
    }
    
    /**
     * 
     * @return the id
     */
    public String getId() {
        return this.id;
    }
    
    /**
     * 
     * @return the listener that got saved here
     */
    public ActionListener getListener() {
        return this.listener;
    }
    
}
