package edu.pse.beast.stringresource;

import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;

/**
 *
 * @author Niels
 */
public abstract class StringResourceProvider {

    /**
     * languageId. Choose "de" for german
     */
    protected String languageId;

    /**
     * The constructor doesn't call initialize. If you make a subclass you have
     * to call it yourself
     *
     * @param languageId the language. Choose "de" for german
     */
    public StringResourceProvider(String languageId) {
        this.languageId = languageId;
    }

    /**
     *
     * @param languageId the language. Choose "de" for german
     */
    public void changeLanguage(String languageId) {
        this.languageId = languageId;
        this.initialize();
    }

    /**
     * this has to be implemented by every subclass.
     */
    protected abstract void initialize();

    /**
     *
     * @param moduleName the Name of the StringResource you want
     * @return the relative fileLocation
     */
    private String getFileLocationString(String moduleName) {
        return ("/core/stringfiles/" + languageId + "/" + moduleName + "_" + languageId + ".txt");
    }

    /**
     * reports Error to the class file toolbox
     *
     * @param file that has the wrongFormat
     */
    private void errorFileHasWrongFormat(File file) {
        ErrorLogger.log("The file " + file.getName() + " is not correctly formated");
        ErrorLogger.log("You can find and correct the file in this directory " + file.getAbsolutePath());
    }

    private void fileNotFound(File file) {

        ErrorLogger.log("The file " + file.getName() + " can not be found");
        ErrorLogger.log("The file should be in this directory " + file.getAbsolutePath());
    }

    /**
     * This method is used to initialize the StringResourceLoaders of the
     * subclasses
     *
     * @param moduleName the Name of the txt File without the language or the
     * path
     * @return returns the StringResourceLoader
     */
    protected final StringResourceLoader getStringResourceLoaderFromModuleName(String moduleName) {
        String subFolderAndFilename = getFileLocationString(moduleName);
        String superFolder = SuperFolderFinder.getSuperFolder();
        String location = superFolder + subFolderAndFilename;
        File file = new File(location);
        {
            LinkedList<String> inputList;
            try {
                inputList = FileLoader.loadFileAsString(file);
                return new StringResourceLoader(inputList);
            } catch (IOException ex) {
                fileNotFound(file);
            } catch (IndexOutOfBoundsException ie) {
                errorFileHasWrongFormat(file);
            }

        }
        return null;
    }

}
