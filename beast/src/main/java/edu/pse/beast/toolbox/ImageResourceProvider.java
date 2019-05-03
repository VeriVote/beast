package edu.pse.beast.toolbox;

import java.awt.Image;
import java.io.File;
import java.util.HashMap;

/**
 *
 * @author Holger Klein
 */
public class ImageResourceProvider {
    private static final String PNG_FILE_ENDING = ".png";

    private static final String[] IDS
        = {
            "new", "load", "save", "save_as",
            "undo", "redo", "cut", "copy", "paste",
            "start", "stop",
            "showHelpToUser", "simulation"
        };
    private static final String RESOURCE = "/core/images/toolbar/";

    private static ImageResourceProvider toolbar;

    private final HashMap<String, Image> images = new HashMap<>();
    private final String folder;
    private final String[] ids;

    public ImageResourceProvider(String folder, String[] ids) {
        this.folder = folder;
        this.ids = ids;
        for (String s : this.ids) {
            File toRead
                  = new File(
                            SuperFolderFinder.getSuperFolder()
                            + this.folder + s + PNG_FILE_ENDING
                            );
            Image img = null;
            img = FileLoader.loadFileAsImage(toRead);
            images.put(s, img);
        }
    }

    public static ImageResourceProvider getToolbarImages() {
        if (toolbar == null) {
            String[] ids = IDS;
            toolbar = new ImageResourceProvider(RESOURCE, ids);
        }
        return toolbar;
    }

    public Image getImageById(String id) {
        return images.get(id);
    }
}