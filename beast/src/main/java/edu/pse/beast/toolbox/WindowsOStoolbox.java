package edu.pse.beast.toolbox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

public class WindowsOStoolbox {

    private static final String RELATIVEPATHTOVSCMD = "/windows/VsDevCmd.bat";

    /**
     * 
     * @return the String that should lead to a vsDevCMD which is required to run
     *         cbmc on windows
     * @throws IOException in case the VScmd couldn't be found this gets thrown
     */
    public static String getVScmdPath() throws IOException {

        File file = new File(SuperFolderFinder.getSuperFolder() + RELATIVEPATHTOVSCMD);

        if (Files.isExecutable(file.toPath())) {
            return file.getPath();
        } else { // we were unable to locate the command promp in the resources
            // and search now for it in the common install directories

            Path x86 = new File("C:/Program Files (x86)").toPath();
            Path x64 = new File("C:/Program Files").toPath();
            String searchTerm = "Microsoft Visual Studio";
            String pathToBatch = "/Common7/Tools/VsDevCmd.bat";

            ArrayList<String> toSearch = new ArrayList<>();
            Files.list(x86).filter(Files::isReadable).filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));
            Files.list(x64).filter(Files::isReadable).filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));

            for (Iterator<String> iterator = toSearch.iterator(); iterator.hasNext();) {
                String toCheck = ((String) iterator.next()) + pathToBatch;

                if (Files.isReadable(new File(toCheck).toPath())) {
                    return toCheck;
                }
            }

            ErrorForUserDisplayer
                    .displayError("The progam was unable to find a Developer Command Prompt for Visual Studio. \n"
                            + " Please install it if you haven't and search for the vsCMD.bat in it! \n"
                            + " Please copy the .bat to the folder /windows/ in your BEST install directory"
                            + "(named \"VsDevCmd.bat\") so it can be found automatically.");

            return "The progam was unable to find a Developer Command Prompt for Visual Studio. Look at the error log";
        }
    }
}
