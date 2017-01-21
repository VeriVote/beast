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
        File toolbarFile;
        toolbarFile = new File(getFileLocationString("BooleanExpEditorToolbar"));
        try {
            LinkedList<String> toolbarList;
            toolbarList = FileLoader.loadFileAsString(toolbarFile);
            toolbarTipStringRes = new StringResourceLoader(toolbarList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(toolbarFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(toolbarFile);
        }
        File menuFile;
        menuFile = new File(getFileLocationString("BooleanExpEditorMenu"));
        try {
            LinkedList<String> menuList;
            menuList = FileLoader.loadFileAsString(menuFile);
            menuStringRes = new StringResourceLoader(menuList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(menuFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(menuFile);
        }
        File expErrorFile;
        expErrorFile = new File(getFileLocationString("BooleanExpEditorBooleanExpError"));
        try {
            LinkedList<String> booleanExpErrorList;
            booleanExpErrorList = FileLoader.loadFileAsString(expErrorFile);
            booleanExpErrorStringRes = new StringResourceLoader(booleanExpErrorList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(expErrorFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(expErrorFile);
        }
        File windowFile;
        windowFile = new File(getFileLocationString("BooleanExpEditorWindow"));
        try {
            LinkedList<String> windowList;
            windowList = FileLoader.loadFileAsString(windowFile);
            booleanExpEditorWindow = new StringResourceLoader(windowList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(windowFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(windowFile);
        }
        File symbVarFile;
        symbVarFile = new File(getFileLocationString("BooleanExpEditorSymbVarList"));
        try {
            LinkedList<String> symbVarList;
            symbVarList = FileLoader.loadFileAsString(symbVarFile);
            booleanExpEditorSymbVarListRes = new StringResourceLoader(symbVarList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(symbVarFile);
        } catch (IOException | ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(symbVarFile);
        }
    }

}
