package edu.pse.beast.api.codegen.helperfunctions.code_template.templates;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVotingFunctionVoteArrayInit {
	public final static String templateSingleChoice = 
			  " unsigned int VOTE_ARR[AMT_VOTERS];\n"
			+ "	for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ "     VOTE_ARR[i] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i];\n" 
			+ " }";
		
	public final static List<LoopBound> loopBoundsSingleChoice =
			LoopBound.codeGenLoopboundList(
					Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS)
			);

	public final static String templatePreference = 
			  " unsigned int VOTE_ARR[AMT_VOTERS][AMT_CANDIDATES];\n"
			+ "	for (int i = 0; i < CURRENT_AMT_VOTER; ++i) {\n"
			+ " 	for (int j = 0; j < CURRENT_AMT_CAND; ++j) {\n"
			+ "     	VOTE_ARR[i][j] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i][j];\n" 
			+ "     }\n" 
			+ " }";			
	
	public final static List<LoopBound> loopBoundsPreference =
			LoopBound.codeGenLoopboundList(
					Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_VOTERS, LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);
	
	public final static String templateApproval = templatePreference;
	public final static List<LoopBound> loopBoundsApproval = loopBoundsPreference;

}
