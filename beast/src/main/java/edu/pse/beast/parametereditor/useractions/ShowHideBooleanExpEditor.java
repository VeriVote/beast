package edu.pse.beast.parametereditor.useractions;

//import java.awt.Frame;
//
//import javax.swing.JFrame;
//
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction to toggle visibility of the BooleanExpEditor window
// * @author Nikolai Schnell
// */
//public class ShowHideBooleanExpEditor extends UserAction {
//    private final JFrame booleanExpEditorWindow;
//    private final ParameterEditor editor;
//    /**
//     * Constructor
//     * @param editor ParameterEditor which this UserAction belongs to
//     * @param booleanExpEditorWindow BooleanExpEditor window
//     */
//    public ShowHideBooleanExpEditor(ParameterEditor editor, JFrame booleanExpEditorWindow) {
//        super("showBooleanExpEditor");
//        this.editor = editor;
//        this.booleanExpEditorWindow = booleanExpEditorWindow;
//    }
//
//    @Override
//    public void perform() {
//        if (editor.getReacts()) {
//            if (booleanExpEditorWindow.isVisible() && booleanExpEditorWindow.getState() == 0) {
//                booleanExpEditorWindow.setVisible(false);
//            } else {
//                booleanExpEditorWindow.setVisible(true);
//                booleanExpEditorWindow.setState(Frame.NORMAL);
//                booleanExpEditorWindow.requestFocus();
//            }
//        }
//    }
//}
