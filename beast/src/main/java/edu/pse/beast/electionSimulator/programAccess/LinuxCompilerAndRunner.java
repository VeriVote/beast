package edu.pse.beast.electionSimulator.programAccess;

import edu.pse.beast.codearea.ErrorHandling.CodeError;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * the linux implementation for checking the code This implementation uses gcc
 * for checking
 * 
 * @author Lukas
 *
 */
public class LinuxCompilerAndRunner extends SystemSpecificCompilerAndExecutioner {

    // program that is to be used for checking
    private final String compilerString = "gcc";

    // this flag prohibits that file are creates by the compiler and
    // only the syntax is checked
    private final String findMissingReturnOption = "-Wreturn-type";

    // we want to compile to a specific name, so we can delete the file
    // then later on
    private final String setOutputFileName = "-o ";

    private final String enableUserInclude = "-I/";
    private final String userIncludeFolder = "/core/user_includes/";

    // we want to compile all available c files, so the user doesn't have to
    // specify anything
    private final String cFileEnder = ".c";

    // if gcc finds, that a return is missing, it prints out this error message.
    // The error then
    // stands in the format: "FILENANE:LINE:COLUMN warning:control reaches..."
    private final String gccMissingReturnFound = "warning: control reaches end of non-void function";

    // if gcc finds that a function is missing, it gets displayed like this:
    private final String gccMissingFuctionFound = "warning: implicit declaration of function";

    
	@Override
	protected Process compileCfile(File toCheck) {

		//the name of the file
        String nameOfOutFile = toCheck.getName().replace(".c", ".out");
        
        File outFile = new File(toCheck.getParentFile(), nameOfOutFile);

        String compileToThis =  setOutputFileName + outFile.getAbsolutePath();

        String userIncludeAndPath = enableUserInclude + SuperFolderFinder.getSuperFolder() + userIncludeFolder;

        // get all Files from the form "*.c" so we can include them into cbmc,
        List<String> allFiles = FileLoader.listAllFilesFromFolder(
                "\"" + SuperFolderFinder.getSuperFolder() + userIncludeFolder + "\"", cFileEnder);

        Process startedProcess = null;

        List<String> arguments = new ArrayList<String>();

        // add the arguments needed for the call
        arguments.add(compilerString);

        arguments.add(userIncludeAndPath);

        arguments.add(findMissingReturnOption);

        // add the path to the created file that should be checked
        arguments.add(toCheck.getAbsolutePath());

        // iterate over all "*.c" files from the include folder, to include them
        for (Iterator<String> iterator = allFiles.iterator(); iterator.hasNext();) {
            String toBeIncludedFile = (String) iterator.next();
            arguments.add(toBeIncludedFile.replace("\"", "").replace(" ", "\\ "));
        }

        // defines the position to what place the compiled files should be sent
        arguments.add(compileToThis);

        ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));

        try {
            // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startedProcess;
	}

	@Override
	protected Process runWithData(String toRun, File dataFile) {
		
		 Process startedProcess = null;
		
		//the list where the arguments for the call get saved in
		List<String> arguments = new ArrayList<String>();
		
		//on linux, our executable ends with .out
		toRun = toRun + ".out";
		
		//this argument calls the generated program
		arguments.add("./" + toRun);
		
		//the absolute path to the file that holds 
		arguments.add(dataFile.getAbsolutePath());
		
		ProcessBuilder prossBuild = new ProcessBuilder(arguments.toArray(new String[0]));

        try {
            // start the process
            startedProcess = prossBuild.start();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return startedProcess;
	}
}
