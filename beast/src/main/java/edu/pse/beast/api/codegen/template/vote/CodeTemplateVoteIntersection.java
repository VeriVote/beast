package edu.pse.beast.api.codegen.template.vote;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.VotingInputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateVoteIntersection {
    private static final String FILE_PREFIX = "intersection_";

    private static final List<LoopBound> LOOP_BOUNDS_SINGLE_CHOICE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_VOTERS)
            );

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                Arrays.asList(
                LoopBoundType.AMOUNT_VOTERS,
                LoopBoundType.AMOUNT_VOTERS,
                LoopBoundType.AMOUNT_VOTERS)
            );

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
            Map.of(VotingInputTypes.SINGLE_CHOICE, LOOP_BOUNDS_SINGLE_CHOICE,
                    VotingInputTypes.PREFERENCE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputTypes.APPROVAL, LOOP_BOUNDS_PREFERENCE));

    private static final Map<VotingInputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingInputTypes, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: SINGLE_CHOICE_STACK, WEIGHTED_APPROVAL etc.
    public final String getTemplate(final VotingInputTypes key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
