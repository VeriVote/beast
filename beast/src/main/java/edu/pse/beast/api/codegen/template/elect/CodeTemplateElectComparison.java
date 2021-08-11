package edu.pse.beast.api.codegen.template.elect;

import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.loopbound.LoopBoundType;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.VotingOutputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeTemplateElectComparison {
    private static final String FILE_PREFIX = "comparison_";

    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_AMOUNT_CANDIDATES)
                    );

    private static final Map<VotingOutputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputTypes, List<LoopBound>>(
            Map.of(VotingOutputTypes.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputTypes.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: PARLIAMENT_STACK, SINGLE_CANDIDATE etc.
    public final String getTemplate(final VotingOutputTypes key,
                                           final boolean unequal) {
        assert key != null;
        final String realKey = key.name().toLowerCase() + (unequal ? "_uneq" : "");
        return PathHandler.getTemplate(realKey, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
