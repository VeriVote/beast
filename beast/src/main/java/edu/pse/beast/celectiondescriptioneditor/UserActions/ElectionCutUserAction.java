package edu.pse.beast.celectiondescriptioneditor.UserActions;

import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
import edu.pse.beast.toolbox.UserAction;

/**
 * @author NikolaiLMS
 */
public class ElectionCutUserAction extends UserAction {

    private final CElectionDescriptionEditor electionEditor;

    public ElectionCutUserAction(CElectionDescriptionEditor electionEditor) {
        super("cut");
        this.electionEditor = electionEditor;
    }

    @Override
    public void perform() {
        electionEditor.getCodeArea().getUserActionList().getActionById("cut").perform();
    }
}
