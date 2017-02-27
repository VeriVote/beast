/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.pse.beast.options;

import java.util.ArrayList;
import java.util.List;

/**
 * A parent class with which Options can be presented.
 * @author Lukas
 */
public abstract class Options {

    /**
     * the list of suboptions that are saved by this object
     */
    protected List<Options> subOptions = new ArrayList<Options>();
    
    /**
     * the option Elements that are saved by this object
     */
    protected List<OptionElement> optElements = new ArrayList<OptionElement>();
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
    public List<OptionElement> getOptionElements() {
        return optElements;
    }

    /**
     * 
     * @return the list of all subOptions.
     */
    public List<Options> getSubOptions() {
        return subOptions;
    }
    
    /**
     * 
     * @param elementToAdd the element to be added to the list
     */
    public void addOptionElement(OptionElement elementToAdd) {
        optElements.add(elementToAdd);
    }
    
    /**
     * 
     * @param optionToAdd the suboption to be added
     */
    public void addSubOptions(Options optionToAdd) {
        subOptions.add(optionToAdd);
    }

    /**
     * Gets called when an option got changed and reapplies it where
     * it is used.
     */
    public void reapply() {
        reapplySpecialized();
        for (Options opt : subOptions) opt.reapply();
    }
    
    /**
     * reapplies the specialized options
     */
    protected abstract void reapplySpecialized();

}
