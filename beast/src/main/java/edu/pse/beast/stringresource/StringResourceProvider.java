/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

import java.io.File;

/**
 *
 * @author Niels
 */
public abstract class StringResourceProvider {

    protected String relativePath;
    protected String languageId;

    public StringResourceProvider(String languageId, String relativePath) {
        this.languageId = languageId;
        this.relativePath = relativePath;
    }

    public void changeLanguage(String languageId) {
        this.languageId = languageId;
        this.initialize();
    }

    protected abstract void initialize();

    protected final String getFileLocationString(String moduleName) {
        return (relativePath + languageId + "/" + moduleName + "_" + languageId + ".txt");
    }

    protected static void errorFileNotFound(File file) {
        System.err.println("The file " + file.getName() + " could not be found");
        System.err.println("It is supposed to be in this directory: " + file.getAbsolutePath());
    }

    protected static void errorFileHasWrongFormat(File file) {
        System.err.println("The file " + file.getName() + " is not correclty formated");
        System.err.println("You can find and correct the file in this directory " + file.getAbsolutePath());
    }

}
