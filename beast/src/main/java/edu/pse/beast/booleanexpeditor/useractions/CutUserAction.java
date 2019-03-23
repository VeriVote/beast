//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for cutting text in the last focused BooleanExpEditorCodeAreas.
// * @author NikolaiLMS
// */
//public class CutUserAction extends UserAction {
//    private final BooleanExpEditor editor;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public CutUserAction(BooleanExpEditor editor) {
//        super("cut");
//        this.editor = editor;
//    }
//
//    @Override
//    public void perform() {
//        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("cut").perform();
//    }
//}
