package edu.pse.beast.options;

import toBeImplemented.CElectionEditor;

public class CElectionEditorOptions extends Options {
    private final CElectionEditor editor;
    private final CElectionCodeAreaOptions cElectionCodeAreaOptions;
    
    /**
     * 
     * @param id the id
     * @param editor the editor
     * @param cElectionCodeAreaOptions the options
     */
    public CElectionEditorOptions(String id, CElectionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        super(id);
        this.editor = editor;
        this.cElectionCodeAreaOptions = cElectionCodeAreaOptions;
    }

    /**
     * 
     * @return the editor
     */
    public CElectionEditor getEditor() {
        return editor;
    }

    /**
     * 
     * @return the options
     */
    public CElectionCodeAreaOptions getcElectionCodeAreaOptions() {
        return cElectionCodeAreaOptions;
    }

    @Override
    public void reapply() {
        // TODO Auto-generated method stub
        
    }
    
}
