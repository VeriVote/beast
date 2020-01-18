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
    /** The instance. */
    private SaverLoaderHelper h = new SaverLoaderHelper();

    @Test
    public void testCreateSaveString() {
        String s = h.getStringForAttr("name", "Jack");
        assertEquals("4 name4 Jack", s);
    }

    @Test
    public void testParsesSaveString() {
        Map<String, String> m = h.parseSaveString("4 name4 Jack");
        assertEquals("Jack", m.get("name"));
        m = h.parseSaveString(h.getStringForAttr("name", "Monica")
                              + h.getStringForAttr("age", "14"));
        assertEquals("Monica", m.get("name"));
        assertEquals("14", m.get("age"));
    }
}
