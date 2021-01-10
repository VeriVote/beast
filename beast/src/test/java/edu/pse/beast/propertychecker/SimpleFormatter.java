package edu.pse.beast.propertychecker;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public class SimpleFormatter {

	static String getTabs(int amt) {
		String created = "";
		for (int i = 0; i < amt; ++i)
			created += "\t";
		return created;
	}

	static String format(String code) {
		code = code.replace("{", "{\n").replace("}", "}\n").replace(";", ";\n");
		List<String> lines = new ArrayList<>(Arrays.asList(code.split("\n")));

		int tabs = 0;
		for (int i = 0; i < lines.size(); i++) {
			String replacement = lines.get(i).trim();

			if (replacement.contains("}")) {
				tabs--;
			}

			replacement = getTabs(tabs) + replacement;
			lines.set(i, replacement);

			if (replacement.contains("{")) {
				tabs++;
			}

		}

		return String.join("\n", lines);
	}
}
