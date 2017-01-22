/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.celectiondescriptioneditor.GUI.CEditorWindowStarter;
import edu.pse.beast.celectiondescriptioneditor.UserActions.ChangeElectionTypeUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.LoadElectionUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.NewElectionUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.PresentOptionsUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.SaveAsElectionUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.SaveBeforeChangeHandler;
import edu.pse.beast.celectiondescriptioneditor.UserActions.SaveElectionUserAction;
import edu.pse.beast.celectiondescriptioneditor.UserActions.StaticCheckUserAction;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionDescriptionEditorBuilder {
    
    private final String[] menuHeadingIds = {"file", "edit", "editor", "code"};
    private CElectionCodeAreaBuilder codeAreaBuilder = new CElectionCodeAreaBuilder();
    
    public CElectionDescriptionEditor createCElectionDescriptionEditor(ObjectRefsForBuilder objRefsForBuilder) {
        CEditorWindowStarter starter = new CEditorWindowStarter();
        CCodeEditorGUI gui = starter.getGUIWindow();
        
        CElectionCodeArea codeArea = codeAreaBuilder.createCElectionCodeArea(
                gui.getCodeArea(), 
                gui.getCodeAreaScrollPane(),
                objRefsForBuilder);
        
        CElectionDescriptionEditor editor = new CElectionDescriptionEditor(codeArea, gui);        
        
        CElectionEditorMenubarHandler menuBarHandler = 
                new CElectionEditorMenubarHandler(
                        menuHeadingIds,
                        gui,
                        createActionIdAndListenerList(objRefsForBuilder, editor, codeArea),
                        objRefsForBuilder.getStringIF());
        
        
        
        starter.start();
        
        return editor;
    }
    
    private ArrayList<ArrayList<ActionIdAndListener>> 
        createActionIdAndListenerList(
                ObjectRefsForBuilder objRefsForBuilder,
                CElectionDescriptionEditor editor,
                CElectionCodeArea codeArea) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
        
        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        UserAction newAcc = createNewElectionUserAction();
        UserAction save = createSaveElectionUserAction();
        UserAction saveAs = createSaveAsElectionUserAction();
        UserAction load = createLoadElectionUserAction();
        
        fileList.add(createFromUserAction(newAcc));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));
        fileList.add(createFromUserAction(load));
        
        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        UserAction changeElec = createChangeElectionTypeUserAction();
        editList.add(createFromUserAction(codeArea.getUserActionList().getActionById("copy")));
        editList.add(createFromUserAction(codeArea.getUserActionList().getActionById("cut")));
        editList.add(createFromUserAction(codeArea.getUserActionList().getActionById("paste")));
        editList.add(createFromUserAction(codeArea.getUserActionList().getActionById("undo")));
        editList.add(createFromUserAction(codeArea.getUserActionList().getActionById("redo")));
        editList.add(createFromUserAction(changeElec));
        
        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        UserAction options = createPresentOptionsUserAction();
        editorList.add(createFromUserAction(options));
        
        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
        UserAction staticCodeAnalysis = createStaticCheckUserAction();
        codeList.add(createFromUserAction(staticCodeAnalysis));
        
        created.add(fileList);
        created.add(editList);
        created.add(editorList);
        created.add(codeList);
        
        return created;        
    }
    //file
    private SaveBeforeChangeHandler createSaveBeforeChangeHandler() {
        return new SaveBeforeChangeHandler();
    }
    private NewElectionUserAction createNewElectionUserAction() {
        return new NewElectionUserAction();
    } 
    private SaveElectionUserAction createSaveElectionUserAction() {
        return new SaveElectionUserAction();
    } 
    private SaveAsElectionUserAction createSaveAsElectionUserAction() {
        return new SaveAsElectionUserAction();
    }    
    private LoadElectionUserAction createLoadElectionUserAction() {
        return new LoadElectionUserAction();
    }
    
    //edit
    private ChangeElectionTypeUserAction createChangeElectionTypeUserAction() {
        return new ChangeElectionTypeUserAction();
    }
    
    //editor
    private PresentOptionsUserAction createPresentOptionsUserAction() {
        return new PresentOptionsUserAction();
    }
    
    //code
    private StaticCheckUserAction createStaticCheckUserAction() {
        return new StaticCheckUserAction();
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
