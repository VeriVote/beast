package edu.pse.beast.api.codegen.template.vote;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.VotingInputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateVoteComparison {
    private static final String FILE_PREFIX = "comparison_";

    private static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_VOTERS)
                    );

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(
                            LoopBoundType.AMOUNT_VOTERS,
                            LoopBoundType.AMOUNT_SEATS)
                    );

    private static final Map<VotingInputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputType, List<LoopBound>>(
            Map.of(VotingInputType.SINGLE_CHOICE, LOOP_BOUNDS_SINGLE_CHOICE,
                    VotingInputType.PREFERENCE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputType.APPROVAL, LOOP_BOUNDS_PREFERENCE));

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: SINGLE_CHOICE_STACK, WEIGHTED_APPROVAL etc.
    public final String getTemplate(final VotingInputType key,
                                           final boolean unequal) {
        assert key != null;
        final String realKey = key.name().toLowerCase() + (unequal ? "_uneq" : "");
        return PathHandler.getTemplate(realKey, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
