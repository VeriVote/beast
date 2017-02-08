/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.codearea.InputToCode;

import edu.pse.beast.toolbox.UserAction;
import java.awt.event.KeyEvent;
import java.util.HashMap;
import javafx.scene.input.KeyCode;

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
    
    public void addAction(char keyChar, UserAction ac) {
        shortcutMap.put(getKeyCode(keyChar), ac);
    }

    public Integer getMappedcharFor(String id) {
        for(Integer i : shortcutMap.keySet()) {
            System.out.println("char " + id.toString() + " mapped to " + shortcutMap.get(i).getId());
            if(shortcutMap.get(i).getId() == id) 
                return i;
        }
        return -1;
    } 

    private Integer getKeyCode(char keyChar) {
        switch(keyChar) {
                case 'z':
                    return 90;
                case 'r':
                    return 82;
                case 'c':
                    return 67;
                case 'x':
                    return 88;
                case 'v':
                    return 86;
                case 's':
                    return 83;                    
                case 'o':
                    return 79;
                default:
                    return -1;                            
        }     
    }

}
