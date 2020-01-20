package edu.pse.beast.codearea.codeinput;

import java.awt.event.KeyEvent;
import java.util.HashMap;
import java.util.Map;

import edu.pse.beast.toolbox.UserAction;

/**
 * This class links chosrtcuts to useraction.
 *
 * @author Holger Klein
 */
public class ShortcutHandler {

    /** The Constant NO_CHAR. */
    private static final int NO_CHAR = -1;

    /** The Constant N_CHAR. */
    private static final int N_CHAR = 78;

    /** The Constant O_CHAR. */
    private static final int O_CHAR = 79;

    /** The Constant S_CHAR. */
    private static final int S_CHAR = 83;

    /** The Constant V_CHAR. */
    private static final int V_CHAR = 86;

    /** The Constant X_CHAR. */
    private static final int X_CHAR = 88;

    /** The Constant C_CHAR. */
    private static final int C_CHAR = 67;

    /** The Constant R_CHAR. */
    private static final int R_CHAR = 82;

    /** The Constant Z_CHAR. */
    private static final int Z_CHAR = 90;

    /** The shortcut map. */
    private Map<Integer, UserAction> shortcutMap =
            new HashMap<Integer, UserAction>();

    /**
     * If a useraction is mapped to the Keyevents keycode, it will be performed.
     *
     * @param ke
     *            the KeyEvent whose keycode might be mapped to a useraction
     */
    public void handleKey(final KeyEvent ke) {
        shortcutMap.get(ke.getKeyCode()).perform();
    }

    /**
     * Maps the given action to the key on the keyboard corresponding to the
     * given char.
     *
     * @param keyChar
     *            the char corresponding to the key which sould be mapped onto
     *            performing ac
     * @param ac
     *            the action to be performaed once ctrl + keychar is pressed
     */
    public void addAction(final char keyChar, final UserAction ac) {
        shortcutMap.put(getKeyCode(keyChar), ac);
    }

    /**
     * Gets the mappedchar for.
     *
     * @param id
     *            the id
     * @return the mappedchar for
     */
    public Integer getMappedcharFor(final String id) {
        for (Integer i : shortcutMap.keySet()) {
            if (shortcutMap.get(i).getId() == id) {
                return i;
            }
        }
        return NO_CHAR;
    }

    /**
     * Gets the key code.
     *
     * @param keyChar
     *            the key char
     * @return the key code
     */
    private Integer getKeyCode(final char keyChar) {
        final Integer keyCode;
        switch (keyChar) {
        case 'z':
            keyCode = Z_CHAR;
            break;
        case 'r':
            keyCode = R_CHAR;
            break;
        case 'c':
            keyCode = C_CHAR;
            break;
        case 'x':
            keyCode = X_CHAR;
            break;
        case 'v':
            keyCode = V_CHAR;
            break;
        case 's':
            keyCode = S_CHAR;
            break;
        case 'o':
            keyCode = O_CHAR;
            break;
        case 'n':
            keyCode = N_CHAR;
            break;
        default:
            keyCode = NO_CHAR;
        }
        return keyCode;
    }
}
