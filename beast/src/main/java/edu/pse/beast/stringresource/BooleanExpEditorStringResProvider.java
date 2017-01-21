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
public class BooleanExpEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader booleanExpErrorStringRes;
    private StringResourceLoader booleanExpEditorWindow;

    public BooleanExpEditorStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        this.initialize();
    }

    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    public StringResourceLoader getBooleanExpErrorStringRes() {
        return booleanExpErrorStringRes;
    }
    public StringResourceLoader getBooleanExpEditorWindowStringRes() {
        return booleanExpEditorWindow;
    }

    /**
     *
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
    }

}
