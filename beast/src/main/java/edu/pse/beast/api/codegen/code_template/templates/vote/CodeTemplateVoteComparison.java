package edu.pse.beast.api.codegen.code_template.templates.vote;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;
import edu.pse.beast.api.paths.PathHandler;

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

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
            Map.of(VotingInputTypes.SINGLE_CHOICE, LOOP_BOUNDS_SINGLE_CHOICE,
                    VotingInputTypes.PREFERENCE, LOOP_BOUNDS_PREFERENCE,
                    VotingInputTypes.APPROVAL, LOOP_BOUNDS_PREFERENCE));

    private static final Map<String, String> TEMPLATES =
            new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: SINGLE_CHOICE_STACK, WEIGHTED_APPROVAL etc.
    public final String getTemplate(final VotingInputTypes key,
                                           final boolean unequal) {
        assert key != null;
        final String realKey = key.name().toLowerCase() + (unequal ? "_uneq" : "");
        return PathHandler.getTemplate(realKey, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
