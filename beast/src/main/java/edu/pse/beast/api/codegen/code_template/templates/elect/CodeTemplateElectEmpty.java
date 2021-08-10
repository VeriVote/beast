package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.pse.beast.api.paths.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateElectEmpty {
    private static final String EMPTY = "";
    private static final String FILE_KEY = "EMPTY";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public final String getTemplate() {
        return PathHandler.getTemplate(FILE_KEY, TEMPLATES, EMPTY, this.getClass());
    }
}
