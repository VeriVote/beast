package edu.kit.kastel.formal.beast.api.codegen;

import java.util.Map;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeGenerationToolbox {
    public static String replacePlaceholders(final String code,
                                             final Map<String, String> replacements) {
        String replacedCode = code;
        for (final Map.Entry<String, String> e : replacements.entrySet()) {
            replacedCode = replacedCode.replaceAll(e.getKey(), e.getValue());
        }
        return replacedCode;
    }
}
