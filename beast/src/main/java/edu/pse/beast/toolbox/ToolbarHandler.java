/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.stringresource.StringResourceProvider;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 *
 * @author Holger-Desktop
 */
public abstract class ToolbarHandler {
    private StringResourceLoader stringRes;
    private ActionIdAndListener[] actionIdsAndListener;
    private ImageResourceProvider imageRes;
    private JButton[] buttons;
    protected JToolBar toolbar;
    
    public ToolbarHandler(
            ImageResourceProvider imageRes,
            StringResourceLoader stringRes,
            ActionIdAndListener[] actionIdsAndListener,
            JToolBar toolbar) {
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
                            id)));
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
