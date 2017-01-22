package edu.pse.beast.saverloader;

import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import javax.imageio.ImageIO;

/**
 * 
 * @author Justin
 *
 */
public final class FileLoader {

    /**
     * 
     * @param file the file to load
     * @return a String that holds the content of the file
     * @throws IOException in case the reading fails
     */
    public static String loadFileAsString(File file) throws IOException {
        if (!file.canRead()) {
            return null;
        }
            
        return new String(Files.readAllBytes(file.toPath()));
    }

    /**
     * 
     * @param file the file to be read
     * @return the image that was read, or null, if the image doesn't exist
     * @throws IOException in case the reading fails 
     */
    public static Image loadFileAsImage(File file) throws IOException {
        if (!file.canRead()) {
            return null;
        }

        return ImageIO.read(file);
    }
}
