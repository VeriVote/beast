package edu.pse.beast.toolbox;

import java.awt.Image;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

import edu.pse.beast.stringresource.StringResourceLoader;

/**
 * The Class ToolbarHandler.
 *
 * @author Holger Klein
 */
public abstract class ToolbarHandler {
      /** The Constant BUTTON_WIDTH. */
    private static final int BUTTON_WIDTH = 32;
      /** The Constant BUTTON_HEIGHT. */
    private static final int BUTTON_HEIGHT = 32;

    /** The Constant STANDARD_ID_ORDER. */
    private static final String[] STANDARD_ID_ORDER
        = {
            "new", "load", "save", "save_as",
            "undo", "redo", "cut", "copy", "paste"
        };

    /** The toolbar. */
    private JToolBar toolbar;

    /** The string res. */
    private StringResourceLoader stringRes;
      /** The action ids and listener. */
    private final ActionIdAndListener[] actionIdsAndListener;
      /** The image res. */
    private final ImageResourceProvider imageRes;
      /** The buttons. */
    private final JButton[] buttons;

    /**
     * The constructor.
     *
     * @param imRes the im res
     * @param stringResource the string resource
     * @param actionIdsAndListeners the action ids and listeners
     * @param jToolbar the j toolbar
     */
    public ToolbarHandler(final ImageResourceProvider imRes,
                          final StringResourceLoader stringResource,
                          final ActionIdAndListener[] actionIdsAndListeners,
                          final JToolBar jToolbar) {
        Arrays.sort(actionIdsAndListeners, new ActionIdListenerSorter());
        this.stringRes = stringResource;
        this.actionIdsAndListener = actionIdsAndListeners;
        this.imageRes = imRes;
        this.buttons = new JButton[actionIdsAndListeners.length];
        this.toolbar = jToolbar;

        for (int i = 0; i < actionIdsAndListeners.length; ++i) {
            String id = actionIdsAndListeners[i].getId();
            JButton currentButton = new JButton();
            currentButton.addActionListener(actionIdsAndListeners[i].getListener());
            currentButton.setIcon(new ImageIcon(
                    imageRes.getImageById(id).getScaledInstance(BUTTON_WIDTH,
                                                                BUTTON_HEIGHT,
                                                                Image.SCALE_DEFAULT)));

            currentButton.setToolTipText(stringResource.getStringFromID(id));
            jToolbar.add(currentButton);
            buttons[i] = currentButton;
        }
    }

    /**
     * Update tooltips.
     *
     * @param stringResource the string resource
     */
    protected void updateTooltips(final StringResourceLoader stringResource) {
        this.stringRes = stringResource;
        updateToolbar();
    }

    /**
     * Update toolbar.
     */
    private void updateToolbar() {
        for (int i = 0; i < actionIdsAndListener.length; ++i) {
            String id = actionIdsAndListener[i].getId();
            buttons[i].setToolTipText(stringRes.getStringFromID(id));
        }
    }

    /**
     * The Class ActionIdListenerSorter.
     */
    private class ActionIdListenerSorter implements Comparator<ActionIdAndListener> {

        /**
         * Compare.
         *
         * @param lhs the lhs
         * @param rhs the rhs
         * @return the int
         */
        @Override
        public int compare(final ActionIdAndListener lhs,
                           final ActionIdAndListener rhs) {
            Integer lhsPos = findInArr(lhs.getId());
            int rhsPos = findInArr(rhs.getId());
            return lhsPos.compareTo(rhsPos);
        }

        /**
         * Find in arr.
         *
         * @param s the s
         * @return the int
         */
        private int findInArr(final String s) {
            for (int i = 0; i < STANDARD_ID_ORDER.length; i++) {
                if (STANDARD_ID_ORDER[i].equals(s)) {
                    return i;
                }
            }
            return STANDARD_ID_ORDER.length;
        }
    }
}
