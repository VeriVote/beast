package edu.pse.beast.api.codegen.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbound.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbound.LoopBound;
import edu.pse.beast.api.codegen.template.vote.CodeTemplateVoteSumForCandidate;
import edu.pse.beast.api.method.VotingInputType;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VoteSumHelper {
    private static final String GENERATED_VAR = "GENERATED_VAR";
    private static final String AMOUNT_MEMBER = "AMT_MEMBER";
    private static final String LIST_MEMBER = "LIST_MEMBER";
    private static final String CANDIDATE_VAR = "CANDIDATE_VAR";
    private static final String AMOUNT_VOTERS = "AMT_VOTERS";
    private static final String VOTE_VAR = "VOTE_VAR";
    private static final String VOTE_NUMBER = "voteNUMBER";
    private static final String NUMBER = "NUMBER";

    public static String generateCode(final String generatedVarName,
                                      final VoteArrayAccess voteAccess,
                                      final VotingInputType votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler) {
        final Map<String, String> replaceMap =
                Map.of(GENERATED_VAR, generatedVarName,
                       AMOUNT_MEMBER, voteAccess.voteStruct.getAmountName(),
                       LIST_MEMBER, voteAccess.voteStruct.getListName(),
                       CANDIDATE_VAR, voteAccess.voteIndex,
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       VOTE_VAR, VOTE_NUMBER.replace(NUMBER,
                                                     String.valueOf(voteAccess.voteNumber)));
        final CodeTemplateVoteSumForCandidate sumForCand = new CodeTemplateVoteSumForCandidate();
        final String code = sumForCand.getTemplate(votingInputType);
        final List<LoopBound> loopbounds =
                CodeTemplateVoteSumForCandidate.getLoopBounds(votingInputType);

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        return CodeGenerationToolbox.replacePlaceholders(code, replaceMap);
    }

    /**
     * TODO: Write documentation.
     *
     * @author Holger Klein
     *
     */
    public static class VoteArrayAccess {
        public final ElectionTypeCStruct voteStruct;
        public final int voteNumber;
        public final String voteIndex;

        public VoteArrayAccess(final ElectionTypeCStruct struct,
                               final int number,
                               final String index) {
            this.voteStruct = struct;
            this.voteNumber = number;
            this.voteIndex = index;
        }
    }
}
