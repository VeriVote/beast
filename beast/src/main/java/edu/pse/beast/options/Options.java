package edu.pse.beast.options;

import java.util.ArrayList;
import java.util.List;

/**
 * A parent class with which Options can be presented.
 *
 * @author Lukas Stapelbroek
 */
public abstract class Options {

    /**
     * the list of suboptions that are saved by this object
     */
    private List<Options> subOptions = new ArrayList<>();

    /**
     * the option elements that are saved by this object
     */
    private List<OptionElement> optElements = new ArrayList<>();
    private final String id;

    /**
     * creates a new Options object
     *
     * @param id the ID of this object
     */
    public Options(String id) {
        this.id = id;
    }

    /**
     * Getter for the ID
     *
     * @return the ID of this object
     */
    public String getId() {
        return id;
    }

    /**
     * Getter for the OptionElements
     *
     * @return the List of OptionElements
     */
    public List<OptionElement> getOptionElements() {
        return optElements;
    }

    /**
     * Getter for suboptions
     *
     * @return the list of all subOptions.
     */
    public List<Options> getSubOptions() {
        return subOptions;
    }

    /**
     * Adds an OptionElement to the OptionElement list
     *
     * @param elementToAdd the element to be added to the list
     */
    public void addOptionElement(OptionElement elementToAdd) {
        optElements.add(elementToAdd);
    }

    /**
     * Adds a set of suboptions to the suboptions list
     *
     * @param optionToAdd the suboption to be added
     */
    public void addSubOptions(Options optionToAdd) {
        subOptions.add(optionToAdd);
    }

    /**
     * Gets called when an option got changed and re-applies it where it is used.
     */
    public void reapply() {
        reapplySpecialized();
        for (Options opt : subOptions) {
            opt.reapply();
        }
    }

    /**
     * re-applies the specialized options
     */
    protected abstract void reapplySpecialized();
}