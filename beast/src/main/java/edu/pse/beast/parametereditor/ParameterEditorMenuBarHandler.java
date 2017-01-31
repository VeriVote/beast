package edu.pse.beast.parametereditor;

import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.stringresource.StringResourceLoader;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.MenuBarHandler;
import java.util.ArrayList;
/**
 *
 * @author Jonas
 */
public class ParameterEditorMenuBarHandler extends MenuBarHandler {
    private ParameterEditorWindow window;
    
    public ParameterEditorMenuBarHandler(String[] headingIds,
    ArrayList<ArrayList<ActionIdAndListener>> actionIDAndListener, 
    StringResourceLoader resLoader, ParameterEditorWindow window) {
        super(headingIds, actionIDAndListener, resLoader);
                this.window = window;
                this.window.setJMenuBar(createdMenuBar);
    }
    @Override
    public void updateStringRes(StringLoaderInterface stringResIF) {
        updateStringResLoader(stringResIF.getPropertyListStringResProvider().getMenuStringRes());
        window.setJMenuBar(createdMenuBar);
    }
    
}
