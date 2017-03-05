package edu.pse.beast.SaverLoader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.PostAndPrePropertiesDescription;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.PostAndPrePropertiesDescriptionSaverLoader;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit Testclass for saverloader.PostAndPrePropertiesDescription.
 * @author NikolaiLMS
 */
public class PostAndPrePropertiesDescriptionSaverLoaderTest {
    private static PostAndPrePropertiesDescriptionSaverLoader postAndPrePropertiesDescriptionSaverLoader;
    private static PostAndPrePropertiesDescription description;

    @BeforeClass
    public static void setUpClass() {
        postAndPrePropertiesDescriptionSaverLoader =
                new PostAndPrePropertiesDescriptionSaverLoader();
        FormalPropertiesDescription pre = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        FormalPropertiesDescription post = new FormalPropertiesDescription("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD");
        SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable("candidate", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
        description = new PostAndPrePropertiesDescription("description1", pre, post, list);
    }

    /**
     * Tests the PostAndPrePropertiesDescriptionSaverLoader by creating a saveString from a
     * PostAndPrePropertiesDescription object, then recreating that object from the saveString and checking it for
     * integrity.
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        String saveString = postAndPrePropertiesDescriptionSaverLoader.createSaveString(description);
        PostAndPrePropertiesDescription recreatedPostAndPrePropertiesDescription = (PostAndPrePropertiesDescription)
                postAndPrePropertiesDescriptionSaverLoader.createFromSaveString(saveString);

        assert (recreatedPostAndPrePropertiesDescription.getName().equals("description1"));

        // check FormalPropertiesDescriptions for integrity
        FormalPropertiesDescription recreatedPreFormalPropertiesDescription =
                recreatedPostAndPrePropertiesDescription.getPrePropertiesDescription();
        assert (recreatedPreFormalPropertiesDescription.getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));
        FormalPropertiesDescription recreatedPostFormalPropertiesDescription =
                recreatedPostAndPrePropertiesDescription.getPrePropertiesDescription();
        assert (recreatedPostFormalPropertiesDescription.getCode().
                equals("CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD"));

        // check SymbolicVariableList for integrity
        SymbolicVariableList recreatedList = recreatedPostAndPrePropertiesDescription.getSymVarList();
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
