package edu.pse.beast.api.codegen.cbmc;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class StringReplacementMap {
    public static Map<String, String> genMap(final String... values) {
        final Map<String, String> created = new HashMap<>();
        for (int i = 0; i < values.length - 1; i += 2) {
            created.put(values[i], values[i + 1]);
        }
        return created;
    }
}
