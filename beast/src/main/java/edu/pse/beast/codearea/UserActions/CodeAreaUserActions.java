/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.UserActions;

import edu.pse.beast.codearea.CodeArea;
import edu.pse.beast.toolbox.UserAction;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class CodeAreaUserActions {
    private CodeArea area;
    private ArrayList<UserAction> actions = new ArrayList<>();
    
    public CodeAreaUserActions(CodeArea area) {
        addCopyAction();
        addCutAction();
        addPasteAction();
        addRedoAction();
        addUndoAction();
    }
    
    public UserAction getActionById(String id) {
        for(UserAction ac : actions) {
            if(ac.getId() == id) return ac;
        }
        return null;
    }
    
    private void addCopyAction() {
        actions.add(new CopyUserAction());
    }
    
    private void addCutAction() {
        actions.add(new CutUserAction());
    }
    
    private void addPasteAction() {
        actions.add(new PasteUserAction());
    }
    
    private void addRedoAction() {
        actions.add(new RedoLastUserAction());
    }
    
    private void addUndoAction() {
        actions.add(new UndoLastUserAction());
    }
}
