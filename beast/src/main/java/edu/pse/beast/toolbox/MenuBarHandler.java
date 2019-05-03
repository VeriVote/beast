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
 *
 * @author Holger Klein
 */
public abstract class MenuBarHandler implements DisplaysStringsToUser {
    private static final String[] STANDARD_ID_ORDER
        = {
            "file", "edit", "code"
        };

    /**
     * the JMenuBar
     */
    private JMenuBar createdMenuBar;

    private final String[] headingIds;
    private final ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener;
    private JMenu[] createdMenus;
    private final ArrayList<ArrayList<JMenuItem>> createdItems = new ArrayList<>();
    private StringResourceLoader currentResourceLoader;

    /**
     *
     * @param headingIds          the id of the heading
     * @param actionIDAndListener the IDandListener
     * @param resLoader           the resourceLoader
     */
    public MenuBarHandler(String[] headingIds,
                          ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener,
                          StringResourceLoader resLoader) {
        Arrays.sort(headingIds, new MenuHeadingSorter());
        this.headingIds = headingIds;
        this.actionIDAndListener = actionIDAndListener;
        this.currentResourceLoader = resLoader;
        createMenuBar();
    }

    /**
     *
     * @return the created Menu bar
     */
    public JMenuBar getCreatedMenuBar() {
        return createdMenuBar;
    }

    /**
     *
     * @param resLoader the resourceLoader
     */
    protected void updateStringResLoader(StringResourceLoader resLoader) {
        this.currentResourceLoader = resLoader;
        createMenuBar();
    }

    private void createMenuBar() {
        createdMenuBar = new JMenuBar();
        createdMenus = new JMenu[headingIds.length];
        for (int i = 0; i < headingIds.length; ++i) {
            JMenu currentMenu = new JMenu(currentResourceLoader.getStringFromID(headingIds[i]));
            createdMenus[i] = currentMenu;
            createdItems.add(new ArrayList<>());
            for (int j = 0; j < actionIDAndListener.get(i).size(); ++j) {
                ActionIdAndListener currentAccIdAndL = actionIDAndListener.get(i).get(j);
                String currentAcId = currentAccIdAndL.getId();
                JMenuItem currentItem
                      = new JMenuItem(currentResourceLoader.getStringFromID(currentAcId));
                currentItem.addActionListener(currentAccIdAndL.getListener());
                createdItems.get(i).add(currentItem);
                currentMenu.add(currentItem);
            }
            createdMenuBar.add(currentMenu);
        }
    }

    private class MenuHeadingSorter implements Comparator<String> {
        @Override
        public int compare(String lhs, String rhs) {
            Integer lhsPos = findInarr(lhs);
            int rhsPos = findInarr(rhs);
            return lhsPos.compareTo(rhsPos);
        }

        private int findInarr(String s) {
            for (int i = 0; i < STANDARD_ID_ORDER.length; i++) {
                if (s.contains(STANDARD_ID_ORDER[i])) {
                    return i;
                }
            }
            return STANDARD_ID_ORDER.length;
        }
    }
}