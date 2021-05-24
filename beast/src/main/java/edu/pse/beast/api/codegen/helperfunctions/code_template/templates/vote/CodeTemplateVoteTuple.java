package edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateVoteTuple {
	public final static String templateVarSetup = 
			  "        VOTE_TYPE VAR_NAME;\n"
			+ "        VAR_NAME.AMT_MEMBER = NONDET_UINT();\n"
			+ "        ASSUME(VAR_NAME.AMT_MEMBER == VOTE_AMT_SUM);\n"
			+ "        unsigned int pos = 0;\n";
	
	public final static String templatePreference = 
			  "        for (unsigned int i = 0; i < CURRENT_VOTE.AMT_MEMBER && i < AMT_VOTERS; ++pos) {\n"
			+ "            for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
			+ "                VAR_NAME.LIST_MEMBER[pos][j] = NONDET_UINT();\n"
			+ "                ASSUME(VAR_NAME.LIST_MEMBER[pos][j] == CURRENT_VOTE.LIST_MEMBER[i][j]);\n"
			+ "            }\n"
			+ "            pos++;\n"
			+ "        }\n";
	

	public final static List<LoopBound> loopBoundsPreference =
			LoopBound.codeGenLoopboundList(
				Arrays.asList(
					LoopBoundType.LOOP_BOUND_AMT_VOTERS, 
					LoopBoundType.LOOP_BOUND_AMT_CANDS)
			);
			
}
