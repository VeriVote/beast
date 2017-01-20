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

    private PropertyListStringResProvider propListStr;
    private BooleanExpEditorStringResProvider booleanExpStr;
    private CElectionEditorStringResProvider cElecStr;
    private ParameterEditorStringResProvider paramEdStr;
    private OptionStringResProvider optionStr;
    private final String fileLocation = "src/main/resources/stringfiles/";

    public StringLoaderInterface(String languageId) {
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

}
