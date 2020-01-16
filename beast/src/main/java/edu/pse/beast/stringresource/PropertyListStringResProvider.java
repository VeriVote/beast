package edu.pse.beast.stringresource;

/**
 * Class that manages all StringResources for the PropertyList.
 *
 * @author Niels Hanselmann
 */
public class PropertyListStringResProvider extends StringResourceProvider {
    /** The menu string res. */
    private StringResourceLoader menuStringRes;

    /** The toolbar tip string res. */
    private StringResourceLoader toolbarTipStringRes;

    /** The other string res. */
    private StringResourceLoader otherStringRes;

    /**
     * Instantiates a new property list string res provider.
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public PropertyListStringResProvider(final String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     * Gets the menu string res.
     *
     * @return MenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     * Gets the toolbar tip string res.
     *
     * @return ToolbarTipStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     * Gets the other string res.
     *
     * @return otherStringRes
     */
    public StringResourceLoader getOtherStringRes() {
        return otherStringRes;
    }

    // Initializes all attributes. Loads all StringResourceLoaders with the
    // filenames. The superclass provides the functions for this process.
    @Override
    protected final void initialize() {
        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("PropertyListToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("PropertyListMenu");
        otherStringRes = this.getStringResourceLoaderFromModuleName("PropertyListOther");
    }
}
