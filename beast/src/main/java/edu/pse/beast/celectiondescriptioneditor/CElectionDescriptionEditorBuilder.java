/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.ElectionTemplates.ElectionTemplateHandler;
import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.celectiondescriptioneditor.GUI.CEditorWindowStarter;
import edu.pse.beast.celectiondescriptioneditor.UserActions.*;
import edu.pse.beast.saverloader.ElectionDescriptionSaverLoader;
import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.*;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import javax.swing.text.BadLocationException;

/**
 *
 * @author Holger-Desktop
 */
public class CElectionDescriptionEditorBuilder {
    
    private final String[] menuHeadingIds = {"file", "edit", "editor", "code"};
    private CElectionCodeAreaBuilder codeAreaBuilder;
    
    private UserAction newAcc;
    private UserAction save;
    private UserAction saveAs;
    private UserAction load;
    private UserAction options;
    private UserAction staticErrorFinding;
    private UserAction undo;
    private UserAction redo;
    private UserAction paste;
    private UserAction copy;
    private UserAction cut;
    
    public CElectionDescriptionEditor createCElectionDescriptionEditor(ObjectRefsForBuilder objRefsForBuilder) {
        CEditorWindowStarter starter = new CEditorWindowStarter();
        CCodeEditorGUI gui = starter.getGUIWindow();
        gui.updateStringRes(objRefsForBuilder.getStringIF());

        //create new ErrorWindow
        ErrorWindow errorWindow = new ErrorWindow(gui.getErrorPane(), objRefsForBuilder.getStringIF());
        codeAreaBuilder = new CElectionCodeAreaBuilder(objRefsForBuilder);

        //codeAreaObject.setSyntaxHLRegexAndColorList()
        CElectionCodeArea codeArea = codeAreaBuilder.createCElectionCodeArea(
                gui.getCodeArea(), 
                gui.getCodeAreaScrollPane(),
                new CErrorDisplayer(gui.getCodeArea(), objRefsForBuilder.getStringIF()));

        //create FileChooser
        FileChooser fileChooser = new FileChooser(
                objRefsForBuilder.getStringIF().getCElectionEditorStringResProvider().getMenuStringRes(),
                new ElectionDescriptionSaverLoader(),
                gui);

        // create new SaveBeforeChangeHandler
        SaveBeforeChangeHandler saveBeforeChangeHandler = new SaveBeforeChangeHandler(codeArea.getPane(), gui);

        //create new CElectionEditor
        CElectionDescriptionEditor editor = new CElectionDescriptionEditor(codeArea, gui, codeAreaBuilder, errorWindow, saveBeforeChangeHandler,
                objRefsForBuilder.getStringIF(), fileChooser);
        
        CElectionEditorMenubarHandler menuBarHandler = 
                new CElectionEditorMenubarHandler(
                        menuHeadingIds,
                        gui,
                        createActionIdAndListenerList(objRefsForBuilder, editor),
                        objRefsForBuilder.getStringIF());
        
        //toolbar: new save save_as load copy cut paste undo redo
        saveBeforeChangeHandler.setSaveElectionUserAction((SaveElectionUserAction) save);

                ActionIdAndListener[] idAndListener = {
            createFromUserAction(newAcc),
            createFromUserAction(save),
            createFromUserAction(saveAs),
            createFromUserAction(load),
            createFromUserAction(undo),
            createFromUserAction(redo),
            createFromUserAction(copy),
            createFromUserAction(cut),
            createFromUserAction(paste)
        };
        
        ImageResourceProvider imageRes = ImageResourceProvider.getToolbarImages();
        
        CElectionEditorToolbarHandler toolbarHandler = 
                new CElectionEditorToolbarHandler(
                        idAndListener,
                        imageRes, 
                        objRefsForBuilder.getStringIF(), 
                        gui);
        
        starter.start();
        
        ElectionTemplateHandler templateHandler = new ElectionTemplateHandler();

        try {
            editor.loadElectionDescription(new CCodeHelper().generateElectionDescription(
                    templateHandler.getInputIds()[0],
                    templateHandler.getOutputIds()[0],
                    "new_election",
                    templateHandler,
                    objRefsForBuilder.getStringIF().getCElectionEditorStringResProvider().getElectionStringRes()));
        } catch (BadLocationException ex) {
            Logger.getLogger(CElectionDescriptionEditorBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }

        editor.setcElectionEditorMenubarHandler(menuBarHandler);
        editor.setcElectionEditorToolbarHandler(toolbarHandler);
        return editor;
    }
    
    private ArrayList<ArrayList<ActionIdAndListener>> 
        createActionIdAndListenerList(
                ObjectRefsForBuilder objRefsForBuilder,
                CElectionDescriptionEditor editor) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
        
        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();

        newAcc = createNewElectionUserAction(editor);
        saveAs = createSaveAsElectionUserAction(editor);
        save = createSaveElectionUserAction(editor);
        load = createLoadElectionUserAction(editor);
        copy = createElectionCopyUserAction(editor);
        cut = createElectionCutUserAction(editor);
        paste = createElectionPasteUserAction(editor);
        undo = createElectionUndoUserAction(editor);
        redo = createElectionRedoUserAction(editor);
        
        editor.addUserAction('n', newAcc);
        editor.addUserAction('s', save);
        editor.addUserAction('o', load);
        editor.addUserAction('c', copy);
        editor.addUserAction('x', cut);
        editor.addUserAction('v', paste);
        
        fileList.add(createFromUserAction(newAcc));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));
        fileList.add(createFromUserAction(load));
        
        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        editList.add(createFromUserAction(copy));
        editList.add(createFromUserAction(cut));
        editList.add(createFromUserAction(paste));
        editList.add(createFromUserAction(undo));
        editList.add(createFromUserAction(redo));

        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        options = createPresentOptionsUserAction();
        editorList.add(createFromUserAction(options));
        
        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
        staticErrorFinding = createStaticErrorFindingUserAction(editor);
        codeList.add(createFromUserAction(staticErrorFinding));
        
        created.add(fileList);
        created.add(editList);
        created.add(editorList);
        created.add(codeList);
        
        
        return created;        
    }
    //file

    private NewElectionUserAction createNewElectionUserAction(
            CElectionDescriptionEditor editor) {
        return new NewElectionUserAction(editor);
    } 
    private SaveElectionUserAction createSaveElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new SaveElectionUserAction(cElectionDescriptionEditor);
    } 
    private SaveAsElectionUserAction createSaveAsElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new SaveAsElectionUserAction(cElectionDescriptionEditor);
    }    
    private LoadElectionUserAction createLoadElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new LoadElectionUserAction(cElectionDescriptionEditor);
    }
    private ElectionCopyUserAction createElectionCopyUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new ElectionCopyUserAction(cElectionDescriptionEditor);
    }
    private ElectionCutUserAction createElectionCutUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new ElectionCutUserAction(cElectionDescriptionEditor);
    }
    private ElectionPasteUserAction createElectionPasteUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new ElectionPasteUserAction(cElectionDescriptionEditor);
    }
    private ElectionRedoUserAction createElectionRedoUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new ElectionRedoUserAction(cElectionDescriptionEditor);
    }
    private ElectionUndoUserAction createElectionUndoUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
        return new ElectionUndoUserAction(cElectionDescriptionEditor);
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
    private StaticErrorFindingUserAction createStaticErrorFindingUserAction(CElectionDescriptionEditor editor) {
        return new StaticErrorFindingUserAction(editor);
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
