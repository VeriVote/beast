package edu.pse.beast.toolbox;

import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * The Class FileLoader.
 *
 * @author Niels Hanselmann
 */
public final class FileLoader {
    // We want to compile all available c files, so the user does not need to
    // specify anything.

    /** The Constant C_FILE_ENDING. */
    public static final String C_FILE_ENDING = ".c";

    /** The Constant BAT_FILE_ENDING. */
    public static final String BAT_FILE_ENDING = ".bat";

    /** The Constant EXE_FILE_ENDING. */
    public static final String EXE_FILE_ENDING = ".exe";

    /** The Constant OBJECT_FILE_ENDING. */
    public static final String OBJECT_FILE_ENDING = ".obj";

    /** The Constant OUT_FILE_ENDING. */
    public static final String OUT_FILE_ENDING = ".out";

    /** The "\"" string. */
    public static final String QUOTE = "\"";

    /** The Constant HUNDRED. */
    private static final int HUNDRED = 100;

    /** The Constant INT_LENGTH. */
    private static final int INT_LENGTH = 32;

    /**
     * Instantiates a new file loader.
     */
    private FileLoader() { }

    /**
     * Load file as string.
     *
     * @param file
     *            the file that will be read
     * @return A LinkedList of String elements which are in the same order as in
     *         the file
     * @throws FileNotFoundException
     *             if the file is not found it throws an exception
     * @throws IOException
     *             throws Exception
     */
    public static LinkedList<String> loadFileAsString(final File file)
            throws FileNotFoundException, IOException {
        LinkedList<String> stringlist;
        InputStream inputStream = new FileInputStream(file);
        BufferedReader br =
                new BufferedReader(
                        new InputStreamReader(inputStream, "UTF-8")
                );
        stringlist = new LinkedList<String>();
        String line;
        line = br.readLine();
        while (line != null) {
            stringlist.add(line);
            line = br.readLine();
        }
        br.close();
        return stringlist;
    }

    /**
     * Load file as image.
     *
     * @param toRead
     *            the File you want to read
     * @return the image, if it was possible to read it. In case it could not be
     *         read, the method returns null
     */
    public static BufferedImage loadFileAsImage(final File toRead) {
        BufferedImage toReturn = null;
        try {
            toReturn = ImageIO.read(toRead);
        } catch (IOException e) {
            ErrorLogger.log("The specified file: " + toRead.getAbsolutePath()
                            + " could not be loaded");
        }
        return toReturn;
    }

    /**
     * Creates a new name inside a directory.
     *
     * @param pathToDir
     *            the path of the directory you want the new unique String to be
     *            created in
     * @return the unique String
     */
    public static synchronized String getNewUniqueName(final String pathToDir) {
        ArrayList<String> usedNames = new ArrayList<String>();
        File folder = new File(pathToDir.replace(QUOTE, ""));
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile()) {
                    usedNames.add(file.getName());
                }
            }
        }
        String newName = getRandomName(HUNDRED);
        while (usedNames.contains(newName)) {
            newName = getRandomName(HUNDRED);
        }
        return newName;
    }

    /**
     * Gets the random name.
     *
     * @param wordSize
     *            the word size
     * @return the random name
     */
    private static String getRandomName(final int wordSize) {
        SecureRandom random = new SecureRandom();
        return new java.math.BigInteger(wordSize, random).toString(INT_LENGTH);
    }

    /**
     * Returns all files that end with the specified String that are in this
     * folder.
     *
     * @param pathToDir
     *            the path to the folder
     * @param endsWith
     *            the String
     * @return the described files
     */
    public static List<String> listAllFilesFromFolder(final String pathToDir,
                                                      final String endsWith) {
        ArrayList<String> foundFiles = new ArrayList<String>();
        File folder = new File(pathToDir.replace(QUOTE, ""));
        File[] listOfFiles = folder.listFiles();
        if (listOfFiles != null) {
            for (File file : listOfFiles) {
                if (file.isFile() && file.getName().endsWith(endsWith)) {
                    // surround it with quotes in case there are spaces in there
                    foundFiles.add(QUOTE + file.getAbsolutePath() + QUOTE);
                }
            }
        }
        return foundFiles;
    }
}
