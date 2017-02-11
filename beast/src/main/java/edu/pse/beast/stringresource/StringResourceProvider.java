/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import java.io.File;
import edu.pse.beast.toolbox.ErrorLogger;
import edu.pse.beast.toolbox.FileLoader;
import edu.pse.beast.toolbox.SuperFolderFinder;
import java.io.IOException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Niels
 */
public abstract class StringResourceProvider {

    /**
     * the path containing all languagefolders with the textfiles
     */
    protected String relativePath;
    /**
     * languageId. Choose "de" for german
     */
    protected String languageId;

    /**
     * The constructor doesn't call initialize. If you make a subclass you have
     * to call it yourself
     *
     * @param languageId the language. Choose "de" for german
     * @param relativePath the path containing all languagefolders with the
     * textfiles
     */
    public StringResourceProvider(String languageId, String relativePath) {
        this.languageId = languageId;
        this.relativePath = relativePath;
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
    protected final String getFileLocationString(String moduleName) {
        return ("/core/stringfiles/" + languageId + "/" + moduleName + "_" + languageId + ".txt");
    }

    /**
     * reports Error to the class in toolbox
     *
     * @param file that has the wrongFormat
     */
    protected final void errorFileHasWrongFormat(File file) {
        ErrorLogger.log("The file " + file.getName() + " is not correclty formated");
        ErrorLogger.log("You can find and correct the file in this directory " + file.getAbsolutePath());
    }

    protected final StringResourceLoader getStringResourceLoaderFromModuleName(String moduleName) {

        String subFolderAndFilename = getFileLocationString(moduleName);
        String superFolder = SuperFolderFinder.getSuperFolder();
        String location = superFolder + subFolderAndFilename;

        String content = null;
        File in = new File(location); //for ex foo.txt
        {
            LinkedList<String> inputList;
            try {
                inputList = FileLoader.loadFileAsString(in);
                return new StringResourceLoader(inputList);
            } catch (IOException ex) {
                Logger.getLogger(PropertyListStringResProvider.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        return null;
    }

}
