package edu.pse.beast.api.codegen.helperfunctions;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.helperfunctions.templates.CodeTemplateVoteComparison;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class IsVoteEmptyHelper {
	public static String generateCode(
				String generatedVarName,
				String testedVarName,
				ElectionTypeCStruct votingStruct,
				VotingInputTypes votingInputType,
				String assumeAssert,
				CodeGenOptions options,
				LoopBoundHandler loopBoundHandler			
			) {
		
		String code = null;		
		switch(votingInputType) {
			case APPROVAL : {					
				break;
			}
			case WEIGHTED_APPROVAL : {
				break;
			}
			case PREFERENCE : {				
				break;
			}
			case SINGLE_CHOICE : {
				break;
			}
			case SINGLE_CHOICE_STACK : {
				break;
			}
		}
		
		return code;
	}
}
