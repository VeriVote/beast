package edu.pse.beast.zzz.electionsimulator.useractions;

//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Lets the user save the PropertyList with a given filename.
// * @author Justin Hecht
// */
//public class SaveAsPropertyList extends UserAction {
//    private final PropertyList controller;
//
//    /**
//     * Constructor
//     * @param controller A reference to the PropertyList controller
//     */
//    public SaveAsPropertyList(PropertyList controller) {
//        super("save_as");
//        this.controller = controller;
//    }
//
//    @Override
//    public void perform() {
//        if (controller.getFileChooser().saveObject(controller.getModel(), true)) {
//            controller.getChangeHandler().setChangedSinceSave(false);
//            controller.getView().setWindowTitle(controller.getModel().getName());
//        }
//    }
//}
