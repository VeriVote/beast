//package edu.pse.beast.booleanexpeditor.UserActions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for saving Properties whithout forcing a save dialog.
// * @author NikolaiLMS
// */
//public class SavePropsUserAction extends UserAction {
//    private final BooleanExpEditor editor;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public SavePropsUserAction(BooleanExpEditor editor) {
//        super("save");
//        this.editor = editor;
//    }
//
//    @Override
//    public void perform() {
//        if (editor.getFileChooser().saveObject(editor.getCurrentlyLoadedPreAndPostCondition(), false)) {
//            editor.getChangeHandler().updatePreValues();
//            editor.getView().setWindowTitle(editor.getCurrentlyLoadedPreAndPostCondition().getName());
//            editor.findErrorsAndDisplayThem();
//        }
//    }
//}
