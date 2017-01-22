/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Actionlist;

import edu.pse.beast.codearea.ActionAdder.ActionAdder;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class Actionlist {
    private ArrayList<Action> lastPerformed = new ArrayList<>();
    private ArrayList<Action> lastUndone = new ArrayList<>();
    private ArrayList<ActionAdder> actionAdderList = new  ArrayList<>();
    
    public Actionlist() {        
    }
    
    public void add(Action acc) {
        lastPerformed.add(acc);
        lastUndone.clear();
    }
    
    public void undoLast() {  
        msgAllAdderStop();        
        if(lastPerformed.isEmpty()) return;
        Action latestAcc = lastPerformed.get(lastPerformed.size() - 1);
        lastPerformed.remove(lastPerformed.size() - 1);
        latestAcc.undo();
        lastUndone.add(latestAcc);
        msgAllAdderRes();
    }
    
    public void redoLast() {
        msgAllAdderStop();        
        if(lastUndone.isEmpty()) return;
        Action latestUndoneAcc = lastUndone.get(lastUndone.size() - 1);
        lastUndone.remove(lastUndone.size() - 1);
        latestUndoneAcc.redo();
        lastPerformed.add(latestUndoneAcc);
        msgAllAdderRes();
    }
    
    public void addActionAdder(ActionAdder adder) {
        this.actionAdderList.add(adder);
    }
    
    public void clear() {
        lastPerformed.clear();
        lastUndone.clear();
    }
    
    private void msgAllAdderStop() {
        for(ActionAdder ad : actionAdderList)
            ad.stopListening();
    }
    
    private void msgAllAdderRes() {
        for(ActionAdder ad : actionAdderList)
            ad.resumeListening();
    }
}
