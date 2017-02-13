/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

/**
 * This class manages all StringResProviders and therefore all acces to all
 * Strings the User gets to see. If you want to change the location of the
 * Stringfiles inside the Project you have to edit this class.
 *
 * @author Niels
 */
public class StringLoaderInterface {

    private static final String FILELOCATION = "";
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
        propListStr = new PropertyListStringResProvider(languageId, FILELOCATION);
        booleanExpStr = new BooleanExpEditorStringResProvider(languageId, FILELOCATION);
        cElecStr = new CElectionEditorStringResProvider(languageId, FILELOCATION);
        paramEdStr = new ParameterEditorStringResProvider(languageId, FILELOCATION);
        optionStr = new OptionStringResProvider(languageId, FILELOCATION);
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
