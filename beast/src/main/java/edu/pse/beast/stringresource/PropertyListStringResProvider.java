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
public class PropertyListStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;

    public PropertyListStringResProvider(String languageId, String relativePath) {
        this.containingFolder = languageId;
        this.relativePath = relativePath;
        File toolbarFile;
        toolbarFile = new File(relativePath + languageId + "/PropertyListToolbar_" + languageId + ".txt");
        try {
            LinkedList<String> toolbarList;
            toolbarList = FileLoader.loadFileAsString(toolbarFile);
            toolbarTipStringRes = new StringResourceLoader(toolbarList);
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {
            
        }
        File menuFile;
        menuFile = new File(relativePath + languageId + "/PropertyListMenu_" + languageId + ".txt");
        try {
            LinkedList<String> menuList;
            menuList = FileLoader.loadFileAsString(menuFile);
            menuStringRes = new StringResourceLoader(menuList);
        } catch (FileNotFoundException e) {
            
        } catch (IOException e) {

        }

    }

    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

}
