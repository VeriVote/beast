/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

/**
 *
 * @author Niels
 */
public abstract class StringResourceProvider {

    protected String containingFolder;
    protected String relativePath;
    protected String languageId;

    public StringResourceProvider(String languageId, String relativePath) {
        this.containingFolder = languageId;
        this.relativePath = relativePath;
        this.initialize();
    }

    public void changeLanguage(String languageId){
        this.languageId = languageId;
        this.initialize();
    }

    protected abstract void initialize();
    
    protected final String getFileLocationString(String moduleName) {
        return (relativePath + languageId + "/" + moduleName + "_" + languageId + ".txt");
    }

}
