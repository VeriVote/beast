/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import static edu.pse.beast.stringresource.StringResourceProvider.errorFileHasWrongFormat;
import edu.pse.beast.toolbox.FileLoader;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        {
            String location = getFileLocationString("Option");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                optionStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
