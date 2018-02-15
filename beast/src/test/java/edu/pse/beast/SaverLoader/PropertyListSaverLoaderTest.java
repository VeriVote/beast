package edu.pse.beast.SaverLoader;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.propertylist.Model.PLModel;
import edu.pse.beast.propertylist.Model.PropertyItem;
import edu.pse.beast.saverloader.PropertyListSaverLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * JUnit Testclass for saverloader.PropertyListSaverLoader.
 * @author NikolaiLMS
 */
public class PropertyListSaverLoaderTest {
    private static PLModel plModel;

    @BeforeClass
    public static void setUpClass() {
        FormalPropertiesDescription pre = new FormalPropertiesDescription(
                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription(
                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("candidate", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
        PreAndPostConditionsDescription description1 = new PreAndPostConditionsDescription("description1",
                pre, post, list);
        PreAndPostConditionsDescription description2 = new PreAndPostConditionsDescription("description2",
                pre, post, list);

        plModel = new PLModel();
        plModel.initialize();
        PropertyItem propertyItem = new PropertyItem(description1, true, true);
        PropertyItem propertyItem2 = new PropertyItem(description2, false, false);
        plModel.addDescription(propertyItem);
        plModel.addDescription(propertyItem2);
    }

    /**
     * Tests the PropertyListSaverLoader by creating a saveString from a PLModel object, then recreating
     * that object from the saveString and checking its integrity.
     */
    @Test
    public void testSaverLoader() throws Exception {
        String saveString = new PropertyListSaverLoader().createSaveString(plModel);
        PLModel recreatedPLModel = (PLModel) new PropertyListSaverLoader().createFromSaveString(saveString);

        assert (recreatedPLModel.getPropertyList().get(0).getDescription().getName().equals("description1"));
        assert (recreatedPLModel.getPropertyList().get(0).getDescription().getPostConditionsDescription().getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        assert (recreatedPLModel.getPropertyList().get(0).getDescription().getPreConditionsDescription().getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        assert (recreatedPLModel.getPropertyList().get(0).getTestStatus().equals(true));

        // check SymbolicVariableList for integrity
        SymbolicVariableList recreatedList = recreatedPLModel.getPropertyList().get(1).getDescription().getSymVarList();
        assert (recreatedList.getSymbolicVariables().get(0).getId().equals("voter1"));
        assert (recreatedList.getSymbolicVariables().get(0).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(1).getId().equals("voter2"));
        assert (recreatedList.getSymbolicVariables().get(1).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(2).getId().equals("candidate"));
        assert (recreatedList.getSymbolicVariables().get(2).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.CANDIDATE));
        assert (recreatedList.getSymbolicVariables().get(3).getId().equals("seat"));
        assert (recreatedList.getSymbolicVariables().get(3).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.SEAT));

        assert (recreatedPLModel.getPropertyList().get(1).getDescription().getName().equals("description2"));
        assert (recreatedPLModel.getPropertyList().get(1).getDescription().getPostConditionsDescription().getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        assert (recreatedPLModel.getPropertyList().get(1).getDescription().getPreConditionsDescription().getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        assert (recreatedPLModel.getPropertyList().get(1).getTestStatus().equals(false));

        // check SymbolicVariableList for integrity
        recreatedList = recreatedPLModel.getPropertyList().get(1).getDescription().getSymVarList();
        assert (recreatedList.getSymbolicVariables().get(0).getId().equals("voter1"));
        assert (recreatedList.getSymbolicVariables().get(0).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(1).getId().equals("voter2"));
        assert (recreatedList.getSymbolicVariables().get(1).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(2).getId().equals("candidate"));
        assert (recreatedList.getSymbolicVariables().get(2).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.CANDIDATE));
        assert (recreatedList.getSymbolicVariables().get(3).getId().equals("seat"));
        assert (recreatedList.getSymbolicVariables().get(3).getInternalTypeContainer().getInternalType().
                equals(InternalTypeRep.SEAT));
    }
}
