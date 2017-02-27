package edu.pse.beast.toolbox;

import java.io.File;

/**
 * 
 * @author Lukas
 *
 */
public final class SuperFolderFinder {
    
    /**
     * this class is used so other classes can get the folder that BEAST is installed in 
     * so resources can be found easily
     * @return the location of the top folder containing beast
     */
    public static String getSuperFolder() {
        File f = new File(SuperFolderFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //the class is two "directories away from the super folder
        //counterakt possible whitespace errors on windows
        return (new File(f.getParent()).getParent()).replaceAll("%20", " ");
    }
}
