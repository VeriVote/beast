package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.code_template.templates.elect.CodeTemplateElectComparison;
import edu.pse.beast.api.codegen.loopbounds.CodeGenLoopBoundHandler;
import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class ElectComparisonHelper {

	private static List<LoopBound> getLoopBounds(
			VotingOutputTypes votingOutputType) {
		switch (votingOutputType) {
			case CANDIDATE_LIST : {
				return CodeTemplateElectComparison.loopBoundsCandidateList;
			}
			case PARLIAMENT : {
				return CodeTemplateElectComparison.loopBoundsParliament;
			}
			case PARLIAMENT_STACK : {
				throw new NotImplementedException();
			}
			case SINGLE_CANDIDATE : {
				throw new NotImplementedException();
			}
		}
		return List.of();
	}

	public static String generateCode(String generatedVarName,
			String lhsVarName, String rhsVarName,
			ElectionTypeCStruct comparedType,
			VotingOutputTypes votingOutputType, CodeGenOptions options,
			String compareSymbol, CodeGenLoopBoundHandler loopBoundHandler) {

		Map<String, String> replacementMap = Map.of("GENERATED_VAR",
				generatedVarName, "LHS_VAR", lhsVarName, "RHS_VAR", rhsVarName,
				"AMT_MEMBER", comparedType.getAmtName(), "AMT_CANDIDATES",
				options.getCbmcAmountMaxCandsVarName(), "COMP", compareSymbol,
				"LIST_MEMBER", comparedType.getListName());

		String code = null;

		if (compareSymbol.equals("!=")) {
			switch (votingOutputType) {
				case CANDIDATE_LIST : {
					code = CodeTemplateElectComparison.templateCandidateListUneq;
					break;
				}
				case PARLIAMENT : {
					code = CodeTemplateElectComparison.templateParliamentUneq;
					break;
				}
				case PARLIAMENT_STACK : {
					break;
				}
				case SINGLE_CANDIDATE : {
					code = CodeTemplateElectComparison.templateSingleCandidate;
					break;
				}
			}
		} else {
			switch (votingOutputType) {
				case CANDIDATE_LIST : {
					code = CodeTemplateElectComparison.templateCandidateList;
					break;
				}
				case PARLIAMENT : {
					code = CodeTemplateElectComparison.templateParliament;
					break;
				}
				case PARLIAMENT_STACK : {
					break;
				}
				case SINGLE_CANDIDATE : {
					code = CodeTemplateElectComparison.templateSingleCandidate;
					break;
				}
			}
		}

		List<LoopBound> loopbounds = getLoopBounds(votingOutputType);
		loopBoundHandler.pushMainLoopBounds(loopbounds);

		code = CodeGenerationToolbox.replacePlaceholders(code, replacementMap);
		return code;
	}
}
