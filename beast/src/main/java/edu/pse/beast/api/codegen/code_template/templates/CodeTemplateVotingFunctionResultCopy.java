package edu.pse.beast.api.codegen.code_template.templates;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;
import edu.pse.beast.api.paths.PathHandler;

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

    private static final Map<VotingOutputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputTypes, List<LoopBound>>(
            Map.of(VotingOutputTypes.SINGLE_CANDIDATE, LOOP_BOUNDS_SINGLE_CANDIDATE,
                    VotingOutputTypes.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputTypes.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<VotingOutputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingOutputTypes, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: PARLIAMENT_STACK etc.
    public final String getTemplate(final VotingOutputTypes key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
