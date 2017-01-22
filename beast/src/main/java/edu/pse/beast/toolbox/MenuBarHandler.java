/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.highlevel.DisplaysStringsToUser;
import edu.pse.beast.stringresource.StringResourceLoader;
import java.util.ArrayList;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;

/**
 *
 * @author Holger-Desktop
 */
public abstract class MenuBarHandler implements DisplaysStringsToUser {

    /**
     * the JMenuBar
     */
    protected JMenuBar createdMenuBar;

    private String[] headingIds;
    private ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener;
    private JMenu[] createdMenus;
    private ArrayList<ArrayList<JMenuItem>> createdItems = new ArrayList<>();
    private StringResourceLoader currentResourceLoader;

    /**
     * 
     * @param headingIds
     *            the id of the heading
     * @param actionIDAndListener
     *            the IDandListener
     * @param resLoader
     *            the resourceLoader
     */
    public MenuBarHandler(String[] headingIds, ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener,
            StringResourceLoader resLoader) {
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
     * @param resLoader
     *            the resourceLoader
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
                JMenuItem currentItem = new JMenuItem(currentResourceLoader.getStringFromID(currentAcId));
                currentItem.addActionListener(currentAccIdAndL.getListener());
                createdItems.get(i).add(currentItem);

                currentMenu.add(currentItem);
            }

            createdMenuBar.add(currentMenu);
        }
    }

}
