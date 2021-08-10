package edu.pse.beast.api.codegen.code_template.templates.elect;

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
public class CodeTemplateElectTuple {
    private static final String FILE_PREFIX = "tuple_";

    private static final Map<String, String> SETUP_TEMPLATES =
            new LinkedHashMap<String, String>();

    private static final Map<VotingOutputTypes, String> TEMPLATES =
            new LinkedHashMap<VotingOutputTypes, String>();

    private static final List<LoopBound> LOOP_BOUNDS_CANDIDATE_LIST =
            LoopBound.codeGenLoopboundList(
                    Arrays.asList(LoopBoundType.AMOUNT_CANDIDATES));

    private static final Map<VotingOutputTypes, List<LoopBound>> LOOP_BOUNDS =
            new LinkedHashMap<VotingOutputTypes, List<LoopBound>>(
                    Map.of(VotingOutputTypes.CANDIDATE_LIST, LOOP_BOUNDS_CANDIDATE_LIST));

    public static final List<LoopBound> getLoopBounds(final VotingOutputTypes key) {
        assert key != null;
        return PathHandler.getLoopBounds(key, LOOP_BOUNDS);
    }

    public final String getTemplate(final String key) {
        assert key != null;
        return PathHandler.getTemplate(key, SETUP_TEMPLATES, FILE_PREFIX, this.getClass());
    }

    // TODO: PARLIAMENT, PARLIAMENT_STACK, SINGLE_CANDIDATE etc.
    public final String getTemplate(final VotingOutputTypes key) {
        assert key != null;
        return PathHandler.getTemplate(key, TEMPLATES, FILE_PREFIX, this.getClass());
    }
}
