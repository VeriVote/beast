//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for redoing the last undone action
// * in the last focused BooleanExpEditorCodeArea.
// * @author Nikolai Schnell
// */
//public class UndoBoolUserAction extends UserAction {
//    private final BooleanExpEditor editor;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public UndoBoolUserAction(BooleanExpEditor editor) {
//        super("undo");
//        this.editor = editor;
//    }
//
//    @Override
//    public void perform() {
//        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList()
//        .getActionById("undo").perform();
//    }
//}
