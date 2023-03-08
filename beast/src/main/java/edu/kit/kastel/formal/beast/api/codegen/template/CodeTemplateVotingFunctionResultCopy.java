package edu.kit.kastel.formal.beast.api.codegen.template;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBound;
import edu.kit.kastel.formal.beast.api.codegen.loopbound.LoopBoundType;
import edu.kit.kastel.formal.beast.api.io.PathHandler;
import edu.kit.kastel.formal.beast.api.method.VotingOutputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateVotingFunctionResultCopy {
    private static final String FILE_PREFIX = "result_copy_";

    private static final List<LoopBound> LOOP_BOUNDS_SINGLE_CANDIDATE = Arrays.asList();
    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_AMOUNT_CANDIDATES)
                    );

    private static final Map<VotingOutputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputType, List<LoopBound>>(
            Map.of(VotingOutputType.SINGLE_CANDIDATE, LOOP_BOUNDS_SINGLE_CANDIDATE,
                    VotingOutputType.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputType.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<VotingOutputType, String> TEMPLATES =
            new LinkedHashMap<VotingOutputType, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: PARLIAMENT_STACK etc.
    public final String getTemplate(final VotingOutputType key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
