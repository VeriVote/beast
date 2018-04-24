//package edu.pse.beast.booleanexpeditor.UserActions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Responsible for adding BooleanExpEditorConst objects to the last focused BooleanExpEditorCodeArea.
// * Subclass of UserAction.
// * @author NikolaiLMS
// */
//public class AddConstUserAction extends UserAction {
//    private final BooleanExpEditorConst booleanExpEditorConst;
//    private final BooleanExpEditor booleanExpEditor;
//
//    /**
//     * Constructor
//     * @param booleanExpEditorConst the BooleanExpEditorConst object this UserAction adds
//     * @param booleanExpEditor the BooleanExpEditor object this UserAction belongs to
//     * @param id the ID of this UserAction
//     */
//    public AddConstUserAction(String id, BooleanExpEditorConst booleanExpEditorConst, BooleanExpEditor booleanExpEditor)
//    {
//        super("numberOf" + id);
//        this.booleanExpEditor = booleanExpEditor;
//        this.booleanExpEditorConst = booleanExpEditorConst;
//    }
//
//    @Override
//    public void perform() {
//        booleanExpEditor.getCodeAreaFocusListener().getLastFocused().
//                insertString(booleanExpEditorConst.toString());
//    }
//}
