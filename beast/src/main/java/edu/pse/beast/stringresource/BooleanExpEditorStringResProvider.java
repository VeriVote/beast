package edu.pse.beast.stringresource;

/**
 * Class that Manages all StringResources for the BooleanExpEditor
 *
 * @author Niels Hanselmann
 */
public class BooleanExpEditorStringResProvider extends StringResourceProvider {

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader booleanExpErrorStringRes;
    private StringResourceLoader booleanExpEditorWindow;
    private StringResourceLoader booleanExpEditorSymbVarListRes;

    /**
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public BooleanExpEditorStringResProvider(String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     *
     * @return returns the MenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     *
     * @return returns the booleanExpEditorSymbVarListRes
     */
    public StringResourceLoader getBooleanExpEditorSymbVarListRes() {
        return booleanExpEditorSymbVarListRes;
    }

    /**
     *
     * @return returns the ToolbarStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     *
     * @return returns the BooleanExpErrorStringRes
     */
    public StringResourceLoader getBooleanExpErrorStringRes() {
        return booleanExpErrorStringRes;
    }

    /**
     *
     * @return the BooleanExpEditorWindowStringRes
     */
    public StringResourceLoader getBooleanExpEditorWindowStringRes() {
        return booleanExpEditorWindow;
    }

    /**
     * Initializes all attributes. Loads all StringResourceLoaders with the
     * filenames. The superclass provides the functions for this process
     */
    @Override
    protected final void initialize() {
        toolbarTipStringRes = this.getStringResourceLoaderFromModuleName("BooleanExpEditorToolbar");
        menuStringRes = this.getStringResourceLoaderFromModuleName("BooleanExpEditorMenu");
        booleanExpErrorStringRes = this.getStringResourceLoaderFromModuleName("BooleanExpEditorBooleanExpError");
        booleanExpEditorWindow = this.getStringResourceLoaderFromModuleName("BooleanExpEditorWindow");
        booleanExpEditorSymbVarListRes = this.getStringResourceLoaderFromModuleName("BooleanExpEditorSymbVarList");
    }

}
