package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.helperfunctions.templates.typeGenerator.elect.CodeTemplateElectPermutation;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;
import edu.pse.beast.api.electiondescription.VotingOutputTypes;

public class IsElectEmptyHelper {
	public static String generateCode(
				String generatedVarName,
				String testedVarName,
				ElectionTypeCStruct electStruct,
				VotingOutputTypes votingOutputType,
				String assumeAssert,
				CodeGenOptions options,
				LoopBoundHandler loopBoundHandler			
			) {
		
		String code = null;		
		switch(votingOutputType) {
			case CANDIDATE_LIST : {
				break;
			}
			case PARLIAMENT : {
				break;
			}
			case PARLIAMENT_STACK : {
				break;
			}
			case SINGLE_CANDIDATE : {
				break;
			}
		}
		
		return code;
	}
}
