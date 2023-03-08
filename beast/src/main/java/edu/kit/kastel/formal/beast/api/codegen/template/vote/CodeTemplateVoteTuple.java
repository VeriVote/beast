package edu.kit.kastel.formal.beast.api.codegen.template.vote;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBoundType;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.VotingInputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateVoteTuple {
    private static final String FILE_PREFIX = "tuple_";

    private static final Map<String, String> SETUP_TEMPLATES =
            new LinkedHashMap<String, String>();

    private static final Map<VotingInputType, String> TEMPLATES =
            new LinkedHashMap<VotingInputType, String>();

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                    LoopBoundType.AMOUNT_VOTERS,
                    LoopBoundType.AMOUNT_CANDIDATES)
            );

    private static final Map<VotingInputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputType, List<LoopBound>>(
                    Map.of(VotingInputType.PREFERENCE, LOOP_BOUNDS_PREFERENCE)
            );

    public static final List<LoopBound> getLoopBounds(final VotingInputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, SETUP_TEMPLATES, FILE_PREFIX, this.getClass());
    }

    // TODO: APPROVAL, WEIGHTED_APPROVAL, SINGLE_CHOICE, SINGLE_CHOICE_STACK etc.
    public final String getTemplate(final VotingInputType key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
