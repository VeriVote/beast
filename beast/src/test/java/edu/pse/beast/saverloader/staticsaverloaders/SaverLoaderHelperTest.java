package edu.pse.beast.saverloader.staticsaverloaders;

import static org.junit.Assert.assertEquals;

import java.util.Map;

import org.junit.Test;

/**
 * The tests for SaverLoaderHelper.
 *
 * @author Holger Klein
 */
public class SaverLoaderHelperTest {
    /** A test name key. */
    private static final String NAME = "name";
    /** A test name string. */
    private static final String TEST_NAME = "Jack";
    /** A save test name string. */
    private static final String SAVE_TEST_NAME = "4 name4 Jack";
    /** Another test name string. */
    private static final String TEST_NAME_TWO = "Monica";
    /** A test age key. */
    private static final String AGE = "age";
    /** A test age string. */
    private static final String TEST_AGE = "14";

    /** The instance. */
    private SaverLoaderHelper h = new SaverLoaderHelper();

    /**
     * Test create save string.
     */
    @Test
    public void testCreateSaveString() {
        String s = h.getStringForAttr(NAME, TEST_NAME);
        assertEquals(SAVE_TEST_NAME, s);
    }

    /**
     * Test parses save string.
     */
    @Test
    public void testParsesSaveString() {
        Map<String, String> m = h.parseSaveString(SAVE_TEST_NAME);
        assertEquals(TEST_NAME, m.get(NAME));
        m = h.parseSaveString(h.getStringForAttr(NAME, TEST_NAME_TWO)
                              + h.getStringForAttr(AGE, TEST_AGE));
        assertEquals(TEST_NAME_TWO, m.get(NAME));
        assertEquals(TEST_AGE, m.get(AGE));
    }
}
