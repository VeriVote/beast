/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.toolbox;

import edu.pse.beast.saverloader.SaverLoaderInterface;
import edu.pse.beast.stringresource.StringLoaderInterface;
import edu.pse.beast.options.LanguageOptions;
import edu.pse.beast.options.OptionsInterface;

/**
 * This holds references to all interfaces needed by the several builder classes 
 * in this project. These references are:
 * OptionsInterface
 * StringLoaderInterface
 * SaverLoaderInterface
 * @author Holger-Desktop & Lukas
 */
public class ObjectRefsForBuilder {

    private final OptionsInterface optionsIF;
    private final StringLoaderInterface stringIF;
    private final LanguageOptions languageOpts;
    private final SaverLoaderInterface saverLoaderIF;
    
    /**
     * 
     * @param optionsIF the optionsInterface
     * @param stringIF the Stringloaderinterface
     * @param languageOpts this languageoptionsinterface
     * @param saverLoaderIF the saverloaderinterface
     */
    public ObjectRefsForBuilder(OptionsInterface optionsIF, StringLoaderInterface stringIF,
            LanguageOptions languageOpts, SaverLoaderInterface saverLoaderIF) {
        this.optionsIF = optionsIF;
        this.stringIF = stringIF;
        this.languageOpts = languageOpts;
        this.saverLoaderIF = saverLoaderIF;
    }
    
    /**
     * 
     * @return the optionsInterface
     */
    public OptionsInterface getOptionIF() {
        return optionsIF;
    }
    
    /**
     * 
     * @return the stringloaderinterface
     */
    public StringLoaderInterface getStringIF() {
        return stringIF;
    }
    
    /**
     * 
     * @return the languageOptionsInterface
     */
    public LanguageOptions getLanguageOpts() {
        return languageOpts;
    }
    
    /**
     * 
     * @return the saverloaderinterface
     */
    public SaverLoaderInterface getSaverLoaderIF() {
        return saverLoaderIF;
    }
    
}
