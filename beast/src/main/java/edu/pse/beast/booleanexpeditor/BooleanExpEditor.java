/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.toolbox.MenuBarHandler;
import edu.pse.beast.toolbox.ToolbarHandler;

/**
 * The main class of this package that serves as an interface to the outside.
 * Part of the "Controller" in an MVC-Pattern.
 * @author NikolaiLMS
 */
public class BooleanExpEditor {
    private BooleanExpEditorWindow window;
    private SymbolicVarList symbolicVarList;
    private BooleanExpEditorWindowStarter windowStarter;
    private ErrorWindow errorWindow;
    private MenuBarHandler menuBarHandler;
    private ToolbarHandler toolBarHandler;
    private BooleanExpCodeArea prePropCodeArea;
    private BooleanExpCodeArea postPropCodeArea;

    /**
     * Temporary Constructor declaration to build BooleanExpEditor for Dummy-GUI
     * @param window BooleanExpEditorWindow object
     * @param symbolicVarList SymbolicVarList object
     */
    BooleanExpEditor(BooleanExpCodeArea prePropCodeArea, BooleanExpCodeArea postPropCodeArea,
                     BooleanExpEditorWindow window, SymbolicVarList symbolicVarList, ErrorWindow errorWindow,
                     BooleanExpEditorMenubarHandler menuBarHandler, BooleanExpEditorToolbarHandler toolBarHandler){
        this.window = window;
        this.errorWindow = errorWindow;
        this.symbolicVarList = symbolicVarList;
        this.prePropCodeArea = prePropCodeArea;
        this.postPropCodeArea = postPropCodeArea;
        this.menuBarHandler = menuBarHandler;
        this.toolBarHandler = toolBarHandler;
        windowStarter = new BooleanExpEditorWindowStarter(window);
    }

    /**
     * Executes the showWindow function in windowStarter.
     */
    public void showWindow() {
        windowStarter.showWindow();
    }
}
