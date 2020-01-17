package edu.pse.beast.stringresource;

/**
 * This class manages all StringResProviders and therefore all access to all
 * Strings the user gets to see.
 *
 * @author Niels Hanselmann
 */
public class StringLoaderInterface {
    /** The prop list str. */
    private final PropertyListStringResProvider propListStr;

    /** The boolean exp str. */
    private final BooleanExpEditorStringResProvider booleanExpStr;

    /** The c elec str. */
    private final CElectionEditorStringResProvider cElecStr;

    /** The param ed str. */
    private final ParameterEditorStringResProvider paramEdStr;

    /** The option str. */
    private final OptionStringResProvider optionStr;

    /**
     * Instantiates a new string loader interface.
     *
     * @param languageId
     *            the languageId. choose "de" for german
     */
    public StringLoaderInterface(final String languageId) {
        propListStr = new PropertyListStringResProvider(languageId);
        booleanExpStr = new BooleanExpEditorStringResProvider(languageId);
        cElecStr = new CElectionEditorStringResProvider(languageId);
        paramEdStr = new ParameterEditorStringResProvider(languageId);
        optionStr = new OptionStringResProvider(languageId);
    }

    /**
     * Gets the property list string res provider.
     *
     * @return thePropertyListStringResProvider
     */
    public PropertyListStringResProvider getPropertyListStringResProvider() {
        return propListStr;
    }

    /**
     * Gets the boolean exp editor string res provider.
     *
     * @return the BooleanExpEditorStringResProvider
     */
    public BooleanExpEditorStringResProvider getBooleanExpEditorStringResProvider() {
        return booleanExpStr;
    }

    /**
     * Gets the c election editor string res provider.
     *
     * @return the CEledctionEditorStringResProvider
     */
    public CElectionEditorStringResProvider getCElectionEditorStringResProvider() {
        return cElecStr;
    }

    /**
     * Gets the parameter editor string res provider.
     *
     * @return the ParameterEditorStringResProvider
     */
    public ParameterEditorStringResProvider getParameterEditorStringResProvider() {
        return paramEdStr;
    }

    /**
     * Gets the option string res provider.
     *
     * @return the OptionStringResProvider
     */
    public OptionStringResProvider getOptionStringResProvider() {
        return optionStr;
    }

    /**
     * Sets the language.
     *
     * @param languageId
     *            sets language. Use "de" for german
     */
    public void setLanguage(final String languageId) {
        propListStr.changeLanguage(languageId);
        booleanExpStr.changeLanguage(languageId);
        cElecStr.changeLanguage(languageId);
        paramEdStr.changeLanguage(languageId);
        optionStr.changeLanguage(languageId);
    }
}
