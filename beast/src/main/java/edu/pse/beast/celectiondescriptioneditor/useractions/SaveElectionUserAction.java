//package edu.pse.beast.celectiondescriptioneditor.useractions;
//
//import edu.pse.beast.celectiondescriptioneditor.CElectionDescriptionEditor;
//import edu.pse.beast.toolbox.UserAction;
//
//
///**
// *
// * @author Holger-Desktop
// */
//public class SaveElectionUserAction extends UserAction {
//    private final CElectionDescriptionEditor cElectionDescriptionEditor;
//
//    public SaveElectionUserAction(CElectionDescriptionEditor cElectionDescriptionEditor) {
//        super("save");
//        this.cElectionDescriptionEditor = cElectionDescriptionEditor;
//    }
//    
//    @Override
//    public void perform() {
//        if (cElectionDescriptionEditor.getFileChooser().saveObject(cElectionDescriptionEditor.getElectionDescription(), false)) {
//            cElectionDescriptionEditor.getChangeHandler().updatePreValue();
//            cElectionDescriptionEditor.getView().setWindowTitle(cElectionDescriptionEditor.getElectionDescription().getName());
//            cElectionDescriptionEditor.findErrorsAndDisplayThem();
//        }
//    }
//    
//}
