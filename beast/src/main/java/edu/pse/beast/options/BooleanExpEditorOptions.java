package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;

public class BooleanExpEditorOptions {
    private final BooleanExpEditor editor;
    private final BooleanExpCodeAreaOptions booleanExpCodeAreaOptions;
    
    /**
     * 
     * @param editor the editor
     * @param booleanExpCodeAreaOptions the options
     */
    public BooleanExpEditorOptions(BooleanExpEditor editor,
            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
        this.editor = editor;
        this.booleanExpCodeAreaOptions = booleanExpCodeAreaOptions;
    }
    
    /**
     * 
     * @return the options
     */
    public BooleanExpCodeAreaOptions getBooleanExpCodeAreaOptions() {
        return booleanExpCodeAreaOptions;
    }
    
    /**
     * 
     * @return the editor
     */
    public BooleanExpEditor getBooleanExpEditor() {
        return editor;
    }
}
