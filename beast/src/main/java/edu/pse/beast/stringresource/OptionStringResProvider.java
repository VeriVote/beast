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
 *
 * @author Niels
 */
public class OptionStringResProvider extends StringResourceProvider {

    private StringResourceLoader optionStringRes;

    public OptionStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        File optionFile;
        optionFile = new File(getFileLocationString("Option"));
        try {
            LinkedList<String> optionList;
            optionList = FileLoader.loadFileAsString(optionFile);
            optionStringRes = new StringResourceLoader(optionList);
        } catch (FileNotFoundException e) {

        } catch (IOException e) {

        }

    }

    public StringResourceLoader getOptionStringRes() {
        return optionStringRes;
    }

}
