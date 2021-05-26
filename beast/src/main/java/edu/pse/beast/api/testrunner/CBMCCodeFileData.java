package edu.pse.beast.api.testrunner;

import java.io.File;

import edu.pse.beast.api.codegen.cbmc.info.CBMCGeneratedCodeInfo;

public class CBMCCodeFileData {
	private File f;
	private CBMCGeneratedCodeInfo codeInfo;

	public File getFile() {
		return f;
	}

	public void setFile(File f) {
		this.f = f;
	}	

	public CBMCGeneratedCodeInfo getCodeInfo() {
		return codeInfo;
	}

	public void setCodeInfo(CBMCGeneratedCodeInfo code) {
		this.codeInfo = code;
	}
}
