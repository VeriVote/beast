package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class ElectionCopyUserAction extends UserAction{
    private CElectionDescriptionEditor electionEditor;

    public ElectionCopyUserAction(CElectionDescriptionEditor electionEditor) {
        super("copy");
        this.electionEditor = electionEditor;
    }


    @Override
    public void perform() {
        electionEditor.getCodeArea().getUserActionList().getActionById("copy").perform();
    }
}
