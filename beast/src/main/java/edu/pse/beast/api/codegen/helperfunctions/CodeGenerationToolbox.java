package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;

public class CodeGenerationToolbox {
	public static void replaceAll(List<String> list, String before,
			String after) {
		list.replaceAll(s -> s.replaceAll(before, after));
	}
}
