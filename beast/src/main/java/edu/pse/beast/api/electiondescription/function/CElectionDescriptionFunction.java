package edu.pse.beast.api.electiondescription.function;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import edu.pse.beast.api.c_parser.AntlrCLoopParser;
import edu.pse.beast.api.c_parser.CLoopParseResultType;
import edu.pse.beast.api.c_parser.ExtractedCLoop;
import edu.pse.beast.api.codegen.c_code.CFunction;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.loopbounds.LoopBoundType;

public abstract class CElectionDescriptionFunction {
	protected String name;
	protected List<String> code = new ArrayList<>();
	protected List<ExtractedCLoop> extractedLoops = new ArrayList<>();

	public CElectionDescriptionFunction(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public String getCode() {
		return String.join("\n", code);
	}

	public void setCode(String code) {
		this.code = Arrays.asList(code.split("\n"));
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<String> getCodeAsList() {
		return code;
	}

	public abstract String getDeclCString(CodeGenOptions codeGenOptions);

	@Override
	public String toString() {
		return name;
	}

	public List<ExtractedCLoop> getExtractedLoops() {
		return extractedLoops;
	}

	public void setExtractedLoops(List<ExtractedCLoop> extractedLoops) {
		this.extractedLoops = extractedLoops;
	}

	public boolean allLoopsDescribed() {
		if (extractedLoops.isEmpty()) {
			if (AntlrCLoopParser.getAmtLoops(getCode()) != 0)
				return false;
		} else {
			for (ExtractedCLoop l : extractedLoops) {
				if (l.getParsedLoopBoundType() == LoopBoundType.MANUALLY_ENTERED_INTEGER
						&& l.getManualInteger() == null) {
					return false;
				}
			}
		}
		return true;
	}

	public abstract String getReturnText(CodeGenOptions codeGenOptions);

}
