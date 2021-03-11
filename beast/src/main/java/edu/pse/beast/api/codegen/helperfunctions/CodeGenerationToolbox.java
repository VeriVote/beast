package edu.pse.beast.api.codegen.helperfunctions;

import java.util.List;
import java.util.Map;

public class CodeGenerationToolbox {
	public static void replaceLoopBounds(List<String> list, String before,
			String after) {
		list.replaceAll(s -> s.replaceAll(before, after));
	}

	public static String replacePlaceholders(String code,
			Map<String, String> replacements) {

		for (String k : replacements.keySet()) {
			code = code.replaceAll(k, replacements.get(k));
		}

		return code;
	}
}
