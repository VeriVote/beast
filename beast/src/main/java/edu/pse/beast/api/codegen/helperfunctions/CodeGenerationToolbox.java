package edu.pse.beast.api.codegen.helperfunctions;

import java.util.Map;

public class CodeGenerationToolbox {
    public static String replacePlaceholders(final String code,
                                             final Map<String, String> replacements) {
        String replacedCode = code;
        for (final String k : replacements.keySet()) {
            replacedCode = replacedCode.replaceAll(k, replacements.get(k));
        }
        return replacedCode;
    }
}
