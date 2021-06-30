package edu.pse.beast.api.savingloading;

import java.io.File;

public interface RelativePathConverter {
	public String getRelativePathTo(File f);

	public File getFileFromRelativePath(String path);
}
