package edu.pse.beast.api.c_parser;

import java.util.List;

import org.junit.Test;

import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;

public class AntlrCParserTest {
	@Test
	public void testAntlrCParser() {
		String bordaCode = 
				  "    unsigned int i = 0;\n"
				+ "    unsigned int j = 0;\n"
				+ "    for (i = 0; i < C; i++) {\n" 
				+ "        result[i] = 0;\n"
				+ "    }\n" 
				+ "    for (i = 0; i < V; i++) {\n"
				+ "        for (j = 0; j < C; j++) {\n"
				+ "            if (votes[i][j] < C) {\n"
				+ "                result[votes[i][j]] += (C - j) - 1;\n"
				+ "            }\n" 
				+ "        }\n" 
				+ "    }";
		
		CodeGenOptions options = new CodeGenOptions();
		options.setCurrentAmountCandsVarName("C");
		options.setCurrentAmountVotersVarName("V");
		
		List<ExtractedCLoop>extractedCLoops = 
				AntlrCLoopParser.findLoops("voting", bordaCode, options);
		
		for(ExtractedCLoop l : extractedCLoops) {
			System.out.println(l.toString());
		}
	}
}
