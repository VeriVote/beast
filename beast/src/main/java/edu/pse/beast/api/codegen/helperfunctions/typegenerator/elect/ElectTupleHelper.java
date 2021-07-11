package edu.pse.beast.api.codegen.helperfunctions.typegenerator.elect;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectTuple;
import edu.pse.beast.api.codegen.helperfunctions.CodeGenerationToolbox;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.descr.c_electiondescription.VotingOutputTypes;

public class ElectTupleHelper {
    public static String generateCode(String generatedVarName,
            List<String> electNames, ElectionTypeCStruct electStruct,
            VotingOutputTypes votingOutputType, CodeGenOptions options,
            CodeGenLoopBoundHandler loopBoundHandler) {

        String electAmtSum = "";
        for (int i = 0; i < electNames.size() - 1; ++i) {
            electAmtSum += "CURRENT_ELECT.AMT_MEMBER + "
                    .replaceAll("CURRENT_ELECT", electNames.get(i));
        }
        electAmtSum += "CURRENT_ELECT.AMT_MEMBER".replaceAll("CURRENT_ELECT",
                electNames.get(electNames.size() - 1));

        Map<String, String> replacementMap = Map.of("ELECT_AMT_SUM",
                electAmtSum, "AMT_MEMBER", electStruct.getAmtName(),
                "LIST_MEMBER", electStruct.getListName(), "NONDET_UINT",
                options.getCbmcNondetUintName(), "AMT_CANDIDATES",
                options.getCbmcAmountMaxCandsVarName(), "VOTE_TYPE",
                electStruct.getStruct().getName(), "VAR_NAME", generatedVarName,
                "ASSUME", options.getCbmcAssumeName());

        String code = CodeTemplateElectTuple.templateVarSetup;
        List<LoopBound> loopbounds = Arrays.asList();

        switch (votingOutputType) {
        case CANDIDATE_LIST: {
            for (String electVarName : electNames) {
                code += CodeTemplateElectTuple.templateCandidateList
                        .replaceAll("CURRENT_ELECT", electVarName);
                loopbounds = CodeTemplateElectTuple.loopBoundsCandidateList;
            }
            break;
        }
        case PARLIAMENT: {
            throw new NotImplementedException();
        }
        case PARLIAMENT_STACK: {
            throw new NotImplementedException();
        }
        case SINGLE_CANDIDATE: {
            throw new NotImplementedException();
        }
        }

        loopBoundHandler.pushMainLoopBounds(loopbounds);
        code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
        return code;
    }

}
