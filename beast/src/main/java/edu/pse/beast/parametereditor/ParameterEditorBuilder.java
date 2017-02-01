/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.parametereditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.propertylist.PropertyList;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.UserAction;
import edu.pse.beast.parametereditor.UserActions.*;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 *
 * @author Holger-Desktop
 */
public class ParameterEditorBuilder {
    private ParameterEditor editor;
    private ParameterEditorWindow window;
    private String[] menuHeadingIds = {"fileMenu", "projectMenu", "optionsMenu"};
    
    public ParameterEditor createParameterEditor(
            ObjectRefsForBuilder refs,
            CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList) {
        ParameterEditorWindowStarter windowStarter = new ParameterEditorWindowStarter();
        window = windowStarter.getParameterEditorWindow();
        /*window.updateStringRes(refs.getStringIF());  //TODO: Implement
        ParameterEditorMenuBarHandler menuBarHandler = new ParameterEditorMenuBarHandler(menuHeadingIds, 
            createActionIdAndListenerListForMenuHandler(cElectionDescriptionEditor, propertyList, 
                    refs.getSaverLoaderIF()), 
            refs.getStringIF().getParameterEditorStringResProvider().getMenuStringRes(), window);
        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
        ParameterEditorToolbarHandler toolbarHandler = new ParameterEditorToolbarHandler(imageRes, 
            refs.getStringIF().getParameterEditorStringResProvider().getToolbarTipStringRes(),
            createActionIdAndListenerListForToolbarHandler(cElectionDescriptionEditor, propertyList, 
                    refs.getSaverLoaderIF()), window.getToolbar(), window);
        */
        editor = new ParameterEditor(cElectionDescriptionEditor, propertyList, window);
        windowStarter.start();
        return editor;
    }
    
    private ArrayList<ArrayList<ActionIdAndListener>>
    createActionIdAndListenerListForMenuHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
        
        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction saveAs = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction start = createStartCheckUserAction();
        UserAction stop = createAbortCheckUserAction();
        
        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        fileList.add(createFromUserAction(newly));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));
        fileList.add(createFromUserAction(load));
        
        ArrayList<ActionIdAndListener> projectList = new ArrayList<>();
        projectList.add(createFromUserAction(start));
        projectList.add(createFromUserAction(stop));
        
        created.add(fileList);
        created.add(projectList);
        return created;
    }
    
    private ActionIdAndListener[] createActionIdAndListenerListForToolbarHandler(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        ActionIdAndListener[] created = new ActionIdAndListener[6];
        
        UserAction newly = createNewProjectUserAction(cElectionDescriptionEditor, propertyList);
        UserAction save = createSaveProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction saveAs = createSaveProjectAsUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction load = createLoadProjectUserAction(cElectionDescriptionEditor, propertyList, saverLoaderIF);
        UserAction start = createStartCheckUserAction();
        UserAction stop = createAbortCheckUserAction();
        
        created[0] = createFromUserAction(newly);
        created[1] = createFromUserAction(save);
        created[2] = createFromUserAction(saveAs);
        created[3] = createFromUserAction(load);
        created[4] = createFromUserAction(start);
        created[5] = createFromUserAction(stop);
        
        return created;
    }

    private UserAction createNewProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList) {
        return new NewProjectUserAction(propertyList, cElectionDescriptionEditor, editor);
    }

    private UserAction createSaveProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new SaveProjectUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createSaveProjectAsUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new SaveProjectAsUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createLoadProjectUserAction(CElectionDescriptionEditor cElectionDescriptionEditor,
            PropertyList propertyList, SaverLoaderInterface saverLoaderIF) {
        return new LoadProjectUserAction(propertyList, cElectionDescriptionEditor, editor, saverLoaderIF);
    }

    private UserAction createStartCheckUserAction() {
        return new StartCheckUserAction(editor);
    }

    private UserAction createAbortCheckUserAction() {
        return new AbortCheckUserAction(editor);
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
