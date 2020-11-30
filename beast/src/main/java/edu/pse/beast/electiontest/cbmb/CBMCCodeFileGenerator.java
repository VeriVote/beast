package edu.pse.beast.electiontest.cbmb;

import java.io.File;
import java.util.ArrayList;

import edu.pse.beast.datatypes.electiondescription.ElectionDescription;
import edu.pse.beast.datatypes.propertydescription.PreAndPostConditionsDescription;
import edu.pse.beast.electionsimulator.ElectionSimulationData;
import edu.pse.beast.propertychecker.CBMCCodeGenerator;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.FileSaver;
import edu.pse.beast.toolbox.SuperFolderFinder;

public class CBMCCodeFileGenerator {  
	
	/** The Constant PATH_TO_TEMP_FOLDER. */
    private static final String PATH_TO_TEMP_FOLDER = "/core/generated_c_files/";   
    
    private static final String CANNOT_FIND_PARENT =
            "Cannot find a parent to your file!";


	 /**
     * Creates a new c-Code file that then can be used by all the underlying
     * checkers to check it with cbmc.
     *
     * @param electionDesc
     *            the source that describes the election
     * @param postAndPrepPropDesc
     *            the property that this specific process factory should check
     * @param inputData
     *            input data
     * @return a file that contains the generated code from the two above
     *         variables
     */
    public static File createCodeFileTest(final ElectionDescription electionDesc,
                                   final PreAndPostConditionsDescription postAndPrepPropDesc) {
        // Create a code generator, that creates a code file for this call only
        // one time in this factory factory;
        final CBMCCodeGenerator generator =
                new CBMCCodeGenerator(electionDesc,
                                      postAndPrepPropDesc);
        final ArrayList<String> code = generator.getCode();
        final String absolutePath = SuperFolderFinder.getSuperFolder()
                                    + PATH_TO_TEMP_FOLDER;
        final File file = new File(new File(absolutePath),
                                   FileLoader.getNewUniqueName(absolutePath)
                                       + FileLoader.C_FILE_ENDING);
        
        //TODO overhaul error logging
        if (file.getParentFile() == null) {
            ErrorLogger.log(CANNOT_FIND_PARENT);
        } else if (!file.getParentFile().exists()) {
            file.getParentFile().mkdirs();
        }
        FileSaver.writeStringLinesToFile(code, file);
        // FileSaver.writeStringLinesToFile(generator.getCode(), file);
        return file;
    }
}
