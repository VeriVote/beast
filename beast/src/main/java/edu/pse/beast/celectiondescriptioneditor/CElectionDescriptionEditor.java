/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.errorFinder.BooleanExpErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.GUI.CCodeEditorGUI;
import edu.pse.beast.celectiondescriptioneditor.UserActions.SaveBeforeChangeHandler;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.descofvoting.ElectionDescription;
import edu.pse.beast.datatypes.descofvoting.ElectionDescriptionChangeListener;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.toolbox.FileChooser;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.*;
import javax.swing.text.BadLocationException;


/**
 *  
 * @author Holger Klein
 */
public class CElectionDescriptionEditor implements ElectionDescriptionSource{
    private CElectionCodeArea codeArea;
    private ElectionDescription currentDescription;
    private CCodeEditorGUI gui;
    private CElectionCodeAreaBuilder builder;
    private ErrorWindow errorWindow;
    private SaveBeforeChangeHandler saveBeforeChangeHandler;
    private ArrayList<ElectionDescriptionChangeListener> descriptionChangeListeners = new ArrayList<>();
    private CElectionEditorMenubarHandler menubarHandler;
    private CElectionEditorToolbarHandler toolbarHandler;
    private StringLoaderInterface stringLoaderInterface;
    private FileChooser fileChooser;

    public CElectionDescriptionEditor(
            CElectionCodeArea codeArea,
            CCodeEditorGUI gui,            
            CElectionCodeAreaBuilder builder,
            ErrorWindow errorWindow,
            SaveBeforeChangeHandler saveBeforeChangeHandler,
            StringLoaderInterface stringLoaderInterface,
            FileChooser fileChooser) {
        this.codeArea = codeArea;
        this.gui = gui;
        this.builder = builder;
        this.errorWindow = errorWindow;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
        this.stringLoaderInterface = stringLoaderInterface;
        this.fileChooser = fileChooser;
    }

    @Override
    public ElectionDescription getElectionDescription() {
        updateCurrentDescription();
        return currentDescription;
    }

    public void updateCurrentDescription() {
        List<String> code;
        currentDescription.setVotingDeclLine(codeArea.getFirstLockedLine());
        System.out.println(currentDescription.getVotingDeclLine());
        String content = codeArea.getPane().getText();
        String lines[] = content.split("\n");
        for (String s : lines) {
            System.out.println(s);
        }
        code = Arrays.asList(lines);
        currentDescription.setCode(code);
    }

    public void findErrorsAndDisplayThem() {
        ArrayList<CodeError> errors = codeArea.getErrorCtrl().getErrorFinderList().getErrors();
        errorWindow.displayErrors(errors, (CErrorDisplayer) codeArea.getErrorCtrl().getDisplayer());
    }

    public void changeElectionDescriptionInput(ElectionTypeContainer cont) {
        currentDescription.setInputType(cont);
        for(ElectionDescriptionChangeListener l : descriptionChangeListeners) 
            l.inputChanged(cont);
    }
    
    public void changeElectionDescriptionOutput(ElectionTypeContainer cont) {
        currentDescription.setOutputType(cont);
        for(ElectionDescriptionChangeListener l : descriptionChangeListeners) 
            l.outputChanged(cont);
    }

    public void setcElectionEditorMenubarHandler(CElectionEditorMenubarHandler cElectionEditorMenubarHandler) {
        this.menubarHandler = cElectionEditorMenubarHandler;
    }

    public void setcElectionEditorToolbarHandler(CElectionEditorToolbarHandler cElectionEditorToolbarHandler) {
        this.toolbarHandler = cElectionEditorToolbarHandler;
    }

    public CElectionEditorMenubarHandler getcElectionEditorMenubarHandler() {
        return menubarHandler;
    }

    public CElectionEditorToolbarHandler getcElectionEditorToolbarHandler() {
        return toolbarHandler;
    }

    @Override
    public boolean isCorrect() {
        return codeArea.getErrorCtrl().getErrorFinderList().getErrors().isEmpty();
    }

    @Override
    public void stopReacting() {
    }

    @Override
    public void resumeReacting() {
    }

    /**
     * Getter
     * @return the CElectionCodeArea
     */
    public CElectionCodeArea getCodeArea() {
        return codeArea;
    }

    public boolean letUserEditElectionDescription(ElectionDescription description) throws BadLocationException {
        if (saveBeforeChangeHandler.ifHasChangedOpenDialog(currentDescription.getName())) {
            loadElectionDescription(description);
            fileChooser.setHasBeenSaved(true);
            return true;
        } else {
            return false;
        }
    }

    public void loadElectionDescription(ElectionDescription description) throws BadLocationException {
        this.currentDescription = description;
        gui.setNewCodeArea();
        codeArea = builder.createCElectionCodeArea(gui.getCodeArea(),
                gui.getCodeAreaScrollPane(),
                (CErrorDisplayer) codeArea.getErrorCtrl().getDisplayer());
        codeArea.letUserEditCode(description.getCode());
        codeArea.lockLine(description.getVotingDeclLine());
        codeArea.lockLine(description.getCode().size() - 1);
        saveBeforeChangeHandler.addNewTextPane(codeArea.getPane());
        gui.setWindowTitle(description.getName());
        for(ElectionDescriptionChangeListener l : descriptionChangeListeners) {
            l.inputChanged(description.getInputType());
            l.outputChanged(description.getOutputType());
        }
        findErrorsAndDisplayThem();
    }

    /**
     * Getter
     * @return the CCodeEditorGUI object of this class
     */
    public CCodeEditorGUI getGui() {
        return this.gui;
    }

    /**
     * Getter
     * @return the SaveBeforeChangeHandler object of this class
     */
    public SaveBeforeChangeHandler getSaveBeforeChangeHandler() {
        return this.saveBeforeChangeHandler;
    }
    
    public void addListener(ElectionDescriptionChangeListener l) {
        descriptionChangeListeners.add(l);
    }
    
    public void removeListener(ElectionDescriptionChangeListener l) {
        descriptionChangeListeners.remove(l);
    }
    
    public boolean getIsVisible() {
        return gui.isVisible();
    }
    
    public void setVisible(boolean vis) {
        gui.setVisible(vis);
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void updateStringIf(StringLoaderInterface stringLoaderInterface) {
        this.stringLoaderInterface = stringLoaderInterface;
        gui.updateStringRes(stringLoaderInterface);
        menubarHandler.updateStringRes(stringLoaderInterface);
        toolbarHandler.updateStringRes(stringLoaderInterface);
    }

    public StringLoaderInterface getStringInterface() {
        return this.stringLoaderInterface;
    }
}
