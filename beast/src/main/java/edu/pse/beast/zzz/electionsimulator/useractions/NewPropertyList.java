package edu.pse.beast.zzz.electionsimulator.useractions;

//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Adds new properties to PropertyList.
// * @author Justin Hecht
// */
//public class NewPropertyList extends UserAction {
//    private final PropertyList controller;
//
//    /**
//     * Constructor
//     * @param controller A reference to the PropertyList controller
//     */
//    public NewPropertyList(PropertyList controller) {
//        super("new");
//        this.controller = controller;
//    }
//
//    @Override
//    public void perform() {
//        if (controller.getChangeHandler().hasChanged()) {
//            if (!controller.getFileChooser().openSaveChangesDialog(controller.getModel())) {
//                return;
//            }
//        }
//        controller.getChangeHandler().setChangedSinceSave(false);
//        controller.resetActionList();
//        controller.getModel().setNewList();
//        //controller.getFileChooser().setHasBeenSaved(false);
//    }
//
//}
