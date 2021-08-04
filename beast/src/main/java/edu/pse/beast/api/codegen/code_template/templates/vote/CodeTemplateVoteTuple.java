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

public class CodeTemplateVoteTuple {
    private static final String RESOURCES =
            "/edu/pse/beast/api/codegen/code_template/templates/vote/";
    private static final String FILE_PREFIX = "tuple_";
    private static final String FILE_ENDING = ".template";

    private static final Map<String, String> SETUP_TEMPLATES =
            new LinkedHashMap<String, String>();

    private static final Map<VotingInputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingInputTypes, String>();

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                    LoopBoundType.LOOP_BOUND_AMT_VOTERS,
                    LoopBoundType.LOOP_BOUND_AMT_CANDS)
            );

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
                    Map.of(VotingInputTypes.PREFERENCE, LOOP_BOUNDS_PREFERENCE)
            );

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        if (LOOP_BOUNDS.isEmpty() || !LOOP_BOUNDS.containsKey(key)) {
            // throw new NotImplementedException();
            return Arrays.asList();
        }
        return LOOP_BOUNDS.get(key);
    }

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (SETUP_TEMPLATES.isEmpty() || !SETUP_TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES
                            + FILE_PREFIX + key.toLowerCase() + FILE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            SETUP_TEMPLATES.put(key, writer.toString());
        }
        return SETUP_TEMPLATES.get(key);
    }

    // TODO: APPROVAL, WEIGHTED_APPROVAL, SINGLE_CHOICE, SINGLE_CHOICE_STACK etc.
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
