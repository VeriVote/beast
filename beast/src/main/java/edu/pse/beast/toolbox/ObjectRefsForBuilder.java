/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.stringresource.StringLoaderInterface;

/**
 * This holds references to all interfaces needed by the several builder classes 
 * in this project. These references are:
 * OptionsInterface
 * StringLoaderInterface
 * SaverLoaderInterface
 * @author Holger-Desktop
 */
public class ObjectRefsForBuilder {

    private StringLoaderInterface stringIF;
    
    public ObjectRefsForBuilder(StringLoaderInterface stringIF) {
        this.stringIF = stringIF; 
    }
    
    /**
     * Temporary method declaration to test other classes
     * @return null
     */
    public StringLoaderInterface getStringIF() {
        return stringIF;
    }
}
