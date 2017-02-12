package edu.pse.beast.options.BooleanExpEditorOptions;

import edu.pse.beast.options.CodeAreaOptions.CodeAreaOptions;
import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
import edu.pse.beast.booleanexpeditor.booleanExpCodeArea.BooleanExpCodeArea;
import edu.pse.beast.options.Options;

public class BooleanExpCodeAreaOptions extends Options {
    private BooleanExpEditor editor;
    private final CodeAreaOptions codeAreaOptions;
    
    /**     * 
     * @param id the id of these options
     * @param booleanExpCodeArea the codearea
     * @param codeAreaOptions the options
     */
    public BooleanExpCodeAreaOptions(BooleanExpEditor editor,
            CodeAreaOptions codeAreaOptions) {
        super("booleanexpcodearea_opts");
        this.editor = editor;
        this.codeAreaOptions = codeAreaOptions;
        subOptions.add(codeAreaOptions);
    }

    BooleanExpCodeAreaOptions(BooleanExpEditor editor) {
        super("booleanexpcodearea_opts");
        this.editor = editor;
        this.codeAreaOptions = new CodeAreaOptions(editor.getPrePropCodeArea());
        subOptions.add(codeAreaOptions);
    }

    @Override
    protected void reapplySpecialized() {
        codeAreaOptions.setCodeArea(editor.getPrePropCodeArea());
        codeAreaOptions.reapply();
        codeAreaOptions.setCodeArea(editor.getPostPropCodeArea());
        codeAreaOptions.reapply();
    }
}
