package edu.pse.beast.codearea.codeinput;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import edu.pse.beast.toolbox.UserAction;

/**
 * This class links chosrtcuts to useraction.
 * @author Holger-Desktop
 */
public class ShortcutHandler {
    
    private HashMap<Integer, UserAction> shortcutMap = new HashMap<>();

    /**
     * if a useraction is mapped to the Keyevents keycode, it will be performed
     * @param ke the KeyEvent whose keycode might be mapped to a useraction
     */
    public void handleKey(KeyEvent ke) {        
        shortcutMap.get(ke.getKeyCode()).perform();
    }
    
    /**
     * mapps the given action to the key on the keyboard corresponding to the 
     * given char
     * @param keyChar the char corresponding to the key which sould be
     * mapped onto performing ac
     * @param ac the action to be performaed once ctrl + keychar is pressed
     */
    public void addAction(char keyChar, UserAction ac) {
        shortcutMap.put(getKeyCode(keyChar), ac);
    }

    public Integer getMappedcharFor(String id) {
        for(Integer i : shortcutMap.keySet()) {
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
                case 'n':
                    return 78;
                default:
                    return -1;                            
        }     
    }

}
