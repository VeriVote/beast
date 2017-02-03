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
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.ActionIdAndListener;
import edu.pse.beast.toolbox.CCodeHelper;
import edu.pse.beast.toolbox.ImageResourceProvider;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.UserAction;
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
    private UserAction changeElec;
    private UserAction options;
    private UserAction staticCodeAnalysis;
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

        //TODO create an ArrayList of RegexAndColor objects and apply it to the codeareas by calling
        //codeAreaObject.setSyntaxHLRegexAndColorList()
        CElectionCodeArea codeArea = codeAreaBuilder.createCElectionCodeArea(
                gui.getCodeArea(), 
                gui.getCodeAreaScrollPane(),
                new CErrorDisplayer(gui.getCodeArea(), objRefsForBuilder.getStringIF()));

        CElectionDescriptionEditor editor = new CElectionDescriptionEditor(codeArea, gui, codeAreaBuilder, errorWindow);
        
        CElectionEditorMenubarHandler menuBarHandler = 
                new CElectionEditorMenubarHandler(
                        menuHeadingIds,
                        gui,
                        createActionIdAndListenerList(objRefsForBuilder, editor, codeArea),
                        objRefsForBuilder.getStringIF());
        
        //toolbar: new save save_as load copy cut paste undo redo
        
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
            editor.letUserEditElectionDescription(new CCodeHelper().generateElectionDescription(
                    templateHandler.getInputIds()[0],
                    templateHandler.getOutputIds()[0],
                    "new_election",
                    templateHandler,
                    objRefsForBuilder.getStringIF().getCElectionEditorStringResProvider().getElectionStringRes()));
        } catch (BadLocationException ex) {
            Logger.getLogger(CElectionDescriptionEditorBuilder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return editor;
    }
    
    private ArrayList<ArrayList<ActionIdAndListener>> 
        createActionIdAndListenerList(
                ObjectRefsForBuilder objRefsForBuilder,
                CElectionDescriptionEditor editor,
                CElectionCodeArea codeArea) {
        ArrayList<ArrayList<ActionIdAndListener>> created = new ArrayList<>();
        
        ArrayList<ActionIdAndListener> fileList = new ArrayList<>();
        SaveBeforeChangeHandler saveBeforeChangeHandler = new SaveBeforeChangeHandler(codeArea.getPane());

        newAcc = createNewElectionUserAction(editor, objRefsForBuilder.getStringIF());
        save = createSaveElectionUserAction();
        saveAs = createSaveAsElectionUserAction();
        load = createLoadElectionUserAction();
        copy = createElectionCopyUserAction(editor);
        cut = createElectionCutUserAction(editor);
        paste = createElectionPasteUserAction(editor);
        undo = createElectionUndoUserAction(editor);
        redo = createElectionRedoUserAction(editor);
        
        fileList.add(createFromUserAction(newAcc));
        fileList.add(createFromUserAction(save));
        fileList.add(createFromUserAction(saveAs));
        fileList.add(createFromUserAction(load));
        
        ArrayList<ActionIdAndListener> editList = new ArrayList<>();
        changeElec = createChangeElectionTypeUserAction();
        editList.add(createFromUserAction(copy));
        editList.add(createFromUserAction(cut));
        editList.add(createFromUserAction(paste));
        editList.add(createFromUserAction(undo));
        editList.add(createFromUserAction(redo));
        editList.add(createFromUserAction(changeElec));

        ArrayList<ActionIdAndListener> editorList = new ArrayList<>();
        options = createPresentOptionsUserAction();
        editorList.add(createFromUserAction(options));
        
        ArrayList<ActionIdAndListener> codeList = new ArrayList<>();
        staticCodeAnalysis = createStaticCheckUserAction(editor);
        codeList.add(createFromUserAction(staticCodeAnalysis));
        
        created.add(fileList);
        created.add(editList);
        created.add(editorList);
        created.add(codeList);
        
        
        return created;        
    }
    //file

    private NewElectionUserAction createNewElectionUserAction(
            CElectionDescriptionEditor editor,
            StringLoaderInterface stringIf) {
        return new NewElectionUserAction(editor, stringIf);
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
    private ElectionCopyUserAction createElectionCopyUserAction(CElectionDescriptionEditor editor) {
        return new ElectionCopyUserAction(editor);
    }
    private ElectionCutUserAction createElectionCutUserAction(CElectionDescriptionEditor editor) {
        return new ElectionCutUserAction(editor);
    }
    private ElectionPasteUserAction createElectionPasteUserAction(CElectionDescriptionEditor editor) {
        return new ElectionPasteUserAction(editor);
    }
    private ElectionRedoUserAction createElectionRedoUserAction(CElectionDescriptionEditor editor) {
        return new ElectionRedoUserAction(editor);
    }
    private ElectionUndoUserAction createElectionUndoUserAction(CElectionDescriptionEditor editor) {
        return new ElectionUndoUserAction(editor);
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
    private StaticCheckUserAction createStaticCheckUserAction(CElectionDescriptionEditor editor) {
        return new StaticCheckUserAction(editor);
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
