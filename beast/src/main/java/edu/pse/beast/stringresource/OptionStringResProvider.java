/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import edu.pse.beast.toolbox.FileLoader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.LinkedList;

/**
 * Class that Manages all StringResources for the Options
 *
 * @author Niels
 */
public class OptionStringResProvider extends StringResourceProvider {

    private StringResourceLoader optionStringRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     * @param relativePath the location of the folder with the languagesorted
     * stringfiles
     */
    public OptionStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        this.initialize();
    }

    /**
     *
     * @return OptionStringRes
     */
    public StringResourceLoader getOptionStringRes() {
        return optionStringRes;
    }

    /**
     * Initializes all attributes Loads all StringResourceLoaders with the files
     * It uses the super classes methods errorFileHasWrongFormat,
     * errorFileNotFound and getFileLocationString
     */
    @Override
    protected final void initialize() {
        File optionFile;
        optionFile = new File(getFileLocationString("Option"));
        try {
            LinkedList<String> optionList;
            optionList = FileLoader.loadFileAsString(optionFile);
            optionStringRes = new StringResourceLoader(optionList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(optionFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(optionFile);
        }
    }
}
