/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Actionlist;

import java.util.ArrayList;
import edu.pse.beast.codearea.ActionAdder.ActionlistListener;

/**
 *
 * @author Holger-Desktop
 */
public class Actionlist {
    private ArrayList<Action> lastPerformed = new ArrayList<>();
    private ArrayList<Action> lastUndone = new ArrayList<>();
    private ArrayList<ActionlistListener> listener = new  ArrayList<>();
    
    public Actionlist() {        
    }
    
    public void add(Action acc) {
        lastPerformed.add(acc);
        lastUndone.clear();
    }
    
    public void undoLast() {  
        msgAllListenerStarted();        
        if(lastPerformed.isEmpty()) return;
        Action latestAcc = lastPerformed.get(lastPerformed.size() - 1);
        lastPerformed.remove(lastPerformed.size() - 1);
        latestAcc.undo();
        lastUndone.add(latestAcc);
        msgAllListenerEnded();
    }
    
    public void redoLast() {
        msgAllListenerStarted();        
        if(lastUndone.isEmpty()) return;
        Action latestUndoneAcc = lastUndone.get(lastUndone.size() - 1);
        lastUndone.remove(lastUndone.size() - 1);
        latestUndoneAcc.redo();
        lastPerformed.add(latestUndoneAcc);
        msgAllListenerEnded();
    }
    
    public void addActionAdder(ActionlistListener adder) {
        this.listener.add(adder);
    }
    
    public void clear() {
        lastPerformed.clear();
        lastUndone.clear();
    }
    
    private void msgAllListenerStarted() {
        for(ActionlistListener ad : listener)
            ad.undoingAction();
    }
    
    private void msgAllListenerEnded() {
        for(ActionlistListener ad : listener)
            ad.finishedUndoingAction();
    }
}
