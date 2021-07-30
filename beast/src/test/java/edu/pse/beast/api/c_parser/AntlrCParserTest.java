package edu.pse.beast.api.c_parser;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.List;

import org.apache.commons.io.IOUtils;
import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class AntlrCParserTest {
    private static final String RESOURCES = "/codegen/";
    private static final String FILE_ENDING = ".template";

    private static final String BORDA = "borda";
    private static final String C = "C";
    private static final String V = "V";
    private static final String VOTING = "voting";

    private static String getTemplate(final String key, final Class<?> c) {
        final InputStream stream =
                c.getResourceAsStream(RESOURCES + key.toLowerCase() + FILE_ENDING);
        final StringWriter writer = new StringWriter();
        try {
            IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
        } catch (final IOException e) {
            e.printStackTrace();
        }
        return writer.toString();
    }

    @Test
    public void testAntlrCParser() {
        final Class<?> c = this.getClass();
        final String bordaCode = getTemplate(BORDA, c);

        final CodeGenOptions options = new CodeGenOptions();
        options.setCurrentAmountCandsVarName(C);
        options.setCurrentAmountVotersVarName(V);

        final List<ExtractedCLoop> extractedCLoops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, options);
        for (final ExtractedCLoop l : extractedCLoops) {
            System.out.println(l.toString());
        }
    }
}
