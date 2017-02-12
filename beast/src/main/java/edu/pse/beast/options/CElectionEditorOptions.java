package edu.pse.beast.options;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;

public class CElectionEditorOptions extends Options {
    private  CElectionDescriptionEditor editor;
    private final CElectionCodeAreaOptions cElectionCodeAreaOptions;
    
    /**
     * 
     * @param id the id
     * @param editor the editor
     * @param cElectionCodeAreaOptions the options
     */
    public CElectionEditorOptions(
            CElectionDescriptionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        super("ceditor_opts");
        this.editor = editor;
        this.cElectionCodeAreaOptions = cElectionCodeAreaOptions;
        subOptions.add(cElectionCodeAreaOptions);
    }

    public CElectionEditorOptions(CElectionDescriptionEditor editor) {
        super("ceditor_opts");
        this.editor = editor;
        this.cElectionCodeAreaOptions = new CElectionCodeAreaOptions(editor.getCodeArea());
        subOptions.add(cElectionCodeAreaOptions);
    }
    
    @Override
    protected void reapplySpecialized() {
        cElectionCodeAreaOptions.setCodeArea(editor.getCodeArea());
    }    
}
