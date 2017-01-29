package edu.pse.beast.options;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;

public class CElectionEditorOptions extends Options {
    private final CElectionDescriptionEditor editor;
    private final CElectionCodeAreaOptions cElectionCodeAreaOptions;
    
    /**
     * 
     * @param id the id
     * @param editor the editor
     * @param cElectionCodeAreaOptions the options
     */
    public CElectionEditorOptions(String id, CElectionDescriptionEditor editor,
            CElectionCodeAreaOptions cElectionCodeAreaOptions) {
        super(id);
        this.editor = editor;
        this.cElectionCodeAreaOptions = cElectionCodeAreaOptions;
    }

    /**
     * 
     * @return the editor
     */
    public CElectionDescriptionEditor getEditor() {
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
