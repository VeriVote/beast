package edu.pse.beast.toolbox;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;

import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;

/**
 * The Class MenuBarHandler.
 *
 * @author Holger Klein
 */
public abstract class MenuBarHandler implements DisplaysStringsToUser {

    /** The Constant STANDARD_ID_ORDER. */
    private static final String[] STANDARD_ID_ORDER =
        {"file", "edit", "code"};

    /**
     * The JMenuBar.
     */
    private JMenuBar createdMenuBar;

    /** The heading ids. */
    private final String[] headingIds;

    /** The action ID and listener. */
    private final ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener;

    /** The created menus. */
    private JMenu[] createdMenus;

    /** The created items. */
    private final ArrayList<ArrayList<JMenuItem>> createdItems =
            new ArrayList<ArrayList<JMenuItem>>();

    /** The current resource loader. */
    private StringResourceLoader currentResourceLoader;

    /**
     * Instantiates a new menu bar handler.
     *
     * @param headIds
     *            the id of the heading
     * @param actionIDAndListeners
     *            the IDandListener
     * @param resLoader
     *            the resourceLoader
     */
    public MenuBarHandler(final String[] headIds,
                          final ArrayList<ArrayList<ActionIdAndListener>>
                                    actionIDAndListeners,
                          final StringResourceLoader resLoader) {
        Arrays.sort(headIds, new MenuHeadingSorter());
        this.headingIds = headIds;
        this.actionIDAndListener = actionIDAndListeners;
        this.currentResourceLoader = resLoader;
        createMenuBar();
    }

    /**
     * Gets the created menu bar.
     *
     * @return the created Menu bar
     */
    public JMenuBar getCreatedMenuBar() {
        return createdMenuBar;
    }

    /**
     * Update string res loader.
     *
     * @param resLoader
     *            the resourceLoader
     */
    protected void updateStringResLoader(final StringResourceLoader resLoader) {
        this.currentResourceLoader = resLoader;
        createMenuBar();
    }

    /**
     * Creates the menu bar.
     */
    private void createMenuBar() {
        createdMenuBar = new JMenuBar();
        createdMenus = new JMenu[headingIds.length];
        for (int i = 0; i < headingIds.length; ++i) {
            final JMenu currentMenu =
                    new JMenu(currentResourceLoader.getStringFromID(headingIds[i]));
            createdMenus[i] = currentMenu;
            createdItems.add(new ArrayList<JMenuItem>());
            for (int j = 0; j < actionIDAndListener.get(i).size(); ++j) {
                final ActionIdAndListener currentAccIdAndL =
                        actionIDAndListener.get(i).get(j);
                final String currentAcId = currentAccIdAndL.getId();
                final JMenuItem currentItem = new JMenuItem(
                        currentResourceLoader.getStringFromID(currentAcId));
                currentItem.addActionListener(currentAccIdAndL.getListener());
                createdItems.get(i).add(currentItem);
                currentMenu.add(currentItem);
            }
            createdMenuBar.add(currentMenu);
        }
    }

    /**
     * The Class MenuHeadingSorter.
     */
    private class MenuHeadingSorter implements Comparator<String> {

        /**
         * Compare.
         *
         * @param lhs
         *            the lhs
         * @param rhs
         *            the rhs
         * @return the int
         */
        @Override
        public int compare(final String lhs, final String rhs) {
            final Integer lhsPos = findInArr(lhs);
            final int rhsPos = findInArr(rhs);
            return lhsPos.compareTo(rhsPos);
        }

        /**
         * Find in arr.
         *
         * @param s
         *            the s
         * @return the int
         */
        private int findInArr(final String s) {
            for (int i = 0; i < STANDARD_ID_ORDER.length; i++) {
                if (s.contains(STANDARD_ID_ORDER[i])) {
                    return i;
                }
            }
            return STANDARD_ID_ORDER.length;
        }
    }
}
