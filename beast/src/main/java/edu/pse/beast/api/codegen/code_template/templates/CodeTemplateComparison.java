package edu.pse.beast.api.codegen.code_template.templates;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pse.beast.api.paths.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateComparison {
    private static final String DIM = "D";
    private static final String UNEQ = "_UNEQ";
    private static final String EMPTY = "";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    public final String getTemplate(final int dimension,
                                    final boolean unequal) {
        assert dimension == 0 || dimension == 1;
        final String key =
                "" + dimension + DIM + ((dimension > 0 && unequal) ? UNEQ : "");
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }
}
