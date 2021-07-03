package edu.pse.beast.zzz.electionsimulator.useractions;

//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Lets the user save the PropertyList with the already-known
// * filename or calls SaveAsPropertyList.
// *
// * @author Justin Hecht
// */
//public class SavePropertyList extends UserAction {
//    private final PropertyList controller;
//
//    /**
//     * Constructor
//     * @param controller A reference to the PropertyList controller
//     */
//    public SavePropertyList(PropertyList controller) {
//        super("save");
//        this.controller = controller;
//    }
//
//    @Override
//    public void perform() {
//        if (controller.getFileChooser().saveObject(controller.getModel(), false)) {
//            controller.getChangeHandler().setChangedSinceSave(false);
//            controller.getView().setWindowTitle(controller.getModel().getName());
//        }
//    }
//}
