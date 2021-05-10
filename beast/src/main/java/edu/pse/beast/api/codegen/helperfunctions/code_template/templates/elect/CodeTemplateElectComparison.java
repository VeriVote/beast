package edu.pse.beast.api.codegen.helperfunctions.code_template.templates.elect;

import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public class CodeTemplateElectComparison {
	
		public static String templateSingleCandidate = 
			  "unsigned int GENERATED_VAR = LHS_VAR.LIST_MEMBER COMP RHS_VAR.LIST_MEMBER;\n";	
		
		public static String templateCandidateList = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i] COMP RHS_VAR.LIST_MEMBER[i];\n"
				+ "}\n";
		
		public static String templateCandidateListUneq = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i] != RHS_VAR.LIST_MEMBER[i];\n"
				+ "}\n";		
		
		public final static List<LoopBoundType> loopBoundsCandidateList 
											= Arrays.asList(LoopBoundType.LOOP_BOUND_AMT_CANDS);		

		public static String templateParliamentUneq = templateCandidateListUneq;
		public static String templateParliament = templateCandidateList;
		
		public final static List<LoopBoundType> loopBoundsParliament
											= loopBoundsCandidateList;

}
