package edu.pse.beast.api.savingloading;

import java.io.File;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public interface RelativePathConverter {
    String getRelativePathTo(File f);
    File getFileFromRelativePath(String path);
}
