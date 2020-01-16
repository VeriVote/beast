package edu.pse.beast.stringresource;

/**
 * Class that manages all StringResources for the CElectionEditor.
 *
 * @author Niels Hanselmann
 */
public class CElectionEditorStringResProvider extends StringResourceProvider {
    /** The menu string res. */
    private StringResourceLoader menuStringRes;

    /** The toolbar tip string res. */
    private StringResourceLoader toolbarTipStringRes;

    /** The c error string res. */
    private StringResourceLoader cErrorStringRes;

    /** The election string res. */
    private StringResourceLoader electionStringRes;

    /**
     * Instantiates a new c election editor string res provider.
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public CElectionEditorStringResProvider(final String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     * Gets the menu string res.
     *
     * @return theCEllectionEditorMenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     * Gets the toolbar tip string res.
     *
     * @return the CEllectionToolbarTipStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     * Gets the c error string res.
     *
     * @return the CErrorStringRes
     */
    public StringResourceLoader getCErrorStringRes() {
        return cErrorStringRes;
    }

    // Initializes all attributes. Loads all StringResourceLoaders with the
    // filenames. The superclass provides the functions for this process
    @Override
    protected final void initialize() {
        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorMenu");
        cErrorStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorCError");
        electionStringRes = this.getStringResourceLoaderFromModuleName("CElectionEditorElection");
    }

    /**
     * Gets the election string res.
     *
     * @return the Election String res
     */
    public StringResourceLoader getElectionStringRes() {
        return electionStringRes;
    }
}
