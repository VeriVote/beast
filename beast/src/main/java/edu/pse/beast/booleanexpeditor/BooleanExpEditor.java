/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.CodeAreaFocusListener;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.toolbox.MenuBarHandler;
import edu.pse.beast.toolbox.ToolbarHandler;

import java.util.ArrayList;

/**
 * The main class of this package that serves as an interface to the outside.
 * Part of the "Controller" in an MVC-Pattern.
 * @author NikolaiLMS
 */
public class BooleanExpEditor {
    private final BooleanExpEditorWindow window;
    private SymbolicVarList symbolicVarList;
    private final BooleanExpEditorWindowStarter windowStarter;
    private final ErrorWindow errorWindow;
    private PostAndPrePropertiesDescription currentlyLoadedPostAndPreProp;
    private MenuBarHandler menuBarHandler;
    private ToolbarHandler toolBarHandler;
    private final BooleanExpCodeArea prePropCodeArea;
    private final BooleanExpCodeArea postPropCodeArea;
    private SaveBeforeChangeHandler saveBeforeChangeHandler;
    private CodeAreaFocusListener codeAreaFocusListener;

    /**
     * Temporary Constructor declaration to build BooleanExpEditor for Dummy-GUI
     * @param window BooleanExpEditorWindow object
     * @param symbolicVarList SymbolicVarList object
     */
    BooleanExpEditor(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea,
                     BooleanExpEditorWindow window, SymbolicVarList symbolicVarList, ErrorWindow errorWindow,
                     SaveBeforeChangeHandler saveBeforeChangeHandler, CodeAreaFocusListener codeAreaFocusListener,
                     PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        this.window = window;
        this.errorWindow = errorWindow;
        this.currentlyLoadedPostAndPreProp = postAndPrePropertiesDescription;
        this.symbolicVarList = symbolicVarList;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
        this.saveBeforeChangeHandler = saveBeforeChangeHandler;
        prePropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        postPropCodeArea.getPane().addFocusListener(codeAreaFocusListener);
        this.codeAreaFocusListener = codeAreaFocusListener;
        loadPostAndPreProperties(postAndPrePropertiesDescription);
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
        ArrayList<Error> errors = prePropCodeArea.getErrors();
        errors.addAll(postPropCodeArea.getErrors());
        errorWindow.displayErrors(errors);
    }

    /**
     * Loads and displays the given PostAndPrePropertiesDescrion Object.
     * @param postAndPrePropertiesDescription The PostAndPrePropertiesDescription Object
     * @return a boolean stating the success of the loading
     */
    public boolean loadPostAndPreProperties(PostAndPrePropertiesDescription postAndPrePropertiesDescription) {
        // TODO implement saveBeforeChangeListener call
        symbolicVarList.setSymbVarList(postAndPrePropertiesDescription.getSymbolicVariableList());
        prePropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPrePropertiesDescription().getCode());
        postPropCodeArea.getPane().setText(postAndPrePropertiesDescription.getPostPropertiesDescription().getCode());
        this.findErrorsAndDisplayThem();
        this.window.setWindowTitle(postAndPrePropertiesDescription.getName());
        saveBeforeChangeHandler.updatePreValues();
        return true;
    }

    public CodeAreaFocusListener getCodeAreaFocusListener() {
        return codeAreaFocusListener;
    }

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