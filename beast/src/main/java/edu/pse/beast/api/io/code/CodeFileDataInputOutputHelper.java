package edu.pse.beast.api.io.code;

import java.io.File;

import org.json.JSONObject;

import edu.pse.beast.api.codegen.cbmc.info.GeneratedCodeInfo;
import edu.pse.beast.api.io.RelativePathConverter;
import edu.pse.beast.api.runner.codefile.CodeFileData;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class CodeFileDataInputOutputHelper {
    private static final String GENERATED_CODE_INFO_KEY = "generated_code_info";
    private static final String RUN_FILE_KEY = "run_file";

    public static JSONObject codeFileToJSON(final CodeFileData cbmcCodeFileData,
                                            final RelativePathConverter pathHandler) {
        final JSONObject json = new JSONObject();
        final String relPath = pathHandler.getRelativePathTo(cbmcCodeFileData.getFile());
        json.put(RUN_FILE_KEY, relPath);
        json.put(GENERATED_CODE_INFO_KEY,
                GeneratedCodeInfoInputOutputHelper.generatedCodeInfoToJSON(
                        cbmcCodeFileData.getCodeInfo()));
        return json;
    }

    public static CodeFileData codeFileFromJSON(final JSONObject json,
                                                final RelativePathConverter pathHandler) {
        final CodeFileData cbmcCodeFileData = new CodeFileData();
        final String relCodeFilePath = json.getString(RUN_FILE_KEY);
        final File codeFile = pathHandler.getFileFromRelativePath(relCodeFilePath);
        final GeneratedCodeInfo codeInfo =
                GeneratedCodeInfoInputOutputHelper
                .generatedCodeInfoFromJSON(json.getJSONObject(GENERATED_CODE_INFO_KEY));
        cbmcCodeFileData.setFile(codeFile);
        cbmcCodeFileData.setCodeInfo(codeInfo);
        return cbmcCodeFileData;
    }
}
