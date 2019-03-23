//package edu.pse.beast.parametereditor.useractions;
//
//import java.awt.Frame;
//
//import javax.swing.JFrame;
//
//import edu.pse.beast.parametereditor.ParameterEditor;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction to toggle visibility of the CElectionEditor window
// * @author NikolaiLMS
// */
//public class ShowHideCElectionEditor extends UserAction {
//    private final JFrame cElectionEditorWindow;
//    private final ParameterEditor editor;
//    /**
//     * Constructor
//     * @param editor ParameterEditor which this UserAction belongs to
//     * @param cElectionEditorWindow CElectionEditor window
//     */
//    public ShowHideCElectionEditor(ParameterEditor editor, JFrame cElectionEditorWindow) {
//        super("showCElectionEditor");
//        this.editor = editor;
//        this.cElectionEditorWindow = cElectionEditorWindow;
//    }
//
//
//    @Override
//    public void perform() {
//        if (editor.getReacts()) {
//            if (cElectionEditorWindow.isVisible() && cElectionEditorWindow.getState() == 0) {
//                cElectionEditorWindow.setVisible(false);
//            } else {
//                cElectionEditorWindow.setVisible(true);
//                cElectionEditorWindow.setState(Frame.NORMAL);
//                cElectionEditorWindow.requestFocus();
//            }
//        }
//    }
//}
