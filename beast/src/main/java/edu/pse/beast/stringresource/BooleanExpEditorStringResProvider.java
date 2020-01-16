package edu.pse.beast.stringresource;

/**
 * Class that manages all StringResources for the BooleanExpEditor.
 *
 * @author Niels Hanselmann
 */
public class BooleanExpEditorStringResProvider extends StringResourceProvider {
    /** The Constant TOOLBAR_TIP. */
    private static final String TOOLBAR_TIP       = "BooleanExpEditorToolbar";

    /** The Constant MENU. */
    private static final String MENU              = "BooleanExpEditorMenu";

    /** The Constant BOOLEAN_EXP_ERROR. */
    private static final String BOOLEAN_EXP_ERROR = "BooleanExpEditorBooleanExpError";

    /** The Constant WINDOW. */
    private static final String WINDOW            = "BooleanExpEditorWindow";

    /** The Constant SYMB_VAR_LIST. */
    private static final String SYMB_VAR_LIST     = "BooleanExpEditorSymbVarList";

    /** The menu string res. */
    private StringResourceLoader menuStringRes;

    /** The toolbar tip string res. */
    private StringResourceLoader toolbarTipStringRes;

    /** The boolean exp error string res. */
    private StringResourceLoader booleanExpErrorStringRes;

    /** The boolean exp editor window res. */
    private StringResourceLoader booleanExpEditorWindowRes;

    /** The boolean exp editor symb var list res. */
    private StringResourceLoader booleanExpEditorSymbVarListRes;

    /**
     * Instantiates a new boolean exp editor string res provider.
     *
     * @param languageId the languageId. Set to "de" for german
     */
    public BooleanExpEditorStringResProvider(final String languageId) {
        super(languageId);
        this.initialize();
    }

    /**
     * Gets the menu string res.
     *
     * @return returns the MenuStringRes
     */
    public StringResourceLoader getMenuStringRes() {
        return menuStringRes;
    }

    /**
     * Gets the boolean exp editor symb var list res.
     *
     * @return returns the booleanExpEditorSymbVarListRes
     */
    public StringResourceLoader getBooleanExpEditorSymbVarListRes() {
        return booleanExpEditorSymbVarListRes;
    }

    /**
     * Gets the toolbar tip string res.
     *
     * @return returns the ToolbarStringRes
     */
    public StringResourceLoader getToolbarTipStringRes() {
        return toolbarTipStringRes;
    }

    /**
     * Gets the boolean exp error string res.
     *
     * @return returns the BooleanExpErrorStringRes
     */
    public StringResourceLoader getBooleanExpErrorStringRes() {
        return booleanExpErrorStringRes;
    }

    /**
     * Gets the boolean exp editor window string res.
     *
     * @return the BooleanExpEditorWindowStringRes
     */
    public StringResourceLoader getBooleanExpEditorWindowStringRes() {
        return booleanExpEditorWindowRes;
    }

    // Initializes all attributes. Loads all StringResourceLoaders with the
    // filenames. The superclass provides the functions for this process
    @Override
    protected final void initialize() {
        toolbarTipStringRes            = getStringResourceLoaderFromModuleName(TOOLBAR_TIP);
        menuStringRes                  = getStringResourceLoaderFromModuleName(MENU);
        booleanExpErrorStringRes       = getStringResourceLoaderFromModuleName(BOOLEAN_EXP_ERROR);
        booleanExpEditorWindowRes      = getStringResourceLoaderFromModuleName(WINDOW);
        booleanExpEditorSymbVarListRes = getStringResourceLoaderFromModuleName(SYMB_VAR_LIST);
    }
}
