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
public class CodeTemplateVoteSumForCandidate {
    private static final String FILE_PREFIX = "sum_";

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_VOTERS)
                    );

    private static final Map<VotingInputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputType, List<LoopBound>>(
            Map.of(VotingInputType.SINGLE_CHOICE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputType.SINGLE_CHOICE_STACK, LOOP_BOUNDS_PREFERENCE));

    private static final Map<VotingInputType, String> TEMPLATES =
            new LinkedHashMap<VotingInputType, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: APPROVAL, WEIGHTED_APPROVAL, PREFERENCE etc.
    public final String getTemplate(final VotingInputType key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
