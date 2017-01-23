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
 * Class that Manages all StringResources for the BooleanExpEditor
 *
 * @author Niels
 */
public class BooleanExpEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader booleanExpErrorStringRes;
    private StringResourceLoader booleanExpEditorWindow;
    private StringResourceLoader booleanExpEditorSymbVarListRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     * @param relativePath the location of the folder with the languagesorted
     * stringfiles
     */
    public BooleanExpEditorStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        this.initialize();
    }

    /**
     *
     * @return returns the MenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }
    /**
     * 
     * @return returns the booleanExpEditorSymbVarListRes
     */
    public StringResourceLoader getBooleanExpEditorSymbVarListRes() {
        return booleanExpEditorSymbVarListRes;
    }
    /**
     *
     * @return returns the ToolbarStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     *
     * @return returns the BooleanExpErrorStringRes
     */
    public StringResourceLoader getBooleanExpErrorStringRes() {
        return booleanExpErrorStringRes;
    }

    /**
     *
     * @return the BooleanExpEditorWindowStringRes
     */
    public StringResourceLoader getBooleanExpEditorWindowStringRes() {
        return booleanExpEditorWindow;
    }

    /**
     * Initializes all attributes Loads all StringResourceLoaders with the files
     * It uses the super classes methods errorFileHasWrongFormat,
     * errorFileNotFound and getFileLocationString
     */
    @Override
    protected final void initialize() {
        {
            String location = getFileLocationString("BooleanExpEditorToolbar");
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
            String location = getFileLocationString("BooleanExpEditorMenu");
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
            String location = getFileLocationString("BooleanExpEditorBooleanExpError");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                booleanExpErrorStringRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        {
            String location = getFileLocationString("BooleanExpEditorWindow");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                booleanExpEditorWindow = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        {
            String location = getFileLocationString("BooleanExpEditorSymbVarList");
            InputStream in = getClass().getClassLoader().getResourceAsStream(location);
            try {
                LinkedList<String> inputList;
                inputList = FileLoader.loadFileAsString(in);
                booleanExpEditorSymbVarListRes = new StringResourceLoader(inputList);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

}
