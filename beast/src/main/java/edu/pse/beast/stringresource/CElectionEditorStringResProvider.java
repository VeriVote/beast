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
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Class that Manages all StringResources for the CElectionEditor
 *
 * @author Niels
 */
public class CElectionEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader cErrorStringRes;
    private StringResourceLoader electionStringRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     * @param relativePath the location of the folder with the languagesorted
     * stringfiles
     */
    public CElectionEditorStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        this.initialize();
    }

    /**
     *
     * @return theCEllectionEditorMenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     *
     * @return the CEllectionToolbarTipStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     *
     * @return the CErrorStringRes
     */
    public StringResourceLoader getCErrorStringRes() {
        return cErrorStringRes;
    }

    /**
     * Initializes all attributes Loads all StringResourceLoaders with the files
     * It uses the super classes methods errorFileHasWrongFormat,
     * errorFileNotFound and getFileLocationString
     */
    @Override
    protected final void initialize() {
        {
            String location = getFileLocationString("CElectionEditorToolbar");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                toolbarTipStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        {
            String location = getFileLocationString("CElectionEditorMenu");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                menuStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
        {
            String location = getFileLocationString("CElectionEditorCError");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                cErrorStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        
         {
            String location = getFileLocationString("CElectionEditorElection");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                electionStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public StringResourceLoader getElectionStringRes() {
        return electionStringRes;
    }
}
