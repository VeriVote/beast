/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.stringresource;

/**
 *
 * @author Niels
 */
public class StringLoaderInterface {

    private String languageId;
    private final PropertyListStringResProvider propListStr;
    private final BooleanExpEditorStringResProvider booleanExpStr;
    private final CElectionEditorStringResProvider cElecStr;
    private final ParameterEditorStringResProvider paramEdStr;
    private final OptionStringResProvider optionStr;
    private final String fileLocation = "src/main/resources/stringfiles/";

    public StringLoaderInterface(String languageId) {
        this.languageId = languageId;
        propListStr = new PropertyListStringResProvider(languageId, fileLocation);
        booleanExpStr = new BooleanExpEditorStringResProvider(languageId, fileLocation);
        cElecStr = new CElectionEditorStringResProvider(languageId, fileLocation);
        paramEdStr = new ParameterEditorStringResProvider(languageId, fileLocation);
        optionStr = new OptionStringResProvider(languageId, fileLocation);
    }

    public PropertyListStringResProvider getPropertyListStringResProvider() {
        return propListStr;
    }

    public BooleanExpEditorStringResProvider getBooleanExpEditorStringResProvider() {
        return booleanExpStr;
    }

    public CElectionEditorStringResProvider getCElectionEditorStringResProvider() {
        return cElecStr;
    }

    public ParameterEditorStringResProvider getParameterEditorStringResProvider() {
        return paramEdStr;
    }

    public OptionStringResProvider getOptionStringResProvider() {
        return optionStr;
    }

    public void setLanguage(String languageId) {
        this.languageId = languageId;
        propListStr.changeLanguage(languageId);
        booleanExpStr.changeLanguage(languageId);
        cElecStr.changeLanguage(languageId);
        paramEdStr.changeLanguage(languageId);
        optionStr.changeLanguage(languageId);
    }
}
