package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class ElectionRedoUserAction extends UserAction {
    private CElectionDescriptionEditor electionEditor;

    public ElectionRedoUserAction(CElectionDescriptionEditor electionEditor) {
        super("redo");
        this.electionEditor = electionEditor;
    }


    @Override
    public void perform() {
        electionEditor.getCodeArea().getUserActionList().getActionById("redo").perform();
    }
}
