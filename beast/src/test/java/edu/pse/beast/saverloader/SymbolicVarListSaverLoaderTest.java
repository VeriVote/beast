package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.propertydescription.SymbolicVariableList;
import edu.pse.beast.saverloader.staticsaverloaders.SymbolicVarListSaverLoader;
import edu.pse.beast.types.InternalTypeContainer;
import edu.pse.beast.types.InternalTypeRep;

/**
 * JUnit Testclass for
 * saverloader.StaticSaverLoaders.SymbolicVarListSaverLoader.
 *
 * @author Nikolai Schnell
 */
public class SymbolicVarListSaverLoaderTest {
    /** A voter test string. */
    private static final String VOTER_ONE = "voter1";
    /** Another voter test string. */
    private static final String VOTER_TWO = "voter2";
    /** A candidate test string. */
    private static final String CANDIDATE = "candidate";
    /** A seat test string. */
    private static final String SEAT = "seat";

    /** The symbolic variable list instance. */
    private static SymbolicVariableList symbolicVariableList;

    @BeforeClass
    public static void setUpClass() {
        symbolicVariableList = new SymbolicVariableList();
        symbolicVariableList.addSymbolicVariable(
                VOTER_ONE, new InternalTypeContainer(InternalTypeRep.VOTER)
        );
        symbolicVariableList.addSymbolicVariable(
                VOTER_TWO, new InternalTypeContainer(InternalTypeRep.VOTER)
        );
        symbolicVariableList.addSymbolicVariable(
                CANDIDATE, new InternalTypeContainer(InternalTypeRep.CANDIDATE)
        );
        symbolicVariableList.addSymbolicVariable(
                SEAT, new InternalTypeContainer(InternalTypeRep.SEAT)
        );
    }

    /**
     * Tests the SymbolicVarListSaverLoader by creating a saveString from a
     * SymbolicVarList object, then recreating that object from the saveString and
     * checking its integrity.
     */
    @Test
    public void testSaverLoader() {
        String saveString = SymbolicVarListSaverLoader.createSaveString(symbolicVariableList);
        SymbolicVariableList recreatedList
              = SymbolicVarListSaverLoader.createFromSaveString(saveString);
        assertEquals(recreatedList.getSymbolicVariables().get(0).getId(), VOTER_ONE);
        assertEquals(recreatedList.getSymbolicVariables().get(0)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
        assertEquals(recreatedList.getSymbolicVariables().get(1).getId(), VOTER_TWO);
        assertEquals(recreatedList.getSymbolicVariables().get(1)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
        assertEquals(recreatedList.getSymbolicVariables().get(2).getId(), CANDIDATE);
        assertEquals(recreatedList.getSymbolicVariables().get(2)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
        assertEquals(recreatedList.getSymbolicVariables().get(3).getId(), SEAT);
        assertEquals(recreatedList.getSymbolicVariables().get(3)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
    }
}
