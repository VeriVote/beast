package edu.pse.beast.toolbox;

import java.io.File;

public final class SuperFolderFinder {
    
    public static String getSuperFolder() {
        File f = new File(SuperFolderFinder.class.getProtectionDomain().getCodeSource().getLocation().getPath());
        //the class is two "directories away from the super folder
        //counterakt possible whitespace errors on windows
        return (new File(f.getParent()).getParent()).replaceAll("%20", " ");
    }
}
