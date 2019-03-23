//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for copying text in the last focused BooleanExpEditorCodeArea.
// * @author NikolaiLMS
// */
//public class CopyUserAction extends UserAction {
//
//    private final BooleanExpEditor editor;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public CopyUserAction(BooleanExpEditor editor) {
//        super("copy");
//        this.editor = editor;
//    }
//
//    @Override
//    public void perform() {
//        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("copy").perform();
//    }
//
//}
