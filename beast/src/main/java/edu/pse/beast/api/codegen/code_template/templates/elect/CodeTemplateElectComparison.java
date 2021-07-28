package edu.pse.beast.api.codegen.code_template.templates.elect;

import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class CodeTemplateElectComparison {
    private static final String RESOURCES =
            "/edu/pse/beast/api/codegen/code_template/templates/elect/";
    private static final String FILE_PREFIX = "comparison_";
    private static final String FILE_ENDING = ".template";

    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_LOOP_BOUND_AMT_CANDS)
                    );

    private static final Map<VotingOutputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputTypes, List<LoopBound>>(
            Map.of(VotingOutputTypes.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputTypes.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputTypes key) {
        assert key != null;
        if (LOOP_BOUNDS.isEmpty() || !LOOP_BOUNDS.containsKey(key)) {
            // throw new NotImplementedException();
            return Arrays.asList();
        }
        return LOOP_BOUNDS.get(key);
    }

    // TODO: PARLIAMENT_STACK, SINGLE_CANDIDATE etc.
    public static final String getTemplate(final VotingOutputTypes key,
                                           final boolean unequal,
                                           final Class<?> c) {
        assert key != null;
        final String realKey = key.name().toLowerCase() + (unequal ? "_uneq" : "");
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(realKey)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + FILE_PREFIX + realKey + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            TEMPLATES.put(realKey, writer.toString());
        }
        return TEMPLATES.get(realKey);
    }
}
