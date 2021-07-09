package edu.pse.beast.api.testrunner.code_files;

import java.io.File;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

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
