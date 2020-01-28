package edu.pse.beast.toolbox;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedList;

import javax.imageio.ImageIO;

import org.junit.Test;

/**
 * The tests for FileLoader.
 *
 * @author Niels Hanselmann
 */
public class FileLoaderTest {
    /** The Constant SUB_FOLDER_AND_FILENAME. */
    private static final String SUB_FOLDER_AND_FILENAME =
            "/src/test/testfiles/fileLoaderFileAsStringTest.txt";
    /** The Constant TEST_STRING_ONE. */
    private static final String TEST_STRING_ONE = "erste Zeile";
    /** The Constant TEST_STRING_TWO. */
    private static final String TEST_STRING_TWO = "zweite Zeile";
    /** The Constant TEST_STRING_THREE. */
    private static final String TEST_STRING_THREE = "ende";
    /** The Constant TEST_FILE_IMAGE. */
    private static final String TEST_FILE_IMAGE = "/src/test/testfiles/eye.png";
    /** The Constant TEST_PATH. */
    private static final String TEST_PATH = "/src/test/testfiles/";

    /**
     * File LoaderTest.
     */
    public FileLoaderTest() { }

    private boolean bufferedImagesEqual(final BufferedImage img1,
                                        final BufferedImage img2) {
        boolean result = true;
        if (img1 == null) {
            result = img2 == null;
        } else if (img1.getWidth() == img2.getWidth()
                && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        result = false;
                    }
                }
            }
        } else {
            result = false;
        }
        return result;
    }

    /**
     * Test of loadFileAsString method, of class FileLoader.
     *
     * @throws java.lang.Exception throws an exception if the file was not found
     */
    @Test
    public void testLoadFileAsString() throws Exception {
        System.out.println("loadFileAsString");
        final String superFolder = SuperFolderFinder.getSuperFolder();
        final String location = superFolder + SUB_FOLDER_AND_FILENAME;
        final File file = new File(location);
        final LinkedList<String> expResult = new LinkedList<String>();
        expResult.add(TEST_STRING_ONE);
        expResult.add(TEST_STRING_TWO);
        expResult.add(TEST_STRING_THREE);
        final LinkedList<String> result = FileLoader.loadFileAsString(file);
        assertEquals(expResult.size(), result.size());
        for (int i = 0; i < result.size(); i++) {
            assertEquals(expResult.get(i), result.get(i));
        }
    }

    /**
     * Test of loadFileAsImage method, of class FileLoader.
     */
    @Test
    public void testLoadFileAsImage() {
        System.out.println("loadFileAsImage");
        final File toRead =
                new File(SuperFolderFinder.getSuperFolder() + TEST_FILE_IMAGE);
        BufferedImage expResult = null;
        final BufferedImage result = FileLoader.loadFileAsImage(toRead);
        assertNotNull(result);
        try {
            expResult = ImageIO.read(toRead);
        } catch (IOException e) {
            System.out.println("The Testfile eye.png could not be found.");
        }
        assertTrue(bufferedImagesEqual(result, expResult));
    }

    /**
     * Test of getNewUniqueName method, of class FileLoader.
     */
    @Test
    public void testGetNewUniqueName() {
        System.out.println("getNewUniqueName");
        final ArrayList<String> usedNames = new ArrayList<String>();
        final File folder = new File(TEST_PATH);
        final File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    usedNames.add(file.getName());
                }
            }
        }
        final String result = FileLoader.getNewUniqueName(TEST_PATH);
        assertNotNull(result);
        usedNames.forEach(filename -> {
            assertNotEquals(filename, result);
        });
    }
}
