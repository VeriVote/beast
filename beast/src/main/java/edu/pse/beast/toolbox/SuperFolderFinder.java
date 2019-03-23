package edu.pse.beast.toolbox;

import java.io.File;

/**
 * 
 * @author Lukas
 *
 */
public final class SuperFolderFinder {

    private static boolean INITIALIZED = false;

    private static String superFolder = "";

    /**
     * this class is used so other classes can get the folder that BEAST is
     * installed in so resources can be found easily
     *
     * @return the location of the top folder containing beast
     */
    public static String getSuperFolder() {
        if (!INITIALIZED || superFolder.length() == 0) {
            // the class is two "directories away from the super folder
            // counteract possible whitespace errors on windows
            File f = new File(SuperFolderFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
            superFolder = (new File(f.getParent()).getParent()).replaceAll("%20", " ");
        }
        return superFolder;
    }
}
