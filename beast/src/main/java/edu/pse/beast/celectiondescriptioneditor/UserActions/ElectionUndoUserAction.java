//package edu.pse.beast.celectiondescriptioneditor.UserActions;
//
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * @author NikolaiLMS
// */
//public class ElectionUndoUserAction extends UserAction {
//
//    private final CElectionDescriptionEditor electionEditor;
//
//    public ElectionUndoUserAction(CElectionDescriptionEditor electionEditor) {
//        super("undo");
//        this.electionEditor = electionEditor;
//    }
//
//    @Override
//    public void perform() {
//        electionEditor.getCodeArea().getUserActionList().getActionById("undo").perform();
//    }
//}
