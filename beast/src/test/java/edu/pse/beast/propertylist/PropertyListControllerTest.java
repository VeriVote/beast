package edu.pse.beast.propertylist;

//import static org.junit.Assert.assertEquals;
//import static org.junit.Assert.assertFalse;
//
//import java.util.ArrayList;
//
//import org.junit.After;
//import org.junit.AfterClass;
//import org.junit.Before;
//import org.junit.BeforeClass;
//import org.junit.Test;
//
//import edu.pse.beast.booleanexpeditor.BooleanExpEditor;
//import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
//import edu.pse.beast.propertylist.model.PLModel;
//import edu.pse.beast.propertylist.model.PropertyItem;
//
///**
// * Tests the commands for changing the PropertyList.
// * @author Justin Hecht
// */
//public class PropertyListControllerTest {
//    static PropertyList list;
//    static BooleanExpEditor editor;
//
//    @BeforeClass
//    public static void setUpBeforeClass() throws Exception {
//        TestHelper helper = new TestHelper();
//        //helper.startNewBEASTInstance();
//        //list = helper.getPropertyList();
//
//        editor = helper.getBooleanExpEditor();
//        list = new PropertyList(new PLModel(), editor);
//    }
//
//    @AfterClass
//    public static void tearDownAfterClass() throws Exception {
//    }
//
//    @Before
//    public void setUp() throws Exception {
//        list.addNewProperty();
//        list.addNewProperty();
//        list.addNewProperty();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        list.setNewList();
//    }
//
//    @Test
//    public void changeNameTest() {
//        PropertyItem item = list.getList().get(0);
//        editor.letUserEditPreAndPostConditions(item.getDescription(), false);
//        list.changeName(item, "testname");
//
//        assertEquals(item.getDescription().getName(), "testname");
//        assertFalse(list.changeName(list.getList().get(1), "testname"));
//    }
//
//    @Test
//    public void changeTestStatusTest() {
//        boolean before = list.getList().get(0).getTestStatus();
//        list.setTestStatus(list.getList().get(0), !before);
//        assertEquals(list.getList().get(0).getTestStatus(), !before);
//    }
//
//    @Test
//    public void deleteItemTest() {
//        list.deleteProperty(list.getList().get(0));
//        assertEquals(list.getList().size(), 2);
//        //assertFalse(list.deleteProperty(null));
//        list.deleteProperty(list.getList().get(1));
//        assertEquals(list.getList().size(), 1);
//    }
//
//    @Test
//    public void addDescriptionTest() {
//        PropertyItem item = new PropertyItem();
//        item.setDescriptionName("testname");
//        list.addDescription(item);
//        assertEquals(list.getList().size(), 4);
//        list.addDescription(item);
//        assertEquals(list.getList().get(3), item);
//  }
//
//    @Test
//    public void addPropertyTest() {
//        list.addNewProperty();
//        assertEquals(list.getList().size(), 4);
//    }
//
//    @Test
//    public void editPropertyTest() {
//        list.editProperty(list.getList().get(1));
//    }
//
//    @Test
//    public void getTestedList() {
//        list.getList().get(0).setTestStatus(true);
//        ArrayList<PreAndPostConditionsDescription> testedList =
//            list.getPreAndPostConditionsDescriptionsCheck();
//        assertEquals(testedList.size(), 1);
//    }
//}
