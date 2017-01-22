package edu.pse.beast.options;

import toBeImplemented.CElectionEditor;

public class CElectionEditorOptions {
    private final CElectionEditor editor;
    private final CElectionCodeAreaOptions cElectionCodeAreaOptions;
    
    /**
     * 
     * @param editor the editor
     * @param cElectionCodeAreaOptions the options
     */
    public CElectionEditorOptions(CElectionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
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
    
}
