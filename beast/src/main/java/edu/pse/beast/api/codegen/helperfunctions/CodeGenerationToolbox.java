package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

public class CodeGenerationToolbox {
    public static String replacePlaceholders(final String code,
                                             final Map<String, String> replacements) {
        String replacedCode = "";
        for (final String k : replacements.keySet()) {
            replacedCode = code.replaceAll(k, replacements.get(k));
        }
        return replacedCode;
    }
}
