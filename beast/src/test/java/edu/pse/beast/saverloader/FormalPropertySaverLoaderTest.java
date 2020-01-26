package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.datatypes.propertydescription.FormalPropertiesDescription;
import edu.pse.beast.saverloader.staticsaverloaders.FormalPropertySaverLoader;

/**
 * JUnit Testclass for saverloader.StaticSaverLoaders.FormalPropertySaverLoader.
 *
 * @author Nikolai Schnell
 */
public class FormalPropertySaverLoaderTest {
    /** A test description string. */
    private static final String TEST_DESCRIPTION_STRING =
        "CODECODEOCDEOASD ASDAOSDASOD ;;; ;ASODAOSD";

    /** The description instance. */
    private static FormalPropertiesDescription description;

    @BeforeClass
    public static void setUpClass() {
        description = new FormalPropertiesDescription(TEST_DESCRIPTION_STRING);
    }

    /**
     * Tests the FormalElectionDescriptionSaverLoader by creating a saveString from
     * a FormalPropertiesDescription object, then recreating that object from the
     * saveString and checking its integrity.
     *
     * @throws Exception when String creation does not work
     */
    @Test
    public void testCreateFromSaveString() throws Exception {
        final String saveString = FormalPropertySaverLoader.createSaveString(description);
        final FormalPropertiesDescription recreatedFormalPropertiesDescription =
                FormalPropertySaverLoader.createFromSaveString(saveString);

        assertEquals(recreatedFormalPropertiesDescription.getCode(),
                     TEST_DESCRIPTION_STRING);
    }
}
