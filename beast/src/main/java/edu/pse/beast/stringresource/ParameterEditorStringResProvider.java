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
public class ParameterEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader otherStringRes;

    public ParameterEditorStringResProvider(String languageId, String relativePath) {
        super(languageId, relativePath);
        this.initialize();
    }

    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    public StringResourceLoader getOtherStringRes() {
        return otherStringRes;
    }

    @Override
    protected final void initialize() {
        File toolbarFile;
        toolbarFile = new File(getFileLocationString("ParameterEditorToolbar"));
        try {
            LinkedList<String> toolbarList;
            toolbarList = FileLoader.loadFileAsString(toolbarFile);
            toolbarTipStringRes = new StringResourceLoader(toolbarList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(toolbarFile);
        } catch (IOException e) {
            errorFileHasWrongFormat(toolbarFile);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(toolbarFile);
        }
        File menuFile;
        menuFile = new File(getFileLocationString("ParameterEditorMenu"));
        try {
            LinkedList<String> menuList;
            menuList = FileLoader.loadFileAsString(menuFile);
            menuStringRes = new StringResourceLoader(menuList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(menuFile);
        } catch (IOException e) {
            errorFileHasWrongFormat(menuFile);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(menuFile);
        }
        File otherFile;
        otherFile = new File(getFileLocationString("ParameterEditorOther"));
        try {
            LinkedList<String> otherList;
            otherList = FileLoader.loadFileAsString(otherFile);
            otherStringRes = new StringResourceLoader(otherList);
        } catch (FileNotFoundException e) {
            errorFileNotFound(otherFile);
        } catch (IOException e) {
            errorFileHasWrongFormat(otherFile);
        } catch (ArrayIndexOutOfBoundsException e) {
            errorFileHasWrongFormat(otherFile);
        }
    }
}
