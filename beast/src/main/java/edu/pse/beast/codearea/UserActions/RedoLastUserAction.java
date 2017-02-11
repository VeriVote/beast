/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.UserActions;

import edu.pse.beast.codearea.Actionlist.Actionlist;
import edu.pse.beast.toolbox.UserAction;

/**
 * this useraction asks the given actionlist to redo the last action
 * @author Holger-Desktop
 */
public class RedoLastUserAction extends UserAction {
    private Actionlist list;
    
    public RedoLastUserAction(Actionlist list) {
        super("redo");
        this.list = list;        
    }

    @Override
    public void perform() {
        System.out.println("redo");
        list.redoLast();
    }
    
    
}
