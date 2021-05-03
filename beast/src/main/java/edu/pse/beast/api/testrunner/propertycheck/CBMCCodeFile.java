package edu.pse.beast.api.testrunner.propertycheck;

import java.io.File;

import edu.pse.beast.api.codegen.CBMCGeneratedCodeInfo;

public class CBMCCodeFile {
	private File f;
	private CBMCGeneratedCodeInfo code;

	public File getFile() {
		return f;
	}

	public CBMCGeneratedCodeInfo getCode() {
		return code;
	}

	public void setFile(File f) {
		this.f = f;
	}

	public void setCode(CBMCGeneratedCodeInfo code) {
		this.code = code;
	}
}
