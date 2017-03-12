package edu.pse.beast.SaverLoader.StaticSaverLoaders;

import org.junit.Test;
import edu.pse.beast.saverloader.StaticSaverLoaders.SaverLoaderHelper;
import java.util.Map;

import static org.junit.Assert.*;

/**
 * Created by holger on 12.03.17.
 */
public class SaverLoaderHelperTest {

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
        m = h.parseSaveString(
                h.getStringForAttr("name", "Monica") +
                        h.getStringForAttr("age", "14"));
        assertEquals("Monica", m.get("name"));
        assertEquals("14", m.get("age"));
    }
}