package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundHandler;
import edu.pse.beast.api.electiondescription.VotingInputTypes;

public class VoteTupleHelper {
	public static String generateCode(
			String generatedVarName,
			List<String> voteNames, 
			ElectionTypeCStruct voteArrStruct,
			VotingInputTypes votingInputType,
			CodeGenOptions options, 
			LoopBoundHandler loopBoundHandler
			) {
		String code = null;
		
		return code;
	}
}
