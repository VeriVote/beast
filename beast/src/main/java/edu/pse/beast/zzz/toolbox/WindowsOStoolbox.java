package edu.pse.beast.zzz.toolbox;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * The Class WindowsOStoolbox.
 *
 * @author Lukas Stapelbroek
 */
public final class WindowsOStoolbox {

    /** The Constant RELATIVE_PATH_TO_VS_CMD. */
    private static final String RELATIVE_PATH_TO_VS_CMD = "/windows/VsDevCmd.bat";

    /**
     * Instantiates a new windows O stoolbox.
     */
    private WindowsOStoolbox() { }

    /**
     * Gets the v scmd path.
     *
     * @return the String that should lead to a vsDevCMD which is required to
     *         run cbmc on windows
     * @throws IOException
     *             in case the VScmd could not be found this gets thrown
     */
    public static String getVScmdPath() throws IOException {
        final File file =
                new File(SuperFolderFinder.getSuperFolder() + RELATIVE_PATH_TO_VS_CMD);
        if (Files.isExecutable(file.toPath())) {
            return file.getPath();
        } else { 
        	// we were unable to locate the command prompt in the resources
            // and search now for it in the common install directories
            final Path x86 = new File("C:/Program Files (x86)").toPath();
            final Path x64 = new File("C:/Program Files").toPath();
            final String searchTerm = "Microsoft Visual Studio";
            final String pathToBatch = "/Common7/Tools/VsDevCmd.bat";

            final ArrayList<String> toSearch = new ArrayList<String>();
            Files.list(x86).filter(Files::isReadable)
                    .filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));
            Files.list(x64).filter(Files::isReadable)
                    .filter(path -> path.toString().contains(searchTerm))
                    .forEach(VSPath -> toSearch.add(VSPath.toString()));

            for (final Iterator<String> iterator = toSearch.iterator();
                    iterator.hasNext();) {
                final String toCheck = (iterator.next()) + pathToBatch;
                if (Files.isReadable(new File(toCheck).toPath())) {
                    return toCheck;
                }
            }
            ErrorForUserDisplayer
                    .displayError("The progam was unable to find a "
                            + "Developer Command Prompt for Visual Studio. \n"
                            + " Please install it if you have not and search for "
                            + "the vsCMD.bat in it! \n"
                            + " Please copy the .bat to the folder /windows/ in your "
                            + "BEAST install directory"
                            + "(named \"VsDevCmd.bat\") so it can "
                            + "be found automatically.");
            return "The program was unable to find a Developer Command Prompt "
                    + "for Visual Studio. Look at the error log";
        }
    }
}
