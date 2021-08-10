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
public class CodeTemplateVoteSumForCandidate {
    private static final String FILE_PREFIX = "sum_";

    private static final List<LoopBound> LOOP_BOUNDS_PREFERENCE =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_VOTERS)
                    );

    private static final Map<VotingInputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingInputTypes, List<LoopBound>>(
            Map.of(VotingInputTypes.SINGLE_CHOICE, null,
                    VotingInputTypes.SINGLE_CHOICE_STACK, LOOP_BOUNDS_PREFERENCE));

    private static final Map<VotingInputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingInputTypes, String>();

    public static final List<LoopBound> getLoopBounds(final VotingInputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: APPROVAL, WEIGHTED_APPROVAL, PREFERENCE etc.
    public final String getTemplate(final VotingInputTypes key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
