//package edu.pse.beast.propertylist.useractions;
//
//import edu.pse.beast.propertylist.DeleteDescriptionAction;
//import edu.pse.beast.propertylist.PropertyList;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * Undoes changes to the PropertyList.
// * @author Justin Hecht
// */
//public class UndoChangesPropertyList extends UserAction {
//
//    private final PropertyList controller;
//
//    /**
//     * Constructor
//     * @param controller A reference to the PropertyList controller
//     */
//    public UndoChangesPropertyList(PropertyList controller) {
//        super("undo");
//        this.controller = controller;
//    }
//
//    @Override
//    public void perform() {
//        DeleteDescriptionAction lastAct = controller.getLastAction();
//        if (lastAct != null) {
//          lastAct.undo();
//        }
//    }
//}