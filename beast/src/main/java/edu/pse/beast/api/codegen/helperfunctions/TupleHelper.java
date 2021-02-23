package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

import edu.pse.beast.api.codegen.CodeGenOptions;
import edu.pse.beast.api.codegen.ElectionTypeCStruct;

public class TupleHelper {
	
	private static String varSetupTemplate = 
			  "        VOTE_TYPE VAR_NAME;\n"
			+ "        VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "        ASSUME(VAR_NAME.AMT_MEMBER == VOTEAMTSUM);\n"
			+ "        unsigned int pos = 0;\n";
	
	private static String template2d = 
			  "        for (unsigned int i = 0; i < CURRENT_VOTE.AMT_MEMBER; ++pos) {\n"
			+ "            for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "                VAR_NAME.votes[pos][j] = NONDET_UINT();\n"
			+ "                ASSUME(VAR_NAME.LIST_MEMBER[pos][j] == CURRENT_VOTE.LIST_MEMBER[i][j]);\n"
			+ "            }\n"
			+ "            pos++;\n"
			+ "        }\n";
	
	public static String generateCode(String generatedVarName, List<String> voteNames,
			ElectionTypeCStruct voteArrStruct, CodeGenOptions options) {
		String code = varSetupTemplate;
		String voteSum = "";
		for(int i = 0; i < voteNames.size() - 1; ++i) {
			voteSum += "CURRENT_VOTE.AMT_MEMBER + ".replaceAll("CURRENT_VOTE", voteNames.get(i));
		}
		voteSum += "CURRENT_VOTE.AMT_MEMBER".replaceAll("CURRENT_VOTE", voteNames.get(voteNames.size() - 1));
		code = code.replaceAll("VOTEAMTSUM", voteSum);
		
		if(voteArrStruct.getVotingType().getListDimensions() == 2) {
			for(String voteVarName : voteNames) {
				code += template2d;
				code = code.replaceAll("CURRENT_VOTE", voteVarName);
			}			
			code = code.replaceAll("AMT_MEMBER", voteArrStruct.getAmtName());
			code = code.replaceAll("LIST_MEMBER", voteArrStruct.getListName());
			code = code.replaceAll("NONDET_UINT", options.getCbmcNondetUintName());
			code = code.replaceAll("AMT_CANDIDATES", options.getCbmcAmountCandidatesVarName());
			code = code.replaceAll("VOTE_TYPE", voteArrStruct.getStruct().getName());
			code = code.replaceAll("VAR_NAME", generatedVarName);
			code = code.replaceAll("ASSUME", options.getCbmcAssumeName());

		}
		
		System.out.println(code);
		return code;
	}
}
