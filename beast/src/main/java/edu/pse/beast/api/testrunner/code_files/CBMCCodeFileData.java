package edu.pse.beast.api.testrunner.code_files;

import java.io.File;

import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CBMCCodeFileData {
    private File f;
    private CBMCGeneratedCodeInfo codeInfo;

    public final File getFile() {
        return f;
    }

    public final void setFile(final File file) {
        this.f = file;
    }

    public final CBMCGeneratedCodeInfo getCodeInfo() {
        return codeInfo;
    }

    public final void setCodeInfo(final CBMCGeneratedCodeInfo code) {
        this.codeInfo = code;
    }
}
