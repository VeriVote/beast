/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;



/**
 * Class that Manages all StringResources for the ParameterEditor
 *
 * @author Niels
 */
public class ParameterEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader otherStringRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public ParameterEditorStringResProvider(String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     *
     * @return MenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     *
     * @return ToolbarTipStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     *
     * @return OtherStringRes
     */
    public StringResourceLoader getOtherStringRes() {
        return otherStringRes;
    }

    /**
     * Initializes all attributes. Loads all StringResourceLoaders with the filenames.
     * The superclass provides the functions for this process
     */
    @Override
    protected final void initialize() {

        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("ParameterEditorToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("ParameterEditorMenu");
        otherStringRes = this.getStringResourceLoaderFromModuleName("ParameterEditorOther");

    }
}
