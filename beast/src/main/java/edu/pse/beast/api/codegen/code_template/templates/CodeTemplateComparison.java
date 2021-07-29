package edu.pse.beast.api.codegen.code_template.templates;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.LinkedHashMap;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

public class CodeTemplateComparison {
    private static final String RESOURCES = "/edu/pse/beast/api/codegen/code_template/templates/";
    private static final String FILE_ENDING = ".template";
    private static final String DIM = "D";
    private static final String UNEQ = "_UNEQ";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    public static final String getTemplate(final int dimension,
                                           final boolean unequal,
                                           final Class<?> c) {
        assert dimension == 0 || dimension == 1;
        final String key =
                "" + dimension + DIM + ((dimension > 0 && unequal) ? UNEQ : "");
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(key, writer.toString());
        }
        return TEMPLATES.get(key);
    }
}
