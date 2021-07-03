package edu.pse.beast.zzz.toolbox;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.List;

/**
 * The Class FileSaver.
 *
 * @author Lukas Stapelbroek
 */
public final class FileSaver {

    /**
     * Instantiates a new file saver.
     */
    private FileSaver() { }

    /**
     * Write string lines to file.
     *
     * @param text
     *            the text, in a list where each entry is a line that is to be
     *            saved
     * @param file
     *            the file where it should be saved. WARNING: it overwrites
     *            everything that stood in it before
     */
    public static void writeStringLinesToFile(final List<String> text,
                                              final File file) {
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        PrintWriter writer = null;
        try {
            writer = new PrintWriter(file);
        } catch (FileNotFoundException e) {
            ErrorLogger.log("File not found");
        }
        for (Iterator<String> iterator = text.iterator();
                iterator.hasNext();) {
            final String line = iterator.next();
            writer.println(line);
        }
        writer.close();
    }
}
