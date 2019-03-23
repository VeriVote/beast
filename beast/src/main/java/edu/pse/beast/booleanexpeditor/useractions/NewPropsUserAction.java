//package edu.pse.beast.booleanexpeditor.useractions;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
//import edu.pse.beast.toolbox.UserAction;
//
///**
// * UserAction subclass responsible for creating a new PreAndPostConditionsDescription object
// * and loading it into the editor and propertylist.
// * @author NikolaiLMS
// */
//public class NewPropsUserAction extends UserAction {
//    private final BooleanExpEditor booleanExpEditor;
//
//    /**
//     * Constructor
//     * @param booleanExpEditor the BooleanExpEditor object this UserAction belongs to
//     */
//    public NewPropsUserAction(BooleanExpEditor booleanExpEditor) {
//        super("new");
//        this.booleanExpEditor = booleanExpEditor;
//    }
//
//    /**
//     * Method that
//     * @return s an empty PreAndPostConditionsDescription object that can be loaded into the editor/propertylist.
//     */
//    public static PreAndPostConditionsDescription createEmptyPreAndPostConditionObject() {
//        FormalPropertiesDescription preDesc = new FormalPropertiesDescription("");
//        FormalPropertiesDescription postDesc = new FormalPropertiesDescription("");
//        SymbolicVariableList symbolicVariableList = new SymbolicVariableList();
//        return new PreAndPostConditionsDescription("NewFormalProperty", preDesc, postDesc,
//                        symbolicVariableList);
//    }
//
//    @Override
//    public void perform() {
//        if (booleanExpEditor.letUserEditPreAndPostConditions(createEmptyPreAndPostConditionObject(), false)) {
//            booleanExpEditor.getFileChooser().setHasBeenSaved(false);
//            booleanExpEditor.getPropertyListController().addNewProperty();
//        }
//    }
//}
