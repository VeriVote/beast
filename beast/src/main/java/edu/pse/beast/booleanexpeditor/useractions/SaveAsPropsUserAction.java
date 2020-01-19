package edu.pse.beast.booleanexpeditor.useractions;

//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.saverloader.FileChooser;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for saving Properties while forcing a save dialog.
// * @author Nikolai Schnell
// */
//public class SaveAsPropsUserAction extends UserAction {
//    private final BooleanExpEditor editor;
//    private final FileChooser fileChooser;
//
//    /**
//     * Constructor
//     * @param editor reference to the GUI controller.
//     */
//    public SaveAsPropsUserAction(BooleanExpEditor editor) {
//        super("save_as");
//        this.editor = editor;
//        this.fileChooser = editor.getFileChooser();
//    }
//
//    @Override
//    public void perform() {
//        PreAndPostConditionsDescription currentlyLoaded =
//            editor.getCurrentlyLoadedPreAndPostCondition();
//        if (fileChooser.saveObject(currentlyLoaded, true)) {
//            editor.getChangeHandler().updatePreValues();
//            editor.getView().setWindowTitle(
//                editor.getCurrentlyLoadedPreAndPostCondition().getName()
//            );
//            editor.findErrorsAndDisplayThem();
//        }
//    }
//}
