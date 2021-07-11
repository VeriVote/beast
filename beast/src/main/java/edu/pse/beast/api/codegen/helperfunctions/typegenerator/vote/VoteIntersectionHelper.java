package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteIntersection;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VoteIntersectionHelper {

    //TODO: This code was written when the intersection method was still thought
    //to be a lot simpler. Thus, this code could generate intersection for an 
    //arbitrary amount of vote structs. This is no longer the case,
    //and intersection should only generate code for pairwaise intersections.
    //
    //This has been fixed for the generation of intersection code of 
    //election result structs. See the class 
    //edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect.ElectIntersectionHelper
    
    public static String generateVoteIntersection(String generatedVarName,
            List<String> intersectedVotesVarNames,
            ElectionTypeCStruct voteArrStruct, VotingInputTypes votingInputType,
            CodeGenOptions options, CodeGenLoopBoundHandler loopBoundHandler) {

        String comparison = "";

        if (voteArrStruct.getVotingType().getListDimensions() == 2) {
            int i = 0;
            for (; i < intersectedVotesVarNames.size() - 2; ++i) {
                comparison += intersectedVotesVarNames.get(i)
                        + ".LIST_MEMBER[i][j] == "
                        + intersectedVotesVarNames.get(i + 1)
                        + ".LIST_MEMBER[i][j] && ";
            }
            comparison += intersectedVotesVarNames.get(i)
                    + ".LIST_MEMBER[i][j] == "
                    + intersectedVotesVarNames.get(i + 1)
                    + ".LIST_MEMBER[i][j]";
        } else if (voteArrStruct.getVotingType().getListDimensions() == 1) {
            int i = 0;
            for (; i < intersectedVotesVarNames.size() - 2; ++i) {
                comparison += intersectedVotesVarNames.get(i)
                        + ".LIST_MEMBER[i]] == "
                        + intersectedVotesVarNames.get(i + 1)
                        + ".LIST_MEMBER[i] && ";
            }
            comparison += intersectedVotesVarNames.get(i)
                    + ".LIST_MEMBER[i] == "
                    + intersectedVotesVarNames.get(i + 1) + ".LIST_MEMBER[i]";
        }

        Map<String, String> replacementMap = Map.of("COMPARE_VARS", comparison,
                "VOTE_TYPE", voteArrStruct.getStruct().getName(), "AMT_MEMBER",
                voteArrStruct.getAmtName(), "LIST_MEMBER",
                voteArrStruct.getListName(), "GENERATED_VAR_NAME",
                generatedVarName, "LHS_VAR_NAME",
                intersectedVotesVarNames.get(0), "AMT_VOTERS",
                options.getCbmcAmountMaxVotersVarName(), "AMT_CANDIDATES",
                options.getCbmcAmountMaxCandsVarName(), "ASSUME",
                options.getCbmcAssumeName(), "NONDET_UINT",
                options.getCbmcNondetUintName());

        String code = null;
        List<LoopBound> loopbounds = List.of();

        switch (votingInputType) {
        case APPROVAL: {
            code = CodeTemplateVoteIntersection.templateApproval;
            loopbounds = CodeTemplateVoteIntersection.loopBoundsApproval;
            break;
        }
        case WEIGHTED_APPROVAL: {
            throw new NotImplementedException();
        }
        case PREFERENCE: {
            code = CodeTemplateVoteIntersection.templatePreference;
            loopbounds = CodeTemplateVoteIntersection.loopBoundsPreference;
            break;
        }
        case SINGLE_CHOICE: {
            code = CodeTemplateVoteIntersection.templateSingleChoice;
            loopbounds = CodeTemplateVoteIntersection.loopBoundsSingleChoice;
            break;
        }
        case SINGLE_CHOICE_STACK: {
            throw new NotImplementedException();
        }
        }

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
        return code;
    }
}
