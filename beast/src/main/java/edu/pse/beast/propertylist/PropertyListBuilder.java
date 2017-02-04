package edu.pse.beast.propertylist;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import edu.pse.beast.booleanexpeditor.UserActions.LoadPropsUserAction;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PLModelInterface;
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
 * Builds the property list components and returns the controller of property list.
 * @author Justin
 */
public class PropertyListBuilder {
	
	PropertyList controller;
    private PropertyListWindow window;
	private String[] menuHeadingIds = { "fileMenu", "editMenu" };
	
	/**
	 * Builds all relevant components.
	 * @param refs 
	 * @param booleanExpEditor
	 * @return
	 */
	public PropertyList createPropertyList(ObjectRefsForBuilder refs, BooleanExpEditor booleanExpEditor) {
		PLModelInterface model = new PLModel();
		controller = new PropertyList(model, booleanExpEditor);
		//PropertyListWindowStarter starter = new PropertyListWindowStarter(controller, model);
		
		//window = starter.getPropertyListWindow();
		window = controller.getView();
		window.updateStringRes(refs.getStringIF());

		PropertyListMenuBarHandler menuBarHandler = new PropertyListMenuBarHandler(menuHeadingIds, 
				createActionIdAndListenerListForMenuHandler(), 
				refs.getStringIF().getPropertyListStringResProvider().getMenuStringRes(), window);
		
		ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
		
		PropertyListToolbarHandler toolbarHandler = new PropertyListToolbarHandler(imageRes, 
				refs.getStringIF().getPropertyListStringResProvider().getToolbarTipStringRes(),
				createActionIdAndListenerListForToolbarHandler(), window.getToolbar(), window);
		
		//starter.start();
		controller.start();
		
		return (PropertyList) controller;
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
        ActionIdAndListener[] created = new ActionIdAndListener[5];


        UserAction newly = createNewPropertyList();
        UserAction load = createLoadPropertyList();
        
        UserAction save = createSavePropertyList();
        UserAction saveAs = createSaveAsPropertyList();
        UserAction undo = createUndoChangesPropertyList();

        created[0] = createFromUserAction(newly);
        created[1] = createFromUserAction(load);
        
        created[2] = createFromUserAction(save);
        created[3] = createFromUserAction(saveAs);
        created[4] = createFromUserAction(undo);


        return created;
    }
    
    private LoadPropertyList createLoadPropertyList() {
        return new LoadPropertyList((PropertyList) controller);
    }
    private NewPropertyList createNewPropertyList() {
    	return new NewPropertyList((PropertyList) controller);
    }
    private RedoChangesPropertyList createRedoChangesPropertyList() {
    	return new RedoChangesPropertyList((PropertyList) controller);
    }
    private SaveAsPropertyList createSaveAsPropertyList() {
    	return new SaveAsPropertyList((PropertyList) controller);
    }
    private SavePropertyList createSavePropertyList() {
    	return new SavePropertyList((PropertyList) controller);
    }
    private UndoChangesPropertyList createUndoChangesPropertyList() {
    	return new UndoChangesPropertyList((PropertyList) controller);
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
