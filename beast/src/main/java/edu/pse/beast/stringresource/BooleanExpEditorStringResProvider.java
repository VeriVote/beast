package edu.pse.beast.stringresource;

/**
 * Class that Manages all StringResources for the BooleanExpEditor
 *
 * @author Niels Hanselmann
 */
public class BooleanExpEditorStringResProvider extends StringResourceProvider {
    private static final String TOOLBAR_TIP       = "BooleanExpEditorToolbar";
    private static final String MENU              = "BooleanExpEditorMenu";
    private static final String BOOLEAN_EXP_ERROR = "BooleanExpEditorBooleanExpError";
    private static final String WINDOW            = "BooleanExpEditorWindow";
    private static final String SYMB_VAR_LIST     = "BooleanExpEditorSymbVarList";

    private StringResourceLoader menuStringRes;
    private StringResourceLoader toolbarTipStringRes;
    private StringResourceLoader booleanExpErrorStringRes;
    private StringResourceLoader booleanExpEditorWindowRes;
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
        return booleanExpEditorWindowRes;
    }

    /**
     * Initializes all attributes. Loads all StringResourceLoaders with the
     * filenames. The superclass provides the functions for this process
     */
    @Override
    protected final void initialize() {
        toolbarTipStringRes            = getStringResourceLoaderFromModuleName(TOOLBAR_TIP);
        menuStringRes                  = getStringResourceLoaderFromModuleName(MENU);
        booleanExpErrorStringRes       = getStringResourceLoaderFromModuleName(BOOLEAN_EXP_ERROR);
        booleanExpEditorWindowRes      = getStringResourceLoaderFromModuleName(WINDOW);
        booleanExpEditorSymbVarListRes = getStringResourceLoaderFromModuleName(SYMB_VAR_LIST);
    }
}
