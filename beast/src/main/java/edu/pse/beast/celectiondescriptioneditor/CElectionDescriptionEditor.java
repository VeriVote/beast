/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.celectiondescriptioneditor;

import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeArea;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.CElectionCodeAreaBuilder;
import edu.pse.beast.celectiondescriptioneditor.CElectionCodeArea.ErrorHandling.CErrorDisplayer;
import edu.pse.beast.celectiondescriptioneditor.View.CCodeEditorWindow;
import edu.pse.beast.celectiondescriptioneditor.View.ErrorWindow;
import edu.pse.beast.codearea.ErrorHandling.CodeError;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.electiondescription.ElectionDescriptionChangeListener;
import edu.pse.beast.highlevel.ElectionDescriptionSource;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.saverloader.FileChooser;
import edu.pse.beast.toolbox.UserAction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.text.BadLocationException;


/**
 *  
 * @author Holger Klein
 */
public class CElectionDescriptionEditor implements ElectionDescriptionSource{
    private CElectionCodeArea codeArea;
    private ElectionDescription currentDescription;
    private CCodeEditorWindow window;
    private CElectionCodeAreaBuilder builder;
    private ErrorWindow errorWindow;
    private CElectionDescriptionEditorChangeHandler changeHandler;
    private ArrayList<ElectionDescriptionChangeListener> descriptionChangeListeners = new ArrayList<>();
    private CElectionEditorMenubarHandler menubarHandler;
    private CElectionEditorToolbarHandler toolbarHandler;
    private StringLoaderInterface stringLoaderInterface;
    private FileChooser fileChooser;
    private ArrayList<UserAction> userActions = new ArrayList<>();
    private ArrayList<Character> userActionChars = new ArrayList<>();
    private Boolean wasVisible;

    public CElectionDescriptionEditor(
            CElectionCodeArea codeArea,
            CCodeEditorWindow gui,
            CElectionCodeAreaBuilder builder,
            ErrorWindow errorWindow,
            CElectionDescriptionEditorChangeHandler CElectionDescriptionEditorChangeHandler,
            StringLoaderInterface stringLoaderInterface,
            FileChooser fileChooser) {
        this.codeArea = codeArea;
        this.window = gui;
        this.builder = builder;
        this.errorWindow = errorWindow;
        this.changeHandler = CElectionDescriptionEditorChangeHandler;
        this.stringLoaderInterface = stringLoaderInterface;
        this.fileChooser = fileChooser;
    }

    @Override
    public ElectionDescription getElectionDescription() {
        updateCurrentDescription();
        return currentDescription;
    }

    public void addUserAction(char c, UserAction ac) {
        userActions.add(ac);
        userActionChars.add(c);
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

    public void setcElectionEditorMenubarHandler(CElectionEditorMenubarHandler cElectionEditorMenubarHandler) {
        this.menubarHandler = cElectionEditorMenubarHandler;
    }

    public void setcElectionEditorToolbarHandler(CElectionEditorToolbarHandler cElectionEditorToolbarHandler) {
        this.toolbarHandler = cElectionEditorToolbarHandler;
    }

    @Override
    public boolean isCorrect() {
        return codeArea.getErrorCtrl().getErrorFinderList().getErrors().isEmpty();
    }

    @Override
    public void stopReacting() {
        wasVisible = window.isVisible();
        window.setVisible(false);
    }

    @Override
    public void resumeReacting() {
        if (wasVisible) {
            window.setVisible(true);
        }
    }

    /**
     * Getter
     * @return the CElectionCodeArea
     */
    public CElectionCodeArea getCodeArea() {
        return codeArea;
    }

    public boolean letUserEditElectionDescription(ElectionDescription description) throws BadLocationException {
        if (changeHandler.hasChanged()) {
            if (fileChooser.openSaveChangesDialog(getElectionDescription())) {
                loadElectionDescription(description);
                return true;
            } else {
                return false;
            }
        } else {
            loadElectionDescription(description);
            return true;
        }
    }

    public void loadElectionDescription(ElectionDescription description) throws BadLocationException {
        this.currentDescription = description;
        window.setNewCodeArea();
        
        codeArea.getErrorCtrl().stopThread();
        codeArea.getAutoComplCtrl().stopThread();
        
        codeArea = builder.createCElectionCodeArea(window.getCodeArea(),
                window.getCodeAreaScrollPane(),
                new CErrorDisplayer(window.getCodeArea(), stringLoaderInterface));
        for (int i = 0; i < userActions.size(); i++) {
            UserAction get = userActions.get(i);
            char c = userActionChars.get(i);
            codeArea.linkActionToShortcut(c, get);
        }
        
        codeArea.letUserEditCode(description.getCode());
        codeArea.lockLine(description.getVotingDeclLine());
        codeArea.lockLine(description.getCode().size() - 1);
        changeHandler.addNewTextPane(codeArea.getPane());
        window.setWindowTitle(description.getName());
        for(ElectionDescriptionChangeListener l : descriptionChangeListeners) {
            l.inputChanged(description.getInputType());
            l.outputChanged(description.getOutputType());
        }
        findErrorsAndDisplayThem();
    }

    /**
     * Getter
     * @return the CCodeEditorWindow object of this class
     */
    public CCodeEditorWindow getView() {
        return this.window;
    }

    /**
     * Getter
     * @return the ChangeHandler object of this class
     */
    public CElectionDescriptionEditorChangeHandler getChangeHandler() {
        return this.changeHandler;
    }
    
    public void addListener(ElectionDescriptionChangeListener l) {
        descriptionChangeListeners.add(l);
    }
    
    public void removeListener(ElectionDescriptionChangeListener l) {
        descriptionChangeListeners.remove(l);
    }

    public void setVisible(boolean vis) {
        window.setVisible(vis);
    }

    public FileChooser getFileChooser() {
        return fileChooser;
    }

    public void updateStringIf(StringLoaderInterface stringLoaderInterface) {
        this.stringLoaderInterface = stringLoaderInterface;
        window.updateStringRes(stringLoaderInterface);
        menubarHandler.updateStringRes(stringLoaderInterface);
        toolbarHandler.updateStringRes(stringLoaderInterface);
    }

    public StringLoaderInterface getStringInterface() {
        return this.stringLoaderInterface;
    }
}
