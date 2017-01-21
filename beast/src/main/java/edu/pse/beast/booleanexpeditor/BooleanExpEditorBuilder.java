package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 * Builder Class to create BooleanExpEditor Object. Called by PropertyList.
 * @author Nikolai
 */
public class BooleanExpEditorBuilder {

    /**
     * Constructor
     * @param objectRefsForBuilder Object with references to needed interfaces to build GUI
     * @return BooleanExpEditor Object
     */
    public BooleanExpEditor createBooleanExpEditorObject(ObjectRefsForBuilder objectRefsForBuilder) {
        BooleanExpEditorWindow window = new BooleanExpEditorWindow(objectRefsForBuilder.getStringIF());
        SymbolicVarList symbolicVarList = new SymbolicVarList(window.getSymVarList(), window.getAddSymVarButton(),
                window.getRemoveSymVarButton(), objectRefsForBuilder.getStringIF());
        ErrorWindow errorWindow = new ErrorWindow(window.getErrorTextPane(), objectRefsForBuilder.getStringIF());
        return new BooleanExpEditor(window, symbolicVarList, errorWindow);
    }
}
