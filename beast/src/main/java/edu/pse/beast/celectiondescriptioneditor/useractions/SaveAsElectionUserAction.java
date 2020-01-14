//package edu.pse.beast.celectiondescriptioneditor.useractions;
//
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// *
// * @author Holger Klein
// */
//public class SaveAsElectionUserAction extends UserAction {
//    private final CElectionDescriptionEditor electionDescriptionEditor;
//
//    public SaveAsElectionUserAction(CElectionDescriptionEditor electionDescriptionEditor) {
//        super("save_as");
//        this.electionDescriptionEditor = electionDescriptionEditor;
//    }
//
//    @Override
//    public void perform() {
//        ElectionDescription currentlyLoaded = electionDescriptionEditor.getElectionDescription();
//        if (electionDescriptionEditor.getFileChooser().saveObject(currentlyLoaded, true)) {
//            electionDescriptionEditor.getChangeHandler().updatePreValue();
//            electionDescriptionEditor.getView()
//            .setWindowTitle(electionDescriptionEditor.getElectionDescription().getName());
//            electionDescriptionEditor.findErrorsAndDisplayThem();
//        }
//    }
//}
