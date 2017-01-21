/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import java.awt.event.ActionListener;

/**
 *
 * @author Holger-Desktop
 */
public class ActionIdAndListener {
    final private String id;
    final private ActionListener l;
    public ActionIdAndListener(String id, ActionListener l) {
        this.l = l;
        this.id = id;
    }
    
    public String getId() {
        return this.id;
    }
    
    public ActionListener getListener() {
        return this.l;
    }
    
}
