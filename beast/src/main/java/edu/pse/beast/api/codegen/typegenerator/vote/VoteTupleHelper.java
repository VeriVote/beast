package edu.pse.beast.api.codegen.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.template.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.method.VotingInputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteTupleHelper {
    private static final String EMPTY = "";
    private static final String LINE_BREAK = "\n";
    private static final String PLUS = " + ";

    private static final String VAR_SETUP_FILE_KEY = "VAR_SETUP";
    private static final String AMOUNT_FILE_KEY = "AMOUNT";
    private static final String VOTE_AMOUNT_SUM = "VOTE_AMT_SUM";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String NONDET_UINT = "NONDET_UINT";
    private static final String AMOUNT_CANDIDATES = "AMT_CANDIDATES";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String VOTE_TYPE = "VOTE_TYPE";
    private static final String VAR_NAME = "VAR_NAME";
    private static final String ASSUME = "ASSUME";
    private static final String CURRENT_VOTE = "CURRENT_VOTE";

    public static String generateCode(final String generatedVarName,
                                      final List<String> voteNames,
                                      final ElectionTypeCStruct voteArrStruct,
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final CodeTemplateVoteTuple voteTuple = new CodeTemplateVoteTuple();
        String voteAmtSum = EMPTY;
        for (int i = 0; i < voteNames.size() - 1; ++i) {
            voteAmtSum +=
                    voteTuple.getTemplate(AMOUNT_FILE_KEY)
                    .replaceAll(CURRENT_VOTE, voteNames.get(i))
                    .replace(AMOUNT_MEMBER, voteArrStruct.getAmountName())
                    .replace(LINE_BREAK, EMPTY)
                    + PLUS;
        }
        voteAmtSum +=
                voteTuple.getTemplate(AMOUNT_FILE_KEY)
                .replaceAll(CURRENT_VOTE, voteNames.get(voteNames.size() - 1))
                .replace(AMOUNT_MEMBER, voteArrStruct.getAmountName())
                .replace(LINE_BREAK, EMPTY);

        final Map<String, String> replacementMap =
                Map.of(VOTE_AMOUNT_SUM, voteAmtSum,
                       AMOUNT_MEMBER, voteArrStruct.getAmountName(),
                       LIST_MEMBER, voteArrStruct.getListName(),
                       NONDET_UINT, options.getCbmcNondetUintName(),
                       AMOUNT_CANDIDATES, options.getCbmcAmountMaxCandsVarName(),
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       VOTE_TYPE, voteArrStruct.getStruct().getName(),
                       VAR_NAME, generatedVarName,
                       ASSUME, options.getCbmcAssumeName());
        String code = voteTuple.getTemplate(VAR_SETUP_FILE_KEY);

        for (final String voteVarName : voteNames) {
            code += voteTuple.getTemplate(votingInputType)
                    .replaceAll(CURRENT_VOTE, voteVarName);
        }
        final List<LoopBound> loopbounds = CodeTemplateVoteTuple.getLoopBounds(votingInputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
    }
}
