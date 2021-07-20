package edu.pse.beast.api.savingloading;

import java.io.File;

public interface RelativePathConverter {
    String getRelativePathTo(File f);
    File getFileFromRelativePath(String path);
}
