package edu.pse.beast.codearea.codeinput;

import java.awt.event.KeyEvent;
import java.util.HashMap;

import edu.pse.beast.toolbox.UserAction;

/**
 * This class links chosrtcuts to useraction.
 *
 * @author Holger Klein
 */
public class ShortcutHandler {
    private static final int NO_CHAR = -1;
    private static final int N_CHAR = 78;
    private static final int O_CHAR = 79;
    private static final int S_CHAR = 83;
    private static final int V_CHAR = 86;
    private static final int X_CHAR = 88;
    private static final int C_CHAR = 67;
    private static final int R_CHAR = 82;
    private static final int Z_CHAR = 90;

    private HashMap<Integer, UserAction> shortcutMap = new HashMap<>();

    /**
     * If a useraction is mapped to the Keyevents keycode, it will be performed.
     *
     * @param ke the KeyEvent whose keycode might be mapped to a useraction
     */
    public void handleKey(final KeyEvent ke) {
        shortcutMap.get(ke.getKeyCode()).perform();
    }

    /**
     * Maps the given action to the key on the keyboard corresponding to the given
     * char.
     *
     * @param keyChar the char corresponding to the key which sould be mapped onto
     *                performing ac
     * @param ac      the action to be performaed once ctrl + keychar is pressed
     */
    public void addAction(final char keyChar,
                          final UserAction ac) {
        shortcutMap.put(getKeyCode(keyChar), ac);
    }

    public Integer getMappedcharFor(final String id) {
        for (Integer i : shortcutMap.keySet()) {
            if (shortcutMap.get(i).getId() == id) {
                return i;
            }
        }
        return NO_CHAR;
    }

    private Integer getKeyCode(final char keyChar) {
        switch (keyChar) {
        case 'z':
            return Z_CHAR;
        case 'r':
            return R_CHAR;
        case 'c':
            return C_CHAR;
        case 'x':
            return X_CHAR;
        case 'v':
            return V_CHAR;
        case 's':
            return S_CHAR;
        case 'o':
            return O_CHAR;
        case 'n':
            return N_CHAR;
        default:
            return NO_CHAR;
        }
    }
}
