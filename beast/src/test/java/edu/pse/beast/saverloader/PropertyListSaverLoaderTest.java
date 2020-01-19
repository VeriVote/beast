package edu.pse.beast.saverloader;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//import static org.junit.Assert.assertTrue;
//
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
//import edu.pse.beast.propertylist.model.PLModel;
//import edu.pse.beast.propertylist.model.PropertyItem;
//import edu.pse.beast.saverloader.PropertyListSaverLoader;
//import edu.pse.beast.types.InternalTypeContainer;
//import edu.pse.beast.types.InternalTypeRep;
//
///**
// * JUnit test class for saverloader.PropertyListSaverLoader.
// * @author Nikolai Schnell
// */
//public class PropertyListSaverLoaderTest {
//    private static PLModel plModel;
//
//    @BeforeClass
//    public static void setUpClass() {
//        FormalPropertiesDescription pre = new FormalPropertiesDescription(
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        FormalPropertiesDescription post = new FormalPropertiesDescription(
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        SymbolicVariableList list = new SymbolicVariableList();
//        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
//        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
//        list.addSymbolicVariable("candidate",
//                                 new InternalTypeContainer(InternalTypeRep.CANDIDATE));
//        list.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
//        PreAndPostConditionsDescription description1 =
//            new PreAndPostConditionsDescription("description1",
//                                                pre, post, list);
//        PreAndPostConditionsDescription description2 =
//            new PreAndPostConditionsDescription("description2",
//                                                pre, post, list);
//
//        plModel = new PLModel();
//        plModel.initialize();
//        PropertyItem propertyItem = new PropertyItem(description1, true, true);
//        PropertyItem propertyItem2 = new PropertyItem(description2, false, false);
//        plModel.addDescription(propertyItem);
//        plModel.addDescription(propertyItem2);
//    }
//
//    /**
//     * Tests the PropertyListSaverLoader by creating a saveString from
//     * a PLModel object, then recreating that object from the saveString
//     * and checking its integrity.
//     */
//    @Test
//    public void testSaverLoader() throws Exception {
//        String saveString = new PropertyListSaverLoader().createSaveString(plModel);
//        PLModel recreatedPLModel =
//            (PLModel) new PropertyListSaverLoader().createFromSaveString(saveString);
//
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getName(), "description1");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getPostConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(0)
//                .getDescription().getPreConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertTrue(recreatedPLModel.getPropertyList().get(0).getTestStatus());
//
//        // check SymbolicVariableList for integrity
//        SymbolicVariableList recreatedList =
//            recreatedPLModel.getPropertyList().get(1).getDescription().getSymVarList();
//        assertEquals(recreatedList.getSymbolicVariables().get(0).getId(), "voter1");
//        assertEquals(recreatedList.getSymbolicVariables().get(0)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedList.getSymbolicVariables().get(1).getId(), "voter2");
//        assertEquals(recreatedList.getSymbolicVariables().get(1)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedList.getSymbolicVariables().get(2).getId(), "candidate");
//        assertEquals(recreatedList.getSymbolicVariables().get(2)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
//        assertEquals(recreatedList.getSymbolicVariables().get(3).getId(), "seat");
//        assertEquals(recreatedList.getSymbolicVariables().get(3)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
//
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getName(), "description2");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getPostConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertEquals(recreatedPLModel.getPropertyList().get(1)
//                .getDescription().getPreConditionsDescription().getCode(),
//                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
//        assertFalse(recreatedPLModel.getPropertyList().get(1).getTestStatus());
//
//        // check SymbolicVariableList for integrity
//        recreatedList =
//            recreatedPLModel.getPropertyList().get(1).getDescription().getSymVarList();
//        assertEquals(recreatedList.getSymbolicVariables().get(0).getId(), "voter1");
//        assertEquals(recreatedList.getSymbolicVariables().get(0)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedList.getSymbolicVariables().get(1).getId(), "voter2");
//        assertEquals(recreatedList.getSymbolicVariables().get(1)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
//        assertEquals(recreatedList.getSymbolicVariables().get(2).getId(), "candidate");
//        assertEquals(recreatedList.getSymbolicVariables().get(2)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
//        assertEquals(recreatedList.getSymbolicVariables().get(3).getId(), "seat");
//        assertEquals(recreatedList.getSymbolicVariables().get(3)
//                .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
//    }
//}
