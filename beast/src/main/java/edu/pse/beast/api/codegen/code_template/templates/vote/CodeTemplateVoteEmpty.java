package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

public class CodeTemplateVoteEmpty {
    private static final String RESOURCES =
            "/edu/pse/beast/api/codegen/code_template/templates/vote/";
    private static final String FILE_KEY = "EMPTY";
    private static final String FILE_ENDING = ".template";

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public static final String getTemplate(final Class<?> c) {
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(FILE_KEY)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + FILE_KEY.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(FILE_KEY, writer.toString());
        }
        return TEMPLATES.get(FILE_KEY);
    }
}
