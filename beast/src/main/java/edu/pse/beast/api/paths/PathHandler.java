package edu.pse.beast.api.paths;

import java.io.File;

import edu.pse.beast.api.savingloading.RelativePathConverter;

public class PathHandler implements RelativePathConverter {
    private static final String REL_PATH_TO_SAVE_FILES = "./saveFiles/";

    private static final String REL_PATH_TO_WORKSPACE_SAVE_FILES = "./workspaces/";
    private static final String REL_PATH_TO_DESCR_SAVE_FILES = "./electionDescriptions/";
    private static final String REL_PATH_TO_PROP_DESCR_SAVE_FILES = "./propertyDescriptions/";

    private static final String REL_PATH_TO_OPTIONS_FILE = "./.beastoptions";

    private String baseDir;

    public PathHandler() {
        tryload();
    }

    public final File getBaseDir() {
        return new File(baseDir);
    }

    public final File getWorkspaceDir() {
        return new File(baseDir + REL_PATH_TO_SAVE_FILES + REL_PATH_TO_WORKSPACE_SAVE_FILES);
    }

    public final File getElectionDescrDir() {
        return new File(baseDir + REL_PATH_TO_SAVE_FILES + REL_PATH_TO_DESCR_SAVE_FILES);
    }

    public final File getPropDescrDir() {
        return new File(baseDir + REL_PATH_TO_SAVE_FILES + REL_PATH_TO_PROP_DESCR_SAVE_FILES);
    }

    public final File getOptionsFile() {
        return new File(baseDir + REL_PATH_TO_OPTIONS_FILE);
    }

    private void tryload() {
        baseDir = System.getProperty("user.dir") + "/";
        if (!getWorkspaceDir().exists()) {
            getWorkspaceDir().mkdirs();
        }
        if (!getElectionDescrDir().exists()) {
            getElectionDescrDir().mkdirs();
        }
        if (!getPropDescrDir().exists()) {
            getPropDescrDir().mkdirs();
        }
    }

    @Override
    public final String getRelativePathTo(final File f) {
        return new File(baseDir).toURI().relativize(f.toURI()).getPath();
    }

    @Override
    public final File getFileFromRelativePath(final String relativePath) {
        return new File(baseDir + relativePath);
    }
}
