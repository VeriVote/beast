package edu.pse.beast.propertylist;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.UserActions.LoadPropsUserAction;
import edu.pse.beast.propertylist.UserActions.LoadPropertyList;
import edu.pse.beast.propertylist.UserActions.NewPropertyList;
import edu.pse.beast.propertylist.UserActions.RedoChangesPropertyList;
import edu.pse.beast.propertylist.UserActions.SaveAsPropertyList;
import edu.pse.beast.propertylist.UserActions.SavePropertyList;
import edu.pse.beast.propertylist.UserActions.UndoChangesPropertyList;
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
	
	public PropertyList createPropertyList(ObjectRefsForBuilder refs, BooleanExpEditor booleanExpEditor) {
		PropertyListWindowStarter starter = new PropertyListWindowStarter();
		PropertyListWindow window = starter.getPropertyListWindow();
		
		PropertyList instance = new PropertyList(booleanExpEditor);
		
		PropertyListMenuBarHandler menuBarHandler = new PropertyListMenuBarHandler(menuHeadingIds, 
				createActionIdAndListenerListForMenuHandler(), 
				refs.getStringIF().getPropertyListStringResProvider().getMenuStringRes(), window);
		
		ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
	
		
		PropertyListToolbarHandler toolbarHandler = new PropertyListToolbarHandler(imageRes, 
				refs.getStringIF().getPropertyListStringResProvider().getToolbarTipStringRes(),
				createActionIdAndListenerListForToolbarHandler(), window.getToolbar(), window);
		
		window.setJMenuBar(menuBarHandler.getCreatedMenuBar());
		
		starter.start();
		
		return instance;
	}
	
	private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerListForMenuHandler() {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();

        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        
        UserAction newly = createNewPropertyList();
        UserAction undo = createUndoChangesPropertyList();
        UserAction redo = createRedoChangesPropertyList();
        UserAction save = createSavePropertyList();
        UserAction saveAs = createSaveAsPropertyList();
        UserAction load = createLoadPropertyList();

        fileList.add(createFromUserAction(newly));

        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));
        fileList.add(createFromUserAction(load));

        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        
        editList.add(createFromUserAction(undo));
        editList.add(createFromUserAction(redo));


        created.add(fileList);
        created.add(editList);

        return created;
    }

    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler() {
        ActionIdAndListener[] created = new ActionIdAndListener[6];


        UserAction newly = createNewPropertyList();
        UserAction undo = createUndoChangesPropertyList();
        UserAction redo = createRedoChangesPropertyList();
        UserAction save = createSavePropertyList();
        UserAction saveAs = createSaveAsPropertyList();
        UserAction load = createLoadPropertyList();

        created[0] = createFromUserAction(newly);
        created[1] = createFromUserAction(undo);
        created[2] = createFromUserAction(redo);
        created[3] = createFromUserAction(save);
        created[4] = createFromUserAction(saveAs);
        created[5] = createFromUserAction(load);


        return created;
    }
    
    private LoadPropertyList createLoadPropertyList() {
        return new LoadPropertyList();
    }
    private NewPropertyList createNewPropertyList() {
    	return new NewPropertyList();
    }
    private RedoChangesPropertyList createRedoChangesPropertyList() {
    	return new RedoChangesPropertyList();
    }
    private SaveAsPropertyList createSaveAsPropertyList() {
    	return new SaveAsPropertyList();
    }
    private SavePropertyList createSavePropertyList() {
    	return new SavePropertyList();
    }
    private UndoChangesPropertyList createUndoChangesPropertyList() {
    	return new UndoChangesPropertyList();
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
