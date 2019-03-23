package edu.pse.beast.stringresource;

/**
 * This class manages all StringResProviders and therefore all acces to all
 * Strings the User gets to see.
 *
 * @author Niels Hanselmann
 */
public class StringLoaderInterface {

    private final PropertyListStringResProvider propListStr;
    private final BooleanExpEditorStringResProvider booleanExpStr;
    private final CElectionEditorStringResProvider cElecStr;
    private final ParameterEditorStringResProvider paramEdStr;
    private final OptionStringResProvider optionStr;

    /**
     *
     * @param languageId the languageId. choose "de" for german
     */
    public StringLoaderInterface(String languageId) {
        propListStr = new PropertyListStringResProvider(languageId);
        booleanExpStr = new BooleanExpEditorStringResProvider(languageId);
        cElecStr = new CElectionEditorStringResProvider(languageId);
        paramEdStr = new ParameterEditorStringResProvider(languageId);
        optionStr = new OptionStringResProvider(languageId);
    }

    /**
     *
     * @return thePropertyListStringResProvider
     */
    public PropertyListStringResProvider getPropertyListStringResProvider() {
        return propListStr;
    }

    /**
     *
     * @return the BooleanExpEditorStringResProvider
     */
    public BooleanExpEditorStringResProvider getBooleanExpEditorStringResProvider() {
        return booleanExpStr;
    }

    /**
     *
     * @return the CEledctionEditorStringResProvider
     */
    public CElectionEditorStringResProvider getCElectionEditorStringResProvider() {
        return cElecStr;
    }

    /**
     *
     * @return the ParameterEditorStringResProvider
     */
    public ParameterEditorStringResProvider getParameterEditorStringResProvider() {
        return paramEdStr;
    }

    /**
     *
     * @return the OptionStringResProvider
     */
    public OptionStringResProvider getOptionStringResProvider() {
        return optionStr;
    }

    /**
     *
     * @param languageId sets language. Use "de" for german
     */
    public void setLanguage(String languageId) {
        propListStr.changeLanguage(languageId);
        booleanExpStr.changeLanguage(languageId);
        cElecStr.changeLanguage(languageId);
        paramEdStr.changeLanguage(languageId);
        optionStr.changeLanguage(languageId);
    }
}
