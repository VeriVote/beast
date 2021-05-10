package edu.pse.beast.api.codegen;

import org.junit.Test;

import edu.pse.beast.api.codegen.helperfunctions.code_template.templates.vote.CodeTemplateVoteIntersection;

public class CParserTest {
	@Test
	public void testFindLoops() {
		String code = CodeTemplateVoteIntersection.templatePreference;
		
		CodeGenCParser.parseC(code);
	}
}
