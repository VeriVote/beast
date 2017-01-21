package edu.pse.beast.booleanexpeditor;

import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeAreaBuilder;
import edu.pse.beast.toolbox.ObjectRefsForBuilder;

/**
 * Builder Class to create BooleanExpEditor Object. Called by PropertyList.
 * @author Nikolai
 */
public class BooleanExpEditorBuilder{

    /**
     * Constructor
     * @param objectRefsForBuilder Object with references to needed interfaces to build GUI
     * @return BooleanExpEditor Object
     */
    public static BooleanExpEditor createBooleanExpEditorObject(ObjectRefsForBuilder objectRefsForBuilder) {
        BooleanExpCodeAreaBuilder codeAreaBuilder = new BooleanExpCodeAreaBuilder();
        BooleanExpEditorWindow window = new BooleanExpEditorWindow();
        window.updateStringRes(objectRefsForBuilder.getStringIF());
        SymbolicVarList symbolicVarList = new SymbolicVarList(window.getSymVarList(), window.getAddSymVarButton(),
                window.getRemoveSymVarButton(), objectRefsForBuilder.getStringIF());
        ErrorWindow errorWindow = new ErrorWindow(window.getErrorTextPane(), objectRefsForBuilder.getStringIF());
        BooleanExpCodeArea prePropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPrePropTextPane(), window.getPrePropScrollPane());
        BooleanExpCodeArea postPropCodeArea = codeAreaBuilder.createBooleanExpCodeAreaObject(objectRefsForBuilder,
                window.getPrePropTextPane(), window.getPostPropScrollPane());
        return new BooleanExpEditor(prePropCodeArea, postPropCodeArea, window, symbolicVarList, errorWindow);
    }
}
