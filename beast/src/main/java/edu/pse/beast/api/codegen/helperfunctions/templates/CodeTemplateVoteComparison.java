package edu.pse.beast.api.codegen.helperfunctions.templates;

import java.util.Arrays;
import java.util.List;

public class CodeTemplateVoteComparison {
		
		public static String templateSingleChoiceEq = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i] COMP RHS_VAR.LIST_MEMBER[i];\n"
				+ "}\n";
		
		public static String templateSingleChoiceUnEq = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i] != RHS_VAR.LIST_MEMBER[i];\n"
				+ "}\n";		
		
		public final static List<String> loopBoundsSingleChoice = Arrays.asList(
				"AMT_VOTERS");
		
		public static String templatePreferenceEq = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER == RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    for (int j = 0; j < INNER_BOUND; ++j) {\n"
				+ "        GENERATED_VAR &= LHS_VAR.LIST_MEMBER[i][j] COMP RHS_VAR.LIST_MEMBER[i][j];\n"
				+ "    }\n"
				+ "}\n";
		
		public static String templatePreferenceUnEq = 
				  "unsigned int GENERATED_VAR = LHS_VAR.AMT_MEMBER != RHS_VAR.AMT_MEMBER;\n"
				+ "for (int i = 0; i < LHS_VAR.AMT_MEMBER; ++i) {\n"
				+ "    for (int j = 0; j < AMT_CANDIDATES; ++j) {\n"
				+ "        GENERATED_VAR |= LHS_VAR.LIST_MEMBER[i][j] != RHS_VAR.LIST_MEMBER[i][j];\n"
				+ "    }\n"
				+ "}\n";
		
		public final static List<String> loopBoundsPreference = Arrays.asList(
				"AMT_VOTERS", "AMT_CANDIDATES");		
	
}
