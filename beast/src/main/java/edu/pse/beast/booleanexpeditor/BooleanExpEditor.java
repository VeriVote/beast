/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.UserActions.SaveBeforeChangeHandler;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.CodeAreaFocusListener;
import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.datatypes.descofvoting.ElectionTypeContainer;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.toolbox.MenuBarHandler;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;
import edu.pse.beast.toolbox.ToolbarHandler;

import javax.swing.*;
import java.util.ArrayList;

/**
 * The main class of this package that serves as an interface to the outside.
 * Part of the "Controller" in an MVC-Pattern.
 * @author NikolaiLMS
 */
public class BooleanExpEditor {
    private final BooleanExpEditorWindow window;
    private final SymbolicVarListController symbolicVarListController;
    private final BooleanExpEditorWindowStarter windowStarter;
    private final ErrorWindow errorWindow;
    private final PostAndPrePropertiesDescription currentlyLoadedPostAndPreProp;
    private MenuBarHandler menuBarHandler;
    private ToolbarHandler toolBarHandler;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;
    private final SaveBeforeChangeHandler saveBeforeChangeHandler;
    private final CodeAreaFocusListener codeAreaFocusListener;
    private final BooleanExpCodeAreaBuilder codeAreaBuilder;
    private ObjectRefsForBuilder refs;
    private CElectionDescriptionEditor cEditor;
    /**
     * Temporary Constructor declaration to build BooleanExpEditor for Dummy-GUI
     * @param window BooleanExpEditorWindow object
     * @param symbolicVarListController SymbolicVarListController object
     */
    BooleanExpEditor(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea,
                     BooleanExpEditorWindow window, SymbolicVarListController symbolicVarListController, ErrorWindow errorWindow,
                     SaveBeforeChangeHandler saveBeforeChangeHandler, CodeAreaFocusListener codeAreaFocusListener,
                     PostAndPrePropertiesDescription postAndPrePropertiesDescription, BooleanExpCodeAreaBuilder codeAreaBuilder,
                     ObjectRefsForBuilder refs, CElectionDescriptionEditor cEditor) {
        this.window = window;
        this.refs = refs;
        this.errorWindow = errorWindow;
        this.currentlyLoadedPostAndPreProp = postAndPrePropertiesDescription;
        this.symbolicVarListController = symbolicVarListController;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
        this.codeAreaBuilder = codeAreaBuilder;
        this.cEditor = cEditor;
        prePropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        postPropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        this.codeAreaFocusListener = codeAreaFocusListener;
        letUserEditPostAndPreProperties(postAndPrePropertiesDescription);
        windowStarter = new BooleanExpEditorWindowStarter(window);
        
    }

    void setToolBarHandler(ToolbarHandler toolBarHandler) {
        this.toolBarHandler = toolBarHandler;
    }

    void setMenuBarHandler(MenuBarHandler menuBarHandler) {
        this.menuBarHandler = menuBarHandler;
    }

    public PostAndPrePropertiesDescription getCurrentlyLoadedPostAndPreProp() {
        return currentlyLoadedPostAndPreProp;
    }

    /**
     * Executes the showWindow function in windowStarter.
     */
    public void showWindow() {
        windowStarter.showWindow();
    }

    public void findErrorsAndDisplayThem() {
        
    }

    /**
     * Loads and displays the given PostAndPrePropertiesDescrion Object.
     * @param postAndPrePropertiesDescription The PostAndPrePropertiesDescription Object
     * @return a boolean stating the success of the loading
     */
    public boolean letUserEditPostAndPreProperties(PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        if (saveBeforeChangeHandler.hasChanged()) {
            int option = window.showOptionPane(currentlyLoadedPostAndPreProp.getName());
            if (option == JOptionPane.YES_OPTION) {
                throw new UnsupportedOperationException("Not supported yet.");
            } else if (option == JOptionPane.CANCEL_OPTION) {
                return false;
            }
        }
        loadNewProperties(postAndPrePropertiesDescription);
        return true;
    }

    void loadNewProperties(PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        System.out.println("Loading symbolic variable list");        
        
        symbolicVarListController.setSymbVarList(postAndPrePropertiesDescription.getSymbolicVariableList());
        window.setNewTextpanes();
        saveBeforeChangeHandler.addNewTextPanes(window.getPrePropTextPane(), window.getPostPropTextPane());
        
        cEditor.removeListener(prePropCodeArea.getVariableErrorFinder());
        cEditor.removeListener(postPropCodeArea.getVariableErrorFinder());
        
        symbolicVarListController.getSymbolicVariableList().removeListener(prePropCodeArea.getVariableErrorFinder().getLis());
        symbolicVarListController.getSymbolicVariableList().removeListener(postPropCodeArea.getVariableErrorFinder().getLis());
        
        prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(refs, window.getPrePropTextPane(),
                window.getPrePropScrollPane(), symbolicVarListController.getSymbolicVariableList());
        postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(refs, window.getPostPropTextPane(),
                window.getPostPropScrollPane(), symbolicVarListController.getSymbolicVariableList());        
                
        cEditor.addListener(prePropCodeArea.getVariableErrorFinder());
        cEditor.addListener(postPropCodeArea.getVariableErrorFinder());
        
        postPropCodeArea.getVariableErrorFinder().inputChanged(cEditor.getElectionDescription().getInputType());
        postPropCodeArea.getVariableErrorFinder().outputChanged(cEditor.getElectionDescription().getOutputType());
        
        prePropCodeArea.getVariableErrorFinder().inputChanged(cEditor.getElectionDescription().getInputType());
        prePropCodeArea.getVariableErrorFinder().outputChanged(cEditor.getElectionDescription().getOutputType());
        
        
        
        prePropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        postPropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        codeAreaFocusListener.addNewCodeAreas(prePropCodeArea, postPropCodeArea);
        System.out.println("Loading properties");
        prePropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPrePropertiesDescription().getCode());
        postPropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPostPropertiesDescription().getCode());
        System.out.println("Finding errors");
        this.findErrorsAndDisplayThem();
        this.window.setWindowTitle(postAndPrePropertiesDescription.getName());
        saveBeforeChangeHandler.updatePreValues();
    }

    /**
     * Getter
     * @return the CodeAreaFocusListener instance of this class
     */
    public CodeAreaFocusListener getCodeAreaFocusListener() {
        return codeAreaFocusListener;
    }

    /**
     * Getter
     * @return the BooleanExpEditorWindow instance of this class
     */
    public BooleanExpEditorWindow getWindow() {
        return window;
    }

    public BooleanExpCodeArea getPostPropCodeArea() {
        return postPropCodeArea;
    }

    public BooleanExpCodeArea getPrePropCodeArea() {
        return prePropCodeArea;
    }
}