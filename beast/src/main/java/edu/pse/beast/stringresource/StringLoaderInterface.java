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
    
    public StringLoaderInterface(String languageId) {
            propListStr = new PropertyListStringResProvider(languageId, "src/main/resources/stringfiles/");
    }
    public PropertyListStringResProvider getPropertyListStringResProvider() {
        return propListStr;
    }
    
}
