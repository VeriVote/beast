/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

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
        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorMenu");
        cErrorStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorCError");
        electionStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorElection");
    }

    public StringResourceLoader getElectionStringRes() {
        return electionStringRes;
    }
}
