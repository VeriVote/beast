package edu.pse.beast.saverloader;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * JUnit Testclass for saverloader.PreAndPostConditionsDescription.
 *
 * @author Nikolai Schnell
 */
public class PreAndPostConditionsDescriptionSaverLoaderTest {
    private static PropertyDescriptionSaverLoader preAndPostConditionsDescriptionSaverLoader;
    private static PreAndPostConditionsDescription description;

    @BeforeClass
    public static void setUpClass() {
        preAndPostConditionsDescriptionSaverLoader = new PropertyDescriptionSaverLoader();
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription(
                "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("candidate", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
        description = new PreAndPostConditionsDescription("description1", pre, post, null, list); // FIXME
    }

    /**
     * Tests the PreAndPostConditionsDescriptionSaverLoader by creating a saveString
     * from a PreAndPostConditionsDescription object, then recreating that object
     * from the saveString and checking it for integrity.
     * @throws Exception String caused an exception
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        String saveString = preAndPostConditionsDescriptionSaverLoader.createSaveString(description);
        PreAndPostConditionsDescription recreatedPreAndPostConditionsDescription =
            (PreAndPostConditionsDescription) preAndPostConditionsDescriptionSaverLoader
                .createFromSaveString(saveString);
        assert (recreatedPreAndPostConditionsDescription.getName().equals("description1"));

        // check FormalPropertiesDescriptions for integrity
        FormalPropertiesDescription recreatedPreFormalPropertiesDescription = recreatedPreAndPostConditionsDescription
                .getPreConditionsDescription();
        assert (recreatedPreFormalPropertiesDescription.getCode().equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        FormalPropertiesDescription recreatedPostFormalPropertiesDescription = recreatedPreAndPostConditionsDescription
                .getPreConditionsDescription();
        assert (recreatedPostFormalPropertiesDescription.getCode()
                .equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));

        // check SymbolicVariableList for integrity
        SymbolicVariableList recreatedList = recreatedPreAndPostConditionsDescription.getSymVarList();
        assert (recreatedList.getSymbolicVariables().get(0).getId().equals("voter1"));
        assert (recreatedList.getSymbolicVariables().get(0).getInternalTypeContainer().getInternalType()
                .equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(1).getId().equals("voter2"));
        assert (recreatedList.getSymbolicVariables().get(1).getInternalTypeContainer().getInternalType()
                .equals(InternalTypeRep.VOTER));
        assert (recreatedList.getSymbolicVariables().get(2).getId().equals("candidate"));
        assert (recreatedList.getSymbolicVariables().get(2).getInternalTypeContainer().getInternalType()
                .equals(InternalTypeRep.CANDIDATE));
        assert (recreatedList.getSymbolicVariables().get(3).getId().equals("seat"));
        assert (recreatedList.getSymbolicVariables().get(3).getInternalTypeContainer().getInternalType()
                .equals(InternalTypeRep.SEAT));
    }
}