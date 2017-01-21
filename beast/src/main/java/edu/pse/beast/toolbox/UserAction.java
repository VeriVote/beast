/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

/**
 *
 * @author Holger-Desktop
 */
public abstract class UserAction {
    private String id;
    
    public UserAction(String id) {
        this.id = id;
    }
    
    public String getId() {
        return id;
    }
    
    public abstract void perform();
}
