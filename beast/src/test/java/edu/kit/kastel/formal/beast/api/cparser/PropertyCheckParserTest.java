package edu.kit.kastel.formal.beast.api.cparser;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.junit.Test;

import edu.kit.kastel.formal.beast.api.codegen.cbmc.CodeGenOptions;
import edu.kit.kastel.formal.beast.api.cparser.AntlrCLoopParser;
import edu.kit.kastel.formal.beast.api.cparser.ExtractedCLoop;
import edu.kit.kastel.formal.beast.api.io.PathHandler;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PropertyCheckParserTest {
    private static final String EMPTY = "";

    private static final String BORDA = "borda";
    private static final String VOTES = "votes";
    private static final String C = "C";
    private static final String V = "V";
    private static final String VOTING = "voting";

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    private String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, EMPTY, this.getClass());
    }

    @Test
    public void testAntlrCParser() {
        final String bordaCode = getTemplate(BORDA);

        final CodeGenOptions options = new CodeGenOptions();
        options.setCurrentVotesVarName(VOTES);
        options.setCurrentAmountCandsVarName(C);
        options.setCurrentAmountVotersVarName(V);

        final List<ExtractedCLoop> extractedCLoops =
                AntlrCLoopParser.findLoops(VOTING, bordaCode, options);
        for (final ExtractedCLoop l : extractedCLoops) {
            System.out.println(l.toString());
        }
    }
}
