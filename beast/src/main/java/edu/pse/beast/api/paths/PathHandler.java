package edu.pse.beast.api.paths;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.NotImplementedException;

import edu.pse.beast.api.codegen.loopbounds.LoopBound;
import edu.pse.beast.api.savingloading.RelativePathConverter;

/**
 * TODO: Write documentation.
 *
 * @author Holger Klein
 *
 */
public class PathHandler implements RelativePathConverter {
    private static final String NONE = "";
    private static final String DOT = ".";
    private static final String SLASH = "/";

    private static final String USER_DIR = "user.dir";
    private static final String TEMPLATE_ENDING = ".template";
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
        baseDir = System.getProperty(USER_DIR) + SLASH;
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

    private static String getDirectory(final Class<?> c) {
        final String dir = File.separator + c.getCanonicalName().replace(DOT, File.separator);
        final String resource = dir.substring(0, dir.lastIndexOf(c.getSimpleName()));
        return resource;
    }

    private static <K> String getKeyString(final K key) {
        final String keyString;
        if (key != null && key instanceof Enum) {
            final Enum<?> enumKey = (Enum<?>) key;
            keyString = enumKey.name();
        } else {
            keyString = key != null ? key.toString() : NONE;
        }
        return keyString;
    }

    public static final <K> List<LoopBound> getLoopBounds(final K key,
                                                          final Map<K, List<LoopBound>> templates) {
        if (templates.isEmpty() || !templates.containsKey(key)) {
            // throw new NotImplementedException();
            return Arrays.asList();
        }
        return templates.get(key);
    }

    public static final <K> String getTemplate(final K key,
                                               final Map<K, String> templates,
                                               final String prefix,
                                               final Class<?> c) {
        if (templates.isEmpty() || !templates.containsKey(key)) {
            final InputStream stream =
                    c.getResourceAsStream(getDirectory(c) + prefix
                                            + getKeyString(key).toLowerCase()
                                            + TEMPLATE_ENDING);
            if (stream == null) {
                throw new NotImplementedException();
            }
            final StringWriter writer = new StringWriter();
            try {
                IOUtils.copy(stream, writer, StandardCharsets.UTF_8);
            } catch (final IOException e) {
                e.printStackTrace();
            }
            templates.put(key, writer.toString());
        }
        return templates.get(key);
    }
}
