/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.booleanexpeditor;

/**
 * The main class of this package that serves as an interface to the outside.
 * @author Nikolai
 */
class BooleanExpEditor {
    private BooleanExpEditorWindow window;
    private SymbolicVarList symbolicVarList;
    private BooleanExpEditorWindowStarter windowStarter;

    /**
     * Temporary Constructor to build BooleanExpEditor for Dummy-GUI
     * @param window BooleanExpEditorWindow object
     * @param symbolicVarList SymbolicVarList object
     */
    BooleanExpEditor(BooleanExpEditorWindow window, SymbolicVarList symbolicVarList, ErrorWindow errorWindow){
        this.window = window;
        this.symbolicVarList = symbolicVarList;
        windowStarter = new BooleanExpEditorWindowStarter(window);
    }
}
