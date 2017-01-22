package edu.pse.beast.propertylist;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.UserActions.LoadPropsUserAction;
import edu.pse.beast.propertylist.UserActions.LoadPropertyList;
import edu.pse.beast.propertylist.View.PropertyListWindow;
import edu.pse.beast.propertylist.View.PropertyListWindowStarter;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;

/**
 * 
 * @author Justin
 */
public class PropertyListBuilder {
	
	private String[] menuHeadingIds = { "fileMenu", "editMenu" };
	
	public PropertyList createPropertyList(ObjectRefsForBuilder refs) {
		PropertyListWindowStarter starter = new PropertyListWindowStarter();
		PropertyListWindow window = starter.getPropertyListWindow();
		
		PropertyList instance = PropertyList.getInstance();
		
		PropertyListMenuBarHandler menuBarHandler = new PropertyListMenuBarHandler(menuHeadingIds, 
				createActionIdAndListenerListForMenuHandler(), 
				refs.getStringIF().getPropertyListStringResProvider().getMenuStringRes(), window);
		
		ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
	
		
		PropertyListToolbarHandler toolbarHandler = new PropertyListToolbarHandler(imageRes, 
				refs.getStringIF().getPropertyListStringResProvider().getToolbarTipStringRes(),
				createActionIdAndListenerListForToolbarHandler(), window.getToolbar(), window);
		
		starter.start();
		
		return instance;
	}
	
	private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerListForMenuHandler() {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        
        UserAction load = createLoadPropertyList();

        fileList.add(createFromUserAction(load));

        ArrayList<ActionIdAndListener> editList = new ArrayList<>();


        created.add(fileList);
        created.add(editList);

        return created;
    }

    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler() {
        ActionIdAndListener[] created = new ActionIdAndListener[1];


        UserAction load = createLoadPropertyList();

        created[0] = createFromUserAction(load);


        return created;
    }
    
    private LoadPropertyList createLoadPropertyList() {
        return new LoadPropertyList();
    }
    
    private ActionIdAndListener createFromUserAction(UserAction userAc) {
        return new ActionIdAndListener(userAc.getId(), new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                userAc.perform();
            }
        });
    }
	
}
