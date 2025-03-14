package edu.kit.kastel.formal.beast.api.codegen.template.elect;

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
public class CodeTemplateElectComparison {
    private static final String FILE_PREFIX = "comparison_";

    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.NECESSARY_AMOUNT_CANDIDATES)
                    );

    private static final Map<VotingOutputType, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputType, List<LoopBound>>(
            Map.of(VotingOutputType.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST,
                    VotingOutputType.PARLIAMENT, LOOP_BOUNDS_CANDIDATE_LIST));

    private static final Map<String, String> TEMPLATES = new LinkedHashMap<String, String>();

    public static final List<LoopBound> getLoopBounds(final VotingOutputType key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    // TODO: PARLIAMENT_STACK, SINGLE_CANDIDATE etc.
    public final String getTemplate(final VotingOutputType key,
                                           final boolean unequal) {
        assert key != null;
        final String realKey = key.name().toLowerCase() + (unequal ? "_uneq" : "");
        return PathHandler.getTemplate(realKey, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
