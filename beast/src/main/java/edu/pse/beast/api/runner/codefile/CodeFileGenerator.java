package edu.pse.beast.api.runner.codefile;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.codegen.init.InitVoteHelper;
import edu.pse.beast.api.io.PathHandler;
import edu.pse.beast.api.method.CElectionDescription;
import edu.pse.beast.api.property.PropertyDescription;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeFileGenerator {
    private static final String TMP_PREFIX = "gen_";
    private static final String DOT_C = ".c";

    /** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/core/cfiles/";

    public static CodeFileData createCodeFileCheck(final CElectionDescription descr,
                                                   final PropertyDescription propDescr,
                                                   final CodeGenOptions options,
                                                   final PathHandler pathHandler,
                                                   final InitVoteHelper initVoteHelper)
                                                           throws IOException {
        final GeneratedCodeInfo code =
                CBMCCodeGenerator.generateCodeForPropertyCheck(descr, propDescr, options,
                                                                   initVoteHelper);
        final String absolutePath =
                pathHandler.getBaseDir().getAbsolutePath() + PATH_TO_TEMP_FOLDER;
        final File file = File.createTempFile(TMP_PREFIX, DOT_C, new File(absolutePath));
        FileUtils.writeStringToFile(file, code.getCode(),
                Charset.defaultCharset());
        final CodeFileData codeFile = new CodeFileData();
        codeFile.setCodeInfo(code);
        codeFile.setFile(file);
        return codeFile;
    }
}
