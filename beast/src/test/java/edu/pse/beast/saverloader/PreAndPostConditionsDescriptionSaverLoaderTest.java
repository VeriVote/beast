package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

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
    /** A voter test string. */
    private static final String VOTER_ONE = "voter1";
    /** Another voter test string. */
    private static final String VOTER_TWO = "voter2";
    /** A candidate test string. */
    private static final String CANDIDATE = "candidate";
    /** A seat test string. */
    private static final String SEAT = "seat";

    /** The Constant VOTER_ONE_IDX. */
    private static final int VOTER_ONE_IDX = 0;
    /** The Constant VOTER_TWO_IDX. */
    private static final int VOTER_TWO_IDX = 1;
    /** The Constant CANDIDATE_IDX. */
    private static final int CANDIDATE_IDX = 2;
    /** The Constant SEAT_IDX. */
    private static final int SEAT_IDX = 3;

    /** The Constant TEST_CODE_STRING. */
    private static final String TEST_CODE_STRING =
            "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD";
    /** A test description string. */
    private static final String TEST_DESCRIPTION_STRING = "description1";

    /** The instance. */
    private static PropertyDescriptionSaverLoader preAndPostConditionsDescriptionSaverLoader;
    /** A description instance. */
    private static PreAndPostConditionsDescription description;

    @BeforeClass
    public static void setUpClass() {
        preAndPostConditionsDescriptionSaverLoader = new PropertyDescriptionSaverLoader();
        final SymbolicVariableList list = new SymbolicVariableList();
        list.addSymbolicVariable(VOTER_ONE, new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable(VOTER_TWO, new InternalTypeContainer(InternalTypeRep.VOTER));
        list.addSymbolicVariable(CANDIDATE, new InternalTypeContainer(InternalTypeRep.CANDIDATE));
        list.addSymbolicVariable(SEAT, new InternalTypeContainer(InternalTypeRep.SEAT));

        final FormalPropertiesDescription pre =
            new FormalPropertiesDescription(TEST_CODE_STRING);
        final FormalPropertiesDescription post =
            new FormalPropertiesDescription(TEST_CODE_STRING);

        final FormalPropertiesDescription bounded = new FormalPropertiesDescription("");
        description =
            new PreAndPostConditionsDescription(TEST_DESCRIPTION_STRING,
                                                pre, post, bounded, list);
    }

    /**
     * Tests the PreAndPostConditionsDescriptionSaverLoader by creating a saveString
     * from a PreAndPostConditionsDescription object, then recreating that object
     * from the saveString and checking it for integrity.
     * @throws Exception String caused an exception
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        final String saveString =
                preAndPostConditionsDescriptionSaverLoader.createSaveString(description);
        final PreAndPostConditionsDescription recreatedPreAndPostConditionsDescription =
                (PreAndPostConditionsDescription) preAndPostConditionsDescriptionSaverLoader
                .createFromSaveString(saveString);
        assertEquals(recreatedPreAndPostConditionsDescription.getName(),
                     TEST_DESCRIPTION_STRING);

        // check FormalPropertiesDescriptions for integrity
        final FormalPropertiesDescription recreatedPreFormalPropertiesDescription =
                recreatedPreAndPostConditionsDescription.getPreConditionsDescription();
        assertEquals(recreatedPreFormalPropertiesDescription.getCode(),
                     TEST_CODE_STRING);
        final FormalPropertiesDescription recreatedPostFormalPropertiesDescription =
                recreatedPreAndPostConditionsDescription.getPreConditionsDescription();
        assertEquals(recreatedPostFormalPropertiesDescription.getCode(),
                     TEST_CODE_STRING);

        // check SymbolicVariableList for integrity
        final SymbolicVariableList recreatedList =
                recreatedPreAndPostConditionsDescription.getSymVarList();
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
