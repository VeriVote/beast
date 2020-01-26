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

    /** The list index for the first voter string. */
    private static final int VOTER_ONE_IDX = 0;
    /** The list index for the second voter string. */
    private static final int VOTER_TWO_IDX = 1;
    /** The list index for the candidate string. */
    private static final int CANDIDATE_IDX = 2;
    /** The list index for the seat string. */
    private static final int SEAT_IDX = 3;

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
        assertEquals(recreatedList.getSymbolicVariables().get(VOTER_ONE_IDX).getId(), VOTER_ONE);
        assertEquals(recreatedList.getSymbolicVariables().get(VOTER_ONE_IDX)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
        assertEquals(recreatedList.getSymbolicVariables().get(VOTER_TWO_IDX).getId(), VOTER_TWO);
        assertEquals(recreatedList.getSymbolicVariables().get(VOTER_TWO_IDX)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.VOTER);
        assertEquals(recreatedList.getSymbolicVariables().get(CANDIDATE_IDX).getId(), CANDIDATE);
        assertEquals(recreatedList.getSymbolicVariables().get(CANDIDATE_IDX)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.CANDIDATE);
        assertEquals(recreatedList.getSymbolicVariables().get(SEAT_IDX).getId(), SEAT);
        assertEquals(recreatedList.getSymbolicVariables().get(SEAT_IDX)
                         .getInternalTypeContainer().getInternalType(), InternalTypeRep.SEAT);
    }
}
