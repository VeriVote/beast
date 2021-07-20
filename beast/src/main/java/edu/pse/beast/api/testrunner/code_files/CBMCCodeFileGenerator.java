package edu.pse.beast.api.testrunner.code_files;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;

import org.apache.commons.io.FileUtils;

import edu.pse.beast.api.codegen.cbmc.CBMCCodeGenerator;
import edu.pse.beast.api.codegen.cbmc.CodeGenOptions;
import edu.pse.beast.api.codegen.cbmc.generated_code_info.CBMCGeneratedCodeInfo;
import edu.pse.beast.api.codegen.helperfunctions.init_vote.InitVoteHelper;
import edu.pse.beast.api.descr.c_electiondescription.CElectionDescription;
import edu.pse.beast.api.descr.property_description.PreAndPostConditionsDescription;
import edu.pse.beast.api.paths.PathHandler;

public class CBMCCodeFileGenerator {

    /** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/cbmc_generated_files/";

    public static CBMCCodeFileData
                createCodeFileTest(final CElectionDescription descr,
                                   final PreAndPostConditionsDescription propDescr,
                                   final CodeGenOptions options,
                                   final PathHandler pathHandler,
                                   final InitVoteHelper initVoteHelper) throws IOException {
        final CBMCGeneratedCodeInfo code =
                CBMCCodeGenerator.generateCodeForCBMCPropertyTest(descr, propDescr, options,
                                                                  initVoteHelper);
        final String absolutePath = pathHandler.getBaseDir().getAbsolutePath()
                + PATH_TO_TEMP_FOLDER;
        final File file = File.createTempFile("cbmc", ".c", new File(absolutePath));
        FileUtils.writeStringToFile(file, code.getCode(),
                Charset.defaultCharset());
        final CBMCCodeFileData codeFile = new CBMCCodeFileData();
        codeFile.setCodeInfo(code);
        codeFile.setFile(file);
        return codeFile;
    }
}
