package edu.pse.beast.SaverLoader;

import edu.pse.beast.datatypes.internal.InternalTypeContainer;
import edu.pse.beast.datatypes.internal.InternalTypeRep;
import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader;
import org.junit.BeforeClass;
import org.junit.Test;

/**
 * JUnit Testclass for saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader.
 * @author NikolaiLMS
 */
public class SymbolicVarListSaverLoaderTest {
    private static SymbolicVariableList symbolicVariableList;
    
    @BeforeClass
    public static void setUpClass() {
        symbolicVariableList = new SymbolicVariableList();
        symbolicVariableList.addSymbolicVariable("voter1", new InternalTypeContainer(InternalTypeRep.VOTER));
        symbolicVariableList.addSymbolicVariable("voter2", new InternalTypeContainer(InternalTypeRep.VOTER));
        symbolicVariableList.addSymbolicVariable("candidate", new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        symbolicVariableList.addSymbolicVariable("seat", new InternalTypeContainer(InternalTypeRep.SEAT));
    }

    /**
     * Tests the SymbolicVarListSaverLoader by creating a saveString from a SymbolicVarList object, then recreating
     * that object from the saveString and checking its integrity.
     */
    @Test
    public void testSaverLoader() {
        String saveString = SymbolicVarListSaverLoader.createSaveString(symbolicVariableList);
        SymbolicVariableList recreatedList = SymbolicVarListSaverLoader.createFromSaveString(saveString);

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
