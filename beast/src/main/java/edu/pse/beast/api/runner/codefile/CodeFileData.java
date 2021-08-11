package edu.pse.beast.api.runner.codefile;

import java.io.File;

import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeFileData {
    private File f;
    private GeneratedCodeInfo codeInfo;

    public final File getFile() {
        return f;
    }

    public final void setFile(final File file) {
        this.f = file;
    }

    public final GeneratedCodeInfo getCodeInfo() {
        return codeInfo;
    }

    public final void setCodeInfo(final GeneratedCodeInfo code) {
        this.codeInfo = code;
    }
}
