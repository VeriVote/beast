package edu.pse.beast.SaverLoader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import edu.pse.beast.saverloader.PropertyListSaverLoader;
import org.junit.*;

/**
 * @author NikolaiLMS
 */
public class PropertyListSaverLoaderTest {
    public PropertyListSaverLoaderTest() {
    }

    @BeforeClass
    public static void setUpClass() {
    }

    @AfterClass
    public static void tearDownClass() {
    }

    @Before
    public void setUp() {
    }

    @After
    public void tearDown() {
    }

    /**
     * Test of createSaveString method, of class PostAndPrePropertiesDescriptionSaverLoader.
     */
    @Test
    public void testCreateSaveString() {
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);

        PLModel plModel = new PLModel();
        plModel.initialize();
        PropertyItem propertyItem = new PropertyItem(description, true);
        PropertyItem propertyItem2 = new PropertyItem(description, false);
        plModel.addDescription(propertyItem);
        plModel.addDescription(propertyItem2);

        System.out.println(PropertyListSaverLoader.createSaveString(plModel));

    }

    /**
     * Test of createFromSaveString method, of class PostAndPrePropertiesDescriptionSaverLoader.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("cand", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("s", new InternalTypeContainer(InternalTypeRep.SEAT));
        PostAndPrePropertiesDescription description = new PostAndPrePropertiesDescription("postAndPre", pre, post, list);

        PLModel plModel = new PLModel();
        plModel.initialize();
        PropertyItem propertyItem = new PropertyItem(description, true);
        PropertyItem propertyItem2 = new PropertyItem(description, false);
        plModel.addDescription(propertyItem);
        plModel.addDescription(propertyItem2);

        PLModel plModel1 = PropertyListSaverLoader.createFromSaveString(PropertyListSaverLoader.createSaveString(plModel));
        plModel.getList().get(0);

        System.out.println(plModel1.getList().get(0).getDescription().getName());
        System.out.println(plModel1.getList().get(0).getDescription().getPostPropertiesDescription());
        System.out.println(plModel1.getList().get(0).getDescription().getPrePropertiesDescription());
        System.out.println(plModel1.getList().get(0).getDescription().getSymbolicVariableList().get(0));
        System.out.println(plModel1.getList().get(0).getDescription().getSymbolicVariableList().get(1));
        System.out.println(plModel1.getList().get(0).getDescription().getSymbolicVariableList().get(2));
        System.out.println(plModel1.getList().get(0).getDescription().getSymbolicVariableList().get(3));
        System.out.println(plModel1.getList().get(0).willBeTested());
        System.out.println(plModel1.getList().get(1).getDescription().getName());
        System.out.println(plModel1.getList().get(1).getDescription().getPostPropertiesDescription());
        System.out.println(plModel1.getList().get(1).getDescription().getPrePropertiesDescription());
        System.out.println(plModel1.getList().get(1).getDescription().getSymbolicVariableList().get(0));
        System.out.println(plModel1.getList().get(1).getDescription().getSymbolicVariableList().get(1));
        System.out.println(plModel1.getList().get(1).getDescription().getSymbolicVariableList().get(2));
        System.out.println(plModel1.getList().get(1).getDescription().getSymbolicVariableList().get(3));
        System.out.println(plModel1.getList().get(1).willBeTested());

    }
}
