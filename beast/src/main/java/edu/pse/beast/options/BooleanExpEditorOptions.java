package edu.pse.beast.options;

import edu.pse.beast.booleanexpeditor.BooleanExpEditor;

public class BooleanExpEditorOptions extends Options {
    private final BooleanExpEditor editor;
    private final BooleanExpCodeAreaOptions booleanExpCodeAreaOptions;
    
    /**
     * 
     * @param id the id of these options
     * @param editor the editor
     * @param booleanExpCodeAreaOptions the options
     */
    public BooleanExpEditorOptions(BooleanExpEditor editor,
            BooleanExpCodeAreaOptions booleanExpCodeAreaOptions) {
        super("booleanexpeditor_opts");
        this.editor = editor;
        this.booleanExpCodeAreaOptions = booleanExpCodeAreaOptions;
        subOptions.add(booleanExpCodeAreaOptions);
    }

    BooleanExpEditorOptions(BooleanExpEditor editor) {
        super("booleanexpeditor_opts");
        this.editor = editor;
        this.booleanExpCodeAreaOptions = new BooleanExpCodeAreaOptions(editor);
        subOptions.add(booleanExpCodeAreaOptions);
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

    @Override
    protected void reapplySpecialized() {
       
    }
}
