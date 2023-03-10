package edu.kit.kastel.formal.beast.api.codegen.template.vote;

import java.util.LinkedHashMap;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateVoteEmpty {
    private static final String EMPTY = "";
    private static final String FILE_KEY = "EMPTY";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public final String getTemplate() {
        return PathHandler.getTemplate(FILE_KEY, TEMPLATES, EMPTY, this.getClass());
    }
}
