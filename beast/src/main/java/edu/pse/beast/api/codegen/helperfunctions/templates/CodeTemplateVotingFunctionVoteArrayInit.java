package edu.pse.beast.api.codegen.helperfunctions.templates;

import java.util.Arrays;
import java.util.List;

public class CodeTemplateVotingFunctionVoteArrayInit {
	public final static String templateSingleChoice = 
			  " unsigned int VOTE_ARR[AMT_VOTERS];\n"
			+ "	for (int i = 0; i < VOTE_INPUT_STRUCT_VAR.AMT_MEMBER; ++i) {\n"
			+ "     VOTE_ARR[i] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i];\n" 
			+ " }";
		
	public final static List<String> singleChoiceloopBounds =
			Arrays.asList("AMT_VOTERS");

	public final static String templatePreference = 
			  " unsigned int VOTE_ARR[AMT_VOTERS][AMT_CANDIDATES];\n"
			+ "	for (int i = 0; i < VOTE_INPUT_STRUCT_VAR.AMT_MEMBER; ++i) {\n"
			+ " 	for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "     	VOTE_ARR[i][j] = VOTE_INPUT_STRUCT_VAR.LIST_MEMBER[i][j];\n" 
			+ "     }\n" 
			+ " }";			
	
	public final static List<String> preferenceLoopBounds =
			Arrays.asList("AMT_VOTERS", "AMT_CANDIDATES");
	
	public final static String templateApproval = templatePreference;
	
}
