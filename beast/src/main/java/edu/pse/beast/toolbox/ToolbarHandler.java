package edu.pse.beast.toolbox;

import java.awt.Image;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.pse.beast.stringresource.StringResourceLoader;

/**
 *
 * @author Holger Klein
 */
public abstract class ToolbarHandler {
    private static final int BUTTON_WIDTH = 32;
    private static final int BUTTON_HEIGHT = 32;

    private static final String[] STANDARD_ID_ORDER
        = {
            "new", "load", "save", "save_as",
            "undo", "redo", "cut", "copy", "paste"
        };

    private JToolBar toolbar;

    private StringResourceLoader stringRes;
    private final ActionIdAndListener[] actionIdsAndListener;
    private final ImageResourceProvider imageRes;
    private final JButton[] buttons;

    public ToolbarHandler(ImageResourceProvider imRes, StringResourceLoader stringRes,
            ActionIdAndListener[] actionIdsAndListener, JToolBar toolbar) {
        Arrays.sort(actionIdsAndListener, new ActionIdListenerSorter());
        this.stringRes = stringRes;
        this.actionIdsAndListener = actionIdsAndListener;
        this.imageRes = imRes;
        this.buttons = new JButton[actionIdsAndListener.length];
        this.toolbar = toolbar;

        for (int i = 0; i < actionIdsAndListener.length; ++i) {
            String id = actionIdsAndListener[i].getId();
            JButton currentButton = new JButton();
            currentButton.addActionListener(actionIdsAndListener[i].getListener());
            currentButton.setIcon(new ImageIcon(
                    imageRes.getImageById(id).getScaledInstance(BUTTON_WIDTH,
                                                                BUTTON_HEIGHT,
                                                                Image.SCALE_DEFAULT)));

            currentButton.setToolTipText(stringRes.getStringFromID(id));
            toolbar.add(currentButton);
            buttons[i] = currentButton;
        }
    }

    protected void updateTooltips(StringResourceLoader stringRes) {
        this.stringRes = stringRes;
        updateToolbar();
    }

    private void updateToolbar() {
        for (int i = 0; i < actionIdsAndListener.length; ++i) {
            String id = actionIdsAndListener[i].getId();
            buttons[i].setToolTipText(stringRes.getStringFromID(id));
        }
    }

    private class ActionIdListenerSorter implements Comparator<ActionIdAndListener> {
        @Override
        public int compare(ActionIdAndListener lhs, ActionIdAndListener rhs) {
            Integer lhsPos = findInarr(lhs.getId());
            int rhsPos = findInarr(rhs.getId());
            return lhsPos.compareTo(rhsPos);
        }

        private int findInarr(String s) {
            for (int i = 0; i < STANDARD_ID_ORDER.length; i++) {
                if (STANDARD_ID_ORDER[i].equals(s)) {
                    return i;
                }
            }
            return STANDARD_ID_ORDER.length;
        }
    }
}