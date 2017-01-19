/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.options;

import java.util.List;

/**
 *
 * @author Lukas
 */
public abstract class Options {

    private final String id;

    /**
     * creates a new Options object
     * @param id the ID of this object
     */
    public Options(String id) {
        this.id = id;
    }

    /**
     * 
     * @return the ID of this object
     */
    public String getId() {
        return id;
    }

    /**
     * 
     * @return the List of OptionElements
     */
    public abstract List<OptionElement> getOptionElements();

    /**
     * 
     * @return the list of all subOptions.
     */
    public abstract List<Options> getSubOptions();

    /**
     * Gets called when an option got changed and reapplies it where
     * it is used.
     */
    public abstract void reapply();

}
