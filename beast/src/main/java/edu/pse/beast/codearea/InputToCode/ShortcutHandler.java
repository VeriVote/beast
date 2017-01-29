/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.UserAction;
import java.awt.event.KeyEvent;
import java.util.HashMap;

/**
 *
 * @author Holger-Desktop
 */
public class ShortcutHandler {
    
    private HashMap<Integer, UserAction> shortcutMap = new HashMap<>();

    public void handleKey(KeyEvent ke) {
        System.out.println(ke.getKeyCode());        
        shortcutMap.get(ke.getKeyCode()).perform();
    }
    
    public void addAction(int keyCode, UserAction ac) {
        shortcutMap.put(keyCode, ac);
    }

    public int getMappedcharFor(String id) {
        for(int i : shortcutMap.keySet()) {
            System.out.println("char " + i + " mapped to " + shortcutMap.get(i).getId());
            if(shortcutMap.get(i).getId() == id) 
                return i;
        }
        return 0;
    }

}
