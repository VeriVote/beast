package edu.pse.beast.toolbox;

import java.io.File;

/**
 * The Class SuperFolderFinder.
 *
 * @author Lukas Stapelbroek
 */
public final class SuperFolderFinder {

    /** The initialized. */
    private static boolean initialized = false;

    /** The super folder. */
    private static String superFolder = "";

    /**
     * Instantiates a new super folder finder.
     */
    private SuperFolderFinder() { }

    /**
     * This class is used so other classes can get the folder that BEAST is
     * installed in so resources can be found easily.
     *
     * @return the location of the top folder containing beast
     */
    public static String getSuperFolder() {
        if (!initialized || superFolder.length() == 0) {
            // the class is two "directories away from the super folder
            // counteract possible whitespace errors on windows
            final String pathName =
            // TODO: Why are we not using 'System.getProperty("user.dir")'?
                    SuperFolderFinder.class.getProtectionDomain()
                            .getCodeSource().getLocation().getPath();
            final File f = new File(pathName);
            superFolder =
                    (new File(f.getParent()).getParent()).replaceAll("%20", " ");
        }
        return superFolder;
    }
}
