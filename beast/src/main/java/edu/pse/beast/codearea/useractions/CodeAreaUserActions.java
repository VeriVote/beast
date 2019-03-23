//package edu.pse.beast.codearea.useractions;
//
//import java.util.ArrayList;
//
//import edu.pse.beast.codearea.CodeArea;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * This class creates all useractions which can be performed on a simple
// * codearea
// * @author Holger Klein
// */
//public class CodeAreaUserActions {
//    private CodeArea area;
//    private ArrayList<UserAction> actions = new ArrayList<>();
//
//    public CodeAreaUserActions(CodeArea area) {
//        this.area = area;
//        addCopyAction();
//        addCutAction();
//        addPasteAction();
//        addRedoAction();
//        addUndoAction();
//    }
//
//    public UserAction getActionById(String id) {
//        for(UserAction ac : actions) {
//            if(ac.getId() == id) return ac;
//        }
//        return null;
//    }
//
//    private void addCopyAction() {
//        actions.add(new CopyUserAction(area.getPane()));
//    }
//
//    private void addCutAction() {
//        actions.add(new CutUserAction(area));
//    }
//
//    private void addPasteAction() {
//        actions.add(new PasteUserAction(area));
//    }
//
//    private void addRedoAction() {
//        actions.add(new RedoLastUserAction(area.getActionlist()));
//    }
//
//    private void addUndoAction() {
//        actions.add(new UndoLastUserAction(area.getActionlist()));
//    }
//}