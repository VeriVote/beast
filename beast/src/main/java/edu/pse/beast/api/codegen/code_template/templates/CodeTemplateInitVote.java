package edu.pse.beast.api.codegen.code_template.templates;

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

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateInitVote {
    private static final String RESOURCES = "/edu/pse/beast/api/codegen/code_template/templates/";
    private static final String FILE_PREFIX = "function_";
    private static final String FILE_ENDING = ".template";

    private static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.AMOUNT_VOTERS,
                            LoopBoundType.AMOUNT_VOTERS)
                    );

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays
                .asList(LoopBoundType.NECESSARY_AMOUNT_VOTERS,
                        LoopBoundType.AMOUNT_CANDIDATES,
                        LoopBoundType.AMOUNT_VOTERS,
                        LoopBoundType.AMOUNT_CANDIDATES,
                        LoopBoundType.NECESSARY_AMOUNT_CANDIDATES,
                        LoopBoundType.AMOUNT_CANDIDATES)
            );

    private static final List<LoopBound> LOOP_BOUNDS_APPROVAL =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.NECESSARY_AMOUNT_VOTERS,
                LoopBoundType.AMOUNT_CANDIDATES,
                LoopBoundType.AMOUNT_VOTERS,
                LoopBoundType.AMOUNT_CANDIDATES)
            );

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
            Map.of(VotingInputTypes.SINGLE_CHOICE, LOOP_BOUNDS_SINGLE_CHOICE,
                    VotingInputTypes.PREFERENCE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputTypes.APPROVAL, LOOP_BOUNDS_APPROVAL));

    private static final Map<VotingInputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingInputTypes, String>();

    private static final Map<String, String> INIT_BOUNDS_TEMPLATE =
            new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        if (LOOP_BOUNDS.isEmpty() || !LOOP_BOUNDS.containsKey(key)) {
            // throw new NotImplementedException();
            return Arrays.asList();
        }
        return LOOP_BOUNDS.get(key);
    }

    // TODO: SINGLE_CHOICE_STACK etc.
    public static final String getTemplate(final VotingInputTypes key,
                                           final Class<?> c) {
        assert key != null;
        if (TEMPLATES.isEmpty() || !TEMPLATES.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(RESOURCES + FILE_PREFIX
                            + key.name().toLowerCase() + FILE_ENDING);
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

    public static final String getTemplate(final String key,
                                           final Class<?> c) {
        assert key != null;
        if (INIT_BOUNDS_TEMPLATE.isEmpty() || !INIT_BOUNDS_TEMPLATE.containsKey(key)) {
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
            INIT_BOUNDS_TEMPLATE.put(key, writer.toString());
        }
        return INIT_BOUNDS_TEMPLATE.get(key);
    }
}
