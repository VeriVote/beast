package edu.pse.beast.toolbox;

import java.io.File;

/**
 *
 * @author Lukas Stapelbroek
 *
 */
public final class SuperFolderFinder {
    private static boolean initialized = false;
    private static String superFolder = "";

    private SuperFolderFinder() { }

    /**
     * this class is used so other classes can get the folder that BEAST is
     * installed in so resources can be found easily
     *
     * @return the location of the top folder containing beast
     */
    public static String getSuperFolder() {
        if (!initialized || superFolder.length() == 0) {
            // the class is two "directories away from the super folder
            // counteract possible whitespace errors on windows
            final String pathName
            // TODO: Why are we not using 'System.getProperty("user.dir")'?
                  = SuperFolderFinder.class.getProtectionDomain()
                        .getCodeSource().getLocation().getPath();
            File f = new File(pathName);
            superFolder
                  = (new File(f.getParent()).getParent())
                    .replaceAll("%20", " ");
        }
        return superFolder;
    }
}
