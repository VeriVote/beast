/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.Actionlist;

import java.util.ArrayList;

import edu.pse.beast.codearea.ActionAdder.ActionlistListener;

/**
 * This class saves actions which can be undone and redone. It provides
 * functionallity to undo and redo these actions in the right order
 * @author Holger-Desktop
 */
public class Actionlist {
    private final ArrayList<Action> lastPerformed = new ArrayList<>();
    private final ArrayList<Action> lastUndone = new ArrayList<>();
    private final ArrayList<ActionlistListener> listener = new  ArrayList<>();
    
    public Actionlist() {        
    }
    
    /**
     * adds the supplied action to the list so if the user calls undoLast 
     * immediatly, this action will be undone
     * @param acc the action to be added to the list
     */
    public void add(Action acc) {
        lastPerformed.add(acc);
        lastUndone.clear();
    }
    
    /**
     * calls undo of the last performed action
     */
    public void undoLast() {  
        msgAllListenerStarted();        
        if(lastPerformed.isEmpty()) return;
        Action latestAcc = lastPerformed.get(lastPerformed.size() - 1);
        lastPerformed.remove(lastPerformed.size() - 1);
        latestAcc.undo();
        lastUndone.add(latestAcc);
        msgAllListenerEnded();
    }
    
    /**
     * calls redo on the last undone action
     */
    public void redoLast() {
        msgAllListenerStarted();        
        if(lastUndone.isEmpty()) return;
        Action latestUndoneAcc = lastUndone.get(lastUndone.size() - 1);
        lastUndone.remove(lastUndone.size() - 1);
        latestUndoneAcc.redo();
        lastPerformed.add(latestUndoneAcc);
        msgAllListenerEnded();
    }
    
    /**
     * the supplied Actionlistlistener will be notified when the list starts 
     * and finishes undoing or redoing actions
     * @param listener the object to be notified in the future
     */
    public void addActionlistListener(ActionlistListener listener) {
        this.listener.add(listener);
    }
    
    /**
     * removes all actions
     */
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
