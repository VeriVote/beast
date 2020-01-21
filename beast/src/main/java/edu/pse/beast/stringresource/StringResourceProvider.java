package edu.pse.beast.stringresource;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 * The Class StringResourceProvider.
 *
 * @author Niels Hanselmann
 */
public abstract class StringResourceProvider {
    /**
     * LanguageId. Choose "de" for German.
     */
    private String languageId;

    /**
     * The constructor does not call initialize. If you make a subclass you have
     * to call it yourself.
     *
     * @param langId
     *            the language. Choose "de" for German
     */
    public StringResourceProvider(final String langId) {
        this.languageId = langId;
    }

    /**
     * Change language.
     *
     * @param langId
     *            the language. Choose "de" for German
     */
    public void changeLanguage(final String langId) {
        this.languageId = langId;
        this.initialize();
    }

    /**
     * This has to be implemented by every subclass.
     */
    protected abstract void initialize();

    /**
     * "The file FILENAME" for error logs.
     *
     * @param file
     *            the file
     * @return the string
     */
    private static String theFile(final File file) {
        return "The file " + file.getName();
    }

    /**
     * Gets the file location string.
     *
     * @param moduleName
     *            the Name of the StringResource you want
     * @return the relative fileLocation
     */
    private String getFileLocationString(final String moduleName) {
        return "/core/stringfiles/" + languageId + "/" + moduleName + "_"
                + languageId + ".txt";
    }

    /**
     * Reports Error to the class file tool-box.
     *
     * @param file
     *            that has the wrongFormat
     */
    private void errorFileHasWrongFormat(final File file) {
        ErrorLogger.log(theFile(file) + " is not correctly formated");
        ErrorLogger.log("You can find and correct the file in this directory "
                        + file.getAbsolutePath());
    }

    /**
     * File not found.
     *
     * @param file
     *            the file
     */
    private void fileNotFound(final File file) {
        ErrorLogger.log(theFile(file) + " cannot be found");
        ErrorLogger.log("The file should be in this directory "
                        + file.getAbsolutePath());
    }

    /**
     * This method is used to initialize the StringResourceLoaders of the
     * subclasses.
     *
     * @param moduleName
     *            the Name of the txt File without the language or the path
     * @return returns the StringResourceLoader
     */
    protected final StringResourceLoader
                getStringResourceLoaderFromModuleName(final String moduleName) {
        final String subFolderAndFilename = getFileLocationString(moduleName);
        final String superFolder = SuperFolderFinder.getSuperFolder();
        final String location = superFolder + subFolderAndFilename;
        final File file = new File(location);

        //////////////////////////////////
        final LinkedList<String> inputList;
        try {
            inputList = FileLoader.loadFileAsString(file);
            return new StringResourceLoader(inputList);
        } catch (IOException ex) {
            fileNotFound(file);
        } catch (IndexOutOfBoundsException ie) {
            errorFileHasWrongFormat(file);
        }
        //////////////////////////////////

        return null;
    }
}
