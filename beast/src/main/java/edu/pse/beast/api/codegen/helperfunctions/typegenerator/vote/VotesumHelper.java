package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteSumForCandidate;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class VotesumHelper {
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
                                      final VotingInputTypes votingInputType,
                                      final CodeGenOptions options,
                                      final CodeGenLoopBoundHandler loopBoundHandler,
                                      final Class<?> c) {
        final Map<String, String> replaceMap =
                Map.of(GENERATED_VAR, generatedVarName,
                       AMOUNT_MEMBER, voteAccess.voteStruct.getAmtName(),
                       LIST_MEMBER, voteAccess.voteStruct.getListName(),
                       CANDIDATE_VAR, voteAccess.voteIndex,
                       AMOUNT_VOTERS, options.getCbmcAmountMaxVotersVarName(),
                       VOTE_VAR, VOTE_NUMBER.replace(NUMBER,
                                                     String.valueOf(voteAccess.voteNumber)));
        final String code = CodeTemplateVoteSumForCandidate.getTemplate(votingInputType, c);
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
