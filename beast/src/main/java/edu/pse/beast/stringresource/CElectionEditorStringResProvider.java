package edu.pse.beast.stringresource;

/**
 * Class that manages all StringResources for the CElectionEditor.
 *
 * @author Niels Hanselmann
 */
public class CElectionEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader cErrorStringRes;
    private StringResourceLoader electionStringRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public CElectionEditorStringResProvider(final String languageId) {
        super(languageId);
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
     * Initializes all attributes. Loads all StringResourceLoaders with the
     * filenames. The superclass provides the functions for this process
     */
    @Override
    protected final void initialize() {
        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorMenu");
        cErrorStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorCError");
        electionStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorElection");
    }

    /**
     *
     * @return the Election String res
     */
    public StringResourceLoader getElectionStringRes() {
        return electionStringRes;
    }
}
