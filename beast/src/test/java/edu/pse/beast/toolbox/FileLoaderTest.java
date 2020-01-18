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
 *
 * @author Niels Hanselmann
 */
public class FileLoaderTest {

    /**
     * file LoaderTest
     */
    public FileLoaderTest() { }

    private boolean bufferedImagesEqual(final BufferedImage img1, final BufferedImage img2) {
        if (img1 == null) {
            return img2 == null;
        } else if (img1.getWidth() == img2.getWidth() && img1.getHeight() == img2.getHeight()) {
            for (int x = 0; x < img1.getWidth(); x++) {
                for (int y = 0; y < img1.getHeight(); y++) {
                    if (img1.getRGB(x, y) != img2.getRGB(x, y)) {
                        return false;
                    }
                }
            }
        } else {
            return false;
        }
        return true;
    }

    /**
     * Test of loadFileAsString method, of class FileLoader.
     *
     * @throws java.lang.Exception throws an exception if the file was not found
     */
    @Test
    public void testLoadFileAsString() throws Exception {
        System.out.println("loadFileAsString");
        String subFolderAndFilename = "/src/test/testfiles/fileLoaderFileAsStringTest.txt";
        String superFolder = SuperFolderFinder.getSuperFolder();
        String location = superFolder + subFolderAndFilename;
        File file = new File(location);
        LinkedList<String> expResult = new LinkedList<>();
        expResult.add("erste Zeile");
        expResult.add("zweite Zeile");
        expResult.add("ende");
        LinkedList<String> result = FileLoader.loadFileAsString(file);
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
        File toRead = new File(SuperFolderFinder.getSuperFolder()
                                + "/src/test/testfiles/eye.png");
        BufferedImage expResult = null;
        BufferedImage result = FileLoader.loadFileAsImage(toRead);
        assertNotNull(result);
        try {
            expResult = ImageIO.read(toRead);
        } catch (IOException e) {
            System.out.println("the Testfile eye.png could not be found");
        }
        assertTrue(bufferedImagesEqual(result, expResult));
    }

    /**
     * Test of getNewUniqueName method, of class FileLoader.
     */
    @Test
    public void testGetNewUniqueName() {
        System.out.println("getNewUniqueName");
        String pathToDir = "/src/test/testfiles/";
        ArrayList<String> usedNames = new ArrayList<>();
        File folder = new File(pathToDir);
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    usedNames.add(file.getName());
                }
            }
        }
        String result = FileLoader.getNewUniqueName(pathToDir);
        assertNotNull(result);
        usedNames.forEach((filename) -> {
            assertNotEquals(filename, result);
        });
    }
}
