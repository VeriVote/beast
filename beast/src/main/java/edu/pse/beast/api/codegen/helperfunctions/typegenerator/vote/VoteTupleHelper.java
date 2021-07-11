package edu.pse.beast.api.codegen.helperfunctions.typegenerator.vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.vote.CodeTemplateVoteTuple;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingInputTypes;

public class VoteTupleHelper {

    public static String generateCode(String generatedVarName,
            List<String> voteNames, ElectionTypeCStruct voteArrStruct,
            VotingInputTypes votingInputType, CodeGenOptions options,
            CodeGenLoopBoundHandler loopBoundHandler) {

        String voteAmtSum = "";
        for (int i = 0; i < voteNames.size() - 1; ++i) {
            voteAmtSum += "CURRENT_VOTE.AMT_MEMBER + "
                    .replaceAll("CURRENT_VOTE", voteNames.get(i))
                    .replace("AMT_MEMBER", voteArrStruct.getAmtName());
        }
        voteAmtSum += "CURRENT_VOTE.AMT_MEMBER"
                .replaceAll("CURRENT_VOTE", voteNames.get(voteNames.size() - 1))
                .replace("AMT_MEMBER", voteArrStruct.getAmtName());

        Map<String, String> replacementMap = Map.of("VOTE_AMT_SUM", voteAmtSum,
                "AMT_MEMBER", voteArrStruct.getAmtName(), "LIST_MEMBER",
                voteArrStruct.getListName(), "NONDET_UINT",
                options.getCbmcNondetUintName(), "AMT_CANDIDATES",
                options.getCbmcAmountMaxCandsVarName(), "AMT_VOTERS",
                options.getCbmcAmountMaxVotersVarName(), "VOTE_TYPE",
                voteArrStruct.getStruct().getName(), "VAR_NAME",
                generatedVarName, "ASSUME", options.getCbmcAssumeName());

        String code = CodeTemplateVoteTuple.templateVarSetup;
        List<LoopBound> loopbounds = new ArrayList<>();

        switch (votingInputType) {
        case APPROVAL: {
            throw new NotImplementedException();
        }
        case WEIGHTED_APPROVAL: {
            throw new NotImplementedException();
        }
        case PREFERENCE: {
            for (String voteVarName : voteNames) {
                code += CodeTemplateVoteTuple.templatePreference
                        .replaceAll("CURRENT_VOTE", voteVarName);
                loopbounds = CodeTemplateVoteTuple.loopBoundsPreference;
            }
            break;
        }
        case SINGLE_CHOICE: {
            throw new NotImplementedException();
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
