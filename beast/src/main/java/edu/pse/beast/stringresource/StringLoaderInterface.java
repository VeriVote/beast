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

    private static final String FILELOCATION = "src/resources/stringfiles/";
    private String languageId;
    private final PropertyListStringResProvider propListStr;
    private final BooleanExpEditorStringResProvider booleanExpStr;
    private final CElectionEditorStringResProvider cElecStr;
    private final ParameterEditorStringResProvider paramEdStr;
    private final OptionStringResProvider optionStr;

    public StringLoaderInterface(String languageId) {
        this.languageId = languageId;
        propListStr = new PropertyListStringResProvider(languageId, FILELOCATION);
        booleanExpStr = new BooleanExpEditorStringResProvider(languageId, FILELOCATION);
        cElecStr = new CElectionEditorStringResProvider(languageId, FILELOCATION);
        paramEdStr = new ParameterEditorStringResProvider(languageId, FILELOCATION);
        optionStr = new OptionStringResProvider(languageId, FILELOCATION);
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
