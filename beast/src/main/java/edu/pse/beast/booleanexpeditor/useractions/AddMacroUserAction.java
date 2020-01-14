package edu.pse.beast.booleanexpeditor.useractions;
//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.toolbox.UserAction;
//
//
///**
// * Responsible for adding BooleanExpEditorMacro objects to the last
// * focused BooleanExpEditorCodeArea.
// * Subclass of UserAction.
// * @author Nikolai Schnell
// */
//public class AddMacroUserAction extends UserAction {
//    private final BooleanExpEditorMacro macro;
//    private final BooleanExpEditor booleanExpEditor;
//
//    /**
//     * Constructor
//     * @param macro the BooleanExpEditorMacro object this UserAction adds
//     * @param booleanExpEditor the BooleanExpEditor object this UserAction belongs to
//     * @param id the ID of this UserAction
//     */
//    public AddMacroUserAction(String id, BooleanExpEditorMacro macro,
//                              BooleanExpEditor booleanExpEditor) {
//        super(id);
//        this.macro = macro;
//        this.booleanExpEditor = booleanExpEditor;
//    }
//
//    @Override
//    public void perform() {
//        booleanExpEditor.getCodeAreaFocusListener().getLastFocused().
//                insertString(macro.toString());
//    }
//}
