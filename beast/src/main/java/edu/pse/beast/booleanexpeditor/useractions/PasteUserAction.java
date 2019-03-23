//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for pasting text in BooleanExpEditorCodeAreas.
// * @author Nikolai Schnell
// */
//public class PasteUserAction extends UserAction {
//    private final BooleanExpEditor editor;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public PasteUserAction(BooleanExpEditor editor) {
//        super("paste");
//        this.editor = editor;
//    }
//
//    @Override
//    public void perform() {
//        editor.getCodeAreaFocusListener().getLastFocused().getUserActionList().getActionById("paste").perform();
//    }
//}
