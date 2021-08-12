package edu.pse.beast.api.codegen.template.elect;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateElectIntersection {
    private static final String FILE_PREFIX = "intersection_";

    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_CANDIDATES)
            );

    private static final Map<VotingOutputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputType, List<LoopBound>>(
            Map.of(VotingOutputType.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputType.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<VotingOutputType, String> TEMPLATES =
            new LinkedHashMap<VotingOutputType, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: PARLIAMENT_STACK, SINGLE_CANDIDATE etc.
    public final String getTemplate(final VotingOutputType key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
