/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.stringresource.StringResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Comparator;

/**
 *
 * @author Holger-Desktop
 */
public abstract class ToolbarHandler {
    private StringResourceLoader stringRes;
    private final ActionIdAndListener[] actionIdsAndListener;
    private final ImageResourceProvider imageRes;
    private final JButton[] buttons;
    protected JToolBar toolbar;
    private final int buttonwidth = 32;
    private final int buttonHeight = 32;

    private class ActionIdListenerSorter implements Comparator<ActionIdAndListener> {
        private String[] standardIdOrder = {"new", "save", "save_as", "load", "undo", "redo", "copy", "cut", "paste"};
        @Override
        public int compare(ActionIdAndListener lhs, ActionIdAndListener rhs) {
            Integer lhsPos = findInarr(lhs.getId());
            int rhsPos = findInarr(rhs.getId());
            return lhsPos.compareTo(rhsPos);
        }

        private int findInarr(String s) {
            for (int i = 0; i < standardIdOrder.length; i++) {
                if(standardIdOrder[i].equals(s)) return i;
            }
            return standardIdOrder.length;
        }
    }
    
    public ToolbarHandler(
            ImageResourceProvider imageRes,
            StringResourceLoader stringRes,
            ActionIdAndListener[] actionIdsAndListener,
            JToolBar toolbar) {
        Arrays.sort(actionIdsAndListener, new ActionIdListenerSorter());
        this.stringRes = stringRes;
        this.actionIdsAndListener = actionIdsAndListener;
        this.imageRes = imageRes;
        this.buttons = new JButton[actionIdsAndListener.length];
        this.toolbar = toolbar;
        
        for(int i = 0; i < actionIdsAndListener.length; ++i) {
            String id = actionIdsAndListener[i].getId();
            JButton currentButton = new JButton();
            currentButton.addActionListener(actionIdsAndListener[i].getListener());
            currentButton.setIcon(new ImageIcon(
                    imageRes.getImageById(
                            id).getScaledInstance(buttonwidth, buttonHeight, Image.SCALE_DEFAULT)));

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
        for(int i = 0; i < actionIdsAndListener.length; ++i) {
            String id = actionIdsAndListener[i].getId();
            buttons[i].setToolTipText(stringRes.getStringFromID(id));            
        }
    }
}
