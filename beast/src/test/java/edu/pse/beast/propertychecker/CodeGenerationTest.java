package edu.pse.beast.propertychecker;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import edu.pse.beast.api.CreationHelper;
import edu.pse.beast.datatypes.electiondescription.ElectionDescription;

class CodeGenerationTest {

	
	
	@Test
	void testGenerateComplexCode() {
		ElectionDescription descr = CreationHelper.createSimpleDescription("return 1;\n");
		List<String> complexCode = descr.getComplexCodeAndSetHeaderLoopBounds();
		String code = String.join("\n", complexCode);
		System.out.println(SimpleFormatter.format(code));
	}

}
