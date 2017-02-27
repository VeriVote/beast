package edu.pse.beast.options;

import java.util.List;

/**
 * A parent class representing an Option.
 * @author Lukas
 */
public abstract class OptionElement {
    
    /**
     * the chosen option
     */
    protected String chosenOption;
    private String id;
    private List<String> choosableOptions;
    /**
     * 
     * @param id The ID of this OptionElement
     * @param choosableOptions A List of Strings with the choosableOptions
     */
    public OptionElement(String id, List<String> choosableOptions) {
        this.id = id;
        this.choosableOptions = choosableOptions;
    }

    /**
     * 
     * @return the ID of this OptionElement
     */
    public String getID() {
        return id;
    }

    /**
     * 
     * @return the List of the ChoosableOptions
     */
    public List<String> getChoosableOptions() {
        return choosableOptions;
    }

    /**
     * Handles the selection
     * @param selection identifies the selection
     */
    public abstract void handleSelection(String selection);

    /**
     * 
     * @return the chosen option
     */
    public String getChosenOption() {
        return chosenOption;
    }
}
