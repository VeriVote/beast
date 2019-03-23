//package edu.pse.beast.datatypes;
//
//import static org.junit.Assert.assertEquals;
//
//import org.junit.Test;
//
//import edu.pse.beast.datatypes.electioncheckparameter.ElectionCheckParameter;
//import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
//import edu.pse.beast.propertylist.model.PLModel;
//
///**
// *
// * @author Niels
// */
//public class ProjectTest {
//
//    /**
//     * Test of getElecDescr method, of class Project.
//     */
//    @Test
//    public void testGetElecDescr() {
//        System.out.println("getElecDescr");
//        ElectionDescription elec = new ElectionDescription("test", null, null, 0);
//        Project instance = new Project(null, null, elec, null);
//        ElectionDescription expResult = elec;
//        ElectionDescription result = instance.getElecDescr();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getPropList method, of class Project.
//     */
//    @Test
//    public void testGetPropList() {
//        System.out.println("getPropList");
//        PLModel propList = new PLModel();
//        Project instance = new Project(null, propList, null, null);
//        PLModel expResult = propList;
//        PLModel result = instance.getPropList();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of getElectionCheckParameter method, of class Project.
//     */
//    @Test
//    public void testGetElectionCheckParameter() {
//        System.out.println("getElectionCheckParameter");
//        ElectionCheckParameter params = new ElectionCheckParameter(null, null, null, null, Integer.BYTES, null);
//        Project instance = new Project(params, null, null, null);
//        ElectionCheckParameter expResult = params;
//        ElectionCheckParameter result = instance.getElectionCheckParameter();
//        assertEquals(expResult, result);
//    }
//
//    /**
//     * Test of setNewName method, of class Project.
//     */
//    @Test
//    public void testSetNewName() {
//        System.out.println("setNewName");
//        String newName = "newName";
//        Project instance = new Project(null, null, null, "test");
//        instance.setNewName(newName);
//        assertEquals(instance.getName(), newName);
// 
//    }
//
//    /**
//     * Test of getName method, of class Project.
//     */
//    @Test
//    public void testGetName() {
//        System.out.println("getName");
//        Project instance = new Project(null, null, null, "test");
//        String expResult = "test";
//        String result = instance.getName();
//        assertEquals(expResult, result);
//    }
//
//}
