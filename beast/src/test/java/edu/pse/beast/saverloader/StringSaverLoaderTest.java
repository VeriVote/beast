package edu.pse.beast.saverloader;

import static org.junit.Assert.assertEquals;

import org.junit.BeforeClass;
import org.junit.Test;

import edu.pse.beast.saverloader.staticsaverloaders.StringSaverLoader;

/**
 * JUnit Testclass for saverloader.StaticSaverLoaders.StringSaverLoader.
 *
 * @author Nikolai Schnell
 */
public class StringSaverLoaderTest {
    /** A test string instance. */
    private static String testString;

    @BeforeClass
    public static void setUpClass() {
        testString = "a fsfdgfd gfdko gfd gfd" + "g\fdgdfg\n\n???>>>><<>><";
    }

    /**
     * Tests the StringSaverLoader by creating a saveString from a String, then
     * recreating that object from the saveString and checking its integrity.
     */
    @Test
    public void testSaverLoader() {
        final String saveString = StringSaverLoader.createSaveString(testString);
        final String recreatedString = StringSaverLoader.createFromSaveString(saveString);
        assertEquals(recreatedString, testString);
    }
}
