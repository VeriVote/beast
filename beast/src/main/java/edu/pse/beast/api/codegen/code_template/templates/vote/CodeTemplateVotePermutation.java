package edu.pse.beast.api.codegen.code_template.templates.vote;

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
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class CodeTemplateVotePermutation {
    private static final String RESOURCES =
            "/edu/pse/beast/api/codegen/code_template/templates/vote/";
    private static final String FILE_PREFIX = "permutation_";
    private static final String FILE_ENDING = ".template";

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
            Map.of(VotingInputTypes.PREFERENCE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputTypes.APPROVAL, LOOP_BOUNDS_PREFERENCE));

    private static final Map<VotingInputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingInputTypes, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        if (LOOP_BOUNDS.isEmpty() || !LOOP_BOUNDS.containsKey(key)) {
            // throw new NotImplementedException();
            return Arrays.asList();
        }
        return LOOP_BOUNDS.get(key);
    }

    // TODO: SINGLE_CHOICE, SINGLE_CHOICE_STACK, WEIGHTED_APPROVAL etc.
    public static final String getTemplate(final VotingInputTypes key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + FILE_PREFIX + key.name().toLowerCase() + FILE_ENDING);
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
